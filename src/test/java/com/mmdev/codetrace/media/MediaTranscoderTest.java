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

    @Test void fullLengthRemuxCopiesVideoWithoutResizingOrDroppingFps() {
        var remux = MediaTranscoder.conversionCommand(
                temp.resolve("long movie[1].mkv"), temp.resolve("full.mp4"),
                MediaTranscoder.Output.VIDEO_REMUX);
        assertFalse(remux.contains("-t"), "Full playback cannot be limited to 30 seconds");
        assertFalse(remux.contains("-r"), "Keep source frame cadence");
        assertFalse(remux.contains("-vf"), "Do not rescale the original video");
        assertEquals("copy", remux.get(remux.indexOf("-c:v") + 1));
        assertEquals("aac", remux.get(remux.indexOf("-c:a") + 1));
        assertEquals("+faststart", remux.get(remux.indexOf("-movflags") + 1));
        assertEquals(temp.resolve("full.mp4").toString(), remux.getLast());
    }

    @Test void optionalFullHdConversionPreservesFrameRateAndFullDuration() {
        var highDefinition = MediaTranscoder.conversionCommand(
                temp.resolve("unplayable-hevc.mkv"), temp.resolve("full-hd.mp4"),
                MediaTranscoder.Output.VIDEO);
        assertFalse(highDefinition.contains("-t"), "Process the entire video");
        assertFalse(highDefinition.contains("-r"), "Do not force 24 or 30 FPS");
        assertTrue(highDefinition.contains(
                "scale=w=1920:h=1080:force_original_aspect_ratio=decrease:force_divisible_by=2"));
        assertEquals("20", highDefinition.get(highDefinition.indexOf("-crf") + 1));
        assertEquals("yuv420p", highDefinition.get(highDefinition.indexOf("-pix_fmt") + 1));
        assertTrue(highDefinition.contains("+faststart"));

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

    @Test void fullSourceRemuxIsFastAndNeverEditsTheOriginalWhenFfmpegIsAvailable()
            throws Exception {
        Process available;
        try {
            available = new ProcessBuilder(MediaTranscoder.executable(), "-version")
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
        } catch (IOException absent) {
            Assumptions.assumeTrue(false, "FFmpeg not installed on CI host");
            return;
        }
        try {
            Assumptions.assumeTrue(available.waitFor(5, TimeUnit.SECONDS)
                    && available.exitValue() == 0, "FFmpeg not available");
        } finally {
            if (available.isAlive()) available.destroyForcibly();
        }
        Path source = temp.resolve("movie.mkv");
        Process create = new ProcessBuilder(MediaTranscoder.executable(),
                "-hide_banner", "-loglevel", "error", "-y",
                "-f", "lavfi", "-i", "testsrc2=size=640x360:rate=30:duration=2",
                "-c:v", "libx264", "-preset", "ultrafast",
                "-pix_fmt", "yuv420p", source.toString())
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
        try {
            Assumptions.assumeTrue(create.waitFor(25, TimeUnit.SECONDS)
                    && create.exitValue() == 0,
                    "This FFmpeg build lacks the fixture's H.264 encoder");
        } finally {
            if (create.isAlive()) create.destroyForcibly();
        }
        byte[] before = Files.readAllBytes(source);
        Path ready = MediaTranscoder.transcode(source,
                MediaTranscoder.Output.VIDEO_REMUX, () -> false);
        try {
            assertTrue(Files.size(ready) > 1024);
            assertArrayEquals(before, Files.readAllBytes(source),
                    "Reference-only playback never modifies the original film");
            // The stream-copy command doesn't set any clipping duration or FPS.
        } finally {
            MediaTranscoder.deleteTemporary(ready);
        }
        assertFalse(Files.exists(ready));
    }
    @Test void unsupportedVideoCodecSelectsCompatiblePlayback() {
        assertEquals(MediaTranscoder.Output.VIDEO_REMUX,
                MediaTranscoder.outputForVideoCodec("h264"));
        assertEquals(MediaTranscoder.Output.VIDEO,
                MediaTranscoder.outputForVideoCodec("hevc"));
        assertEquals(MediaTranscoder.Output.VIDEO,
                MediaTranscoder.outputForVideoCodec("vp9"));
        assertEquals(MediaTranscoder.Output.VIDEO,
                MediaTranscoder.outputForVideoCodec("av1"));
        assertEquals(MediaTranscoder.Output.VIDEO_REMUX,
                MediaTranscoder.outputForVideoCodec(""));
        assertEquals(MediaTranscoder.Output.VIDEO_REMUX,
                MediaTranscoder.outputForVideoCodec("invalid codec"));
    }

}
