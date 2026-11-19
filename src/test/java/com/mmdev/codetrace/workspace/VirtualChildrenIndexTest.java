package com.mmdev.codetrace.workspace;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class VirtualChildrenIndexTest {
    @Test void groupsImmediateChildrenWithoutMutatingOriginalWorkspace() {
        VirtualWorkspace workspace = new VirtualWorkspace();
        workspace.createDirectory("src");
        workspace.createDirectory("src/main");
        workspace.createFile("src/main/App.java");
        workspace.createFile("README.md");
        VirtualChildrenIndex first = VirtualChildrenIndex.snapshot(workspace);
        assertEquals(java.util.Set.of("src", "README.md"),
                java.util.Set.copyOf(first.childrenOf("")));
        assertEquals(java.util.List.of("src/main"), first.childrenOf("src"));
        assertEquals(java.util.List.of("src/main/App.java"),
                first.childrenOf("src/main"));
        assertTrue(first.hasDirectoryChild("src"));
        assertFalse(first.hasDirectoryChild("src/main"));
        assertTrue(first.hasDirectoryChild(""));
        workspace.createFile("src/main/Other.java");
        assertEquals(1, first.childrenOf("src/main").size(),
                "Snapshot must remain stable after changes");
        assertEquals(2, VirtualChildrenIndex.snapshot(workspace)
                .childrenOf("src/main").size());
    }
}
