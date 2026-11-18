package com.mmdev.codetrace.sound;

import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.junit.jupiter.api.Test;

class TypingSoundAssetsTest {
    @Test
    void suppliedSamplesAreEmbeddedAndHaveWaveHeaders() throws Exception {
        for (String name : new String[]{"tecla-correcta.wav.gz","tecla-error.wav.gz"}) {
            try (InputStream raw = getClass().getResourceAsStream("/sounds/" + name)) {
                assertNotNull(raw, name);
                try (GZIPInputStream decoded = new GZIPInputStream(raw)) {
                    byte[] header = decoded.readNBytes(12);
                    assertEquals("RIFF", new String(header, 0, 4,
                            java.nio.charset.StandardCharsets.US_ASCII));
                    assertEquals("WAVE", new String(header, 8, 4,
                            java.nio.charset.StandardCharsets.US_ASCII));
                }
            }
        }
    }
}
