package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceFingerprint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.LinkOption;
import java.util.Map;
import java.util.Objects;

/** Compare saved source fingerprints without touching the reference files.  * <p><strong>Engineering rationale.</strong> Saved fingerprints are evidence about a specific source revision, not authority to alter that source. Validate relative paths and resolved filesystem targets before hashing; report changed or unavailable files so that outdated transcription credit is not silently accepted.</p>
*/
public final class ReferenceChangeDetector {
    private ReferenceChangeDetector() { }

    public static int changedCount(Path root, Map<String, String> fingerprints) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(fingerprints);
        Path normalizedRoot = root.toAbsolutePath().normalize();
        // Resolve the root once. File path containment must be checked on the
        // real path as well: NOFOLLOW_LINKS on a file does not prevent a
        // symlinked parent directory from escaping the selected project.
        Path realRoot;
        try {
            realRoot = normalizedRoot.toRealPath();
        } catch (IOException | SecurityException unavailable) {
            return fingerprints.size();
        }
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
                        || !source.toRealPath().startsWith(realRoot)
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
