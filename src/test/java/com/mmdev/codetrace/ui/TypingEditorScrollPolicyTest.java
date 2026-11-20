package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TypingEditorScrollPolicyTest {
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
