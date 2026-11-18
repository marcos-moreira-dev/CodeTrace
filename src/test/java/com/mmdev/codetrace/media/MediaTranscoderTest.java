package com.mmdev.codetrace.media;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MediaTranscoderTest {
    @TempDir Path temp;

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
}
