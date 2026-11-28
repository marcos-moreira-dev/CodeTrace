package com.mmdev.codetrace.library;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Exercises the actual bundled Java catalog, not substitute fixture sources. */
class JavaIntegralIntegrationTest {
    @TempDir Path temp;
    private static final String ARCH = "20-arquitectura-de-software/";
    private static final String HEX = ARCH + "05-hexagonal-centro-ecuestre";
    private static final String CLIENT = HEX + "/Main.java";
    private static final String LEGACY = "01-fundamentos-en-accion/comparadores/ComparadoresEncadenados.java";

    private Path openJava(Path cache) throws Exception {
        var library = new BuiltInLibrary(cache);
        var pack = library.packs().stream().filter(p -> p.id().equals("java21")).findFirst().orElseThrow();
        return library.open(pack);
    }

    @Test
    void completeJavaTreeIsIndexedWithoutLosingDeepSourcesOrDocuments() throws Exception {
        Path root = openJava(temp.resolve("library"));
        var index = ReferenceTreeIndex.scan(root);
        var library = new BuiltInLibrary(temp.resolve("library"));
        var java = library.packs().getFirst();
        assertEquals(1329, library.size(java));
        assertEquals(library.size(java), index.fileCount());
        assertEquals(library.size(java), index.recursiveFiles(""));
        assertEquals(74, index.recursiveFiles("20-arquitectura-de-software"));
        assertEquals(21, index.children("").size());
        for (String path : library.entries(java)) {
            assertTrue(index.hasFile(path), path);
            if (path.endsWith(".java")) assertTrue(index.isWorkable(path), path);
        }
        assertTrue(index.hasFile(ARCH + "11-comparacion-verduleria/01-en-capas/README.md"));
        assertTrue(index.hasFile("14-comunicacion-con-dispositivos/12-interoperabilidad-nativa/README.md"));
        assertTrue(index.isWorkable(CLIENT));
    }

    @Test
    void selectedArchitectureGraphHasRealDependenciesAndRetainsSession() throws Exception {
        Path root = openJava(temp.resolve("library"));
        var graph = ProjectDependencyGraph.analyze(root, root.resolve(HEX), () -> false);
        assertFalse(graph.truncated());
        assertEquals(8, graph.inspected());
        assertTrue(graph.files(HEX).contains(CLIENT));
        assertEdge(graph, HEX + "/infraestructura/entrada", HEX + "/aplicacion/puertos");
        assertEdge(graph, HEX + "/infraestructura/salida", HEX + "/aplicacion/puertos");
        assertEdge(graph, HEX + "/aplicacion", HEX + "/dominio");
        assertFalse(graph.edges().stream().anyMatch(edge ->
            edge.source().equals(HEX + "/dominio") && edge.target().startsWith(HEX + "/infraestructura")));
        assertTrue(graph.edges().stream().allMatch(edge ->
            edge.source().startsWith(HEX) && edge.target().startsWith(HEX)));

        var layout = new GraphLayout(graph.fingerprint(),
                GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.ORGANIZED),
                GraphLayoutEngine.Mode.ORGANIZED);
        assertEquals(Set.copyOf(graph.nodes()), layout.positions().keySet());

        var original = TextPreview.loadWithHash(root.resolve(CLIENT));
        assertTrue(original.preview().readable());
        var typing = new TypingSession(original.preview().content());
        typing.type("//");
        var workspace = new VirtualWorkspace();
        workspace.ensureFile(CLIENT);
        workspace.setText(CLIENT, typing.enteredText());
        Path archive = temp.resolve("java.ctrace");
        SessionArchive.save(archive, new SessionArchive.State(root, workspace,
                Map.of(CLIENT, typing), Map.of(CLIENT, original.sha256()),
                PracticeMode.DIRECT, Map.of(HEX, layout)));
        var restored = SessionArchive.load(archive);
        assertEquals(root, restored.referenceRoot());
        assertEquals("//", restored.workspace().text(CLIENT));
        assertEquals(2, restored.sessions().get(CLIENT).correctCount());
        assertEquals(original.sha256(), restored.referenceHashes().get(CLIENT));
        assertEquals(layout, restored.graphs().get(HEX));
        assertEquals(original, TextPreview.loadWithHash(root.resolve(CLIENT)));
    }

    @Test
    void samePackageNamesInDifferentMicroprojectsStayIsolatedWhenScoped() throws Exception {
        Path root = openJava(temp.resolve("library"));
        String layers = ARCH + "01-en-capas-verduleria";
        String hex = ARCH + "11-comparacion-verduleria/02-hexagonal";
        for (String unit : List.of(layers, hex)) {
            var graph = ProjectDependencyGraph.analyze(root, root.resolve(unit), () -> false);
            assertFalse(graph.truncated());
            assertTrue(graph.edges().stream().allMatch(edge ->
                    edge.source().startsWith(unit) && edge.target().startsWith(unit)));
            assertTrue(graph.edges().stream().anyMatch(edge -> edge.target().equals(unit + "/dominio")));
        }
    }

    @Test
    void previousVersionReferencesRemainAvailableAfterOpeningNewCatalog() throws Exception {
        Path previous = temp.resolve("practice-library/v37/java21");
        Path oldFile = previous.resolve(LEGACY);
        Files.createDirectories(oldFile.getParent());
        Files.writeString(oldFile, "// archived reference\nclass ComparadoresEncadenados {}\n");
        var old = TextPreview.loadWithHash(oldFile);
        var typing = new TypingSession(old.preview().content());
        typing.type("//");
        var virtual = new VirtualWorkspace();
        virtual.ensureFile(LEGACY);
        virtual.setText(LEGACY, typing.enteredText());
        Path archive = temp.resolve("old.ctrace");
        SessionArchive.save(archive, new SessionArchive.State(previous, virtual,
                Map.of(LEGACY, typing), Map.of(LEGACY, old.sha256()), PracticeMode.DIRECT));
        Path current = openJava(temp.resolve("practice-library").resolve(BuiltInLibrary.CATALOG_VERSION));
        assertNotEquals(previous, current);
        var restored = SessionArchive.load(archive);
        assertEquals(previous, restored.referenceRoot());
        assertEquals(old.sha256(), TextPreview.loadWithHash(oldFile).sha256());
        assertEquals("//", restored.workspace().text(LEGACY));
        assertTrue(Files.isRegularFile(current.resolve(LEGACY)));
    }

    private static void assertEdge(ProjectDependencyGraph.Graph graph, String from, String to) {
        assertTrue(graph.edges().stream().anyMatch(edge -> edge.source().equals(from)
                && edge.target().equals(to)), from + " -> " + to);
    }
}
