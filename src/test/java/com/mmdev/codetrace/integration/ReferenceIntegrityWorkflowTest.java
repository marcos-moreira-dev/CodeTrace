package com.mmdev.codetrace.integration;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import com.mmdev.codetrace.reference.ProjectTextSearch;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.workspace.VirtualTreeRecreator;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Release invariant: using CodeTrace's core workflows does not write to the
 * original reference. The ONLY output is the explicitly chosen .ctrace file.
 */
class ReferenceIntegrityWorkflowTest {
    @TempDir Path sandbox;

    @Test
    void combinedPracticeSearchGraphAndSessionRoundTripKeepReferenceImmutable()
            throws Exception {
        Path project = Files.createDirectory(sandbox.resolve("reference"));
        Path src = Files.createDirectory(project.resolve("src"));
        String text = "package demo;\nimport demo.B;\nclass A {}\n";
        Path main = Files.writeString(src.resolve("A.java"), text);
        Files.writeString(src.resolve("B.java"), "package demo;\nclass B {}\n");
        Files.writeString(project.resolve("README.md"), "# Reference");
        Files.createDirectory(project.resolve("reports"));
        Files.writeString(project.resolve("reports/generated.xml"), "<generated/>");

        Map<String, String> before = fingerprints(project);
        ReferenceTreeIndex tree = ReferenceTreeIndex.scan(project);
        assertTrue(tree.hasFile("reports/generated.xml"), "Read-only explorer may show logs");

        VirtualWorkspace workspace = new VirtualWorkspace();
        VirtualTreeRecreator creator = new VirtualTreeRecreator(
                workspace, tree.recreationNodes());
        var progress = creator.nextBatch(150);
        assertTrue(progress.finished());
        assertTrue(workspace.exists("src/A.java"));
        assertFalse(workspace.exists("reports/generated.xml"),
                "Generated files are not virtual typing exercises");

        var catalogue = ReferenceCatalogue.scan(project);
        assertTrue(catalogue.files().containsKey("src/A.java"));
        assertFalse(catalogue.files().containsKey("reports/generated.xml"));

        var matches = ProjectTextSearch.searchWithReport(project, null,
                new ProjectTextSearch.Options("package", false, false, false),
                () -> false);
        assertEquals(2, matches.hits().size());
        assertFalse(matches.partial());
        var graph = ProjectDependencyGraph.analyze(project, project, () -> false);
        assertFalse(graph.nodes().stream().anyMatch(n -> n.startsWith("reports/")));

        TypingSession typing = new TypingSession(text);
        typing.type("package");
        Map<String, String> hashes = Map.of("src/A.java",
                ReferenceFingerprint.sha256(main));
        Path archive = sandbox.resolve("progress.ctrace");
        SessionArchive.save(archive, new SessionArchive.State(
                project, workspace, Map.of("src/A.java", typing), hashes));
        var restored = SessionArchive.load(archive);
        assertEquals(typing.correctCount(),
                restored.sessions().get("src/A.java").correctCount());
        assertTrue(restored.workspace().exists("src/A.java"));
        assertEquals(hashes, restored.referenceHashes());
        assertEquals(before, fingerprints(project),
                "Search, graph, virtual recreation and .ctrace persistence are read-only");
        assertTrue(Files.isRegularFile(archive));
        assertFalse(archive.startsWith(project));
    }

    private static Map<String, String> fingerprints(Path root) throws Exception {
        Map<String, String> result = new LinkedHashMap<>();
        try (var paths = Files.walk(root)) {
            for (Path file : paths.filter(Files::isRegularFile).sorted().toList()) {
                result.put(root.relativize(file).toString(),
                        ReferenceFingerprint.sha256(file));
            }
        }
        return result;
    }
}
