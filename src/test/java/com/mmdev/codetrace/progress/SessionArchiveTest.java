package com.mmdev.codetrace.progress;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import java.io.DataOutputStream;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SessionArchiveTest {
    @TempDir Path directory;

    @Test
    void roundTripsVirtualWorkspaceAndSparseGraphemes() throws Exception {
        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createDirectory("src");
        virtual.createFile("src/Main.java");
        TypingSession typing = new TypingSession("í\nclass Main {}");
        typing.type("i\u0301\n");
        typing.skip(2, 7);
        virtual.setText("src/Main.java", typing.enteredText());

        String sha = "a".repeat(64);
        SessionArchive.State state = new SessionArchive.State(
                directory.resolve("reference"), virtual,
                Map.of("src/Main.java", typing), Map.of("src/Main.java", sha),
                PracticeMode.DIRECT);
        Path file = directory.resolve("training.ctrace");
        SessionArchive.save(file, state);

        var restored = SessionArchive.load(file);
        assertEquals(state.referenceRoot(), restored.referenceRoot());
        assertEquals(PracticeMode.DIRECT, restored.practiceMode());
        assertEquals(2, restored.sessions().get("src/Main.java").correctCount());
        assertEquals(5, restored.sessions().get("src/Main.java").skippedCount());
        assertEquals(7, restored.sessions().get("src/Main.java").resolvedCount());
        assertEquals("i\u0301\n", restored.workspace().text("src/Main.java"));
        assertEquals(sha, restored.referenceHashes().get("src/Main.java"));
    }

    @Test
    void readsVersionOneSessionsWithManualMode() throws Exception {
        Path archive = directory.resolve("legacy.ctrace");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(archive))) {
            out.writeInt(0x43545243);
            out.writeInt(1);
            byte[] root = directory.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            out.writeInt(root.length);
            out.write(root);
            out.writeInt(1); // root virtual directory
            out.writeInt(0); // empty path
            out.writeByte(1); // directory
            out.writeInt(0); // no typing sessions
            out.writeInt(0); // no hashes
        }
        var restored = SessionArchive.load(archive);
        assertEquals(PracticeMode.RECONSTRUCTION, restored.practiceMode());
        assertTrue(restored.workspace().exists(""));
    }

    @Test
    void rejectsUnknownOrCorruptArchive() throws Exception {
        Path file = directory.resolve("bad.ctrace");
        Files.write(file, new byte[]{1, 2, 3, 4, 5, 6, 7, 8});
        assertThrows(IOException.class, () -> SessionArchive.load(file));
    }

    @Test
    void subsequentSaveReplacesPriorSession() throws Exception {
        VirtualWorkspace virtual = new VirtualWorkspace();
        Path file = directory.resolve("training.ctrace");
        SessionArchive.save(file, new SessionArchive.State(directory, virtual,
                Map.of(), Map.of()));
        virtual.createDirectory("src");
        SessionArchive.save(file, new SessionArchive.State(directory, virtual,
                Map.of(), Map.of()));
        assertTrue(SessionArchive.load(file).workspace().exists("src"));
    }

    @Test
    void rejectsTrailingBytesInsteadOfSilentlyAcceptingAnAlteredSession()
            throws Exception {
        Path archive = directory.resolve("trailing.ctrace");
        SessionArchive.save(archive, new SessionArchive.State(directory,
                new VirtualWorkspace(), Map.of(), Map.of()));
        byte[] original = Files.readAllBytes(archive);
        byte[] altered = java.util.Arrays.copyOf(original, original.length + 2);
        altered[original.length] = 42;
        altered[original.length + 1] = 43;
        Files.write(archive, altered);
        assertThrows(IOException.class, () -> SessionArchive.load(archive));
    }

    @Test
    void rejectsMalformedUtf8InTheRootPath() throws Exception {
        Path archive = directory.resolve("malformed.ctrace");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(archive))) {
            out.writeInt(0x43545243);
            out.writeInt(2);
            out.writeInt(1);
            out.writeByte(0xFF); // Invalid standalone UTF-8 byte
            out.writeByte(1); // PracticeMode.RECONSTRUCTION (unread)
        }
        assertThrows(IOException.class, () -> SessionArchive.load(archive));
    }

    @Test
    void versionFourRetainsGraphLayoutModeWithoutChangingTheReference() throws Exception {
        var graph = new GraphLayout("a".repeat(64), Map.of(
                "src/A.java", new GraphLayout.Point(154.5, 278),
                "src/B.java", new GraphLayout.Point(460, 120)),
                GraphLayoutEngine.Mode.ORGANIZED);
        Path original = Files.writeString(directory.resolve("A.java"), "class A {}");
        Path destination = directory.resolve("graph-session.ctrace");
        SessionArchive.save(destination, new SessionArchive.State(directory,
                new VirtualWorkspace(), Map.of(), Map.of(),
                PracticeMode.DIRECT, Map.of("src", graph)));
        var restored = SessionArchive.load(destination);
        assertEquals(graph, restored.graphs().get("src"));
        assertEquals("class A {}", Files.readString(original),
                "Saving positions must never touch source references");
    }

    @Test
    void versionTwoSessionsRemainReadableWithoutLayouts() throws Exception {
        Path archive = directory.resolve("v2.ctrace");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(archive))) {
            out.writeInt(0x43545243);
            out.writeInt(2);
            byte[] root = directory.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            out.writeInt(root.length);
            out.write(root);
            out.writeByte(1); // direct mode
            out.writeInt(1); // root virtual directory
            out.writeInt(0); // empty root path
            out.writeByte(1); // directory
            out.writeInt(0); // sessions
            out.writeInt(0); // hashes
        }
        var restored = SessionArchive.load(archive);
        assertEquals(PracticeMode.DIRECT, restored.practiceMode());
        assertTrue(restored.graphs().isEmpty());
    }
    @Test
    void versionThreeLayoutsDefaultToGridOnRead() throws Exception {
        Path archive = directory.resolve("v3-graph.ctrace");
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(archive))) {
            out.writeInt(0x43545243);
            out.writeInt(3);
            writeLegacyString(out, directory.toString());
            out.writeByte(1);
            out.writeInt(1); // virtual root
            writeLegacyString(out, "");
            out.writeByte(1); // directory
            out.writeInt(0); // sessions
            out.writeInt(0); // hashes
            out.writeInt(1); // layouts
            writeLegacyString(out, "src");
            writeLegacyString(out, "a".repeat(64));
            out.writeInt(1);
            writeLegacyString(out, "src/Main.java");
            out.writeDouble(340);
            out.writeDouble(415);
        }
        var state = SessionArchive.load(archive);
        assertEquals(GraphLayoutEngine.Mode.GRID, state.graphs().get("src").mode());
        assertEquals(new GraphLayout.Point(340, 415),
                state.graphs().get("src").positions().get("src/Main.java"));
    }


    @Test
    void versionFourPreservesNegativeGraphCoordinatesAndReadOnlyReferences()
            throws Exception {
        Path source = Files.writeString(directory.resolve("original.java"), "class Original {}");
        GraphLayout graph = new GraphLayout("f".repeat(64), Map.of(
                "src/Original.java", new GraphLayout.Point(-300.5, -125),
                "src/Other.java", new GraphLayout.Point(800, 950)),
                GraphLayoutEngine.Mode.ORGANIZED);
        var state = new SessionArchive.State(directory, new VirtualWorkspace(),
                Map.of(), Map.of(), PracticeMode.RECONSTRUCTION,
                Map.of("src", graph));
        Path file = directory.resolve("negative-graph.ctrace");
        SessionArchive.save(file, state);
        var restored = SessionArchive.load(file);
        assertEquals(graph, restored.graphs().get("src"));
        assertEquals("class Original {}", Files.readString(source),
                "Saving camera-independent world positions must never modify originals");
    }

    @Test
    void rejectsTruncatedVersionFourArchiveAtMultipleBoundaries() throws Exception {
        Path good = directory.resolve("valid.ctrace");
        GraphLayout graph = new GraphLayout("b".repeat(64), Map.of(
                "src/Main.java", new GraphLayout.Point(25, 90)),
                GraphLayoutEngine.Mode.ORGANIZED);
        SessionArchive.save(good, new SessionArchive.State(directory,
                new VirtualWorkspace(), Map.of(), Map.of(), PracticeMode.DIRECT,
                Map.of("src", graph)));
        byte[] data = Files.readAllBytes(good);
        for (int cut : new int[]{0, 4, 8, 14, data.length / 2, data.length - 1}) {
            Path broken = directory.resolve("cut-" + cut + ".ctrace");
            Files.write(broken, java.util.Arrays.copyOf(data, cut));
            assertThrows(IOException.class, () -> SessionArchive.load(broken),
                    "Truncated archive with " + cut + " bytes must be rejected");
        }
        assertEquals(graph, SessionArchive.load(good).graphs().get("src"),
                "Invalid files must not damage the original valid session");
    }

    @Test
    void failedOversizedSaveNeverDestroysPreviouslySavedSession() throws Exception {
        Path destination = directory.resolve("safe.ctrace");
        SessionArchive.save(destination, new SessionArchive.State(directory,
                new VirtualWorkspace(), Map.of(), Map.of()));
        byte[] before = Files.readAllBytes(destination);
        VirtualWorkspace huge = new VirtualWorkspace();
        huge.createDirectory("src");
        huge.createFile("src/Main.txt");
        huge.setText("src/Main.txt", "x".repeat(8 * 1024 * 1024 + 32));
        assertThrows(IOException.class, () -> SessionArchive.save(destination,
                new SessionArchive.State(directory, huge, Map.of(), Map.of())));
        assertArrayEquals(before, Files.readAllBytes(destination),
                "Temporary write errors must preserve last good .ctrace file");
        assertTrue(SessionArchive.load(destination).workspace().exists(""));
    }

    private static void writeLegacyString(DataOutputStream out, String value)
            throws IOException {
        byte[] text = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        out.writeInt(text.length);
        out.write(text);
    }

}
