package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.regex.*;

/**
 * Scope-limited, read-only importer. Only links that can be resolved to
 * actual local files/folders are shown; no false external dependency nodes.
  * <p><strong>Engineering rationale.</strong> A dependency map is an exploratory approximation, not a compiler, resolver or proof that imports are valid. Keep traversal bounded, resolve relationships only to known local files, and separate graph extraction from the Canvas-based interaction and layout algorithm.</p>
*/
public final class ProjectDependencyGraph {
    public record Edge(String source, String target, String relation) {}
    public record Graph(String folder, List<String> nodes, List<Edge> edges,
                        int inspected, boolean truncated, String fingerprint) {}
    private record ImportRef(String target, boolean dotted, String kind) {}
    private static final Set<String> CODE = Set.of("java", "kt", "kts", "py",
            "js", "jsx", "ts", "tsx", "c", "cpp", "h", "hpp", "cc", "hh",
            "cs", "go", "rb", "ps1", "psm1", "sh", "bash", "cmd", "bat");
    private static final List<String> ENDINGS = List.of("", ".java", ".kt",
            ".kts", ".py", ".ts", ".tsx", ".js", ".jsx", ".h", ".hpp",
            ".c", ".cpp", ".cs", ".rb", ".sh", ".ps1", ".psm1", ".cmd",
            ".bat", "/__init__.py", "/index.ts", "/index.js");
    private ProjectDependencyGraph() {}

