package com.mmdev.codetrace.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

/**
 * Optional, local FFmpeg fallback for reference-only previews.
 * Never writes into the reference project and never invokes a shell.
 */
public final class MediaTranscoder {
    public enum Output { AUDIO, VIDEO }

    private static final long MAX_INPUT_BYTES = 512L * 1024 * 1024;
    private static final long MAX_OUTPUT_BYTES = 512L * 1024 * 1024;
    private static final long MAX_CONVERSION_SECONDS = 300;
    private static final long MAX_VIDEO_PREVIEW_SECONDS = 75;
    /** Video conversion is preview-only: never transcode a multi-hour movie eagerly. */
    public static final int VIDEO_PREVIEW_SECONDS = 30;
    private MediaTranscoder() {}

    public static boolean needsConversion(Path file) {
        if (file == null || file.getFileName() == null) return false;
        String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.endsWith(".flac") || name.endsWith(".ogg")
                || name.endsWith(".aac") || name.endsWith(".webm")
                || name.endsWith(".mkv") || name.endsWith(".avi")
                || name.endsWith(".mov");
    }

    /**
     * Priority: an explicitly configured executable, an application-bundled
     * binary, a user-installed local binary, then the operating system PATH.
     * Shipping an FFmpeg binary requires a separate licensing review.
     */
    public static String executable() {
        String explicit = System.getProperty("codetrace.ffmpeg");
        if (explicit != null && !explicit.isBlank()) return explicit.trim();
        String binary = System.getProperty("os.name", "").toLowerCase(Locale.ROOT)
                .contains("win") ? "ffmpeg.exe" : "ffmpeg";
        // jpackage sets java.home to <app-image>/runtime. Resolve beside
        // the installed application rather than relying on user.dir.
        Path runtime = Path.of(System.getProperty("java.home", "."));
        Path imageRoot = runtime.getParent();
        if (imageRoot != null) {
            Path bundled = imageRoot.resolve("tools").resolve("ffmpeg").resolve(binary);
            if (Files.isRegularFile(bundled)) return bundled.toString();
        }
        Path besideApp = Path.of(System.getProperty("user.dir", "."),
                "tools", "ffmpeg", binary);
        if (Files.isRegularFile(besideApp)) return besideApp.toString();
        Path userTool = Path.of(System.getProperty("user.home", "."),
                ".codetrace", "tools", "ffmpeg", binary);
        if (Files.isRegularFile(userTool)) return userTool.toString();
        return binary; // Installed in PATH, if available.
    }

