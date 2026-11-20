package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Bounded read-only preview, not the future typing editor.  * <p><strong>Engineering rationale.</strong> A read-only text preview must reject binary, oversized or otherwise unsupported input before it reaches a typing session. Centralized bounded decoding prevents the editor from accidentally treating arbitrary resources as practiceable source text.</p>
*/
public final class TextPreview {
    private static final long MAX_BYTES = 2L * 1024 * 1024;

    private TextPreview() {
    }

    public static Result load(Path path) throws IOException {
        byte[] bytes = readBounded(path);
        return bytes == null ? unavailable() : decode(bytes);
    }

    /**
     * One bounded read for both text eligibility and the exact SHA-256 hash.
     * Only a valid, supported practice target needs a content fingerprint.
     * Multimedia and rejected inputs yield an empty hash: they are still
     * listed in the explorer but are not transcription exercises.
     */
    public static Analysed loadWithHash(Path path) throws IOException {
        byte[] bytes = readBounded(path);
        if (bytes == null) return new Analysed(unavailable(), "");
        Result result = decode(bytes);
        return new Analysed(result, result.readable()
                ? ReferenceFingerprint.sha256(bytes) : "");
    }

    public record Analysed(Result preview, String sha256) { }

    private static Result unavailable() {
        return new Result(null, "Vista previa no disponible.");
    }

    private static byte[] readBounded(Path path) throws IOException {
        if (!Files.isRegularFile(path) || Files.size(path) > MAX_BYTES) return null;
        // A file can grow after Files.size(): keep the actual read bounded
        // rather than allocating its entire newly enlarged contents.
        try (var stream = Files.newInputStream(path)) {
            byte[] bytes = stream.readNBytes((int) MAX_BYTES + 1);
            return bytes.length <= MAX_BYTES ? bytes : null;
        }
    }

    private static Result decode(byte[] bytes) {
        for (byte b : bytes) {
            if (b == 0) return new Result(null, "Archivo binario.");
        }
        try {
            String value = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes)).toString();
            if (value.startsWith("\uFEFF")) value = value.substring(1);
            return new Result(value, null);
        } catch (CharacterCodingException error) {
            return new Result(null, "Codificación de texto no compatible.");
        }
    }

    public record Result(String content, String reason) {
        public boolean readable() {
            return reason == null;
        }
    }
}
