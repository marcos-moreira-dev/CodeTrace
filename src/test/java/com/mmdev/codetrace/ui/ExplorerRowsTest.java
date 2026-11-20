package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.workspace.VirtualChildrenIndex;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExplorerRowsTest {
    @TempDir Path root;

    @Test
    void projectsFoldersBeforeFilesWithoutJavaFxOrFileContentReading() throws Exception {
        Path src = Files.createDirectories(root.resolve("src/nested"));
        Files.writeString(src.resolve("Example.java"), "class Example {}");
        Files.writeString(root.resolve("README.md"), "# Demo");
        var index = ReferenceTreeIndex.scan(root);
        var workspace = new VirtualWorkspace();
        workspace.createDirectory("virtual");
        workspace.createFile("virtual/Draft.java");
        var virtual = VirtualChildrenIndex.snapshot(workspace);
        var expanded = ExplorerRows.allDirectories(index, virtual);
        var result = ExplorerRows.project(index, virtual, expanded, Set.of());
        assertFalse(result.truncated());
        assertEquals(7, result.rows().size());
        assertEquals("", result.rows().get(0).relative());
        assertEquals("src", result.rows().get(1).relative());
        assertEquals("src/nested", result.rows().get(2).relative());
        assertEquals("src/nested/Example.java", result.rows().get(3).relative());
        assertEquals("virtual", result.rows().get(4).relative());
        assertEquals("virtual/Draft.java", result.rows().get(5).relative());
        assertEquals("README.md", result.rows().get(6).relative());

        var folded = ExplorerRows.project(index, virtual, Set.of(""), Set.of());
        assertEquals(4, folded.rows().size()); // root, src, virtual, README
        assertFalse(folded.rows().stream().anyMatch(
                row -> row.relative().contains("nested")));
        var filtered = ExplorerRows.project(index, virtual, expanded,
                Set.of("", "src", "src/nested", "src/nested/Example.java"));
        assertEquals(4, filtered.rows().size());
        assertEquals("src/nested/Example.java",
                filtered.rows().getLast().relative());
    }

    @Test
    void largeVirtualReferenceDoesNotMaterializeTreeItemsOrTouchDisk() throws Exception {
        var index = ReferenceTreeIndex.scan(root);
        var workspace = new VirtualWorkspace();
        final int folders = 200;
        final int filesPerFolder = 40;
        for (int i = 0; i < folders; i++) {
            String folder = "area" + i;
            workspace.createDirectory(folder);
            for (int j = 0; j < filesPerFolder; j++)
                workspace.createFile(folder + "/File" + j + ".java");
        }
        var virtual = VirtualChildrenIndex.snapshot(workspace);
        var all = ExplorerRows.project(index, virtual,
                ExplorerRows.allDirectories(index, virtual), Set.of());
        assertFalse(all.truncated());
        assertEquals(1 + folders + folders * filesPerFolder, all.rows().size());
        assertEquals(0, index.fileCount(),
                "Projection should work entirely in RAM without filesystem files");
        var collapsed = ExplorerRows.project(index, virtual, Set.of(""), Set.of());
        assertEquals(1 + folders, collapsed.rows().size());
    }
}
