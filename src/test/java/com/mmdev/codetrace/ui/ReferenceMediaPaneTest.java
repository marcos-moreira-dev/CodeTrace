package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReferenceMediaPaneTest {
    @Test void classifiesPreviewableFiles() {
        assertEquals(ReferenceMediaPane.Kind.IMAGE,
                ReferenceMediaPane.kindFor(Path.of("logo.png")));
        assertEquals(ReferenceMediaPane.Kind.SVG,
                ReferenceMediaPane.kindFor(Path.of("diagram.svg")));
        assertEquals(ReferenceMediaPane.Kind.IMAGE,
                ReferenceMediaPane.kindFor(Path.of("photo.WEBP")));
        assertTrue(ReferenceMediaPane.needsWebImage(Path.of("photo.WEBP")));
        assertFalse(ReferenceMediaPane.needsWebImage(Path.of("logo.png")));
        assertEquals(ReferenceMediaPane.Kind.AUDIO,
                ReferenceMediaPane.kindFor(Path.of("music.mp3")));
        assertEquals(ReferenceMediaPane.Kind.VIDEO,
                ReferenceMediaPane.kindFor(Path.of("film.mp4")));
        assertEquals(ReferenceMediaPane.Kind.VIDEO,
                ReferenceMediaPane.kindFor(Path.of("film.mkv")));
        assertEquals(ReferenceMediaPane.Kind.UNSUPPORTED,
                ReferenceMediaPane.kindFor(Path.of("binary.exe")));
    }

    @Test void timelineClickSeeksToExactPositionRatherThanBlockIncrement() {
        assertEquals(0, ReferenceMediaPane.percentAtTrack(80, 100, 400), 0.00001);
        assertEquals(0, ReferenceMediaPane.percentAtTrack(100, 100, 400), 0.00001);
        assertEquals(25, ReferenceMediaPane.percentAtTrack(200, 100, 400), 0.00001);
        assertEquals(50, ReferenceMediaPane.percentAtTrack(300, 100, 400), 0.00001);
        assertEquals(100, ReferenceMediaPane.percentAtTrack(500, 100, 400), 0.00001);
        assertEquals(100, ReferenceMediaPane.percentAtTrack(900, 100, 400), 0.00001);
    }

    @Test void timelineRejectsInvalidTrackDimensions() {
        assertEquals(0, ReferenceMediaPane.percentAtTrack(200, 100, 0));
        assertEquals(0, ReferenceMediaPane.percentAtTrack(200, 100, Double.NaN));
        assertEquals(0, ReferenceMediaPane.percentAtTrack(Double.NaN, 100, 400));
    }
}
