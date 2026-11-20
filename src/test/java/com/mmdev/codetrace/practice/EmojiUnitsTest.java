package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmojiUnitsTest {
    @Test void extendedGraphemeClustersRemainSingleVisibleAnnotations() {
        assertEquals(4, EmojiUnits.graphemes("A👨‍👩‍👧‍👦🇪🇨B").size());
        assertTrue(EmojiUnits.isEmoji("👨‍👩‍👧‍👦"));
        assertTrue(EmojiUnits.isEmoji("🇪🇨"));
        assertTrue(EmojiUnits.isEmoji("✅"));
        assertTrue(EmojiUnits.isEmoji("1️⃣"));
        assertFalse(EmojiUnits.isEmoji("+"));
        assertFalse(EmojiUnits.isEmoji("1"));
        assertFalse(EmojiUnits.isEmoji("á"));
    }

    @Test void userTypesOnlyOrdinaryCharactersAndCanCorrectAfterEmoji() {
        TypingSession typing = new TypingSession("a😀b\n👨‍👩‍👧‍👦c🇪🇨");
        assertEquals(3, typing.visibleUnitCount());
        typing.type("ab");
        typing.moveCaret(4); // Start of emoji family followed by c.
        typing.type("x");
        assertFalse(typing.finishedExercise());
        typing.backspace();
        typing.type("c");
        assertTrue(typing.finishedExercise());
        assertEquals(3, typing.correctVisibleCount());
        assertEquals(3, typing.resolvedVisibleCount());
        assertEquals(3, typing.skippedCount());
        assertEquals("a😀b\n👨‍👩‍👧‍👦c🇪🇨", typing.expectedText());
    }

    @Test void emojiOnlyDocumentIsNotAnExercise() {
        TypingSession session = new TypingSession("😀\n✅");
        assertFalse(session.finishedExercise());
        assertEquals(0, session.visibleUnitCount());
    }

    @Test void restorationAndUndoSkipCannotMakeEmojiARequiredKeystroke() {
        TypingSession original = new TypingSession("x😃y");
        original.type("x");
        original.skip(2, 3);
        var restored = TypingSession.restore(original.expectedText(),
                original.enteredSlots(), original.skippedSlots(), original.caret());
        restored.undoSkip(1, 3);
        assertEquals(TypingSession.State.SKIPPED, restored.state(1));
        assertEquals(2, restored.visibleUnitCount());
    }
}
