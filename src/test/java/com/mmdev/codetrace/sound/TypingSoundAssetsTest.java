package com.mmdev.codetrace.sound;

import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.nio.file.Files;
import javax.sound.sampled.AudioSystem;
import java.util.zip.GZIPInputStream;
import org.junit.jupiter.api.Test;

class TypingSoundAssetsTest {
    @Test
    void normalizationProducesAudible16BitPcmWithoutAnAudioDevice() throws Exception {
        var file = TypingSounds.normalizedWav("tecla-correcta.wav.gz", 7.0);
        try (var decoded = AudioSystem.getAudioInputStream(file.toFile())) {
            assertEquals(16, decoded.getFormat().getSampleSizeInBits());
            assertEquals(1, decoded.getFormat().getChannels());
            byte[] pcm = decoded.readAllBytes();
            int peak = 0;
            for (int i = 0; i + 1 < pcm.length; i += 2) {
                int sample = (short) ((pcm[i] & 0xff) | (pcm[i + 1] << 8));
                peak = Math.max(peak, Math.abs(sample));
            }
            assertTrue(peak > 9000, "Cues must no longer be nearly silent");
        } finally {
            Files.deleteIfExists(file);
        }
    }

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
