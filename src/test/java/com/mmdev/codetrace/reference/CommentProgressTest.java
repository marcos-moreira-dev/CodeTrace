package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.ReconstructionStatus;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CommentProgressTest {
    @TempDir Path root;

    @Test void projectAndCompletionUseSameCodeOnlyDenominator() throws Exception {
        Files.writeString(root.resolve("Main.java"), "// note\nint x=1;");
        var catalogue = ReferenceCatalogue.scan(root);
        var info = catalogue.files().get("Main.java");
        assertEquals(15, info.typeableUnits());
        assertEquals(8, info.requiredUnits(false));
        var workspace = new VirtualWorkspace();
        workspace.createFile("Main.java");
        var session = new TypingSession("// note\nint x=1;");
        session.setIncludeComments(false, "Main.java");
        session.type("int x=1;");
        var sessions = Map.of("Main.java", session);
        var hashes = Map.of("Main.java", info.sha256());
        var progress = new ProgressAccumulator(catalogue, workspace, sessions,
                hashes, ProgressScope.defaults(), "", ProgressExclusions.empty(), false);
        assertEquals(8, progress.summary().totalUnits());
        assertEquals(8, progress.summary().correctUnits());
        assertEquals(1.0, progress.summary().ratio());
        var status = ReconstructionStatus.calculate(workspace, sessions, hashes,
                catalogue, ProgressScope.defaults(), ProgressExclusions.empty(), false);
        assertEquals(ReconstructionStatus.State.COMPLETED, status.get("Main.java"));
        assertFalse(progress.matches(catalogue, ProgressScope.defaults(), "",
                ProgressExclusions.empty(), true));
        session.setIncludeComments(true, "Main.java");
        var withComments = new ProgressAccumulator(catalogue, workspace, sessions,
                hashes, ProgressScope.defaults(), "", ProgressExclusions.empty(), true);
        assertEquals(15, withComments.summary().totalUnits());
        assertEquals(8, withComments.summary().correctUnits());
        assertFalse(session.finishedExercise());
    }
}
