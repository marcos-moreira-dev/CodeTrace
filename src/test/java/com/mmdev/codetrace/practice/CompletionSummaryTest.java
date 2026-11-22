package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CompletionSummaryTest {
    @Test void reportsCorrectVisibleSymbolsAndMeasuredMeanSpeed() {
        TypingSession typing = new TypingSession("abc\nxy\n");
        typing.type("abc");
        typing.moveCaret(4);
        typing.type("xy");
        assertTrue(typing.finishedExercise());
        String message = CompletionSummary.message("codigo.ps1", typing, 6_000);
        assertTrue(message.contains("codigo.ps1"));
        assertTrue(message.contains("Aciertos finales: 5 de 5"));
        assertTrue(message.contains("50 caracteres por minuto (CPM)"));
        assertTrue(message.contains("00:00:06"));
        assertFalse(message.contains("omitidos deliberadamente"));
    }

    @Test void correctCorrectionsCountAndShortExercisesDontGiveInflatedSpeed() {
        TypingSession typing = new TypingSession("AB");
        typing.type("Ax");
        assertFalse(typing.finishedExercise());
        typing.backspace();
        typing.type("B");
        assertTrue(typing.finishedExercise());
        String message = CompletionSummary.message("corregido.c", typing, 500);
        assertTrue(message.contains("Aciertos finales: 2 de 2"));
        assertTrue(message.contains("No calculable (práctica muy breve)"));
    }

    @Test void deliberateSkipsAreDisplayedSeparatelyFromAciertos() {
        TypingSession typing = new TypingSession("a{}");
        typing.type("a");
        typing.skip(1, 3);
        assertTrue(typing.finishedExercise());
        String message = CompletionSummary.message("fuente.java", typing, 20_000);
        assertTrue(message.contains("Aciertos finales: 1 de 1"));
        assertTrue(message.contains("Caracteres omitidos deliberadamente: 2"));
    }
    @Test void restoredFileUsesOnlyNewTypingAttemptsForAverageSpeed() {
        TypingSession typing = new TypingSession("123456789");
        typing.type("12345678");
        typing.type("9");
        assertTrue(typing.finishedExercise());
        // This opening only typed the last glyph: 1 accepted character over 6 s.
        // Existing correct text must never inflate that measured speed.
        String message = CompletionSummary.message("restaurado.java", typing,
                6_000, 1, 1);
        assertTrue(message.contains("Aciertos finales: 9 de 9"));
        assertTrue(message.contains("10 caracteres por minuto (CPM)"));
        assertTrue(message.contains("Errores al teclear durante esta práctica: 1"));
    }

}
