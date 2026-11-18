package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectTextSearchTest {
    @TempDir Path root;

    @Test void findsFileOrProjectMatchesWithoutWriting() throws Exception {
        Path docs = Files.createDirectories(root.resolve("docs"));
        Path javaFile = Files.writeString(root.resolve("Main.java"),
                "public class Main {\n // public method\n}\n");
        Path readme = Files.writeString(docs.resolve("README.md"),
                "The word public appears in documentation.");
        byte[] original = Files.readAllBytes(javaFile);
        var options = new ProjectTextSearch.Options("public", false, false, false);
        var projectHits = ProjectTextSearch.search(root, null, options, () -> false);
        assertEquals(3, projectHits.size());
        var mainHit = projectHits.stream()
                .filter(hit -> hit.relative().equals("Main.java")).findFirst().orElseThrow();
        assertEquals(1, mainHit.line());
        assertEquals(1, mainHit.column());
        assertEquals(1, ProjectTextSearch.search(root, "docs/README.md",
                options, () -> false).size());
        assertArrayEquals(original, Files.readAllBytes(javaFile));
        assertEquals("The word public appears in documentation.", Files.readString(readme));
    }

    @Test void supportsWholeWordsCaseAndRegex() throws Exception {
        Files.writeString(root.resolve("Words.java"),
                "cat cats CAT concatenate\nclass Demo {}\n");
        var plain = new ProjectTextSearch.Options("cat", false, true, true);
        assertEquals(1, ProjectTextSearch.search(root, null, plain, () -> false).size());
        var folded = new ProjectTextSearch.Options("cat", false, false, true);
        assertEquals(2, ProjectTextSearch.search(root, null, folded, () -> false).size());
        var pattern = new ProjectTextSearch.Options("class\\s+\\w+", true, false, false);
        assertEquals(1, ProjectTextSearch.search(root, null, pattern, () -> false).size());
        assertThrows(IllegalArgumentException.class, () ->
                ProjectTextSearch.search(root, null,
                        new ProjectTextSearch.Options("[", true, false, false), () -> false));
    }

    @Test void skipsGeneratedFilesAndRejectsOutsideReferences() throws Exception {
        Path generated = Files.createDirectories(root.resolve("node_modules"));
        Files.writeString(generated.resolve("library.js"), "TOKEN");
        Files.writeString(root.resolve("main.java"), "TOKEN");
        var options = new ProjectTextSearch.Options("TOKEN", false, false, false);
        assertEquals(1, ProjectTextSearch.search(root, null, options, () -> false).size());
        assertThrows(java.io.IOException.class, () ->
                ProjectTextSearch.search(root, "../outside.java", options, () -> false));
        assertTrue(ProjectTextSearch.search(root, null, options, () -> true).isEmpty());
    }
}