    public static Graph analyze(Path project, Path selected, BooleanSupplier cancelled)
            throws IOException {
        Path root = project.toRealPath(), folder = selected.toRealPath();
        if (!Files.isDirectory(folder) || !folder.startsWith(root))
            throw new IOException("Selecciona una carpeta de la referencia actual.");
        List<Path> source = new ArrayList<>();
        boolean[] limit = {false};
        Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) {
                if (cancelled.getAsBoolean()) return FileVisitResult.TERMINATE;
                if (!dir.equals(folder) && (Files.isSymbolicLink(dir)
                        || ReferenceScanRules.generatedName(dir.getFileName().toString())))
                    return FileVisitResult.SKIP_SUBTREE;
                return FileVisitResult.CONTINUE;
            }
            @Override public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) {
                if (cancelled.getAsBoolean()) return FileVisitResult.TERMINATE;
                if (attrs.isRegularFile() && !Files.isSymbolicLink(file)
                        && CODE.contains(ext(file))) {
                    if (source.size() >= 1200) { limit[0] = true; return FileVisitResult.TERMINATE; }
                    source.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
            @Override public FileVisitResult visitFileFailed(Path file, IOException ex) {
                return FileVisitResult.CONTINUE;
            }
        });
        source.sort(Comparator.comparing(p -> relative(root, p)));
        Set<String> known = new HashSet<>();
        for (Path file : source) known.add(relative(root, file));
        // Bounded, per-analysis index: spending a little RAM saves repeated scans.
        ResolutionIndex resolution = new ResolutionIndex(known);
        String goModule = "";
        try {
            Matcher m = Pattern.compile("(?m)^\\s*module\\s+(\\S+)")
                    .matcher(Files.readString(root.resolve("go.mod")));
            if (m.find()) goModule = m.group(1);
        } catch (IOException ignored) {}
        Set<Edge> edges = new LinkedHashSet<>();
        MessageDigest signature;
        try {
            signature = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
        signature.update(goModule.getBytes(StandardCharsets.UTF_8));
        for (Path file : source) {
            if (cancelled.getAsBoolean() || edges.size() >= 1200) {
                limit[0] = true; break;
            }
            TextPreview.Result data;
            try { data = TextPreview.load(file); }
            catch (IOException | SecurityException error) { continue; }
            if (!data.readable() || data.content().length() > 500_000) continue;
            String from = relative(root, file);
            signature.update(from.getBytes(StandardCharsets.UTF_8));
            signature.update((byte) 0);
            signature.update(data.content().getBytes(StandardCharsets.UTF_8));
            signature.update((byte) 0);
            for (ImportRef imp : parse(file, data.content())) {
                String target = resolve(root, file, resolution, imp, goModule);
                if (target != null && !target.equals(from))
                    edges.add(new Edge(from, target, imp.kind()));
                if (cancelled.getAsBoolean() || edges.size() >= 1200) break;
            }
        }
        Set<String> allNodes = new TreeSet<>();
        for (Edge edge : edges) {
            allNodes.add(edge.source());
            allNodes.add(edge.target());
        }
        // A JavaFX Canvas cannot safely render tens of thousands of pixels
        // high on every GPU. Keep the most connected 180 nodes visible and
        // disclose truncation instead of silently exhausting graphics memory.
        Map<String, Integer> degree = new HashMap<>();
        for (Edge edge : edges) {
            degree.merge(edge.source(), 1, Integer::sum);
            degree.merge(edge.target(), 1, Integer::sum);
        }
        List<String> ranked = allNodes.stream().sorted(
                Comparator.<String>comparingInt(p -> degree.getOrDefault(p, 0))
                        .reversed().thenComparing(Comparator.naturalOrder())).toList();
        Set<String> display = new HashSet<>(ranked.subList(0,
                Math.min(180, ranked.size())));
        List<Edge> visible = edges.stream()
                .filter(edge -> display.contains(edge.source())
                        && display.contains(edge.target()))
                .sorted(Comparator.comparing(Edge::source).thenComparing(Edge::target))
                .limit(400).toList();
        boolean partial = limit[0] || allNodes.size() > 180 || edges.size() > 400;
        List<String> nodes = display.stream().sorted().toList();
        return new Graph(relative(root, folder), nodes, visible, source.size(), partial,
                HexFormat.of().formatHex(signature.digest()));
    }

    private static String ext(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        int dot = name.lastIndexOf('.');
        return dot < 0 ? "" : name.substring(dot + 1);
    }
    private static String relative(Path root, Path file) {
        return root.relativize(file).toString().replace('\\', '/');
    }
    private static void grab(String source, String expression, boolean dotted,
                             String label, List<ImportRef> out) {
        Matcher found = Pattern.compile(expression).matcher(source);
        while (found.find()) out.add(new ImportRef(found.group(1).trim(), dotted, label));
    }
    private static List<ImportRef> parse(Path file, String text) {
        List<ImportRef> refs = new ArrayList<>();
        switch (ext(file)) {
            case "java", "kt", "kts", "cs" ->
                    grab(text, "(?m)^\\s*(?:import|using)\\s+(?:static\\s+)?([\\w.]+)",
                            true, "import", refs);
            case "py" -> {
                grab(text, "(?m)^\\s*import\\s+([\\w.]+)", true, "import", refs);
                grab(text, "(?m)^\\s*from\\s+([\\w.]+)\\s+import", true, "from", refs);
            }
            case "js", "jsx", "ts", "tsx" -> {
                grab(text, "\\bfrom\\s*['\"]([^'\"]+)['\"]", false, "import", refs);
                grab(text, "\\bimport\\s*['\"]([^'\"]+)['\"]", false, "import", refs);
                grab(text, "\\b(?:require|import)\\s*\\(\\s*['\"]([^'\"]+)['\"]",
                        false, "require/import", refs);
            }
            case "c", "cpp", "cc", "h", "hpp", "hh" ->
                    grab(text, "(?m)^\\s*#\\s*include\\s*\"([^\"]+)\"",
                            false, "include", refs);
            case "rb" ->
                    grab(text, "\\brequire_relative\\s*\\(?\\s*['\"]([^'\"]+)['\"]",
                            false, "require_relative", refs);
            case "sh", "bash", "ps1", "psm1" -> {
                grab(text, "(?m)^\\s*source\\s+['\"]?([.][^\\s'\";]+)",
                        false, "source", refs);
                grab(text, "(?m)^\\s*\\.\\s+['\"]?([.][^\\s'\";]+)",
                        false, "source", refs);
                grab(text, "(?m)^\\s*Import-Module\\s+['\"]?([.][^\\s'\";]+)",
                        false, "Import-Module", refs);
            }
            case "cmd", "bat" ->
                    grab(text, "(?im)^\\s*call\\s+\"?([^\"\\r\\n]+\\.(?:cmd|bat))",
                            false, "call", refs);
            case "go" -> {
                Matcher block = Pattern.compile("(?m)^\\s*import\\s*(?:\\(([\\s\\S]*?)\\)|\"([^\"]+)\")")
                        .matcher(text);
                while (block.find()) {
                    if (block.group(2) != null)
                        refs.add(new ImportRef(block.group(2), false, "go-import"));
                    else {
                        Matcher entries = Pattern.compile("\"([^\"]+)\"")
                                .matcher(block.group(1));
                        while (entries.find())
                            refs.add(new ImportRef(entries.group(1), false, "go-import"));
                    }
                }
            }
            default -> {}
        }
        return refs;
    }
    private static String resolve(Path root, Path from, ResolutionIndex index,
                                  ImportRef ref, String goModule) {
        String requested = ref.target().replace('\\', '/').strip();
        if (requested.isEmpty() || requested.contains("$" + "{")
                || requested.contains("$(")) return null;
        if (ref.kind().equals("go-import")) {
            if (goModule.isEmpty() || !requested.startsWith(goModule + "/")) return null;
            String dir = requested.substring(goModule.length() + 1);
            return index.goPackages.contains(dir) ? dir : null; // Go imports packages.
        }
        if (ref.dotted()) return findUnique(index, requested.replace('.', '/'));
        if (!ref.kind().equals("include") && !ref.kind().equals("call")
                && !requested.startsWith(".")) return null;
        Path target;
        try { target = from.getParent().resolve(requested).normalize(); }
        catch (RuntimeException error) { return null; }
        if (!target.startsWith(root)) return null;
        String relative = relative(root, target);
        for (String extension : ENDINGS) {
            if (index.known.contains(relative + extension)) return relative + extension;
        }
        // For #include "common.h" inside a project-wide include/ folder.
        if (!requested.contains("/")) return findUnique(index, requested);
        return null;
    }
    private static String findUnique(ResolutionIndex index, String candidate) {
        String result = null;
        for (String ending : ENDINGS) {
            for (String path : index.suffixes.getOrDefault(candidate + ending, Set.of())) {
                // Ambiguous source files must not generate invented dependencies.
                if (result != null && !result.equals(path)) return null;
                result = path;
            }
        }
        return result;
    }

    /**
     * The graph caps sources at 1,200. Precompute relative path suffixes in
     * O(n * depth) space/time so resolving imports avoids O(n) linear scans.
     * This index is discarded after each analysis, preventing stale lookups.
     */
    private static final class ResolutionIndex {
        private final Set<String> known;
        private final Map<String, Set<String>> suffixes = new HashMap<>();
        private final Set<String> goPackages = new HashSet<>();

        private ResolutionIndex(Set<String> known) {
            this.known = known;
            for (String path : known) {
                String suffix = path;
                while (true) {
                    suffixes.computeIfAbsent(suffix, ignored -> new HashSet<>()).add(path);
                    int slash = suffix.indexOf('/');
                    if (slash < 0) break;
                    suffix = suffix.substring(slash + 1);
                }
                if (path.endsWith(".go")) {
                    int slash = path.lastIndexOf('/');
                    while (slash > 0) {
                        goPackages.add(path.substring(0, slash));
                        slash = path.lastIndexOf('/', slash - 1);
                    }
                }
            }
        }
    }
}
