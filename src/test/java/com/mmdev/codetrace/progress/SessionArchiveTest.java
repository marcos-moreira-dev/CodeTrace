package com.mmdev.codetrace.progress;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SessionArchiveTest {
    @TempDir Path directory;

    @Test
    void roundTripsVirtualWorkspaceAndSparseGraphemes() throws Exception {
        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createDirectory("src");
        virtual.createFile("src/Main.java");
        TypingSession typing = new TypingSession("í\nclass Main {}");
        typing.type("i\u0301\n");
        typing.skip(2, 7);
        virtual.setText("src/Main.java", typing.enteredText());

        String sha = "a".repeat(64);
        SessionArchive.State state = new SessionArchive.State(
                directory.resolve("reference"), virtual,
                Map.of("src/Main.java", typing), Map.of("src/Main.java", sha));
        Path file = directory.resolve("training.ctrace");
        SessionArchive.save(file, state);

        var restored = SessionArchive.load(file);
        assertEquals(state.referenceRoot(), restored.referenceRoot());
        assertEquals(2, restored.sessions().get("src/Main.java").correctCount());
        assertEquals(5, restored.sessions().get("src/Main.java").skippedCount());
        assertEquals(7, restored.sessions().get("src/Main.java").resolvedCount());
        assertEquals("i\u0301\n", restored.workspace().text("src/Main.java"));
        assertEquals(sha, restored.referenceHashes().get("src/Main.java"));
    }

    @Test
    void rejectsUnknownOrCorruptArchive() throws Exception {
        Path file = directory.resolve("bad.ctrace");
        Files.write(file, new byte[]{1, 2, 3, 4, 5, 6, 7, 8});
        assertThrows(IOException.class, () -> SessionArchive.load(file));
    }

    @Test
    void subsequentSaveReplacesPriorSession() throws Exception {
        VirtualWorkspace virtual = new VirtualWorkspace();
        Path file = directory.resolve("training.ctrace");
        SessionArchive.save(file, new SessionArchive.State(directory, virtual,
                Map.of(), Map.of()));
        virtual.createDirectory("src");
        SessionArchive.save(file, new SessionArchive.State(directory, virtual,
                Map.of(), Map.of()));
        assertTrue(SessionArchive.load(file).workspace().exists("src"));
    }
}
