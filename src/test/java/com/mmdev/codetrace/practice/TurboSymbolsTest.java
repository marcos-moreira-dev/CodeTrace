package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TurboSymbolsTest {
    @Test
    void skipsConsecutiveSymbolsAfterTypingPreviousCharacter() {
        var session = new TypingSession("a{}; b");
        session.type("a");
        assertEquals(3, session.skipTurboSymbols());
        assertEquals(4, session.caret());
        assertEquals(3, session.skippedCount());
        assertEquals(1, session.correctCount());
        assertEquals(TypingSession.State.SKIPPED, session.state(1));
        assertEquals("a{}; b", session.expectedText());
        session.type(" ");
        assertEquals(2, session.correctCount());
    }

    @Test
    void doesNotSkipLettersNumbersAccentsOrWhitespace() {
        assertFalse(TurboSymbols.isAutomatic("ñ"));
        assertFalse(TurboSymbols.isAutomatic("í"));
        assertFalse(TurboSymbols.isAutomatic("5"));
        assertFalse(TurboSymbols.isAutomatic(" "));
        assertFalse(TurboSymbols.isAutomatic("\n"));
        assertTrue(TurboSymbols.isAutomatic("{"));
        assertTrue(TurboSymbols.isAutomatic("="));
        assertTrue(TurboSymbols.isAutomatic("_"));
    }

    @Test
    void previousMistakeIsNotReplacedByAutoSkip() {
        var session = new TypingSession("a{z");
        session.type("x");
        assertEquals(1, session.skipTurboSymbols());
        assertEquals(TypingSession.State.INCORRECT, session.state(0));
        assertEquals(0, session.correctCount());
    }

    @Test
    void backspaceCanStillUndoSkippedCharacters() {
        var session = new TypingSession("a{z");
        session.type("a");
        assertEquals(1, session.skipTurboSymbols());
        session.backspace();
        assertEquals(1, session.caret());
        assertEquals(TypingSession.State.PENDING, session.state(1));
    }
}
