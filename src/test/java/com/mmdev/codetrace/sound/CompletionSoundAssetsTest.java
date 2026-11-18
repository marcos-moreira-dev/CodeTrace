package com.mmdev.codetrace.sound;

import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class CompletionSoundAssetsTest {
    @Test
    void suppliedCompletionSoundIsOnTheClasspath() throws Exception {
        try (InputStream stream = getClass().getResourceAsStream("/sounds/success.mp3")) {
            assertNotNull(stream, "The completion sound must be packaged with CodeTrace");
            assertArrayEquals("ID3".getBytes(StandardCharsets.US_ASCII),
                    stream.readNBytes(3));
            assertTrue(stream.readAllBytes().length > 4000);
        }
    }
}
