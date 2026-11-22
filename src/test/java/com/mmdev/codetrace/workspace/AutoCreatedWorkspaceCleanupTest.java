package com.mmdev.codetrace.workspace;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AutoCreatedWorkspaceCleanupTest {
    @Test void reversibleWithoutDeletingHandmadeOrTypedNodes() {
        var w = new VirtualWorkspace();
        w.createDirectory("src");
        w.createFile("src/Manual.java");
        w.createFile("src/AutoEmpty.java");
        w.createFile("src/AutoTyped.java");
        w.setText("src/AutoTyped.java", "clase");
        assertEquals(1, AutoCreatedWorkspaceCleanup.removeUnused(w,
                Set.of("src/AutoEmpty.java", "src/AutoTyped.java"), Map.of()));
        assertFalse(w.exists("src/AutoEmpty.java"));
        assertTrue(w.exists("src/AutoTyped.java"));
        assertTrue(w.exists("src/Manual.java"));
    }

    @Test void retainsParentOfProtectedChildAndDeletesEmptyBranch() {
        var w = new VirtualWorkspace();
        w.createDirectory("empty");
        w.createFile("empty/File.java");
        w.createDirectory("keep");
        w.createFile("keep/Progress.java");
        var progress = new TypingSession("hello");
        progress.type("h");
        assertEquals(2, AutoCreatedWorkspaceCleanup.removeUnused(w,
                Set.of("empty", "empty/File.java", "keep", "keep/Progress.java"),
                Map.of("keep/Progress.java", progress)));
        assertFalse(w.exists("empty"));
        assertTrue(w.exists("keep/Progress.java"));
        assertTrue(w.exists("keep"));
    }

    @Test void neverErasesImportedSessionsWithoutProvenance() {
        var w = new VirtualWorkspace();
        w.ensureFile("pkg/A.java");
        assertEquals(0, AutoCreatedWorkspaceCleanup.removeUnused(w, Set.of(), Map.of()));
        assertTrue(w.exists("pkg/A.java"));
    }
}
