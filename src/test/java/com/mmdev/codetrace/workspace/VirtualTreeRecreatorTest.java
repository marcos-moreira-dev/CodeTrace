package com.mmdev.codetrace.workspace;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;

class VirtualTreeRecreatorTest {
    @TempDir Path reference;

    @Test void createsOnlyVirtualNodesAcrossBoundedBatches() throws Exception {
        Path source = Files.createDirectories(reference.resolve("src"));
        Path original = Files.writeString(source.resolve("Main.java"), "class Main {}");
        byte[] originalBytes = Files.readAllBytes(original);
        var workspace = new VirtualWorkspace();
        var index = ReferenceTreeIndex.scan(reference);
        var recreator = new VirtualTreeRecreator(workspace, index.recreationNodes());
        var first = recreator.nextBatch(1);
        assertEquals(1, first.processed());
        assertFalse(first.finished());
        assertEquals(1, first.created());
        var rest = recreator.nextBatch(150);
        assertTrue(rest.finished());
        assertEquals(2, rest.created());
        assertEquals(0, rest.omitted());
        assertTrue(workspace.exists("src"));
        assertTrue(workspace.exists("src/Main.java"));
        assertArrayEquals(originalBytes, Files.readAllBytes(original));

        var replay = new VirtualTreeRecreator(workspace, index.recreationNodes());
        assertEquals(0, replay.nextBatch(150).created());
        assertThrows(IllegalArgumentException.class, () -> replay.nextBatch(0));
    }

    @Test void limitsHugeRecreationPlansWithoutDroppingNodeOrdering() {
        var workspace = new VirtualWorkspace();
        java.util.List<ReferenceTreeIndex.Node> nodes = new java.util.ArrayList<>();
        for (int i = 0; i < 10_050; i++)
            nodes.add(new ReferenceTreeIndex.Node("file-" + i + ".java", false));
        var recreator = new VirtualTreeRecreator(workspace, nodes);
        var progress = recreator.nextBatch(12_000);
        assertTrue(progress.finished());
        assertEquals(10_000, progress.created());
        assertEquals(50, progress.omitted());
    }
}
