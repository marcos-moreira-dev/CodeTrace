package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Directory graph semantics: containment is not a source dependency. */
class ProjectDependencyGraphTest {
    @TempDir Path root;

    @Test
    void resolvesExplicitCrossDirectoryDependenciesAcrossLanguages() throws Exception {
        Path java = Files.createDirectories(root.resolve("src/demo"));
        Files.writeString(java.resolve("B.java"), "package demo; public class B {}");
        Files.writeString(Files.createDirectories(root.resolve("src/client")).resolve("A.java"),
                "package client;\nimport demo.B;\nimport java.util.List;\nclass A {}");

        Path web = Files.createDirectories(root.resolve("web"));
        Files.writeString(web.resolve("utils.ts"), "export const size = 5;");
        Files.writeString(Files.createDirectories(web.resolve("ui")).resolve("entry.ts"),
                "import {size} from '../utils';\nimport {readFile} from 'fs';");

        Path nativeDir = Files.createDirectories(root.resolve("native/inc"));
        Files.writeString(nativeDir.resolve("util.h"), "int util();");
        Files.writeString(Files.createDirectories(root.resolve("native/src")).resolve("main.cpp"),
                "#include \"../inc/util.h\"\n#include <iostream>\nint main() {}");

        Files.writeString(Files.createDirectories(root.resolve("py/helpers"))
                .resolve("helper.py"), "def main(): pass");
        Files.writeString(Files.createDirectories(root.resolve("py/runner"))
                .resolve("app.py"), "from helpers.helper import main\nimport json\n");

        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(4, graph.edges().size());
        assertEdge(graph, "src/client", "src/demo");
        assertEdge(graph, "web/ui", "web");
        assertEdge(graph, "native/src", "native/inc");
        assertEdge(graph, "py/runner", "py/helpers");
        assertFalse(graph.nodes().stream().anyMatch(s -> s.contains("java.util.List")));
        assertFalse(graph.truncated());
        assertTrue(graph.files("src/client").contains("src/client/A.java"));
    }

