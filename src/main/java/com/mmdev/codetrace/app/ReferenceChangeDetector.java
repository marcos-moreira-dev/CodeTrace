package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceFingerprint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.LinkOption;
import java.util.Map;
import java.util.Objects;

/** Compare saved source fingerprints without touching the reference files. */
public final class ReferenceChangeDetector {
    private ReferenceChangeDetector() { }

    public static int changedCount(Path root, Map<String, String> fingerprints) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(fingerprints);
        Path normalizedRoot = root.toAbsolutePath().normalize();
        int changes = 0;
        for (var pair : fingerprints.entrySet()) {
            if (Thread.currentThread().isInterrupted())
                throw new java.util.concurrent.CancellationException(
                        "Reference verification cancelled");
            // Saved session paths must never escape their reference root.
            // Symlinks are not indexed as regular source files.
            Path source = normalizedRoot.resolve(pair.getKey()).normalize();
            if (!source.startsWith(normalizedRoot)) {
                changes++;
                continue;
            }
            try {
                if (!Files.isRegularFile(source, LinkOption.NOFOLLOW_LINKS)
                        || !ReferenceFingerprint.sha256(source).equals(pair.getValue())) {
                    changes++;
                }
            } catch (IOException | SecurityException error) {
                changes++;
            }
        }
        return changes;
    }
}
