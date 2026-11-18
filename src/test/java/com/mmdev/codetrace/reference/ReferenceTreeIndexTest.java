package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceTreeIndexTest {
    @TempDir Path root;

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
    }
}
