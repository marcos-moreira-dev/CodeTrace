package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SourceRangeTest {
    @Test void mapsHumanCoordinatesToZeroBasedIndicesWithoutAmbiguity() {
        SourceRange hit = new ProjectTextSearch.Hit(
                "src/Main.java", 64, 28, 3, "example").range();
        assertEquals(64, hit.line());
        assertEquals(28, hit.column());
        assertEquals(63, hit.lineIndex());
        assertEquals(27, hit.columnIndex());
        assertEquals(3, hit.length());
        assertThrows(IllegalArgumentException.class, () -> new SourceRange(0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new SourceRange(1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new SourceRange(1, 1, 0));
    }
}
