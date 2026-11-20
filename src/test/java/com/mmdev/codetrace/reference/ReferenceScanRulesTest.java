package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceScanRulesTest {
    @TempDir Path root;

    @Test void allAnalyzersShareExclusionsWithoutHidingReferenceTree() throws Exception {
        Files.createDirectories(root.resolve("reports"));
        Files.writeString(root.resolve("reports/Generated.java"), "class Generated {}");
        Files.createDirectories(root.resolve("src"));
        Files.writeString(root.resolve("src/Main.java"), "class Main {}");

        assertTrue(ReferenceScanRules.generatedPath("reports/Generated.java"));
        assertTrue(ReferenceScanRules.generatedPath("src/target/classes/Main.class"));
        assertFalse(ReferenceScanRules.generatedPath("src/reports2/Main.java"));
        assertFalse(ReferenceScanRules.generatedPath("src/Main.java"));

        var tree = ReferenceTreeIndex.scan(root);
        assertTrue(tree.hasFile("reports/Generated.java"),
                "Read-only explorer should still show generated files");
        assertFalse(tree.isWorkable("reports/Generated.java"));
        assertFalse(tree.recreationNodes().stream()
                .anyMatch(node -> node.path().startsWith("reports/")));

        var catalogue = ReferenceCatalogue.scan(root);
        assertFalse(catalogue.files().containsKey("reports/Generated.java"));
        assertTrue(catalogue.files().containsKey("src/Main.java"));
        var hits = ProjectTextSearch.search(root, null,
                new ProjectTextSearch.Options("Generated", false, false, false),
                () -> false);
        assertEquals(0, hits.size());
    }
}
