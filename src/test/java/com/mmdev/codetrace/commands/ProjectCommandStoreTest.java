package com.mmdev.codetrace.commands;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectCommandStoreTest {
    @TempDir Path temp;

    @Test
    void writesCommandsPerProjectAndPreservesSeveralPresets() throws Exception {
        var store = new ProjectCommandStore(temp.resolve("settings"));
        Path a = Files.createDirectory(temp.resolve("project-A"));
        Path b = Files.createDirectory(temp.resolve("project-B"));
        var one = new ProjectCommandStore.Preset(
                "Build", ProjectCommandStore.Shell.CMD, "mvn clean verify", ".");
        var two = new ProjectCommandStore.Preset(
                "Run", ProjectCommandStore.Shell.POWERSHELL, "mvn javafx:run", ".");
        store.save(a, List.of(one, two));
        assertEquals(List.of(one, two), store.load(a));
        assertTrue(store.load(b).isEmpty());
        assertTrue(Files.exists(a));
        assertEquals(0, Files.list(a).count(), "Never writes command files inside the project");
    }

    @Test
    void constrainsCommandWorkingDirectoryToSelectedProject() throws Exception {
        Path root = Files.createDirectory(temp.resolve("source"));
        Path inside = Files.createDirectory(root.resolve("src"));
        assertEquals(inside, ProjectCommandStore.workingDirectory(
                root, new ProjectCommandStore.Preset(
                        "Test", ProjectCommandStore.Shell.SH, "echo ok", "src")));
        assertThrows(java.io.IOException.class,
                () -> ProjectCommandStore.workingDirectory(root,
                        new ProjectCommandStore.Preset("No", ProjectCommandStore.Shell.SH,
                                "echo a", "../")));
    }

    @Test
    void noAutoExecutionIsTriggeredBySavingAndLoading() throws Exception {
        var store = new ProjectCommandStore(temp.resolve("storage"));
        Path project = Files.createDirectory(temp.resolve("project"));
        var preset = new ProjectCommandStore.Preset(
                "Example", ProjectCommandStore.Shell.SH, "echo hello", ".");
        store.save(project, List.of(preset));
        assertEquals("echo hello", store.load(project).getFirst().command());
    }
}
