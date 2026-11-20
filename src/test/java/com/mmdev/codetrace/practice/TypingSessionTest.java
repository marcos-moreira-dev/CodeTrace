package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class TypingSessionTest {
    @Test
    void intentionallySkippedSymbolsResolvePracticeWithoutInflatingAciertos() {
        TypingSession session = new TypingSession("a{}\n");
        session.type("a");
        session.skip(1, 3);
        assertTrue(session.finishedExercise());
        assertEquals(1, session.correctCount());
        assertEquals(2, session.skippedCount());
        session.moveCaret(0);
        session.type("x");
        assertFalse(session.finishedExercise());
    }

    @Test
    void newlineAtEndIsNotAnAdditionalTypingRequirement() {
        TypingSession session = new TypingSession("abc\n");
        session.type("abc");
        assertTrue(session.finishedExercise());
        assertEquals(3, session.correctCount());
    }

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
        session.type("a🙂"); // Pasted emoji cannot consume a slot.
        assertEquals(1, session.correctCount());
        assertEquals(1, session.skippedCount());
        assertEquals(3, session.size());
        var restored = TypingSession.restore(session.expectedText(),
                session.enteredSlots(), session.skippedSlots(), session.caret());
        restored.type("b");
        assertEquals(2, restored.correctCount());
        assertTrue(restored.finishedExercise());
    }

    @Test
    void doesNotGenerateClosingBraces() {
        TypingSession session = new TypingSession("{}");
        session.type("{");
        assertEquals(1, session.correctCount());
        assertEquals(TypingSession.State.PENDING, session.state(1));
    }

    @Test
    void separateCombiningMarkCompletesTheFinalReferenceGrapheme() {
        TypingSession session = new TypingSession("í");
        session.type("i");
        assertEquals(1, session.caret());
        assertEquals(1, session.incorrectCount());
        // Some LATAM keyboards/IME environments commit the accent separately.
        session.type("\u0301");
        assertEquals(1, session.size());
        assertEquals(1, session.caret());
        assertEquals(1, session.correctCount());
        assertEquals(0, session.incorrectCount());
        assertTrue(session.finishedExercise());
    }

    @Test
    void onlyDeliberateSkipsCanFinishWithoutInflatingTheProgress() {
        TypingSession session = new TypingSession("{}");
        session.skip(0, session.size());
        assertTrue(session.finishedExercise());
        assertEquals(0, session.correctCount());
        assertEquals(2, session.skippedCount());
        session.undoSkip(1, 2);
        assertFalse(session.finishedExercise());
        assertEquals(0, session.correctCount());
    }

    @Test
    void trailingStructuralNewlineCanBeLeftUntypedAfterSkips() {
        TypingSession session = new TypingSession("import X;\n");
        session.skip(0, "import X;".length());
        assertTrue(session.finishedExercise());
        assertEquals(0, session.correctCount());
    }
    @Test
    void invisibleInternalNewlinesNeverBlockOtherwiseCorrectVisibleCode() {
        TypingSession session = new TypingSession("abc\nxyz\n");
        session.type("abc");
        // A user may jump to another row rather than typing the hidden Enter tile.
        session.moveCaret(4);
        session.type("xyz");
        assertEquals(0, session.incorrectCount());
        assertTrue(session.finishedExercise(),
                "All visible green glyphs should complete the exercise");
        assertEquals(6, session.correctCount());
    }

    @Test
    void invisibleMistypedLineSeparatorCannotBlockCorrectedCode() {
        TypingSession session = new TypingSession("a\nb\n");
        session.type("ax"); // The invisible line break is entered incorrectly.
        session.type("x"); // Also mistype the visible second line.
        assertFalse(session.finishedExercise());
        session.moveCaret(2);
        session.backspace(); // Clear the incorrect line separator.
        session.moveCaret(2);
        session.type("b");
        assertTrue(session.finishedExercise());
        assertEquals(2, session.correctCount());
    }

    @Test
    void anEmptyOrNewlineOnlyDocumentNeverTriggersACompletion() {
        assertFalse(new TypingSession("").finishedExercise());
        assertFalse(new TypingSession("\n\n").finishedExercise());
        TypingSession source = new TypingSession("one\ntwo");
        source.type("one");
        source.moveCaret(4);
        source.type("txo");
        assertFalse(source.finishedExercise(), "Visible typos still block finishing");
        source.moveCaret(6);
        source.backspace();
        source.type("w");
        assertTrue(source.finishedExercise());
    }

    /** Independent O(U) oracle for the cached O(1) production counters. */
    private static void assertCountersMatchSlots(TypingSession session) {
        int visible = 0, correctVisible = 0, resolvedVisible = 0;
        int correct = 0, incorrect = 0;
        for (int i = 0; i < session.size(); i++) {
            var cell = session.cell(i);
            boolean shown = !"\\n".equals(cell.expected())
                    && !EmojiUnits.isEmoji(cell.expected());
            if (cell.state() == TypingSession.State.CORRECT) correct++;
            if (cell.state() == TypingSession.State.INCORRECT) incorrect++;
            if (!shown) continue;
            visible++;
            if (cell.state() == TypingSession.State.CORRECT) correctVisible++;
            if (cell.state() == TypingSession.State.CORRECT
                    || cell.state() == TypingSession.State.SKIPPED) resolvedVisible++;
        }
        assertEquals(visible, session.visibleUnitCount());
        assertEquals(correctVisible, session.correctVisibleCount());
        assertEquals(resolvedVisible, session.resolvedVisibleCount());
        assertEquals(correct, session.correctCount());
        assertEquals(incorrect, session.incorrectCount());
        assertEquals(visible > 0 && resolvedVisible == visible,
                session.finishedExercise());
    }

    @Test
    void incrementalCountersRemainExactAcrossEditsSkipsCombiningAndRestore() {
        TypingSession session = new TypingSession("a🙂í{}\\nB");
        assertCountersMatchSlots(session);
        session.type("a🙂i");
        assertCountersMatchSlots(session);
        session.type("\\u0301");
        assertCountersMatchSlots(session);
        session.skip(3, 5);
        assertCountersMatchSlots(session);
        session.moveCaret(6);
        session.type("B");
        assertCountersMatchSlots(session);
        assertTrue(session.finishedExercise());
        session.undoSkip(4, 5);
        assertCountersMatchSlots(session);
        assertFalse(session.finishedExercise());
        session.moveCaret(4);
        session.type("}");
        assertCountersMatchSlots(session);
        session.backspace();
        assertCountersMatchSlots(session);
        session.type("}");
        assertCountersMatchSlots(session);
        session.deleteAtCaret();
        assertCountersMatchSlots(session);

        TypingSession restored = TypingSession.restore(session.expectedText(),
                session.enteredSlots(), session.skippedSlots(), session.caret());
        assertCountersMatchSlots(restored);
        assertEquals(session.correctCount(), restored.correctCount());
        assertEquals(session.correctVisibleCount(), restored.correctVisibleCount());
    }

    @Test
    void incrementalCountersHandleTurboAndUndoWithoutFalseAciertos() {
        TypingSession session = new TypingSession("{}abc");
        session.skipTurboSymbols();
        assertCountersMatchSlots(session);
        assertEquals(0, session.correctVisibleCount());
        session.undoSkip(0, 2);
        assertCountersMatchSlots(session);
        session.moveCaret(0);
        session.type("{}abc");
        assertCountersMatchSlots(session);
        assertEquals(5, session.correctVisibleCount());
    }

}
