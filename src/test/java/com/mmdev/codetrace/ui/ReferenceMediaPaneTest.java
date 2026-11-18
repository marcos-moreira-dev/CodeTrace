package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReferenceMediaPaneTest {
    @Test void classifiesPreviewableFiles() {
        assertEquals(ReferenceMediaPane.Kind.IMAGE,
                ReferenceMediaPane.kindFor(Path.of("logo.png")));
        assertEquals(ReferenceMediaPane.Kind.SVG,
                ReferenceMediaPane.kindFor(Path.of("diagram.svg")));
        assertEquals(ReferenceMediaPane.Kind.AUDIO,
                ReferenceMediaPane.kindFor(Path.of("music.mp3")));
        assertEquals(ReferenceMediaPane.Kind.VIDEO,
                ReferenceMediaPane.kindFor(Path.of("film.mp4")));
        assertEquals(ReferenceMediaPane.Kind.VIDEO,
                ReferenceMediaPane.kindFor(Path.of("film.mkv")));
        assertEquals(ReferenceMediaPane.Kind.UNSUPPORTED,
                ReferenceMediaPane.kindFor(Path.of("binary.exe")));
    }
}
