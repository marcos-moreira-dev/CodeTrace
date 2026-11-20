package com.mmdev.codetrace.media;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Private loopback HLS video source: no full-file transcode and no source writes.
 * A seek requests exactly the MPEG-TS segment containing that timestamp.
 * Encoding and HTTP happen off the JavaFX thread. Not an internet video server.
  * <p><strong>Engineering rationale.</strong> Segmenting a large video trades a potentially huge full-file conversion for bounded on-demand work and cache memory. The loopback server is local to the application, must shut down with the preview, and must not expose user files beyond the specifically selected reference media.</p>
*/
public final class OnDemandHlsVideo implements AutoCloseable {
    static final int SEGMENT_SECONDS = 6;
    private static final int MAX_SEGMENTS = 12_000;
    private static final int MAX_CACHED_SEGMENTS = 12;
    private static final long MAX_SEGMENT_BYTES = 192L * 1024 * 1024;
    private static final long MAX_SOURCE_BYTES = 16L * 1024 * 1024 * 1024;
    private static final long MAX_ENCODE_SECONDS = 120;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Path source;
    private final Path tempDir;
    private final double durationSeconds;
    private final int count;
    private final String secret;
    private final HttpServer server;
    private final ExecutorService handlers;
    private final ExecutorService encoder;
    private final AtomicBoolean closed = new AtomicBoolean();
    private final Map<Integer, Path> ready = new LinkedHashMap<>(16, .75f, true);
    private final Map<Integer, CompletableFuture<Path>> pending = new java.util.HashMap<>();
    private volatile Process conversionProcess;
    private final String playlistUrl;

    private OnDemandHlsVideo(Path source, double durationSeconds) throws IOException {
        if (!Double.isFinite(durationSeconds) || durationSeconds <= 0)
            throw new IOException("No se pudo determinar la duración del vídeo.");
        int total = segmentCount(durationSeconds);
        if (total > MAX_SEGMENTS)
            throw new IOException("El vídeo es demasiado largo para la vista previa.");
        this.source = source;
        this.durationSeconds = durationSeconds;
        count = total;
        secret = token();
        tempDir = Files.createTempDirectory("codetrace-hls-");
        HttpServer created;
        try {
            created = HttpServer.create(new InetSocketAddress(
                    InetAddress.getByName("127.0.0.1"), 0), 4);
        } catch (IOException problem) {
            deleteDirectory(tempDir);
            throw problem;
        }
        server = created;
        handlers = Executors.newFixedThreadPool(4,
                task -> daemon(task, "codetrace-hls-http"));
        encoder = Executors.newSingleThreadExecutor(
                task -> daemon(task, "codetrace-hls-encoder"));
        String prefix = "/" + secret + "/";
        server.createContext(prefix, this::handle);
        server.setExecutor(handlers);
        playlistUrl = "http://127.0.0.1:" + server.getAddress().getPort()
                + prefix + "index.m3u8";
        server.start();
    }

    /**
     * FFprobe reads container metadata only. Neither FFmpeg nor HTTP is started
     * if this source cannot be probed. Executable is resolved like FFmpeg.
     */
    public static OnDemandHlsVideo open(Path input) throws IOException {
        Path source = input.toRealPath();
        if (!Files.isRegularFile(source) || Files.size(source) > MAX_SOURCE_BYTES)
            throw new IOException("Archivo no válido o demasiado grande.");
        return new OnDemandHlsVideo(source, probeDuration(source));
    }

    public String playlistUrl() { return playlistUrl; }
    public double durationSeconds() { return durationSeconds; }
    public int segments() { return count; }

    static int segmentCount(double duration) {
        return (int) Math.ceil(duration / SEGMENT_SECONDS);
    }

    static String playlist(double duration) {
        int count = segmentCount(duration);
        StringBuilder list = new StringBuilder("#EXTM3U\n"
                + "#EXT-X-VERSION:3\n#EXT-X-TARGETDURATION:" + SEGMENT_SECONDS + "\n"
                + "#EXT-X-MEDIA-SEQUENCE:0\n#EXT-X-PLAYLIST-TYPE:VOD\n");
        for (int i = 0; i < count; i++) {
            double length = Math.min(SEGMENT_SECONDS,
                    Math.max(0.001, duration - (double) i * SEGMENT_SECONDS));
            list.append("#EXTINF:")
                    .append(String.format(Locale.ROOT, "%.3f", length))
                    .append(",\nsegment-")
                    .append(String.format(Locale.ROOT, "%06d", i))
                    .append(".ts\n");
        }
        return list.append("#EXT-X-ENDLIST\n").toString();
    }

