package com.mmdev.codetrace.progress;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Versioned binary .ctrace sessions, not JSON or serialized Java objects.
 * All writes use an adjacent temporary file followed by an atomic replacement
 * where the filesystem supports it.
 */
public final class SessionArchive {
    private static final int MAGIC = 0x43545243; // CTRC
    private static final int VERSION = 3;
    private static final int MAX_ITEMS = 200_000;
    private static final int MAX_STRING_BYTES = 8 * 1024 * 1024;
    private static final long MAX_ARCHIVE_BYTES = 256L * 1024 * 1024;

    private SessionArchive() {
    }

    public record State(Path referenceRoot, VirtualWorkspace workspace,
                        Map<String, TypingSession> sessions,
                        Map<String, String> referenceHashes,
                        PracticeMode practiceMode, Map<String, GraphLayout> graphs) {
        public State(Path referenceRoot, VirtualWorkspace workspace,
                     Map<String, TypingSession> sessions,
                     Map<String, String> referenceHashes) {
            this(referenceRoot, workspace, sessions, referenceHashes,
                    PracticeMode.RECONSTRUCTION, Map.of());
        }

        public State(Path referenceRoot, VirtualWorkspace workspace,
                     Map<String, TypingSession> sessions,
                     Map<String, String> referenceHashes, PracticeMode practiceMode) {
            this(referenceRoot, workspace, sessions, referenceHashes,
                    practiceMode, Map.of());
        }

        public State {
            Objects.requireNonNull(referenceRoot);
            Objects.requireNonNull(workspace);
            Objects.requireNonNull(practiceMode);
            sessions = Map.copyOf(sessions);
            referenceHashes = Map.copyOf(referenceHashes);
            graphs = Map.copyOf(graphs);
            if (graphs.size() > 100) throw new IllegalArgumentException("Demasiados mapas");
        }
    }

    public static void save(Path destination, State state) throws IOException {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(state);
        Path absolute = destination.toAbsolutePath().normalize();
        Path directory = absolute.getParent();
        if (directory == null || !Files.isDirectory(directory)) {
            throw new IOException("Session destination directory is missing");
        }
        Path temp = Files.createTempFile(directory, ".codetrace-", ".tmp");
        try {
            try (DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(temp)))) {
                write(out, state);
            }
            if (Files.size(temp) > MAX_ARCHIVE_BYTES) {
                throw new IOException("La sesión supera el límite de 256 MB");
            }
            try {
                Files.move(temp, absolute,
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException unsupported) {
                Files.move(temp, absolute, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    public static State load(Path source) throws IOException {
        if (Files.size(source) > MAX_ARCHIVE_BYTES) {
            throw new IOException("Session exceeds the supported size");
        }
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(source)))) {
            State state = read(in);
            if (in.read() != -1) throw new IOException("Unexpected trailing session data");
            return state;
        } catch (IllegalArgumentException | IndexOutOfBoundsException malformed) {
            throw new IOException("Invalid CodeTrace session", malformed);
        }
    }

    private static void write(DataOutputStream out, State state) throws IOException {
        out.writeInt(MAGIC);
        out.writeInt(VERSION);
        writeString(out, state.referenceRoot().toString());
        out.writeByte(state.practiceMode().code());

        List<VirtualWorkspace.Entry> entries = state.workspace().entries();
        count(out, entries.size());
        for (var entry : entries) {
            writeString(out, entry.path());
            out.writeByte(entry.kind() == VirtualWorkspace.Kind.DIRECTORY ? 1 : 2);
            if (entry.kind() == VirtualWorkspace.Kind.FILE) {
                writeString(out, entry.text());
            }
        }

        count(out, state.sessions().size());
        for (var pair : state.sessions().entrySet()) {
            writeString(out, pair.getKey());
            TypingSession typing = pair.getValue();
            writeString(out, typing.expectedText());
            out.writeInt(typing.caret());
            count(out, typing.enteredSlots().size());
            for (String value : typing.enteredSlots()) {
                out.writeBoolean(value != null);
                if (value != null) {
                    writeString(out, value);
                }
            }
            count(out, typing.skippedSlots().size());
            for (int index : typing.skippedSlots()) {
                out.writeInt(index);
            }
        }

        count(out, state.referenceHashes().size());
        for (var pair : state.referenceHashes().entrySet()) {
            writeString(out, pair.getKey());
            writeString(out, pair.getValue());
        }

        // v3: layout metadata is session-only, never written into the reference.
        count(out, state.graphs().size());
        for (var graph : state.graphs().entrySet()) {
            writeString(out, graph.getKey());
            writeString(out, graph.getValue().fingerprint());
            count(out, graph.getValue().positions().size());
            for (var point : graph.getValue().positions().entrySet()) {
                writeString(out, point.getKey());
                out.writeDouble(point.getValue().x());
                out.writeDouble(point.getValue().y());
            }
        }
    }

