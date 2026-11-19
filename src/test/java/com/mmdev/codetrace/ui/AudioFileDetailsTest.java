package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AudioFileDetailsTest {
    @TempDir Path root;

    @Test void showsOnlyVerifiedFilePropertiesAndRealMediaTags() throws Exception {
        Path file = Files.write(root.resolve("audición.mp3"), new byte[2_097_152]);
        String metadata = AudioFileDetails.summary(file, Duration.seconds(215),
                Map.of("artist", "Artista", "album", "Disco"));
        assertTrue(metadata.contains("MP3"));
        assertTrue(metadata.contains("2.0 MB"));
        assertTrue(metadata.contains("3:35"));
        assertTrue(metadata.contains("Artista: Artista"));
        assertTrue(metadata.contains("Álbum: Disco"));
        assertFalse(metadata.contains("bitrate"));
    }
}
