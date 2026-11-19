package com.mmdev.codetrace.progress;

import com.mmdev.codetrace.practice.TypingSession;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable mapping of virtual source paths to transcription sessions and
 * original reference hashes. The original project files are never touched.
 * This stays independent of JavaFX and the session file's binary format.
 */
public record PracticeSessionPaths(Map<String, TypingSession> sessions,
                                   Map<String, String> hashes) {
    public PracticeSessionPaths {
        sessions = Collections.unmodifiableMap(new LinkedHashMap<>(
                Objects.requireNonNull(sessions)));
        hashes = Collections.unmodifiableMap(new LinkedHashMap<>(
                Objects.requireNonNull(hashes)));
    }

    public PracticeSessionPaths withoutSubtree(String root) {
        Objects.requireNonNull(root);
        Map<String, TypingSession> remaining = new LinkedHashMap<>();
        Map<String, String> remainingHashes = new LinkedHashMap<>();
        sessions.forEach((path, session) -> {
            if (!under(path, root)) remaining.put(path, session);
        });
        hashes.forEach((path, hash) -> {
            if (!under(path, root)) remainingHashes.put(path, hash);
        });
        return new PracticeSessionPaths(remaining, remainingHashes);
    }

    public PracticeSessionPaths movedSubtree(String source, String destination) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        if (source.equals(destination)) return this;
        Map<String, TypingSession> relocated = new LinkedHashMap<>();
        Map<String, String> relocatedHashes = new LinkedHashMap<>();
        sessions.forEach((path, session) ->
                relocated.put(relocate(path, source, destination), session));
        hashes.forEach((path, hash) ->
                relocatedHashes.put(relocate(path, source, destination), hash));
        return new PracticeSessionPaths(relocated, relocatedHashes);
    }

    private static boolean under(String path, String root) {
        return root.isEmpty() || path.equals(root) || path.startsWith(root + "/");
    }

    private static String relocate(String path, String source, String destination) {
        if (!under(path, source)) return path;
        if (path.equals(source)) return destination;
        return destination + path.substring(source.length());
    }
}
