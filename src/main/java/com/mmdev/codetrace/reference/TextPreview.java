package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Bounded read-only preview, not the future typing editor. */
public final class TextPreview {
    private static final long MAX_BYTES = 2L * 1024 * 1024;

    private TextPreview() {
    }

    public static Result load(Path path) throws IOException {
        if (!Files.isRegularFile(path) || Files.size(path) > MAX_BYTES) {
            return new Result(null, "Vista previa no disponible.");
        }
        byte[] bytes = Files.readAllBytes(path);
        for (byte b : bytes) {
            if (b == 0) {
                return new Result(null, "Archivo binario.");
            }
        }
        try {
            String value = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes)).toString();
            if (value.startsWith("\uFEFF")) {
                value = value.substring(1);
            }
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
