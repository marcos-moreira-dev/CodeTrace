package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceTreeIndexTest {
    @TempDir Path root;

    @Test
    void emptyAndBinaryOnlyFoldersOfferNoWorkableFiles() throws Exception {
        Files.createDirectory(root.resolve("assets"));
        Files.write(root.resolve("assets/photo.jpg"), new byte[] {1, 2, 3});
        Files.createDirectory(root.resolve("empty"));
        Files.writeString(root.resolve("empty/Empty.java"), "");
        var index = ReferenceTreeIndex.scan(root);
        assertFalse(index.hasWorkableDescendant("assets"));
        assertFalse(index.hasWorkableDescendant("empty"));
        assertFalse(index.isWorkable("empty/Empty.java"));
    }

    @Test
    void countsAllNestedFilesAndPreservesExactNames() throws Exception {
        Files.createDirectories(root.resolve("src/main"));
        Files.createDirectory(root.resolve("docs"));
        Files.writeString(root.resolve("src/Main.java"), "a");
        Files.writeString(root.resolve("src/main/B.java"), "b");
        Files.writeString(root.resolve("docs/README.md"), "# readme");
        Files.writeString(root.resolve("pom.xml"), "<project/>");
        var index = ReferenceTreeIndex.scan(root);
        assertEquals(4, index.recursiveFiles(""));
        assertEquals(2, index.recursiveFiles("src"));
        assertEquals(1, index.recursiveFiles("src/main"));
        assertEquals(1, index.recursiveFiles("docs"));
        assertTrue(index.hasFile("src/Main.java"));
        assertFalse(index.hasFile("src/Missing.java"));
        assertFalse(index.hasFile("src/main.java"));
        assertTrue(index.hasDirectory("src/main"));
        assertEquals(2, index.children("src").size());
        assertTrue(index.children("").contains("pom.xml"));
        assertTrue(index.hasWorkableDescendant(""));
        assertTrue(index.hasWorkableDescendant("src"));
        assertTrue(index.isWorkable("src/Main.java"));
        assertFalse(index.isWorkable("pom.xml"));
    }

    @Test
    void autoRecreationPlanPreservesEmptyFoldersAndSkipsGeneratedOutput() throws Exception {
        Files.createDirectories(root.resolve("src/empty"));
        Files.createDirectories(root.resolve("src/code"));
        Files.writeString(root.resolve("src/code/Main.java"), "class Main {}");
        Files.createDirectories(root.resolve("target/classes"));
        Files.writeString(root.resolve("target/classes/Main.class"), "bytecode");
        var nodes = ReferenceTreeIndex.scan(root).recreationNodes();
        assertTrue(nodes.contains(new ReferenceTreeIndex.Node("src/empty", true)));
        assertTrue(nodes.contains(new ReferenceTreeIndex.Node("src/code/Main.java", false)));
        assertFalse(nodes.stream().anyMatch(n -> n.path().startsWith("target/")));
        int parent = nodes.indexOf(new ReferenceTreeIndex.Node("src/code", true));
        int child = nodes.indexOf(new ReferenceTreeIndex.Node("src/code/Main.java", false));
        assertTrue(parent >= 0 && child > parent, "Virtual folders must precede files");
    }
}
