package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.reference.ProgressScope;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReconstructionStatusTest {
    private static final String HASH = "a".repeat(64);

    @Test
    void fileIsYellowAfterCreationAndGreenOnlyWhenEveryCharacterIsCorrect() {
        var workspace = new VirtualWorkspace();
        workspace.createDirectory("src");
        workspace.createFile("src/Main.java");
        var session = new TypingSession("ab{}");
        var files = new ReferenceCatalogue.Snapshot(Map.of(
                "src/Main.java", new ReferenceCatalogue.FileInfo(HASH, 4, 4)));
        var hashes = Map.of("src/Main.java", HASH);

        var states = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", session), hashes, files, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.CREATED, states.get("src/Main.java"));
        session.type("ab");
        session.skip(2, 4);
        states = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", session), hashes, files, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.CREATED, states.get("src/Main.java"));
        assertEquals(ReconstructionStatus.State.CREATED, states.get("src"));
        session.moveCaret(2);
        session.type("{}");
        states = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", session), hashes, files, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.COMPLETED, states.get("src/Main.java"));
        assertEquals(ReconstructionStatus.State.COMPLETED, states.get("src"));

        session.backspace();
        states = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", session), hashes, files, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.CREATED, states.get("src/Main.java"));
        assertEquals(ReconstructionStatus.State.CREATED, states.get("src"));
    }

    @Test
    void excludedDocumentationDoesNotPreventSourceFolderFromCompleting() {
        var workspace = new VirtualWorkspace();
        workspace.createDirectory("src");
        workspace.createFile("src/Main.java");
        var typing = new TypingSession("OK");
        typing.type("OK");
        var catalogue = new ReferenceCatalogue.Snapshot(Map.of(
                "src/Main.java", new ReferenceCatalogue.FileInfo(HASH, 2, 2),
                "src/notes.md", new ReferenceCatalogue.FileInfo(HASH, 4, 4)));
        var states = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", typing), Map.of("src/Main.java", HASH),
                catalogue, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.COMPLETED, states.get("src"));
        assertFalse(states.containsKey("src/notes.md")); // pending
        var withDocs = ReconstructionStatus.calculate(workspace,
                Map.of("src/Main.java", typing), Map.of("src/Main.java", HASH),
                catalogue, new ProgressScope(true, false, true, false));
        assertEquals(ReconstructionStatus.State.CREATED, withDocs.get("src"));
    }

    @Test
    void staleReferenceCannotLookFinished() {
        var workspace = new VirtualWorkspace();
        workspace.createFile("Main.java");
        var typing = new TypingSession("ab");
        typing.type("ab");
        var catalogue = new ReferenceCatalogue.Snapshot(Map.of(
                "Main.java", new ReferenceCatalogue.FileInfo(HASH, 2, 2)));
        var statuses = ReconstructionStatus.calculate(workspace,
                Map.of("Main.java", typing),
                Map.of("Main.java", "b".repeat(64)), catalogue, ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.CREATED, statuses.get("Main.java"));
    }

    @Test
    void emptyFilesHaveNoTranscriptionRequirementAndStayCreated() {
        var workspace = new VirtualWorkspace();
        workspace.createFile("Empty.java");
        var statuses = ReconstructionStatus.calculate(workspace, Map.of(), Map.of(),
                new ReferenceCatalogue.Snapshot(Map.of(
                        "Empty.java", new ReferenceCatalogue.FileInfo(HASH, 0, 0))),
                ProgressScope.defaults());
        assertEquals(ReconstructionStatus.State.CREATED, statuses.get("Empty.java"));
    }
}