    private void handle(HttpExchange request) throws IOException {
        try (request) {
            if (closed.get()) { respond(request, 503, "Cerrado"); return; }
            String method = request.getRequestMethod();
            if (!"GET".equals(method) && !"HEAD".equals(method)) {
                respond(request, 405, "Método no permitido");
                return;
            }
            String path = request.getRequestURI().getPath();
            String prefix = "/" + secret + "/";
            if (!path.startsWith(prefix)) { respond(request, 404, "No encontrado"); return; }
            String name = path.substring(prefix.length());
            if ("index.m3u8".equals(name)) {
                bytes(request, playlist(durationSeconds).getBytes(StandardCharsets.UTF_8),
                        "application/vnd.apple.mpegurl");
                return;
            }
            if (!name.matches("segment-[0-9]{6}\\.ts")) {
                respond(request, 404, "No encontrado");
                return;
            }
            int index = Integer.parseInt(name.substring(8, 14));
            if (index < 0 || index >= count) {
                respond(request, 404, "No encontrado");
                return;
            }
            Path segment;
            try {
                segment = obtain(index);
            } catch (IOException problem) {
                if (!closed.get()) System.err.println("[CodeTrace/HLS] " + problem);
                respond(request, 503, "No se pudo preparar este fragmento.");
                return;
            }
            if (closed.get() || !Files.isRegularFile(segment)) {
                respond(request, 503, "Cerrado");
                return;
            }
            request.getResponseHeaders().set("Content-Type", "video/mp2t");
            request.getResponseHeaders().set("Cache-Control", "no-store");
            if ("HEAD".equals(method)) {
                request.sendResponseHeaders(200, -1);
                return;
            }
            long size = Files.size(segment);
            request.sendResponseHeaders(200, size);
            try (var input = Files.newInputStream(segment);
                 var output = request.getResponseBody()) {
                input.transferTo(output);
            }
        } catch (RuntimeException problem) {
            if (!closed.get()) System.err.println("[CodeTrace/HLS] HTTP: " + problem);
        }
    }

    private Path obtain(int index) throws IOException {
        CompletableFuture<Path> future;
        synchronized (ready) {
            if (closed.get()) throw new IOException("Vídeo cerrado.");
            Path cached = ready.get(index);
            if (cached != null && Files.isRegularFile(cached)) return cached;
            future = pending.get(index);
            if (future == null) {
                future = new CompletableFuture<>();
                pending.put(index, future);
                CompletableFuture<Path> result = future;
                encoder.execute(() -> {
                    try {
                        Path output = encode(index);
                        synchronized (ready) {
                            if (closed.get()) throw new IOException("Vídeo cerrado.");
                            ready.put(index, output);
                            result.complete(output);
                            pending.remove(index);
                            evict(index);
                        }
                    } catch (Exception failure) {
                        synchronized (ready) { pending.remove(index); }
                        result.completeExceptionally(failure);
                    }
                });
            }
        }
        try {
            return future.get(MAX_ENCODE_SECONDS + 10, TimeUnit.SECONDS);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Preparación interrumpida.", interrupted);
        } catch (ExecutionException | TimeoutException failed) {
            throw new IOException("No se pudo preparar un segmento.", failed);
        }
    }

    /** At most twelve *completed* segments in local temporary cache. */
    private void evict(int current) {
        while (ready.size() > MAX_CACHED_SEGMENTS) {
            var iterator = ready.entrySet().iterator();
            if (!iterator.hasNext()) break;
            var oldest = iterator.next();
            // The just-completed segment may still be streaming via HTTP.
            if (oldest.getKey() == current) break;
            Path obsolete = oldest.getValue();
            iterator.remove();
            MediaTranscoder.deleteTemporary(obsolete);
        }
    }

    private Path encode(int index) throws IOException {
        if (closed.get()) throw new IOException("Vídeo cerrado.");
        Path part = tempDir.resolve(String.format(Locale.ROOT, "%06d.part.ts", index));
        Path dest = tempDir.resolve(String.format(Locale.ROOT, "%06d.ts", index));
        List<String> command = segmentCommand(source, part, index, durationSeconds);
        Process process = new ProcessBuilder(command)
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .start();
        conversionProcess = process;
        try {
            long deadline = System.nanoTime()
                    + TimeUnit.SECONDS.toNanos(MAX_ENCODE_SECONDS);
            while (!process.waitFor(150, TimeUnit.MILLISECONDS)) {
                if (closed.get() || Thread.currentThread().isInterrupted()) {
                    throw new IOException("Preparación cancelada.");
                }
                if (Files.exists(part) && Files.size(part) > MAX_SEGMENT_BYTES)
                    throw new IOException("El fragmento es demasiado grande.");
                if (System.nanoTime() - deadline >= 0)
                    throw new IOException("El fragmento tardó demasiado.");
            }
            if (closed.get() || process.exitValue() != 0 || !Files.isRegularFile(part)
                    || Files.size(part) == 0 || Files.size(part) > MAX_SEGMENT_BYTES)
                throw new IOException("FFmpeg no pudo preparar el fragmento.");
            Files.move(part, dest, StandardCopyOption.REPLACE_EXISTING);
            return dest;
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Preparación cancelada.", interrupted);
        } finally {
            if (process.isAlive()) process.destroyForcibly();
            conversionProcess = null;
            MediaTranscoder.deleteTemporary(part);
        }
    }

