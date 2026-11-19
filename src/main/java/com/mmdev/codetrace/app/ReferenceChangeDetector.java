package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceFingerprint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/** Compare saved source fingerprints without touching the reference files. */
public final class ReferenceChangeDetector {
    private ReferenceChangeDetector() { }

    public static int changedCount(Path root, Map<String, String> fingerprints) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(fingerprints);
        int changes = 0;
        for (var pair : fingerprints.entrySet()) {
            if (Thread.currentThread().isInterrupted())
                throw new java.util.concurrent.CancellationException(
                        "Reference verification cancelled");
            Path source = root.resolve(pair.getKey());
            try {
                if (!Files.isRegularFile(source)
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
