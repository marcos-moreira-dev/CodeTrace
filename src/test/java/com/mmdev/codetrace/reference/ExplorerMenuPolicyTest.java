package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.workspace.VirtualChildrenIndex;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExplorerMenuPolicyTest {
    @TempDir Path root;

    @Test void availabilityTracksRealReferenceAndVirtualProgress() throws Exception {
        Files.createDirectories(root.resolve("src/nested"));
        Files.writeString(root.resolve("src/A.java"), "class A {}");
        Files.writeString(root.resolve("src/nested/B.java"), "class B {}");
        Files.writeString(root.resolve("README.md"), "# docs");
        var index = ReferenceTreeIndex.scan(root);
        var workspace = new VirtualWorkspace();
        var children = VirtualChildrenIndex.snapshot(workspace);

        var rootActions = ExplorerMenuPolicy.forPath("", true, index, children, workspace);
        assertTrue(rootActions.recursiveExpansion());
        assertTrue(rootActions.createSubfolder());
        assertFalse(rootActions.createThisFolder());
        assertTrue(rootActions.progressScope());

        var pending = ExplorerMenuPolicy.forPath("src", true, index, children, workspace);
        assertTrue(pending.createThisFolder());
        assertTrue(pending.createSubfolder());
        assertTrue(pending.createFile());
        assertTrue(pending.recursiveExpansion());

        var file = ExplorerMenuPolicy.forPath("src/A.java", false, index, children, workspace);
        assertTrue(file.createThisFile());
        assertFalse(file.progressScope());

        workspace.createDirectory("src");
        workspace.createFile("src/A.java");
        children = VirtualChildrenIndex.snapshot(workspace);
        assertFalse(ExplorerMenuPolicy.forPath("src/A.java", false, index,
                children, workspace).createThisFile());
        assertFalse(ExplorerMenuPolicy.forPath("src", true, index,
                children, workspace).createThisFolder());
        assertFalse(ExplorerMenuPolicy.forPath("README.md", false, index,
                children, workspace).createThisFile());
    }
}
