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
    /** A dependency points from the directory that imports to the directory imported. */
    public record Edge(String source, String target, String relation) {}
    /** Pure filesystem containment, never a source dependency. */
    public record FolderLink(String parent, String child) {}
    /** Read-only evidence explaining why a directory dependency is shown. */
    public record FileLink(String source, String target, String relation) {}
    public record Graph(String folder, List<String> nodes, List<Edge> edges,
                        int inspected, boolean truncated, String fingerprint,
                        Map<String, List<String>> filesByFolder,
                        List<FolderLink> hierarchy,
                        Map<Edge, List<FileLink>> evidence,
                        Map<String, List<String>> hiddenSubfoldersByFolder) {
        /** Kept for existing graph/layout tests and older in-memory callers. */
        public Graph(String folder, List<String> nodes, List<Edge> edges,
                     int inspected, boolean truncated, String fingerprint) {
            this(folder, nodes, edges, inspected, truncated, fingerprint,
                    Map.of(), List.of(), Map.of(), Map.of());
        }

        /** Compatibility for existing tests and in-memory graph callers. */
        public Graph(String folder, List<String> nodes, List<Edge> edges,
                     int inspected, boolean truncated, String fingerprint,
                     Map<String, List<String>> filesByFolder,
                     List<FolderLink> hierarchy,
                     Map<Edge, List<FileLink>> evidence) {
            this(folder, nodes, edges, inspected, truncated, fingerprint,
                    filesByFolder, hierarchy, evidence, Map.of());
        }

        public Graph {
            nodes = List.copyOf(nodes);
            edges = List.copyOf(edges);
            hierarchy = List.copyOf(hierarchy);
            Map<String, List<String>> files = new TreeMap<>();
            filesByFolder.forEach((key, value) -> files.put(key, List.copyOf(value)));
            filesByFolder = Map.copyOf(files);
            Map<Edge, List<FileLink>> sources = new HashMap<>();
            evidence.forEach((key, value) -> sources.put(key, List.copyOf(value)));
            evidence = Map.copyOf(sources);
            Map<String, List<String>> hidden = new TreeMap<>();
            hiddenSubfoldersByFolder.forEach((key, value) ->
                    hidden.put(key, List.copyOf(value)));
            hiddenSubfoldersByFolder = Map.copyOf(hidden);
        }

        public List<String> files(String directory) {
            return filesByFolder.getOrDefault(directory, List.of());
        }

        public List<String> subfolders(String directory) {
            return hierarchy.stream().filter(link -> link.parent().equals(directory))
                    .map(FolderLink::child).sorted().toList();
        }

        /** Actual direct children omitted from the bounded visual map. */
        public List<String> hiddenSubfolders(String directory) {
            return hiddenSubfoldersByFolder.getOrDefault(directory, List.of());
        }
    }

    private record ImportRef(String target, boolean dotted, String kind) {}
    private static final Set<String> CODE = Set.of("java", "kt", "kts", "py",
            "js", "jsx", "ts", "tsx", "c", "cpp", "h", "hpp", "cc", "hh",
            "cs", "go", "rb", "ps1", "psm1", "sh", "bash", "cmd", "bat",
            "asm", "nasm", "pyw", "csx", "psd1", "zsh");
    private static final List<String> ENDINGS = List.of("", ".java", ".kt",
            ".kts", ".py", ".ts", ".tsx", ".js", ".jsx", ".h", ".hpp",
            ".c", ".cpp", ".cs", ".rb", ".sh", ".ps1", ".psm1", ".cmd",
            ".bat", "/__init__.py", "/index.ts", "/index.js");
    private static final int MAX_SOURCES = 1200;
    private static final int MAX_NODES = 180;
    private static final int MAX_EDGES = 400;
    private static final int MAX_EVIDENCE = 8;
    private ProjectDependencyGraph() {}

    /**
     * Read-only, bounded diagram of the selected folder and its descendants.
     * Every node is a real directory, including ancestors containing no source
     * directly. Dashed containment links are separate from resolved source
     * dependencies; neither requires compiling or executing project code.
     */
    public static Graph analyze(Path project, Path selected, BooleanSupplier cancelled)
            throws IOException {
        Path root = project.toRealPath(), folder = selected.toRealPath();
        if (!Files.isDirectory(folder) || !folder.startsWith(root))
            throw new IOException("Selecciona una carpeta de la referencia actual.");

        List<Path> source = new ArrayList<>();
        boolean[] limited = {false};
        Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) {
                if (cancelled.getAsBoolean()) {
                    limited[0] = true;
                    return FileVisitResult.TERMINATE;
                }
                if (!dir.equals(folder) && (Files.isSymbolicLink(dir)
                        || ReferenceScanRules.generatedName(dir.getFileName().toString())))
                    return FileVisitResult.SKIP_SUBTREE;
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) {
                if (cancelled.getAsBoolean()) {
                    limited[0] = true;
                    return FileVisitResult.TERMINATE;
                }
                if (attrs.isRegularFile() && !Files.isSymbolicLink(file)
                        && CODE.contains(ext(file))) {
                    if (source.size() >= MAX_SOURCES) {
                        limited[0] = true;
                        return FileVisitResult.TERMINATE;
                    }
                    source.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(Path file, IOException ex) {
                return FileVisitResult.CONTINUE;
            }
        });
        source.sort(Comparator.comparing(p -> relative(root, p)));
        String selectedId = folderId(relative(root, folder));
        Set<String> known = new HashSet<>();
        Set<String> folders = new TreeSet<>();
        Map<String, List<String>> directFiles = new TreeMap<>();
        folders.add(selectedId);
        for (Path file : source) {
            String relative = relative(root, file);
            known.add(relative);
            String parent = folderId(relative(root, file.getParent()));
            directFiles.computeIfAbsent(parent, ignored -> new ArrayList<>()).add(relative);
            String current = parent;
            while (folders.add(current) && !current.equals(selectedId)) {
                current = parentOf(current);
            }
        }
        ResolutionIndex resolution = new ResolutionIndex(known);
        String goModule = "";
        try {
            Matcher m = Pattern.compile("(?m)^\\s*module\\s+(\\S+)")
                    .matcher(Files.readString(root.resolve("go.mod")));
            if (m.find()) goModule = m.group(1);
        } catch (IOException ignored) {}

        Map<Edge, List<FileLink>> evidence = new LinkedHashMap<>();
        MessageDigest signature;
        try {
            signature = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
        signature.update("folder-map-v1".getBytes(StandardCharsets.UTF_8));
        signature.update(goModule.getBytes(StandardCharsets.UTF_8));
        for (Path file : source) {
            if (cancelled.getAsBoolean()) {
                limited[0] = true;
                break;
            }
            String from = relative(root, file);
            signature.update(from.getBytes(StandardCharsets.UTF_8));
            signature.update((byte) 0);
            TextPreview.Result data;
            try { data = TextPreview.load(file); }
            catch (IOException | SecurityException error) { continue; }
            if (!data.readable() || data.content().length() > 500_000) continue;
            signature.update(data.content().getBytes(StandardCharsets.UTF_8));
            signature.update((byte) 0);
            for (ImportRef imp : parse(file, data.content())) {
                String target = resolve(root, file, resolution, imp, goModule);
                if (target == null || target.equals(from)) continue;
                String fromDirectory = folderId(relative(root, file.getParent()));
                String targetDirectory = known.contains(target)
                        ? parentOf(target) : folderId(target); // Go package imports.
                if (fromDirectory.equals(targetDirectory)
                        || !folders.contains(targetDirectory)) continue;
                Edge edge = new Edge(fromDirectory, targetDirectory, "dependency");
                List<FileLink> uses = evidence.computeIfAbsent(edge,
                        ignored -> new ArrayList<>());
                if (uses.size() < MAX_EVIDENCE) {
                    FileLink link = new FileLink(from, target, imp.kind());
                    if (!uses.contains(link)) uses.add(link);
                }
            }
        }

        // Breadth-first selection preserves every visible node's parent.
        Map<String, List<String>> children = new TreeMap<>();
        for (String dir : folders) {
            if (!dir.equals(selectedId))
                children.computeIfAbsent(parentOf(dir), ignored -> new ArrayList<>()).add(dir);
        }
        children.values().forEach(list -> list.sort(Comparator.naturalOrder()));
        List<String> visibleNodes = new ArrayList<>();
        ArrayDeque<String> pending = new ArrayDeque<>();
        pending.add(selectedId);
        while (!pending.isEmpty() && visibleNodes.size() < MAX_NODES) {
            String dir = pending.removeFirst();
            visibleNodes.add(dir);
            pending.addAll(children.getOrDefault(dir, List.of()));
        }
        Set<String> visibleSet = Set.copyOf(visibleNodes);
        Map<String, List<String>> hiddenChildren = new TreeMap<>();
        for (String dir : visibleNodes) {
            List<String> omitted = children.getOrDefault(dir, List.of()).stream()
                    .filter(child -> !visibleSet.contains(child)).toList();
            if (!omitted.isEmpty()) hiddenChildren.put(dir, omitted);
        }
        List<FolderLink> hierarchy = new ArrayList<>();
        for (String dir : visibleNodes) {
            if (!dir.equals(selectedId))
                hierarchy.add(new FolderLink(parentOf(dir), dir));
        }
        List<Edge> visibleEdges = evidence.keySet().stream()
                .filter(edge -> visibleSet.contains(edge.source())
                        && visibleSet.contains(edge.target()))
                .sorted(Comparator.comparing(Edge::source).thenComparing(Edge::target))
                .limit(MAX_EDGES).toList();
        Map<Edge, List<FileLink>> visibleEvidence = new LinkedHashMap<>();
        for (Edge edge : visibleEdges) visibleEvidence.put(edge, evidence.get(edge));
        Map<String, List<String>> visibleFiles = new TreeMap<>();
        for (String dir : visibleNodes)
            visibleFiles.put(dir, directFiles.getOrDefault(dir, List.of()));
        boolean truncated = limited[0] || folders.size() > MAX_NODES
                || evidence.size() > visibleEdges.size();
        return new Graph(relative(root, folder), visibleNodes, visibleEdges,
                source.size(), truncated, HexFormat.of().formatHex(signature.digest()),
                visibleFiles, hierarchy, visibleEvidence, hiddenChildren);
    }

    private static String folderId(String path) {
        return path.isEmpty() ? "." : path;
    }

    private static String parentOf(String relative) {
        if (relative.equals(".")) return ".";
        int slash = relative.lastIndexOf('/');
        return slash < 0 ? "." : relative.substring(0, slash);
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
            case "sh", "bash", "zsh", "ps1", "psm1" -> {
                grab(text, "(?m)^\\s*source\\s+['\"]?([.][^\\s'\";]+)",
                        false, "source", refs);
                grab(text, "(?m)^\\s*\\.\\s+['\"]?([.][^\\s'\";]+)",
                        false, "source", refs);
                grab(text, "(?m)^\\s*Import-Module\\s+['\"]?([.][^\\s'\";]+)",
                        false, "Import-Module", refs);
                if (ext(file).equals("ps1") || ext(file).equals("psm1")) {
                    // $PSScriptRoot is the one safe built-in directory variable
                    // that can be resolved without evaluating PowerShell.
                    grab(text, "(?im)^\\s*\\.\\s+[\"']?(\\$PSScriptRoot[/\\\\][^\\s\"';]+)",
                            false, "source", refs);
                }
            }
            case "asm", "nasm" ->
                    grab(text, "(?im)^\\s*%include\\s+[\"']([^\"']+)[\"']",
                            false, "include", refs);
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
        // Only well-defined source-directory macros, never arbitrary variables.
        if (requested.regionMatches(true, 0, "$PSScriptRoot/", 0,
                "$PSScriptRoot/".length()))
            requested = "./" + requested.substring("$PSScriptRoot/".length());
        if (requested.regionMatches(true, 0, "%~dp0", 0, 5))
            requested = "./" + requested.substring(5);
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
