package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Stateless read-only text search. No persistent index, file modifications,
 * progress changes or new virtual entries.
 */
public final class ProjectTextSearch {
    private static final Set<String> GENERATED = Set.of(
            ".git", ".idea", ".local", "node_modules", "target", "build",
            "dist", "reports", "__pycache__", ".venv");
    private static final int MAX_PATTERN = 256;
    private static final int MAX_HITS = 1500;
    private static final int MAX_LINE = 4096;
    private ProjectTextSearch() {}

    public record Options(String expression, boolean regex, boolean caseSensitive,
                          boolean wholeWords) {
        public Options {
            if (expression == null || expression.isBlank()
                    || expression.length() > MAX_PATTERN)
                throw new IllegalArgumentException("Introduce una búsqueda de hasta 256 caracteres.");
        }
    }

    /** 1-based line/column, with the original relative path and a short excerpt. */
    public record Hit(String relative, int line, int column, int length, String excerpt) {
        /** Canonical 1-based location for navigation in any source pane. */
        public SourceRange range() { return new SourceRange(line, column, length); }
    }

    /** Results may be incomplete when the display cap or line-length guard is reached. */
    public record Result(List<Hit> hits, boolean capped, int skippedLongLines) {
        public Result {
            hits = List.copyOf(hits);
            if (skippedLongLines < 0) throw new IllegalArgumentException("Negative skips");
        }
        public boolean partial() { return capped || skippedLongLines > 0; }
    }

    /** Compatible convenience API for programmatic callers. */
    public static List<Hit> search(Path root, String oneFile, Options options,
                                   BooleanSupplier cancelled) throws IOException {
        return searchWithReport(root, oneFile, options, cancelled).hits();
    }

    public static Result searchWithReport(Path root, String oneFile, Options options,
                                          BooleanSupplier cancelled) throws IOException {
        if (root == null || options == null || cancelled == null)
            throw new IllegalArgumentException("La búsqueda necesita una referencia.");
        Path realRoot = root.toRealPath();
        if (!Files.isDirectory(realRoot)) throw new IOException("La referencia no es una carpeta.");
        Pattern pattern = compile(options);
        List<Path> candidates = new ArrayList<>();
        if (oneFile != null) {
            Path resolved = realRoot.resolve(oneFile).normalize().toRealPath();
            if (!resolved.startsWith(realRoot) || !Files.isRegularFile(resolved))
                throw new IOException("El archivo seleccionado no pertenece al proyecto.");
            candidates.add(resolved);
        } else {
            Files.walkFileTree(realRoot, new SimpleFileVisitor<>() {
                @Override public FileVisitResult preVisitDirectory(Path dir,
                        BasicFileAttributes attrs) {
                    if (cancelled.getAsBoolean()) return FileVisitResult.TERMINATE;
                    if (!dir.equals(realRoot) && (Files.isSymbolicLink(dir)
                            || GENERATED.contains(dir.getFileName().toString())))
                        return FileVisitResult.SKIP_SUBTREE;
                    return FileVisitResult.CONTINUE;
                }
                @Override public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attrs) {
                    if (cancelled.getAsBoolean()) return FileVisitResult.TERMINATE;
                    if (attrs.isRegularFile() && !Files.isSymbolicLink(file))
                        candidates.add(file);
                    return FileVisitResult.CONTINUE;
                }
                @Override public FileVisitResult visitFileFailed(Path file, IOException ex) {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        candidates.sort(Comparator.comparing(file ->
                realRoot.relativize(file).toString(), String.CASE_INSENSITIVE_ORDER));
        List<Hit> results = new ArrayList<>();
        int skippedLongLines = 0;
        for (Path file : candidates) {
            if (cancelled.getAsBoolean() || results.size() >= MAX_HITS) break;
            TextPreview.Result preview;
            try {
                preview = TextPreview.load(file);
            } catch (IOException | SecurityException notReadable) {
                continue;
            }
            if (!preview.readable()) continue;
            String relative = realRoot.relativize(file).toString().replace('\\', '/');
            String[] lines = preview.content().split("\\R", -1);
            for (int number = 0; number < lines.length && results.size() < MAX_HITS; number++) {
                if (cancelled.getAsBoolean()) break;
                String line = lines[number];
                if (line.length() > MAX_LINE) {
                    skippedLongLines++;
                    continue;
                }
                Matcher match = pattern.matcher(line);
                while (match.find() && results.size() < MAX_HITS) {
                    int start = match.start();
                    int size = match.end() - start;
                    // Avoid matching an empty string endlessly.
                    if (size <= 0) continue;
                    int beginExcerpt = Math.max(0, start - 40);
                    int endExcerpt = Math.min(line.length(), match.end() + 65);
                    results.add(new Hit(relative, number + 1, start + 1,
                            size, line.substring(beginExcerpt, endExcerpt).strip()));
                    if (cancelled.getAsBoolean()) break;
                }
            }
        }
        return new Result(results, results.size() >= MAX_HITS, skippedLongLines);
    }

    private static Pattern compile(Options options) {
        String source = options.regex() ? options.expression()
                : Pattern.quote(options.expression());
        if (options.wholeWords())
            source = "(?<![\\p{L}\\p{N}_])(?:" + source + ")(?![\\p{L}\\p{N}_])";
        int flags = Pattern.UNICODE_CHARACTER_CLASS;
        if (!options.caseSensitive()) flags |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
        try {
            return Pattern.compile(source, flags);
        } catch (PatternSyntaxException invalid) {
            throw new IllegalArgumentException("Expresión regular inválida: "
                    + invalid.getDescription(), invalid);
        }
    }
}
