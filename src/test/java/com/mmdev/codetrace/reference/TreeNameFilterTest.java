package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TreeNameFilterTest {
    @TempDir Path project;

    @Test void matchesOnlyNamesRetainsParentsAndDoesNotReadText() throws Exception {
        Path docs = Files.createDirectories(project.resolve("docs/screenshots"));
        Files.writeString(docs.resolve("Captura Mundial.PNG"), "image");
        Files.writeString(project.resolve("Other.java"), "hidden-word-island");
        VirtualWorkspace workspace = new VirtualWorkspace();
        var index = ReferenceTreeIndex.scan(project);
        var visible = TreeNameFilter.visible(index, workspace, "captura");
        assertTrue(visible.contains(""));
        assertTrue(visible.contains("docs"));
        assertTrue(visible.contains("docs/screenshots"));
        assertTrue(visible.contains("docs/screenshots/Captura Mundial.PNG"));
        assertFalse(visible.contains("Other.java"),
                "Search does not inspect the contents of source files");
        assertFalse(visible.contains("docs/screenshots/not-here.png"));
    }

    @Test void caseAndAccentsAreNormalized() throws Exception {
        Files.writeString(project.resolve("ÁRBOL.md"), "hola");
        var visible = TreeNameFilter.visible(ReferenceTreeIndex.scan(project),
                new VirtualWorkspace(), "arbol");
        assertTrue(visible.contains("ÁRBOL.md"));
    }

    @Test void virtualEntriesCanAlsoBeLocated() throws Exception {
        var workspace = new VirtualWorkspace();
        workspace.createDirectory("generated");
        workspace.createFile("generated/Test.java");
        var visible = TreeNameFilter.visible(ReferenceTreeIndex.scan(project),
                workspace, "test.java");
        assertTrue(visible.contains("generated"));
        assertTrue(visible.contains("generated/Test.java"));
    }
}
