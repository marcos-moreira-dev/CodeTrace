package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

class CommentPracticeModeTest {
    @Test void togglingKeepsStoredSlotsButExcludesCommentsFromRequirements() {
        String reference = "// note\nint a=1;";
        TypingSession s = new TypingSession(reference);
        assertEquals(15, s.visibleUnitCount());
        s.setIncludeComments(false, "Main.java");
        assertEquals(8, s.visibleUnitCount());
        assertEquals(8, s.caret()); // entire comment line, including structural newline
        s.type("int a=1;");
        assertTrue(s.finishedExercise());
        assertEquals(8, s.correctVisibleCount());
        assertEquals(0, s.skippedCount(), "The preference is not a stored skip");
        List<String> slots = new java.util.ArrayList<>(s.enteredSlots());
        s.setIncludeComments(true, "Main.java");
        assertEquals(15, s.visibleUnitCount());
        assertEquals(8, s.correctVisibleCount());
        assertFalse(s.finishedExercise());
        assertEquals(slots, s.enteredSlots());
        s.setIncludeComments(false, "Main.java");
        assertTrue(s.finishedExercise());
        TypingSession restored = TypingSession.restore(reference,
                s.enteredSlots(), s.skippedSlots(), s.caret());
        restored.setIncludeComments(false, "Main.java");
        assertTrue(restored.finishedExercise());
    }

    @Test void allCommentsCanBeSkippedWithoutChangingSavedReference() {
        TypingSession source = new TypingSession("# hola\n");
        source.setIncludeComments(false, "demo.py");
        assertEquals(0, source.visibleUnitCount());
        assertTrue(source.finishedExercise());
        assertEquals("# hola\n", source.expectedText());
        assertEquals(0, source.skippedSlots().size());
        source.setIncludeComments(true, "demo.py");
        assertFalse(source.finishedExercise());
    }

    @Test void inlineCommentDoesNotSwallowNextSourceLine() {
        TypingSession s = new TypingSession("a=1; // note\nb=2;");
        s.setIncludeComments(false, "main.js");
        assertEquals(9, s.visibleUnitCount()); // code plus space before inline comment
        s.type("a=1; ");
        s.type("b=2;");
        assertTrue(s.finishedExercise());
    }

    @Test void powershellAndAsmAreRealTrainingTargets() {
        TypingSession ps = new TypingSession("Write-Host 1 # annotation\n");
        ps.setIncludeComments(false, "demo.ps1");
        assertEquals("Write-Host 1 ".length(), ps.visibleUnitCount());
        TypingSession asm = new TypingSession("mov rax,1 ; annotation\n");
        asm.setIncludeComments(false, "demo.asm");
        assertEquals("mov rax,1 ".length(), asm.visibleUnitCount());
    }
}
