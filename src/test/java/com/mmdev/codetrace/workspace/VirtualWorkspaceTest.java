package com.mmdev.codetrace.workspace;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class VirtualWorkspaceTest {
    @Test
    void createsRenamesCopiesAndDeletesOnlyVirtualFiles() {
        VirtualWorkspace project = new VirtualWorkspace();
        project.createDirectory("src");
        project.createDirectory("src/main");
        project.createFile("src/main/Main.java");
        project.setText("src/main/Main.java", "class Main {}");
        project.move("src/main", "src/java");
        assertEquals("class Main {}", project.text("src/java/Main.java"));
        assertFalse(project.exists("src/main/Main.java"));
        project.copy("src/java", "src/backup");
        assertEquals("class Main {}", project.text("src/backup/Main.java"));
        project.delete("src/java");
        assertTrue(project.exists("src/backup/Main.java"));
        assertFalse(project.exists("src/java/Main.java"));
    }

    @Test
    void rejectsTraversalCaseCollisionsAndMissingParents() {
        VirtualWorkspace project = new VirtualWorkspace();
        project.createDirectory("src");
        project.createFile("src/Main.java");
        assertThrows(IllegalArgumentException.class, () -> project.createFile("src/main.java"));
        assertThrows(IllegalArgumentException.class, () -> project.createFile("../escape"));
        assertThrows(IllegalArgumentException.class, () -> project.createFile("a/b/c.java"));
        assertThrows(IllegalArgumentException.class, () -> project.move("src", "src/nested"));
    }

    @Test
    void snapshotRestoresTreeAndContents() {
        VirtualWorkspace project = new VirtualWorkspace();
        project.createDirectory(".github");
        project.createFile(".github/workflow.yml");
        project.setText(".github/workflow.yml", "name: CI");
        var copy = VirtualWorkspace.fromEntries(project.entries());
        assertEquals("name: CI", copy.text(".github/workflow.yml"));
        assertEquals(project.entries().size(), copy.entries().size());
    }
}
