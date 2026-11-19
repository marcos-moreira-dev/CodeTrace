package com.mmdev.codetrace.media;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assumptions;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MediaTranscoderTest {
    @TempDir Path temp;

    @Test void videoUsesBoundedCompatiblePreviewRatherThanConvertingEntireMovie() {
        var video = MediaTranscoder.conversionCommand(
                temp.resolve("long movie[1].mkv"), temp.resolve("sample.mp4"),
                MediaTranscoder.Output.VIDEO);
        int duration = video.indexOf("-t");
        assertTrue(duration > 0);
        assertEquals("30", video.get(duration + 1));
        assertEquals("baseline", video.get(video.indexOf("-profile:v") + 1));
        assertEquals("yuv420p", video.get(video.indexOf("-pix_fmt") + 1));
        assertEquals("+faststart", video.get(video.indexOf("-movflags") + 1));
        assertTrue(video.contains("scale=w=960:h=540:force_original_aspect_ratio=decrease:force_divisible_by=2"));
        assertEquals(temp.resolve("sample.mp4").toString(), video.getLast());

        var audio = MediaTranscoder.conversionCommand(
                temp.resolve("voice.ogg"), temp.resolve("sample.mp3"),
                MediaTranscoder.Output.AUDIO);
        assertFalse(audio.contains("-t"), "Audio conversion keeps its original duration");
    }

    @Test void classifiesMediaThatJavaFxCannotAlwaysDecode() {
        assertTrue(MediaTranscoder.needsConversion(Path.of("voice.FLAC")));
        assertTrue(MediaTranscoder.needsConversion(Path.of("video.MKV")));
        assertTrue(MediaTranscoder.needsConversion(Path.of("video.webm")));
        assertTrue(MediaTranscoder.needsConversion(Path.of("video.avi")));
        assertTrue(MediaTranscoder.needsConversion(Path.of("audio.ogg")));
        assertTrue(MediaTranscoder.needsConversion(Path.of("audio.aac")));
        assertFalse(MediaTranscoder.needsConversion(Path.of("audio.mp3")));
        assertFalse(MediaTranscoder.needsConversion(Path.of("video.mp4")));
        assertFalse(MediaTranscoder.needsConversion(null));
    }

    @Test void cancelledOperationNeverTouchesTheReference() throws Exception {
        Path source = Files.writeString(temp.resolve("sample.flac"), "original");
        assertThrows(IOException.class, () -> MediaTranscoder.transcode(source,
                MediaTranscoder.Output.AUDIO, () -> true));
        assertEquals("original", Files.readString(source));
    }

    @Test void missingFFmpegDoesNotModifyTheSource() throws Exception {
        Path source = Files.writeString(temp.resolve("sample.ogg"), "original");
        String before = System.getProperty("codetrace.ffmpeg");
        try {
            System.setProperty("codetrace.ffmpeg",
                    temp.resolve("no-ffmpeg-installed").toString());
            IOException failure = assertThrows(IOException.class,
                    () -> MediaTranscoder.transcode(source,
                            MediaTranscoder.Output.AUDIO, () -> false));
            assertTrue(failure.getMessage().contains("FFmpeg"));
            assertEquals("original", Files.readString(source));
        } finally {
            if (before == null) System.clearProperty("codetrace.ffmpeg");
            else System.setProperty("codetrace.ffmpeg", before);
        }
    }

    @Test void deletingGeneratedPreviewIsIdempotent() throws Exception {
        Path preview = Files.writeString(temp.resolve("preview.mp3"), "temporary");
        MediaTranscoder.deleteTemporary(preview);
        MediaTranscoder.deleteTemporary(preview);
        assertFalse(Files.exists(preview));
        MediaTranscoder.deleteTemporary(null);
    }

    @Test void transcodesAnActualAudioSampleWhenFfmpegIsInstalled() throws Exception {
        // Optional integration exercise: runs on hosts with a local FFmpeg.
        boolean available;
        try {
            Process check = new ProcessBuilder(MediaTranscoder.executable(), "-version")
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
            available = check.waitFor(5, TimeUnit.SECONDS) && check.exitValue() == 0;
            if (check.isAlive()) check.destroyForcibly();
        } catch (IOException problem) {
            available = false;
        }
        Assumptions.assumeTrue(available, "FFmpeg not installed on CI host");

        Path wav = temp.resolve("silence.wav");
        AudioFormat format = new AudioFormat(8000, 16, 1, true, false);
        byte[] pcm = new byte[8000]; // Half a second of silence.
        try (AudioInputStream samples = new AudioInputStream(
                new ByteArrayInputStream(pcm), format, pcm.length / 2)) {
            assertTrue(AudioSystem.write(samples, AudioFileFormat.Type.WAVE,
                    wav.toFile()) > 0);
        }
        Path flac = temp.resolve("source.flac");
        Process makeFlac = new ProcessBuilder(MediaTranscoder.executable(),
                "-hide_banner", "-loglevel", "error", "-y",
                "-i", wav.toString(), flac.toString())
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
        assertTrue(makeFlac.waitFor(20, TimeUnit.SECONDS));
        assertEquals(0, makeFlac.exitValue());
        byte[] before = Files.readAllBytes(flac);

        Path mp3 = MediaTranscoder.transcode(flac,
                MediaTranscoder.Output.AUDIO, () -> false);
        try {
            assertTrue(Files.size(mp3) > 0);
            assertArrayEquals(before, Files.readAllBytes(flac),
                    "The source must remain byte-for-byte immutable");
        } finally {
            MediaTranscoder.deleteTemporary(mp3);
        }
        assertFalse(Files.exists(mp3));
    }
}
