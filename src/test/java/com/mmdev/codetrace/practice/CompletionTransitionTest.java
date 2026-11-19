package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CompletionTransitionTest {
    @Test void notifiesOnceWhenTypingFinishes() {
        TypingSession source = new TypingSession("print");
        CompletionTransition transition = new CompletionTransition(source.finishedExercise());
        source.type("prin");
        assertFalse(transition.update(source.finishedExercise()));
        source.type("t");
        assertTrue(transition.update(source.finishedExercise()));
        assertFalse(transition.update(source.finishedExercise()));
    }

    @Test void alreadyCompletedSessionNeverCelebratesWhenReopened() {
        TypingSession source = new TypingSession("hi");
        source.type("hi");
        CompletionTransition restored = new CompletionTransition(source.finishedExercise());
        assertFalse(restored.update(source.finishedExercise()));
        // Correcting the previously completed exercise can finish it again.
        source.backspace();
        assertFalse(restored.update(source.finishedExercise()));
        source.type("i");
        assertTrue(restored.update(source.finishedExercise()));
    }

    @Test void errorsAndEmptyReferencesDoNotComplete() {
        TypingSession source = new TypingSession("a");
        CompletionTransition transition = new CompletionTransition(source.finishedExercise());
        source.type("x");
        assertFalse(transition.update(source.finishedExercise()));
        source.backspace();
        assertFalse(transition.update(source.finishedExercise()));
        source.type("a");
        assertTrue(transition.update(source.finishedExercise()));
        TypingSession empty = new TypingSession("");
        assertFalse(new CompletionTransition(false).update(empty.finishedExercise()));
    }
}