    @Test
    void createsAncestorNodesEvenWhenTheyContainNoFiles() throws Exception {
        Files.writeString(Files.createDirectories(root.resolve("src/main/java/demo"))
                .resolve("Factory.java"), "public class Factory {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(List.of(".", "src", "src/main", "src/main/java",
                "src/main/java/demo"), graph.nodes());
        assertEquals(4, graph.hierarchy().size());
        assertTrue(graph.hierarchy().contains(
                new ProjectDependencyGraph.FolderLink("src/main", "src/main/java")));
        assertEquals(List.of("src"), graph.subfolders("."));
        assertTrue(graph.files("src/main").isEmpty());
        assertEquals(List.of("src/main/java/demo/Factory.java"),
                graph.files("src/main/java/demo"));
        assertTrue(graph.edges().isEmpty(), "Containment must not be a dependency.");
    }

    @Test
    void sameFolderImportsDoNotDrawSelfLoops() throws Exception {
        Files.writeString(root.resolve("Main.java"),
                "import demo.Factory; class Main {}");
        Files.writeString(root.resolve("Factory.java"),
                "package demo; class Factory {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(List.of("."), graph.nodes());
        assertTrue(graph.edges().isEmpty());
        assertTrue(graph.hierarchy().isEmpty());
        assertEquals(2, graph.files(".").size());
    }

    @Test
    void keepsFolderMapOpenWhenNoImportsExist() throws Exception {
        Path a = Files.createDirectories(root.resolve("a"));
        Path b = Files.createDirectories(root.resolve("b"));
        Files.writeString(a.resolve("A.java"), "class A {}");
        Files.writeString(b.resolve("B.java"), "class B {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(List.of(".", "a", "b"), graph.nodes());
        assertEquals(2, graph.hierarchy().size());
        assertTrue(graph.edges().isEmpty());
        assertFalse(graph.truncated());
    }

    @Test
    void doesNotInventDependenciesOutsideSelectedFolder() throws Exception {
        Path a = Files.createDirectories(root.resolve("a"));
        Path b = Files.createDirectories(root.resolve("b"));
        Files.writeString(a.resolve("x.ts"), "import '../b/y';");
        Files.writeString(b.resolve("y.ts"), "export const y = 1;");
        var scoped = ProjectDependencyGraph.analyze(root, a, () -> false);
        assertEquals(List.of("a"), scoped.nodes());
        assertTrue(scoped.edges().isEmpty());
        assertEquals(List.of("a/x.ts"), scoped.files("a"));
        var aborted = ProjectDependencyGraph.analyze(root, root, () -> true);
        assertTrue(aborted.truncated());
        assertTrue(aborted.edges().isEmpty());
    }

    @Test
    void resolvesGoPackageImportsAsDirectoryNodes() throws Exception {
        Files.writeString(root.resolve("go.mod"), "module example.com/demo\n");
        Path pkg = Files.createDirectory(root.resolve("utility"));
        Files.writeString(pkg.resolve("math.go"), "package utility\n");
        Files.writeString(root.resolve("app.go"),
                "package main\nimport \"example.com/demo/utility\"\nfunc main() {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEdge(graph, ".", "utility");
        assertEquals(List.of("utility"), graph.subfolders("."));
    }

    @Test
    void keepsAmbiguousImportsUnresolved() throws Exception {
        Path one = Files.createDirectories(root.resolve("one/demo"));
        Path two = Files.createDirectories(root.resolve("two/demo"));
        Files.writeString(one.resolve("Helper.java"), "package demo; class Helper {}");
        Files.writeString(two.resolve("Helper.java"), "package demo; class Helper {}");
        Files.writeString(root.resolve("Main.java"),
                "import demo.Helper; class Main {}");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertTrue(graph.edges().isEmpty());
        assertTrue(graph.nodes().contains("one/demo"));
        assertTrue(graph.nodes().contains("two/demo"));
    }

    @Test
    void separateFilesCanProduceJustOneDirectoryArrow() throws Exception {
        Path left = Files.createDirectory(root.resolve("left"));
        Path right = Files.createDirectory(root.resolve("right"));
        Files.writeString(right.resolve("Common.ts"), "export const value = 1;");
        Files.writeString(left.resolve("First.ts"),
                "import {value} from '../right/Common';");
        Files.writeString(left.resolve("Second.ts"),
                "import {value} from '../right/Common';");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(1, graph.edges().size());
        assertEdge(graph, "left", "right");
        assertEquals(2, graph.evidence().get(graph.edges().getFirst()).size());
    }

    @Test
    void includesAssemblySourceAsStructuralFiles() throws Exception {
        Files.writeString(root.resolve("pila.asm"), "global multiplicar_y_sumar\n");
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(List.of("."), graph.nodes());
        assertEquals(List.of("pila.asm"), graph.files("."));
    }

    @Test
    void limitsVeryLargeDirectoryGraphsWithoutLosingVisibleParents() throws Exception {
        for (int i = 0; i < 205; i++) {
            Path dir = Files.createDirectory(root.resolve("node" + i));
            String dependency = i < 204
                    ? "import '../node" + (i + 1) + "/source';" : "";
            Files.writeString(dir.resolve("source.ts"), dependency);
        }
        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertTrue(graph.truncated());
        assertTrue(graph.nodes().size() <= 180);
        assertTrue(graph.edges().size() <= 400);
        for (var link : graph.hierarchy()) {
            assertTrue(graph.nodes().contains(link.parent()));
            assertTrue(graph.nodes().contains(link.child()));
        }
        assertEquals(graph.nodes().size() - 1, graph.hierarchy().size());
    }

    @Test
    void sourceContentChangesInvalidateSavedPositions() throws Exception {
        Files.writeString(root.resolve("one.ts"), "const a = 1;");
        Path dependency = Files.writeString(root.resolve("two.ts"),
                "export const b = 2;");
        var before = ProjectDependencyGraph.analyze(root, root, () -> false);
        Files.writeString(dependency, "export const b = 3;");
        var after = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(before.edges(), after.edges());
        assertNotEquals(before.fingerprint(), after.fingerprint());
    }

    @Test
    void resolvesCommonScriptDirectoryMacrosAndAssemblyInclude() throws Exception {
        Path scripts = Files.createDirectories(root.resolve("scripts"));
        Path common = Files.createDirectories(root.resolve("common"));
        Files.writeString(common.resolve("Eventos.ps1"), "function Publicar-Evento {}\n");
        Files.writeString(common.resolve("helper.cmd"), "@echo off\r\n");
        Files.writeString(common.resolve("module.asm"), "global util\n");
        Files.writeString(scripts.resolve("main.ps1"),
                ". \"$PSScriptRoot/../common/Eventos.ps1\"\n");
        Files.writeString(scripts.resolve("main.cmd"),
                "call \"%~dp0../common/helper.cmd\"\r\n");
        Files.writeString(scripts.resolve("main.asm"),
                "%include \"../common/module.asm\"\n");

        var graph = ProjectDependencyGraph.analyze(root, root, () -> false);
        assertEquals(1, graph.edges().size(), "One folder arrow despite three uses.");
        assertEdge(graph, "scripts", "common");
        assertEquals(3, graph.evidence().get(graph.edges().getFirst()).size());
    }

    private static void assertEdge(ProjectDependencyGraph.Graph graph,
                                   String source, String target) {
        assertTrue(graph.edges().stream().anyMatch(edge ->
                edge.source().equals(source) && edge.target().equals(target)),
                () -> "Missing " + source + " → " + target + ": " + graph.edges());
    }
}
