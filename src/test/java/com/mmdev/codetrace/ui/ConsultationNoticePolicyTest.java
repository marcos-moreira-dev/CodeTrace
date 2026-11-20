package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ConsultationNoticePolicyTest {
    @Test void mediaNeverShowsTypingExplanationDialog() {
        for (String name : java.util.List.of("video.mkv", "video.mp4",
                "video.mov", "video.webm", "audio.mp3", "audio.flac",
                "audio.wav", "foto.png", "dibujo.svg", "imagen.webp")) {
            assertFalse(ConsultationNoticePolicy.requiresExplanation(Path.of(name)),
                    name);
        }
    }

    @Test void otherReadOnlyDocumentsKeepTheirExplanation() {
        for (String name : java.util.List.of("documento.pdf",
                "ejemplo.txt", "archivo.docx", "diagrama.json")) {
            assertTrue(ConsultationNoticePolicy.requiresExplanation(Path.of(name)),
                    name);
        }
    }
}
