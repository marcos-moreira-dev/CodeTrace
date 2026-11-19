package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafx.util.Duration;

/** Metadata we can actually verify for the original file. No guessed bitrate. */
public final class AudioFileDetails {
    private AudioFileDetails() { }

    public static String summary(Path file, Duration duration, Map<String, ?> tags) {
        List<String> parts = new ArrayList<>();
        String filename = file.getFileName().toString();
        int dot = filename.lastIndexOf('.');
        parts.add(dot < 0 ? "Audio" : filename.substring(dot + 1)
                .toUpperCase(Locale.ROOT));
        try {
            long size = Files.size(file);
            parts.add(size >= 1_048_576
                    ? String.format(Locale.ROOT, "%.1f MB", size / 1_048_576.0)
                    : String.format(Locale.ROOT, "%.0f KB", size / 1024.0));
        } catch (IOException | SecurityException ignored) {
            // Metadata is additive: unavailable file size is not guessed.
        }
        if (duration != null && !duration.isUnknown() && !duration.isIndefinite()
                && duration.toMillis() > 0) {
            long s = (long) (duration.toMillis() / 1000.0);
            parts.add(String.format(Locale.ROOT, "%d:%02d", s / 60, s % 60));
        }
        if (tags != null) {
            append(parts, tags, "artist", "Artista");
            append(parts, tags, "album", "Álbum");
        }
        return String.join("  ·  ", parts);
    }

    private static void append(List<String> parts, Map<String, ?> tags,
                               String key, String label) {
        Object value = tags.get(key);
        if (value instanceof String text && !text.isBlank())
            parts.add(label + ": " + text.strip());
    }
}
