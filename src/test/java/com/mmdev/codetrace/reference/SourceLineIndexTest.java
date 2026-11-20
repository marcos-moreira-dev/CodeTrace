package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SourceLineIndexTest {
    @Test void keepsNewlinesOnPrecedingRowsIncludingEmptyRowsAndEof() {
        // Source text "a\\n\\nb": line ends on grapheme indices 1, 2, 4.
        var index = new SourceLineIndex(new int[]{1, 2, 4});
        assertEquals(0, index.findLine(0));
        assertEquals(0, index.findLine(1));
        assertEquals(1, index.findLine(2));
        assertEquals(2, index.findLine(3));
        assertEquals(2, index.findLine(4));
        assertEquals(2, index.findLine(20));
        assertEquals(3, index.lineCount());
    }

    @Test void preservesFirstMatchingRowWhenBoundsAreRepeated() {
        var index = new SourceLineIndex(new int[]{0, 0, 2});
        assertEquals(0, index.findLine(0));
        assertEquals(2, index.findLine(1));
        assertEquals(2, index.findLine(2));
    }

    @Test void boundsAreDefensivelyCopiedAndValidated() {
        int[] ends = {2, 5};
        var index = new SourceLineIndex(ends);
        ends[0] = 100;
        assertEquals(0, index.findLine(2));
        assertThrows(IllegalArgumentException.class,
                () -> new SourceLineIndex(new int[0]));
        assertThrows(IllegalArgumentException.class,
                () -> new SourceLineIndex(new int[]{3, 2}));
        assertThrows(IllegalArgumentException.class,
                () -> index.findLine(-1));
    }
}
