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
    @Test void typingARealPowerShellLikeFileAndCorrectingTheLastMistakeNotifiesOnce() {
        String sourceText = "# Script de ejemplo\n\n$nombre = \"CodeTrace\"\n"
                + "$cantidad = 3\n$total = $cantidad\nWrite-Host ($nombre + $total)\n";
        TypingSession session = new TypingSession(sourceText);
        CompletionTransition completion = new CompletionTransition(session.finishedExercise());
        // Make the invisible line endings remain pending while the visible
        // characters in each line are transcribed.
        for (String line : sourceText.split("\\n", -1)) {
            if (line.isEmpty()) continue;
            int start = session.expectedText().indexOf(line);
            session.moveCaret(start);
            if (line.startsWith("Write-Host")) {
                session.type(line.substring(0, line.length() - 1) + "]");
            } else {
                session.type(line);
            }
            assertFalse(completion.update(session.finishedExercise()));
        }
        assertFalse(session.finishedExercise());
        int finalIndex = session.expectedText().lastIndexOf(')');
        session.moveCaret(finalIndex + 1);
        session.backspace();
        assertFalse(completion.update(session.finishedExercise()));
        session.type(")");
        assertTrue(session.finishedExercise());
        assertTrue(completion.update(session.finishedExercise()));
        assertFalse(completion.update(session.finishedExercise()));
    }

}
