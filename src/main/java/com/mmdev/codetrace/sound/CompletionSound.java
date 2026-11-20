package com.mmdev.codetrace.sound;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.media.AudioClip;

/**
 * Per-file success cue. Loaded lazily, kept separate from the typing samples,
 * and extracted to a temporary file so it also works from a packaged JAR.
  * <p><strong>Engineering rationale.</strong> Completion feedback is an optional effect triggered by a transition in typing state, never a condition for marking an exercise finished. Audio playback failures must not affect the progress model or stop the user from continuing.</p>
*/
public final class CompletionSound {
    private AudioClip clip;
    private boolean initialized;
    private String failure;

    public synchronized boolean play() {
        if (!initialized) initialize();
        if (clip == null) return false;
        try {
            clip.play();
            return true;
        } catch (RuntimeException problem) {
            failure = problem.getMessage();
            return false;
        }
    }

    public synchronized String failureReason() {
        return failure;
    }

    private void initialize() {
        initialized = true;
        try (InputStream source = getClass().getResourceAsStream("/sounds/success.mp3")) {
            if (source == null) throw new IOException("Missing completion sound");
            Path temporary = Files.createTempFile("codetrace-success-", ".mp3");
            try {
                Files.copy(source, temporary, StandardCopyOption.REPLACE_EXISTING);
                temporary.toFile().deleteOnExit();
                clip = new AudioClip(temporary.toUri().toString());
                clip.setVolume(0.9);
            } catch (IOException | RuntimeException problem) {
                Files.deleteIfExists(temporary);
                throw problem;
            }
        } catch (IOException | RuntimeException problem) {
            failure = problem.getMessage();
            clip = null;
        }
    }
}