    /** Accurate input seek + standalone H.264/AAC MPEG-TS fragments for HLS. */
    static List<String> segmentCommand(Path source, Path output, int index,
                                       double totalSeconds) {
        double from = (double) index * SEGMENT_SECONDS;
        double length = Math.min(SEGMENT_SECONDS, totalSeconds - from);
        List<String> args = new ArrayList<>(List.of(
                MediaTranscoder.executable(), "-hide_banner", "-nostdin",
                "-loglevel", "error", "-y", "-protocol_whitelist", "file,pipe",
                "-ss", String.format(Locale.ROOT, "%.3f", from),
                "-i", source.toString(),
                "-t", String.format(Locale.ROOT, "%.3f", length),
                "-map", "0:v:0", "-map", "0:a:0?",
                "-vf", "scale=w=1920:h=1080:force_original_aspect_ratio=decrease:force_divisible_by=2",
                "-c:v", "libx264", "-preset", "ultrafast", "-crf", "25",
                "-pix_fmt", "yuv420p", "-g", "60", "-sc_threshold", "0",
                "-c:a", "aac", "-b:a", "128k", "-ar", "48000", "-ac", "2",
                "-muxdelay", "0", "-muxpreload", "0",
                // Each FFmpeg process starts its own MPEG-TS clock. Align
                // timestamps with the global HLS timeline for gap-free seek.
                "-output_ts_offset", String.format(Locale.ROOT, "%.3f", from),
                "-f", "mpegts", output.toString()));
        return List.copyOf(args);
    }

    private static double probeDuration(Path source) throws IOException {
        String binary = MediaTranscoder.executable();
        Path command = Path.of(binary);
        String probeName = System.getProperty("os.name", "")
                .toLowerCase(Locale.ROOT).contains("win") ? "ffprobe.exe" : "ffprobe";
        String probe = command.getParent() == null ? probeName
                : command.getParent().resolve(probeName).toString();
        Process process = new ProcessBuilder(probe, "-v", "error",
                "-protocol_whitelist", "file,pipe",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                source.toString())
                .redirectError(ProcessBuilder.Redirect.DISCARD).start();
        try {
            if (!process.waitFor(8, TimeUnit.SECONDS) || process.exitValue() != 0)
                throw new IOException("No se pudo analizar este vídeo con FFprobe.");
            String output = new String(process.getInputStream().readNBytes(100),
                    StandardCharsets.UTF_8).strip();
            try {
                double seconds = Double.parseDouble(output);
                if (!Double.isFinite(seconds) || seconds <= 0)
                    throw new NumberFormatException();
                return seconds;
            } catch (NumberFormatException invalid) {
                throw new IOException("No se pudo leer la duración del vídeo.", invalid);
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Análisis interrumpido.", interrupted);
        } finally {
            if (process.isAlive()) process.destroyForcibly();
        }
    }

    private static void bytes(HttpExchange req, byte[] data, String type)
            throws IOException {
        req.getResponseHeaders().set("Content-Type", type);
        req.getResponseHeaders().set("Cache-Control", "no-store");
        if ("HEAD".equals(req.getRequestMethod())) {
            req.sendResponseHeaders(200, -1);
        } else {
            req.sendResponseHeaders(200, data.length);
            try (var body = req.getResponseBody()) { body.write(data); }
        }
    }

    private static void respond(HttpExchange req, int code, String text)
            throws IOException {
        bytesWithCode(req, code, text.getBytes(StandardCharsets.UTF_8));
    }

    private static void bytesWithCode(HttpExchange req, int code, byte[] data)
            throws IOException {
        req.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        if ("HEAD".equals(req.getRequestMethod())) {
            req.sendResponseHeaders(code, -1);
        } else {
            req.sendResponseHeaders(code, data.length);
            try (var body = req.getResponseBody()) { body.write(data); }
        }
    }

    private static String token() {
        byte[] random = new byte[16];
        RANDOM.nextBytes(random);
        return HexFormat.of().formatHex(random);
    }

    private static Thread daemon(Runnable task, String name) {
        Thread thread = new Thread(task, name);
        thread.setDaemon(true);
        return thread;
    }

    private static void deleteDirectory(Path folder) {
        if (folder == null) return;
        try (var files = Files.list(folder)) {
            files.forEach(MediaTranscoder::deleteTemporary);
        } catch (IOException ignored) { }
        MediaTranscoder.deleteTemporary(folder);
    }

    @Override public void close() {
        if (!closed.compareAndSet(false, true)) return;
        Process process = conversionProcess;
        if (process != null && process.isAlive()) {
            process.descendants().forEach(child -> child.destroyForcibly());
            process.destroyForcibly();
        }
        server.stop(0);
        encoder.shutdownNow();
        handlers.shutdownNow();
        synchronized (ready) {
            for (var wait : pending.values())
                wait.completeExceptionally(new IOException("Vídeo cerrado."));
            pending.clear();
            ready.clear();
        }
        deleteDirectory(tempDir);
    }
}
