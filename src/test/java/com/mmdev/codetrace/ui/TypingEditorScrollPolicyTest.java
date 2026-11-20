package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TypingEditorScrollPolicyTest {
    @Test
    void advancingInsideVisibleContextDoesNotMoveTheScroll() {
        assertEquals(0, TypingEditor.contextScrollRows(19, 42, 24));
        assertEquals(0, TypingEditor.contextScrollRows(19, 42, 25));
        assertEquals(0, TypingEditor.contextScrollRows(19, 42, 33));
        // A line transition should not snap back to the top of the viewport.
        assertEquals(1, TypingEditor.contextScrollRows(19, 42, 41));
        assertEquals(2, TypingEditor.contextScrollRows(19, 42, 42));
    }

    @Test
    void keepsThreePreviousLinesWhenScrollingBackUp() {
        assertEquals(-3, TypingEditor.contextScrollRows(19, 42, 19));
        assertEquals(-1, TypingEditor.contextScrollRows(19, 42, 21));
        assertEquals(0, TypingEditor.contextScrollRows(19, 42, 22));
        assertEquals(-6, TypingEditor.contextScrollRows(19, 42, 16));
    }

    @Test
    void contextAdaptsToTinyViewportsAndInvalidLayout() {
        assertEquals(0, TypingEditor.contextScrollRows(0, 0, 0));
        assertEquals(0, TypingEditor.contextScrollRows(3, 4, 3));
        assertEquals(0, TypingEditor.contextScrollRows(3, 4, 4));
        assertEquals(-1, TypingEditor.contextScrollRows(5, 8, 5));
        assertEquals(1, TypingEditor.contextScrollRows(5, 8, 8));
        assertEquals(0, TypingEditor.contextScrollRows(-1, -1, 30));
    }

    @Test
    void preservesThreePreviousRowsWhenAvailable() {
        assertEquals(0, TypingEditor.firstLineWithContext(0));
        assertEquals(0, TypingEditor.firstLineWithContext(1));
        assertEquals(0, TypingEditor.firstLineWithContext(2));
        assertEquals(0, TypingEditor.firstLineWithContext(3));
        assertEquals(1, TypingEditor.firstLineWithContext(4));
        assertEquals(21, TypingEditor.firstLineWithContext(24));
        assertEquals(997, TypingEditor.firstLineWithContext(1000));
    }
}
