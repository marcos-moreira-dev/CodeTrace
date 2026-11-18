package com.mmdev.codetrace.sound;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.scene.media.AudioClip;

/**
 * Short, local audio cues supplied by the user.
 *
 * The compact embedded WAVs are 8-bit samples at very low amplitude. Restore
 * them as louder PCM16 WAVs before passing them to JavaFX AudioClip. Never let
 * a missing audio device interfere with typing.
 */
public final class TypingSounds {
    private static final long MIN_GAP_NANOS = 55_000_000L;
    private AudioClip correct;
    private AudioClip wrong;
    private String failure;
    private boolean initialized;
    private long lastPlay;

    public synchronized boolean play(boolean succeeded) {
        return play(succeeded, false);
    }

    /** Play a sample immediately, even if two previews occur quickly. */
    public synchronized boolean preview(boolean succeeded) {
        return play(succeeded, true);
    }

    public synchronized String failureReason() {
        return failure;
    }

    private boolean play(boolean succeeded, boolean preview) {
        long now = System.nanoTime();
        if (!preview && now - lastPlay < MIN_GAP_NANOS) return true;
        lastPlay = now;
        initialize();
        AudioClip selected = succeeded ? correct : wrong;
        if (selected == null) return false;
        try {
            selected.play();
            return true;
        } catch (RuntimeException audioProblem) {
            failure = audioProblem.getMessage();
            return false;
        }
    }

    private void initialize() {
        if (initialized) return;
        initialized = true;
        try {
            correct = new AudioClip(normalizedWav("tecla-correcta.wav.gz", 7.0)
                    .toUri().toString());
            wrong = new AudioClip(normalizedWav("tecla-error.wav.gz", 3.5)
                    .toUri().toString());
            correct.setVolume(0.9);
            wrong.setVolume(0.8);
        } catch (IOException | UnsupportedAudioFileException | RuntimeException issue) {
            failure = issue.getMessage();
            correct = null;
            wrong = null;
        }
    }

    /**
     * Converts quiet unsigned 8-bit PCM WAVs to amplified signed 16-bit PCM.
     * Package-visible for a no-sound-device unit test.
     */
    static Path normalizedWav(String filename, double gain)
            throws IOException, UnsupportedAudioFileException {
        InputStream compressed = TypingSounds.class.getResourceAsStream("/sounds/" + filename);
        if (compressed == null) throw new IOException("Missing WAV sample " + filename);

        byte[] pcm8;
        float sampleRate;
        try (InputStream resource = compressed;
             GZIPInputStream unpacked = new GZIPInputStream(resource);
             BufferedInputStream buffered = new BufferedInputStream(unpacked);
             AudioInputStream audio = AudioSystem.getAudioInputStream(buffered)) {
            AudioFormat format = audio.getFormat();
            if (format.getChannels() != 1 || format.getSampleSizeInBits() != 8
                    || format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED) {
                throw new IOException("Unexpected WAV sample format: " + format);
            }
            sampleRate = format.getSampleRate();
            pcm8 = audio.readAllBytes();
        }

        byte[] pcm16 = new byte[pcm8.length * 2];
        for (int i = 0; i < pcm8.length; i++) {
            int centered = (pcm8[i] & 0xff) - 128;
            int amplified = (int) Math.round(centered * 256.0 * gain);
            amplified = Math.max(-32768, Math.min(32767, amplified));
            pcm16[i * 2] = (byte) amplified;
            pcm16[i * 2 + 1] = (byte) (amplified >>> 8);
        }

        AudioFormat outputFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 1,
                2, sampleRate, false);
        Path destination = Files.createTempFile("codetrace-cue-", ".wav");
        destination.toFile().deleteOnExit();
        try (AudioInputStream sound = new AudioInputStream(
                new ByteArrayInputStream(pcm16), outputFormat, pcm8.length)) {
            AudioSystem.write(sound, AudioFileFormat.Type.WAVE, destination.toFile());
            return destination;
        } catch (IOException issue) {
            Files.deleteIfExists(destination);
            throw issue;
        }
    }
}