    private static State read(DataInputStream in) throws IOException {
        int magic = in.readInt();
        int version = in.readInt();
        if (magic != MAGIC || (version != 1 && version != 2 && version != VERSION)) {
            throw new IOException("Unsupported CodeTrace session header or version");
        }
        Path root = Path.of(readString(in));
        // Original v1 sessions remain readable and keep the manual default.
        PracticeMode mode = version == 1
                ? PracticeMode.RECONSTRUCTION
                : PracticeMode.fromCode(in.readUnsignedByte());
        int entryCount = boundedCount(in);
        List<VirtualWorkspace.Entry> entries = new ArrayList<>(entryCount);
        for (int i = 0; i < entryCount; i++) {
            String path = readString(in);
            int kind = in.readUnsignedByte();
            if (kind == 1) {
                entries.add(new VirtualWorkspace.Entry(
                        path, VirtualWorkspace.Kind.DIRECTORY, null));
            } else if (kind == 2) {
                entries.add(new VirtualWorkspace.Entry(
                        path, VirtualWorkspace.Kind.FILE, readString(in)));
            } else {
                throw new IOException("Unknown virtual node kind");
            }
        }
        VirtualWorkspace workspace = VirtualWorkspace.fromEntries(entries);
        int sessionCount = boundedCount(in);
        Map<String, TypingSession> sessions = new LinkedHashMap<>();
        for (int i = 0; i < sessionCount; i++) {
            String path = readString(in);
            if (path.isEmpty() || sessions.containsKey(path)) {
                throw new IOException("Invalid or duplicate session path");
            }
            VirtualWorkspace.checkPath(path);
            String original = readString(in);
            int caret = in.readInt();
            int unitCount = boundedCount(in);
            List<String> slots = new ArrayList<>(unitCount);
            for (int j = 0; j < unitCount; j++) {
                slots.add(in.readBoolean() ? readString(in) : null);
            }
            int skippedCount = boundedCount(in);
            List<Integer> skipped = new ArrayList<>(skippedCount);
            for (int j = 0; j < skippedCount; j++) {
                skipped.add(in.readInt());
            }
            sessions.put(path, TypingSession.restore(original, slots, skipped, caret));
        }
        int hashCount = boundedCount(in);
        Map<String, String> hashes = new HashMap<>();
        for (int i = 0; i < hashCount; i++) {
            String path = readString(in);
            VirtualWorkspace.checkPath(path);
            String hash = readString(in);
            if (!hash.matches("[0-9a-f]{64}") || hashes.putIfAbsent(path, hash) != null) {
                throw new IOException("Invalid or duplicate SHA-256 record");
            }
        }
        Map<String, GraphLayout> graphs = new LinkedHashMap<>();
        if (version >= 3) {
            int graphCount = boundedCount(in);
            if (graphCount > 100) throw new IOException("Too many graph layouts");
            for (int i = 0; i < graphCount; i++) {
                String folder = readString(in);
                if (!folder.isEmpty()) VirtualWorkspace.checkPath(folder);
                String fingerprint = readString(in);
                int nodeCount = boundedCount(in);
                if (nodeCount > 180) throw new IOException("Too many graph nodes");
                Map<String, GraphLayout.Point> positions = new LinkedHashMap<>();
                for (int n = 0; n < nodeCount; n++) {
                    String node = readString(in);
                    VirtualWorkspace.checkPath(node);
                    GraphLayout.Point point = new GraphLayout.Point(
                            in.readDouble(), in.readDouble());
                    if (positions.putIfAbsent(node, point) != null)
                        throw new IOException("Duplicate graph node");
                }
                if (graphs.putIfAbsent(folder, new GraphLayout(fingerprint, positions)) != null)
                    throw new IOException("Duplicate graph scope");
            }
        }
        return new State(root, workspace, sessions, hashes, mode, graphs);
    }

    private static void writeString(DataOutputStream out, String text) throws IOException {
        byte[] bytes = Objects.requireNonNull(text).getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) {
            throw new IOException("Text field is too large");
        }
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int size = in.readInt();
        if (size < 0 || size > MAX_STRING_BYTES) {
            throw new IOException("Invalid text field size");
        }
        byte[] bytes = new byte[size];
        in.readFully(bytes);
        try {
            return StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException malformed) {
            throw new IOException("Invalid UTF-8 in CodeTrace session", malformed);
        }
    }

    private static void count(DataOutputStream out, int value) throws IOException {
        if (value > MAX_ITEMS) {
            throw new IOException("Too many entries in session");
        }
        out.writeInt(value);
    }

    private static int boundedCount(DataInputStream in) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > MAX_ITEMS) {
            throw new IOException("Invalid session entry count");
        }
        return count;
    }
}
