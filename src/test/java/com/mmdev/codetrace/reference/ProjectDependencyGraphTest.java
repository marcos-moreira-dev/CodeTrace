package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectDependencyGraphTest {
    @TempDir Path root;

    @Test
    void resolvesOnlyActualInternalDependenciesAcrossLanguages() throws Exception {
        Path java = Files.createDirectories(root.resolve("src/main/java/demo"));
        Files.writeString(java.resolve("A.java"),
                "package demo;\nimport demo.B;\nimport java.util.List;\nclass A {}");
        Files.writeString(java.resolve("B.java"), "package demo;\nclass B {}");
        Path typescript = Files.createDirectories(root.resolve("web"));
        Files.writeString(typescript.resolve("entry.ts"),
                "import {size} from './utils';\nimport {readFile} from 'fs';");
        Files.writeString(typescript.resolve("utils.ts"),
                "export const size = 5;");
        Path cpp = Files.createDirectories(root.resolve("native"));
        Files.writeString(cpp.resolve("main.cpp"),
                "#include \"util.h\"\n#include <iostream>\nint main() {}");
        Files.writeString(cpp.resolve("util.h"), "int util();");
        Path python = Files.createDirectories(root.resolve("py"));
        Files.writeString(python.resolve("app.py"), "import helper\nimport json\n");
        Files.writeString(python.resolve("helper.py"), "def main(): pass");

        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(4, graph.edges().size());
        assertTrue(graph.edges().stream().anyMatch(e ->
                e.source().endsWith("A.java") && e.target().endsWith("B.java")));
        assertTrue(graph.edges().stream().anyMatch(e ->
                e.source().endsWith("entry.ts") && e.target().endsWith("utils.ts")));
        assertTrue(graph.edges().stream().anyMatch(e ->
                e.source().endsWith("main.cpp") && e.target().endsWith("util.h")));
        assertTrue(graph.edges().stream().anyMatch(e ->
                e.source().endsWith("app.py") && e.target().endsWith("helper.py")));
        assertFalse(graph.nodes().stream().anyMatch(s -> s.contains("java.util.List")));
    }

    @Test
    void analysesOnlySelectedFolderAndHandlesMissingGraph() throws Exception {
        Path a = Files.createDirectories(root.resolve("a"));
        Path b = Files.createDirectories(root.resolve("b"));
        Files.writeString(a.resolve("x.ts"), "import './y';");
        Files.writeString(b.resolve("y.ts"), "export const y = 1;");
        assertTrue(ProjectDependencyGraph.analyze(root, a, () -> false).edges().isEmpty(),
                "Out-of-scope imports are not drawn as false edges");
        assertTrue(ProjectDependencyGraph.analyze(root, root, () -> true).edges().isEmpty());
    }

    @Test
    void resolvesGoPackageImportsAsDirectoryNodesWhenModuleExists()
            throws Exception {
        Files.writeString(root.resolve("go.mod"), "module example.com/demo\n");
        Path pkg = Files.createDirectory(root.resolve("utility"));
        Files.writeString(pkg.resolve("math.go"), "package utility\n");
        Files.writeString(root.resolve("app.go"),
                "package main\nimport \"example.com/demo/utility\"\nfunc main() {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertTrue(graph.edges().stream().anyMatch(e ->
                e.source().equals("app.go") && e.target().equals("utility")));
    }

    @Test
    void boundsVeryLargeGraphsToAvoidOversizedCanvas() throws Exception {
        for (int i = 0; i < 205; i++) {
            String dependency = i < 204 ? "import './node" + (i + 1) + "';" : "";
            Files.writeString(root.resolve("node" + i + ".ts"), dependency);
        }
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertTrue(graph.truncated(), "UI must explicitly disclose a partial graph");
        assertTrue(graph.nodes().size() <= 180);
        assertTrue(graph.edges().size() <= 400);
    }

    @Test
    void referenceContentChangesInvalidateStoredNodePositions() throws Exception {
        Files.writeString(root.resolve("one.ts"), "import './two';\nconst a = 1;");
        Path dependency = Files.writeString(root.resolve("two.ts"), "export const b = 2;");
        var before = ProjectDependencyGraph.analyze(root, root, () -> false);
        Files.writeString(dependency, "export const b = 3;");
        var after = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(before.edges(), after.edges());
        assertNotEquals(before.fingerprint(), after.fingerprint(),
                "Changing a source must reset any saved positions");
    }
}
