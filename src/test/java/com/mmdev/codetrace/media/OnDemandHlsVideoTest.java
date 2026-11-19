package com.mmdev.codetrace.media;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class OnDemandHlsVideoTest {
    @TempDir Path temp;

    @Test void playlistContainsAllSeekableSegmentsButNoMediaPayload() {
        String text = OnDemandHlsVideo.playlist(14.5);
        assertTrue(text.startsWith("#EXTM3U\n"));
        assertTrue(text.contains("#EXT-X-TARGETDURATION:6\n"));
        assertTrue(text.contains("#EXT-X-PLAYLIST-TYPE:VOD"));
        assertTrue(text.contains("#EXTINF:6.000,\nsegment-000000.ts"));
        assertTrue(text.contains("#EXTINF:6.000,\nsegment-000001.ts"));
        assertTrue(text.contains("#EXTINF:2.500,\nsegment-000002.ts"));
        assertFalse(text.contains("segment-000003.ts"));
        assertTrue(text.endsWith("#EXT-X-ENDLIST\n"));
    }

    @Test void arbitrarySeekEncodesOnlyTheRequestedWindow() {
        var args = OnDemandHlsVideo.segmentCommand(
                Path.of("sample.mov"), Path.of("part.ts"), 121, 900);
        assertEquals("726.000", args.get(args.indexOf("-ss") + 1));
        assertEquals("6.000", args.get(args.indexOf("-t") + 1));
        assertEquals("mpegts", args.get(args.indexOf("-f") + 1));
        assertEquals("libx264", args.get(args.indexOf("-c:v") + 1));
        assertTrue(args.stream().anyMatch(arg -> arg.contains("force_original_aspect_ratio=decrease")));
        assertFalse(args.contains("0:00"));
    }

    @Test void realHttpPlaylistAndDistantChunkWorkWithFfmpegWhenAvailable()
            throws Exception {
        boolean available;
        try {
            Process check = new ProcessBuilder(MediaTranscoder.executable(), "-version")
                    .redirectError(ProcessBuilder.Redirect.DISCARD).start();
            available = check.waitFor(5, TimeUnit.SECONDS)
                    && check.exitValue() == 0;
        } catch (Exception unavailable) { available = false; }
        assumeTrue(available, "FFmpeg no está instalado en este entorno de pruebas.");
        String ffmpeg = MediaTranscoder.executable();
        Path original = temp.resolve("sample-with-audio.mkv");
        Process make = new ProcessBuilder(
                ffmpeg, "-hide_banner", "-nostdin", "-loglevel", "error",
                "-f", "lavfi", "-i", "testsrc2=size=160x120:rate=12:duration=13",
                "-f", "lavfi", "-i", "sine=frequency=440:sample_rate=48000:duration=13",
                "-map", "0:v:0", "-map", "1:a:0", "-c:v", "mpeg4",
                "-c:a", "mp2", "-shortest", "-y", original.toString())
                .redirectError(ProcessBuilder.Redirect.DISCARD).start();
        assumeTrue(make.waitFor(20, TimeUnit.SECONDS) && make.exitValue() == 0,
                "No se pudo generar el fixture multimedia.");
        byte[] originalHash = MessageDigest.getInstance("SHA-256")
                .digest(Files.readAllBytes(original));
        try (OnDemandHlsVideo video = OnDemandHlsVideo.open(original);
             HttpClient client = HttpClient.newHttpClient()) {
            assertEquals(3, video.segments());
            URI playlist = URI.create(video.playlistUrl());
            HttpResponse<String> manifest = client.send(HttpRequest.newBuilder(playlist)
                    .GET().build(), HttpResponse.BodyHandlers.ofString());
            assertEquals(200, manifest.statusCode());
            assertTrue(manifest.body().contains("segment-000002.ts"));
            // Demand a segment near the end FIRST, without a linear conversion.
            URI jump = playlist.resolve("segment-000002.ts");
            HttpResponse<byte[]> chunk = client.send(HttpRequest.newBuilder(jump)
                    .GET().build(), HttpResponse.BodyHandlers.ofByteArray());
            assertEquals(200, chunk.statusCode());
            assertTrue(chunk.body().length > 188);
            assertEquals(0x47, Byte.toUnsignedInt(chunk.body()[0]),
                    "MPEG-TS sync byte");
            assertArrayEquals(originalHash, MessageDigest.getInstance("SHA-256")
                    .digest(Files.readAllBytes(original)));
            assertTrue(playlist.getHost().equals("127.0.0.1"));
        }
    }
}
