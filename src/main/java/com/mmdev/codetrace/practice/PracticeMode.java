package com.mmdev.codetrace.practice;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * Per-project interaction mode. It never changes what qualifies as a correctly
 * typed character or the progress filter; only the creation gate differs.
  * <p><strong>Engineering rationale.</strong> Manual reconstruction and direct transcription are project-level workflows, independent of which file types count toward progress. Keep this decision explicit so a change of mode does not secretly erase the virtual tree or reinterpret a saved typing session.</p>
*/
public enum PracticeMode {
    RECONSTRUCTION(0, "Reconstrucción manual"),
    DIRECT(1, "Transcripción directa");

    private static final Preferences SETTINGS =
            Preferences.userNodeForPackage(PracticeMode.class);
    private final int code;
    private final String label;

    PracticeMode(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int code() { return code; }
    public String label() { return label; }

    public static PracticeMode fromCode(int value) {
        for (var mode : values()) {
            if (mode.code == value) return mode;
        }
        throw new IllegalArgumentException("Unknown practice mode: " + value);
    }

    /**
     * A built-in pack favors instant typing; imported repositories default
     * to deliberate directory/file recreation. Explicit user choice wins.
     */
    public static PracticeMode forProject(Path root) {
        String preferred = SETTINGS.get(key(root), null);
        if (preferred != null) {
            try { return valueOf(preferred); }
            catch (IllegalArgumentException ignored) { /* old preference */ }
        }
        Path bundled = Path.of(System.getProperty("user.home"), ".codetrace",
                "practice-library").toAbsolutePath().normalize();
        return root.toAbsolutePath().normalize().startsWith(bundled)
                ? DIRECT : RECONSTRUCTION;
    }

    public static void remember(Path root, PracticeMode mode) {
        SETTINGS.put(key(root), Objects.requireNonNull(mode).name());
    }

    private static String key(Path root) {
        Objects.requireNonNull(root);
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(
                    root.toAbsolutePath().normalize().toString()
                            .getBytes(StandardCharsets.UTF_8));
            return "practice-mode-" + HexFormat.of().formatHex(hash, 0, 20);
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }
}
