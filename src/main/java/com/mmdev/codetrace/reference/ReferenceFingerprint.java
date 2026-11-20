package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/** SHA-256 calculated without changing the reference file. */
public final class ReferenceFingerprint {
    private ReferenceFingerprint() {
    }

    /**
     * Fingerprint bytes already loaded for UTF-8 validation. Reusing the
     * in-memory buffer avoids opening every source file a second time.
     * The hash is of original bytes, not normalized transcription text.
     */
    public static String sha256(byte[] originalBytes) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(originalBytes));
        } catch (NoSuchAlgorithmException error) {
            throw new IllegalStateException("SHA-256 no disponible", error);
        }
    }

    public static String sha256(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream in = Files.newInputStream(file)) {
                byte[] buffer = new byte[8192];
                int size;
                while (true) {
                    if (Thread.currentThread().isInterrupted())
                        throw new CancellationException("Huella de referencia cancelada");
                    size = in.read(buffer);
                    if (size == -1) break;
                    digest.update(buffer, 0, size);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException error) {
            throw new IllegalStateException("SHA-256 no disponible", error);
        }
    }
}
