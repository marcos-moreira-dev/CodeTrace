package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DependencyToggleTest {
    @Test void togglingMaintainsPreviouslyTypedCharactersAndDeliberateSkips() {
        TypingSession session = new TypingSession("import A;\nclass B {}");
        session.setOmitDependencies(true, "Example.java");
        assertEquals(0, session.skippedCount());
        session.type("class B ");
        int correct = session.correctVisibleCount();
        session.setOmitDependencies(false, "Example.java");
        assertFalse(session.finishedExercise());
        assertEquals(correct, session.correctVisibleCount());
        session.setOmitDependencies(true, "Example.java");
        assertEquals(correct, session.correctVisibleCount());
        assertEquals("import A;\nclass B {}", session.expectedText());
        assertEquals(0, session.skippedCount());
    }

    @Test void goImportBlockIsExcludedButGoSourceRemainsExercisable() {
        String src = "package main\nimport (\n  \"fmt\"\n)\nfunc main() {}";
        TypingSession s = new TypingSession(src);
        s.setOmitDependencies(true, "main.go");
        assertTrue(s.dependenciesOmitted());
        assertTrue(s.visibleUnitCount() < new TypingSession(src).visibleUnitCount());
        assertTrue(s.visibleUnitCount() > 0);
    }
}
