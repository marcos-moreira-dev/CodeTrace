package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectActivationTest {
    @TempDir Path temporary;

    @Test void freshStartsWithNoSavedProgressOrSessionFile() {
        ProjectActivation fresh = ProjectActivation.fresh(temporary);
        assertEquals(temporary.toAbsolutePath().normalize(), fresh.root());
        assertEquals(PracticeMode.forProject(temporary), fresh.practiceMode());
        assertTrue(fresh.workspace().exists(""));
        assertTrue(fresh.sessions().isEmpty());
        assertTrue(fresh.hashes().isEmpty());
        assertTrue(fresh.graphLayouts().isEmpty());
        assertNull(fresh.sessionFile());
    }

    @Test void resumedKeepsVirtualStateAndFingerprintsButCanRelocateReference() {
        VirtualWorkspace workspace = new VirtualWorkspace();
        workspace.ensureFile("src/Main.java");
        TypingSession typing = new TypingSession("abc");
        typing.type("ab");
        Map<String, TypingSession> sessions = new LinkedHashMap<>();
        sessions.put("src/Main.java", typing);
        SessionArchive.State saved = new SessionArchive.State(
                temporary.resolve("old"), workspace, sessions,
                Map.of("src/Main.java", "a".repeat(64)),
                PracticeMode.DIRECT, Map.of());
        Path newRoot = temporary.resolve("relocated");
        Path archive = temporary.resolve("progress.ctrace");
        ProjectActivation resumed = ProjectActivation.resumed(newRoot, archive, saved);
        assertEquals(newRoot, resumed.root());
        assertEquals(archive, resumed.sessionFile());
        assertSame(workspace, resumed.workspace());
        assertSame(typing, resumed.sessions().get("src/Main.java"));
        assertEquals(2, resumed.sessions().get("src/Main.java").correctCount());
        assertEquals("a".repeat(64), resumed.hashes().get("src/Main.java"));
        assertEquals(PracticeMode.DIRECT, resumed.practiceMode());
        assertThrows(UnsupportedOperationException.class,
                () -> resumed.sessions().clear());
    }

    @Test void activationSnapshotsIncomingMaps() {
        var data = new LinkedHashMap<String, TypingSession>();
        var initial = new ProjectActivation(temporary, new VirtualWorkspace(),
                data, Map.of(), PracticeMode.RECONSTRUCTION, Map.of(), null);
        data.put("after", new TypingSession("a"));
        assertTrue(initial.sessions().isEmpty());
    }
}