    /**
     * Audio transcodes normally; video creates only a short, downscaled
     * H.264 Baseline/AAC preview. Full video remains in its original file and
     * can be opened explicitly with an external player. The caller owns the
     * resulting temporary file and must delete it after disposing playback.
     */
    public static Path transcode(Path original, Output output,
                                 BooleanSupplier cancelled) throws IOException {
        Path source = original.toRealPath();
        if (!Files.isRegularFile(source) || Files.size(source) > MAX_INPUT_BYTES) {
            throw new IOException("El medio supera el límite de 512 MB.");
        }
        if (cancelled.getAsBoolean()) throw new IOException("Conversión cancelada.");
        Path temporary = Files.createTempFile("codetrace-preview-",
                output == Output.AUDIO ? ".mp3" : ".mp4");
        Path errorLog = null;
        Process process = null;
        boolean success = false;
        try {
            errorLog = Files.createTempFile("codetrace-ffmpeg-errors-", ".txt");
            process = new ProcessBuilder(conversionCommand(source, temporary, output))
                    .redirectInput(ProcessBuilder.Redirect.PIPE)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(errorLog.toFile())
                    .start();
            long timeoutSeconds = output == Output.VIDEO
                    ? MAX_VIDEO_PREVIEW_SECONDS : MAX_CONVERSION_SECONDS;
            long limit = System.nanoTime()
                    + TimeUnit.SECONDS.toNanos(timeoutSeconds);
            while (!process.waitFor(200, TimeUnit.MILLISECONDS)) {
                if (cancelled.getAsBoolean() || Thread.currentThread().isInterrupted()) {
                    throw new IOException("Conversión cancelada.");
                }
                if (System.nanoTime() - limit >= 0) {
                    throw new IOException(output == Output.VIDEO
                            ? "La vista previa de vídeo tardó demasiado (75 s). "
                              + "Abre el archivo con el reproductor del sistema."
                            : "La conversión superó cinco minutos.");
                }
            }
            if (cancelled.getAsBoolean()) throw new IOException("Conversión cancelada.");
            if (process.exitValue() != 0 || Files.size(temporary) == 0) {
                throw new IOException("FFmpeg no pudo preparar este medio."
                        + errorSummary(errorLog));
            }
            success = true;
            return temporary;
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversión cancelada.", interrupted);
        } catch (IOException problem) {
            if (problem.getMessage() != null && problem.getMessage().contains("Cannot run")) {
                throw new IOException("FFmpeg no está disponible. Instálalo o configúralo "
                        + "con -Dcodetrace.ffmpeg=ruta.", problem);
            }
            throw problem;
        } finally {
            if (process != null && process.isAlive()) process.destroyForcibly();
            if (!success) deleteTemporary(temporary);
            deleteTemporary(errorLog);
        }
    }

    /**
     * A short, low-resolution AVC Baseline/AAC segment is intentionally used
     * rather than converting the entire reference into a temporary MP4.
     * The source file is only read: output is always the given temp path.
     */
    static List<String> conversionCommand(Path source, Path temporary, Output output) {
        List<String> args = new ArrayList<>(List.of(
                executable(), "-hide_banner", "-nostdin", "-loglevel", "error",
                "-y", "-protocol_whitelist", "file,pipe", "-i", source.toString()));
        if (output == Output.AUDIO) {
            args.addAll(List.of("-map", "0:a:0", "-vn", "-c:a",
                    "libmp3lame", "-b:a", "160k"));
        } else {
            args.addAll(List.of("-t", Integer.toString(VIDEO_PREVIEW_SECONDS),
                    "-map", "0:v:0", "-map", "0:a:0?",
                    "-vf", "scale=w=960:h=540:force_original_aspect_ratio=decrease:force_divisible_by=2",
                    "-r", "24",
                    "-c:v", "libx264", "-preset", "ultrafast", "-crf", "27",
                    "-profile:v", "baseline", "-level:v", "3.1",
                    "-pix_fmt", "yuv420p",
                    "-c:a", "aac", "-b:a", "128k", "-ar", "44100", "-ac", "2",
                    "-movflags", "+faststart"));
        }
        args.addAll(List.of("-fs", Long.toString(MAX_OUTPUT_BYTES),
                temporary.toString()));
        return List.copyOf(args);
    }

    /** Bounded FFmpeg error output; no need to lose the cause of a failed conversion. */
    private static String errorSummary(Path log) {
        if (log == null) return " Comprueba los códecs de FFmpeg.";
        try (var input = Files.newInputStream(log)) {
            String detail = new String(input.readNBytes(2048), StandardCharsets.UTF_8)
                    .replaceAll("[\\r\\n\\t]+", " ").trim();
            if (detail.isEmpty()) return " Comprueba los códecs de FFmpeg.";
            if (detail.length() > 300) detail = detail.substring(0, 300) + "…";
            return " FFmpeg: " + detail;
        } catch (IOException unreadable) {
            return " Revisa la instalación de FFmpeg.";
        }
    }

    public static void deleteTemporary(Path temporary) {
        if (temporary == null) return;
        try {
            Files.deleteIfExists(temporary);
        } catch (IOException blocked) {
            // JavaFX on Windows can briefly retain the native file handle.
            temporary.toFile().deleteOnExit();
        }
    }
}
