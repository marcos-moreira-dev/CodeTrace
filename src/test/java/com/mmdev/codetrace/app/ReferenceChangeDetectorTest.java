package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceChangeDetectorTest {
    @TempDir Path root;

    @Test void neverFollowsPathsOutsideTheSelectedReference() throws Exception {
        Path reference = Files.createDirectories(root.resolve("project"));
        Path outside = Files.writeString(root.resolve("outside.java"), "secret");
        String expected = ReferenceFingerprint.sha256(outside);
        assertEquals(1, ReferenceChangeDetector.changedCount(reference,
                Map.of("../outside.java", expected)),
                "A deliberately malformed saved path must not read outside the project");
        assertEquals("secret", Files.readString(outside));
    }


    @Test void rejectsSymlinkedParentThatEscapesTheProject() throws Exception {
        Path reference = Files.createDirectory(root.resolve("link-check"));
        Path outside = Files.createDirectory(root.resolve("external"));
        Path secret = Files.writeString(outside.resolve("Private.java"), "private");
        Path linked = reference.resolve("linked");
        try {
            Files.createSymbolicLink(linked, outside);
        } catch (java.io.IOException | UnsupportedOperationException
                 | SecurityException unsupported) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, 
                    "Symbolic-link creation is unavailable on this Windows setup");
        }
        assertEquals(1, ReferenceChangeDetector.changedCount(reference,
                Map.of("linked/Private.java", ReferenceFingerprint.sha256(secret))));
        assertEquals("private", Files.readString(secret));
    }

    @Test void detectsChangedAndMissingReferencesWithoutWritingThem() throws Exception {
        Path original = Files.writeString(root.resolve("A.java"), "class A {}");
        Path changed = Files.writeString(root.resolve("B.java"), "before");
        String expectedA = ReferenceFingerprint.sha256(original);
        String expectedB = ReferenceFingerprint.sha256(changed);
        var fingerprints = Map.of("A.java", expectedA, "B.java", expectedB);
        assertEquals(0, ReferenceChangeDetector.changedCount(root, fingerprints));
        Files.writeString(changed, "after");
        assertEquals(1, ReferenceChangeDetector.changedCount(root, fingerprints));
        Files.delete(original);
        assertEquals(2, ReferenceChangeDetector.changedCount(root, fingerprints));
        assertEquals("after", Files.readString(changed));
    }
}
