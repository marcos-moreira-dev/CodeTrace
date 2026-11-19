package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CodeLineGutterMetricsTest {
    @Test void rowsStayCompactAndWidthGrowsForLongLineNumbers() {
        assertEquals(24, CodeLineGutter.rowHeight(13));
        assertTrue(CodeLineGutter.rowHeight(24) > CodeLineGutter.rowHeight(13));
        assertTrue(CodeLineGutter.rowHeight(36) > CodeLineGutter.rowHeight(24));
        assertEquals(CodeLineGutter.widthFor(23, 13), CodeLineGutter.widthFor(99, 13));
        assertTrue(CodeLineGutter.widthFor(100000, 13) >
                CodeLineGutter.widthFor(99, 13));
        assertThrows(IllegalArgumentException.class, () -> CodeLineGutter.widthFor(0, 13));
        assertThrows(IllegalArgumentException.class, () -> CodeLineGutter.rowHeight(0));
    }
}
