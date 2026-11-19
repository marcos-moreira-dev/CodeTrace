package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * An index of a reference folder, scanned on a background worker.
 * Hashes cover all regular files; typeable counts cover compatible UTF-8 text.
 */
public final class ReferenceCatalogue {
    private static final Set<String> IGNORED_FOLDERS = Set.of(
            ".git", ".idea", ".local", "target", "node_modules",
            "build", "dist", "__pycache__", ".venv");

    public record FileInfo(String sha256, long bytes, int typeableUnits) {
        public boolean typeable() {
            return typeableUnits >= 0;
        }
    }

    public record Snapshot(Map<String, FileInfo> files) {
        public Snapshot {
            files = Collections.unmodifiableMap(new LinkedHashMap<>(files));
        }

        public long expectedUnits() {
            return expectedUnits(new ProgressScope(true, true, true, true));
        }

        public long expectedUnits(ProgressScope scope) {
            return expectedUnits(scope, "");
        }

        public long expectedUnits(ProgressScope scope, String folder) {
            return expectedUnits(scope, folder, ProgressExclusions.empty());
        }

        public long expectedUnits(ProgressScope scope, String folder,
                                  ProgressExclusions exclusions) {
            return files.entrySet().stream()
                    .filter(pair -> pair.getValue().typeable()
                            && inFolder(pair.getKey(), folder)
                            && !exclusions.excludes(pair.getKey())
                            && scope.includes(ProgressScope.classify(pair.getKey())))
                    .mapToLong(pair -> pair.getValue().typeableUnits())
                    .sum();
        }

        public long includedFileCount(ProgressScope scope) {
            return includedFileCount(scope, "");
        }

        public long includedFileCount(ProgressScope scope, String folder) {
            return includedFileCount(scope, folder, ProgressExclusions.empty());
        }

        public long includedFileCount(ProgressScope scope, String folder,
                                      ProgressExclusions exclusions) {
            return files.entrySet().stream()
                    .filter(pair -> pair.getValue().typeable()
                            && inFolder(pair.getKey(), folder)
                            && !exclusions.excludes(pair.getKey())
                            && scope.includes(ProgressScope.classify(pair.getKey())))
                    .count();
        }

        private static boolean inFolder(String path, String folder) {
            return folder == null || folder.isEmpty() || path.startsWith(folder + "/");
        }

        public long resolvedUnits(VirtualWorkspace workspace,
                                  Map<String, TypingSession> typing,
                                  Map<String, String> originalHashes) {
            return resolvedUnits(workspace, typing, originalHashes,
                    new ProgressScope(true, true, true, true));
        }

        public long resolvedUnits(VirtualWorkspace workspace,
                                  Map<String, TypingSession> typing,
                                  Map<String, String> originalHashes,
                                  ProgressScope scope) {
            return resolvedUnits(workspace, typing, originalHashes, scope, "");
        }

        public long resolvedUnits(VirtualWorkspace workspace,
                                  Map<String, TypingSession> typing,
                                  Map<String, String> originalHashes,
                                  ProgressScope scope, String folder) {
            return resolvedUnits(workspace, typing, originalHashes, scope, folder,
                    ProgressExclusions.empty());
        }

        public long resolvedUnits(VirtualWorkspace workspace,
                                  Map<String, TypingSession> typing,
                                  Map<String, String> originalHashes,
                                  ProgressScope scope, String folder,
                                  ProgressExclusions exclusions) {
            long result = 0;
            for (var pair : files.entrySet()) {
                FileInfo info = pair.getValue();
                if (!info.typeable() || !inFolder(pair.getKey(), folder)
                        || exclusions.excludes(pair.getKey())
                        || !scope.includes(ProgressScope.classify(pair.getKey()))) {
                    continue;
                }
                String path = pair.getKey();
                var virtual = workspace.entry(path);
                if (virtual == null || virtual.kind() != VirtualWorkspace.Kind.FILE) {
                    continue;
                }
                // Empty files contain no typed characters and add no progress.
                if (info.typeableUnits() == 0) continue;
                TypingSession session = typing.get(path);
                if (session != null && info.sha256().equals(originalHashes.get(path))) {
                    result += Math.min(info.typeableUnits(), session.correctCount());
                }
            }
            return result;
        }
    }

    private ReferenceCatalogue() {
    }

    public static Snapshot scan(Path referenceRoot) throws IOException {
        if (!Files.isDirectory(referenceRoot)) {
            throw new IOException("Reference directory is missing");
        }
        Map<String, FileInfo> files = new LinkedHashMap<>();
        Files.walkFileTree(referenceRoot, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attributes) {
                if (Thread.currentThread().isInterrupted())
                    return FileVisitResult.TERMINATE;
                if (!dir.equals(referenceRoot)
                        && IGNORED_FOLDERS.contains(dir.getFileName().toString())) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                if (Thread.currentThread().isInterrupted())
                    return FileVisitResult.TERMINATE;
                if (!attributes.isRegularFile()) {
                    return FileVisitResult.CONTINUE;
                }
                try {
                    String sha = ReferenceFingerprint.sha256(file);
                    int units = -1; // Binary, unsupported, or too large for this editor.
                    TextPreview.Result preview = PracticeEligibility.supports(file)
                            ? TextPreview.load(file) : new TextPreview.Result(null,
                                    "Tipo de archivo no incluido en la práctica.");
                    if (preview.readable()) {
                        units = new TypingSession(preview.content()).size();
                    }
                    String relative = referenceRoot.relativize(file)
                            .toString().replace('\\', '/');
                    files.put(relative, new FileInfo(sha, attributes.size(), units));
                } catch (IOException | SecurityException ignored) {
                    // An unreadable file does not block indexing other files.
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException error) {
                return FileVisitResult.CONTINUE;
            }
        });
        return new Snapshot(files);
    }
}
