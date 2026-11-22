package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.CompletionSummary;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AssessableProgressTest {
    @TempDir Path root;

    @Test void completedFileWithDeliberateSkipsReachesOneHundredPercent() throws Exception {
        Files.writeString(root.resolve("Main.java"), "a{}");
        var cat = ReferenceCatalogue.scan(root);
        var info = cat.files().get("Main.java");
        var workspace = new VirtualWorkspace();
        workspace.createFile("Main.java");
        var typing = new TypingSession("a{}");
        typing.type("a");
        typing.skip(1, 3);
        var sessions = Map.of("Main.java", typing);
        var hashes = Map.of("Main.java", info.sha256());
        var totals = new ProgressAccumulator(cat, workspace, sessions, hashes,
                ProgressScope.defaults(), "", ProgressExclusions.empty(), true, false);
        assertEquals(1, typing.assessableVisibleCount());
        assertEquals(2, typing.deliberatelySkippedVisibleCount());
        assertEquals(1, totals.summary().totalUnits());
        assertEquals(1, totals.summary().correctUnits());
        assertEquals(1.0, totals.summary().ratio());
        assertTrue(typing.finishedExercise());
        assertTrue(CompletionSummary.message("Main.java", typing, 10000)
                .contains("Aciertos finales: 1 de 1"));

        typing.undoSkip(1, 3);
        totals.updateFile("Main.java", workspace, typing, info.sha256());
        assertEquals(3, totals.summary().totalUnits());
        assertEquals(1, totals.summary().correctUnits());
        assertEquals(1.0 / 3.0, totals.summary().ratio());
        typing.skip(1, 3);
        totals.updateFile("Main.java", workspace, typing, info.sha256());
        assertEquals(1.0, totals.summary().ratio());
    }

    @Test void reversibleDependencyOmissionChangesDenominatorWithoutErasingTyping()
            throws Exception {
        String source = "import java.util.List;\nclass App {}";
        Files.writeString(root.resolve("App.java"), source);
        var cat = ReferenceCatalogue.scan(root);
        var info = cat.files().get("App.java");
        var workspace = new VirtualWorkspace();
        workspace.createFile("App.java");
        var typing = new TypingSession(source);
        typing.setOmitDependencies(true, "App.java");
        assertTrue(typing.dependenciesOmitted());
        typing.type("class App {}");
        var sessions = Map.of("App.java", typing);
        var hashes = Map.of("App.java", info.sha256());
        var enabled = new ProgressAccumulator(cat, workspace, sessions, hashes,
                ProgressScope.defaults(), "", ProgressExclusions.empty(), true, true);
        assertTrue(typing.finishedExercise());
        assertEquals(typing.correctVisibleCount(), enabled.summary().totalUnits());
        assertEquals(1.0, enabled.summary().ratio());
        int typed = typing.correctVisibleCount();

        typing.setOmitDependencies(false, "App.java");
        var disabled = new ProgressAccumulator(cat, workspace, sessions, hashes,
                ProgressScope.defaults(), "", ProgressExclusions.empty(), true, false);
        assertFalse(typing.finishedExercise());
        assertTrue(disabled.summary().ratio() < 1.0);
        assertEquals(typed, typing.correctVisibleCount());
        typing.setOmitDependencies(true, "App.java");
        assertTrue(typing.finishedExercise());
        assertEquals(typed, typing.correctVisibleCount());
    }

    @Test void rulesCombineWithoutDoubleSubtractingAndRestoreIntoNewSession()
            throws Exception {
        String source = "// note\nimport java.util.List;\nA{}";
        Files.writeString(root.resolve("A.java"), source);
        var cat = ReferenceCatalogue.scan(root);
        var info = cat.files().get("A.java");
        var workspace = new VirtualWorkspace();
        workspace.createFile("A.java");
        var typing = new TypingSession(source);
        typing.setIncludeComments(false, "A.java");
        typing.setOmitDependencies(true, "A.java");
        typing.type("A");
        typing.skipTurboSymbols(); // skips {}
        typing.type("{}"); // harmless when caret at end
        var sessions = Map.of("A.java", typing);
        var hashes = Map.of("A.java", info.sha256());
        var result = new ProgressAccumulator(cat, workspace, sessions, hashes,
                ProgressScope.defaults(), "", ProgressExclusions.empty(), false, true);
        assertTrue(result.summary().correctUnits() <= result.summary().totalUnits());
        assertEquals(typing.assessableVisibleCount(), result.summary().totalUnits());
        assertTrue(result.summary().ratio() >= 0.0 && result.summary().ratio() <= 1.0);
    }
}
