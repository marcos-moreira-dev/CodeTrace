package com.mmdev.codetrace.sound;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import javafx.scene.media.AudioClip;

/**
 * Optional local audio feedback for actual user typing.
 * Assets are compacted versions of the user's two supplied WAV samples.
 * Never downloads media and does not alter the reference project.
 */
public final class TypingSounds {
    private static final long MIN_GAP_NANOS = 55_000_000L;
    private AudioClip correct;
    private AudioClip wrong;
    private boolean initialized;
    private long lastPlay;

    public void play(boolean succeeded) {
        long now = System.nanoTime();
        if (now - lastPlay < MIN_GAP_NANOS) return;
        lastPlay = now;
        initialize();
        AudioClip clip = succeeded ? correct : wrong;
        if (clip != null) {
            try {
                if (!succeeded) clip.stop();
                clip.play();
            } catch (RuntimeException ignored) {
                // Audio drivers are optional; typing must remain unaffected.
            }
        }
    }

    private void initialize() {
        if (initialized) return;
        initialized = true;
        try {
            correct = new AudioClip(resourceAsWav("tecla-correcta.wav.gz").toUri().toString());
            wrong = new AudioClip(resourceAsWav("tecla-error.wav.gz").toUri().toString());
            correct.setVolume(0.65);
            wrong.setVolume(0.55);
        } catch (IOException | RuntimeException unavailable) {
            correct = null;
            wrong = null;
        }
    }

    /**
     * JavaFX AudioClip reads a file URL. Decode the embedded offline WAV clip
     * into a temporary cache on first use. The original repo remains untouched.
     */
    private static Path resourceAsWav(String filename) throws IOException {
        String resource = "/sounds/" + filename;
        InputStream source = TypingSounds.class.getResourceAsStream(resource);
        if (source == null) throw new IOException("Missing audio asset " + filename);
        Path wav = Files.createTempFile("codetrace-sound-", ".wav");
        wav.toFile().deleteOnExit();
        try (InputStream packed = source;
             GZIPInputStream decoded = new GZIPInputStream(packed)) {
            Files.copy(decoded, wav, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException failure) {
            Files.deleteIfExists(wav);
            throw failure;
        }
        return wav;
    }
}
