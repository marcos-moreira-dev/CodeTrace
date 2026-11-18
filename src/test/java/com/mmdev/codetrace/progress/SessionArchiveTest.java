package com.mmdev.codetrace.progress;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.PracticeMode;
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
}
