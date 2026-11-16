package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class TypingSessionTest {
    @Test
    void keepsGoingAfterMistakesAndBackspaceRestoresPendingTemplate() {
        TypingSession session = new TypingSession("class");
        session.type("clxss");
        assertEquals(4, session.correctCount());
        assertEquals(1, session.incorrectCount());
        assertEquals(5, session.caret());
        session.moveCaret(3);
        session.backspace();
        assertEquals(TypingSession.State.PENDING, session.state(2));
        session.type("a");
        assertEquals(5, session.correctCount());
        assertEquals(0, session.incorrectCount());
    }

    @Test
    void accentsCountAsOneGraphemeAndMatchCanonically() {
        TypingSession session = new TypingSession("í");
        session.type("i\u0301");
        assertEquals(1, session.size());
        assertEquals(1, session.correctCount());
        assertEquals(1, session.caret());
    }

    @Test
    void importsSkippedAreNotCountedAsWritten() {
        TypingSession session = new TypingSession("import A;\nclass B {}");
        session.skip(0, 10);
        assertEquals(10, session.skippedCount());
        assertEquals(0, session.correctCount());
        assertEquals(10, session.resolvedCount());
        session.moveCaret(10);
        session.type("class");
        assertEquals(5, session.correctCount());
    }

    @Test
    void supportsUnicodeAndRestoringSparseSlots() {
        TypingSession session = new TypingSession("a🙂b");
        session.type("a🙂");
        assertEquals(2, session.correctCount());
        assertEquals(3, session.size());
        var restored = TypingSession.restore(session.expectedText(),
                session.enteredSlots(), List.of(), session.caret());
        restored.type("b");
        assertEquals(3, restored.correctCount());
    }

    @Test
    void doesNotGenerateClosingBraces() {
        TypingSession session = new TypingSession("{}");
        session.type("{");
        assertEquals(1, session.correctCount());
        assertEquals(TypingSession.State.PENDING, session.state(1));
    }
}
