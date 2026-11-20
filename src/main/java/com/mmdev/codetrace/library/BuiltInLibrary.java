package com.mmdev.codetrace.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Original, read-only practice references packaged inside CodeTrace.
 * No network operations or generated teaching text. References are materialized
 * into a versioned, private application folder solely because the existing
 * reference engine expects real file paths.
  * <p><strong>Engineering rationale.</strong> Practice packs are bundled, offline teaching content distinct from the user's selected reference folder. Extraction must use controlled destinations and paths, and loading a lesson should still enter the usual read-only reference and transcription workflow instead of creating a second editor.</p>
*/
public final class BuiltInLibrary {
    public static final String CATALOG_VERSION = "v1";

    public record Pack(String id, String name, String extension) {
        public Pack {
            if (!id.matches("[a-z][a-z0-9-]*") || name.isBlank()
                    || extension.isBlank()) throw new IllegalArgumentException("Invalid pack");
        }
    }

    private static final List<Pack> PACKS = List.of(
            new Pack("java21", "Java 21", ".java"),
            new Pack("kotlin", "Kotlin 2.2", ".kt"),
            new Pack("typescript", "TypeScript", ".ts"),
            new Pack("javascript", "JavaScript", ".js"),
            new Pack("python", "Python", ".py"),
            new Pack("c", "C", ".c"),
            new Pack("cpp", "C++", ".cpp"),
            new Pack("csharp", "C#", ".cs"),
            new Pack("go", "Go", ".go"),
            new Pack("ruby", "Ruby", ".rb"),
            new Pack("powershell", "PowerShell", ".ps1"),
            new Pack("shell", "Shell (Linux)", ".sh"),
            new Pack("cmd", "Windows CMD", ".cmd"),
            new Pack("nasm-x64", "Ensamblador NASM x86-64 (Intel)", ".asm")
    );

    private final Path cacheRoot;
    private final Map<String, List<String>> catalog;

    public BuiltInLibrary() throws IOException {
        this(Path.of(System.getProperty("user.home"), ".codetrace",
                "practice-library", CATALOG_VERSION));
    }

    /** A configurable cache path keeps test runs isolated from user data. */
    public BuiltInLibrary(Path cacheRoot) throws IOException {
        this.cacheRoot = Objects.requireNonNull(cacheRoot);
        this.catalog = readCatalog();
    }

    public List<Pack> packs() { return PACKS; }

    public int size(Pack pack) {
        validate(pack);
        return catalog.get(pack.id()).size();
    }

    public List<String> entries(Pack pack) {
        validate(pack);
        return catalog.get(pack.id());
    }

    /**
     * Prepares a complete reference pack offline. Files are intentionally
     * restored if changed: the bundled original must remain deterministic.
     * Typed progress is kept separately in the .ctrace session.
     */
    public Path open(Pack pack) throws IOException {
        validate(pack);
        Path folder = cacheRoot.resolve(pack.id()).toAbsolutePath().normalize();
        Files.createDirectories(folder);
        for (String name : entries(pack)) {
            Path file = folder.resolve(name).normalize();
            if (!file.startsWith(folder)) throw new IOException("Invalid bundled path: " + name);
            String resource = "/practice/" + pack.id() + "/" + name;
            byte[] original;
            try (InputStream input = BuiltInLibrary.class.getResourceAsStream(resource)) {
                if (input == null) throw new IOException("Bundled example missing: " + resource);
                original = input.readAllBytes();
            }
            Files.createDirectories(file.getParent());
            if (!Files.exists(file) || !Arrays.equals(original, Files.readAllBytes(file))) {
                Files.write(file, original);
            }
        }
        return folder;
    }

    private void validate(Pack pack) {
        if (!PACKS.contains(pack)) throw new IllegalArgumentException("Unknown library pack");
    }

    private static Map<String, List<String>> readCatalog() throws IOException {
        InputStream file = BuiltInLibrary.class.getResourceAsStream("/practice/catalog.tsv");
        if (file == null) throw new IOException("Internal practice catalog not bundled");
        Map<String, List<String>> parsed = new HashMap<>();
        Set<String> allowed = new HashSet<>();
        for (Pack p : PACKS) {
            allowed.add(p.id());
            parsed.put(p.id(), new ArrayList<>());
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] fields = line.split("\t", -1);
                if (fields.length != 2 || !allowed.contains(fields[0])
                        || fields[1].isBlank() || fields[1].startsWith("/")
                        || fields[1].contains("..") || fields[1].contains("\\")) {
                    throw new IOException("Invalid internal catalog entry");
                }
                List<String> entries = parsed.get(fields[0]);
                if (entries.contains(fields[1])) {
                    throw new IOException("Duplicate internal exercise: " + fields[1]);
                }
                entries.add(fields[1]);
            }
        }
        for (Pack p : PACKS) {
            if (parsed.get(p.id()).isEmpty()) throw new IOException("Empty pack: " + p.id());
        }
        Map<String, List<String>> result = new HashMap<>();
        parsed.forEach((key, paths) -> result.put(key, List.copyOf(paths)));
        return Map.copyOf(result);
    }
}
