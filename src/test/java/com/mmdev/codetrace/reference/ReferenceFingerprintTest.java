package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceFingerprintTest {
    @TempDir Path folder;

    @Test
    void detectsChangedContent() throws Exception {
        Path file = folder.resolve("Main.java");
        Files.writeString(file, "abc");
        String first = ReferenceFingerprint.sha256(file);
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", first);
        Files.writeString(file, "abd");
        assertNotEquals(first, ReferenceFingerprint.sha256(file));
    }
    @Test
    void interruptStopsChecksumInsteadOfReturningAnIncompleteDigest() throws Exception {
        Path file = Files.writeString(folder.resolve("Large.java"),
                "hello source".repeat(1024));
        Thread.currentThread().interrupt();
        try {
            assertThrows(java.util.concurrent.CancellationException.class,
                    () -> ReferenceFingerprint.sha256(file));
        } finally {
            Thread.interrupted();
        }
        // Cancellation has not modified the source and is safe to retry.
        assertEquals(64, ReferenceFingerprint.sha256(file).length());
        assertEquals("hello source".repeat(1024), Files.readString(file));
    }

}
