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
    /** VIDEO_REMUX copies the original video bitstream (full length and quality).
     * VIDEO encodes the full video into H.264, at most 1080p, preserving FPS. */
    public enum Output { AUDIO, VIDEO, VIDEO_REMUX }

    private static final long MAX_INPUT_BYTES = 16L * 1024 * 1024 * 1024;
    private static final long MAX_OUTPUT_BYTES = 12L * 1024 * 1024 * 1024;
    private static final long MAX_AUDIO_SECONDS = 300;
    private static final long MAX_VIDEO_REMUX_SECONDS = 180;
    private static final long MAX_VIDEO_ENCODE_SECONDS = 10_800;
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
     * Full-length media preparation. We first try a lossless, fast remux
     * (video stream copy); H.264/AAC compatible sources retain all frames,
     * resolution and duration. Other video codecs require explicit HD encode.
     * No original reference file is ever modified.
     */
    public static Path transcode(Path original, Output output,
                                 BooleanSupplier cancelled) throws IOException {
        Path source = original.toRealPath();
        if (!Files.isRegularFile(source) || Files.size(source) > MAX_INPUT_BYTES)
            throw new IOException("El archivo no es válido o supera el límite de 16 GB.");
        if (cancelled.getAsBoolean() || Thread.currentThread().isInterrupted())
            throw new IOException("Conversión cancelada.");

        Path temporary = Files.createTempFile("codetrace-fullmedia-",
                output == Output.AUDIO ? ".mp3" : ".mp4");
        Path errorLog = null;
        Process process = null;
        boolean success = false;
        try {
            long usable = Files.getFileStore(temporary).getUsableSpace();
            long needed = output == Output.VIDEO_REMUX
                    ? Math.min(MAX_OUTPUT_BYTES, Files.size(source) + 128L * 1024 * 1024)
                    : Math.min(MAX_OUTPUT_BYTES, Math.max(256L * 1024 * 1024,
                            Files.size(source) / 2));
            if (usable < needed)
                throw new IOException("Espacio temporal insuficiente para el vídeo completo. "
                        + "Puedes abrir el original con el reproductor externo.");

            errorLog = Files.createTempFile("codetrace-ffmpeg-errors-", ".txt");
            process = new ProcessBuilder(conversionCommand(source, temporary, output))
                    .redirectInput(ProcessBuilder.Redirect.PIPE)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(errorLog.toFile())
                    .start();
            long seconds = switch (output) {
                case AUDIO -> MAX_AUDIO_SECONDS;
                case VIDEO_REMUX -> MAX_VIDEO_REMUX_SECONDS;
                case VIDEO -> MAX_VIDEO_ENCODE_SECONDS;
            };
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
            while (!process.waitFor(250, TimeUnit.MILLISECONDS)) {
                if (cancelled.getAsBoolean() || Thread.currentThread().isInterrupted())
                    throw new IOException("Conversión cancelada.");
                if (Files.size(temporary) > MAX_OUTPUT_BYTES)
                    throw new IOException("La conversión superó 12 GB de espacio temporal.");
                if (System.nanoTime() - deadline >= 0)
                    throw new IOException("La preparación del vídeo tardó demasiado. "
                            + "Abre el original con el reproductor del sistema.");
            }
            if (cancelled.getAsBoolean() || Thread.currentThread().isInterrupted())
                throw new IOException("Conversión cancelada.");
            if (process.exitValue() != 0 || Files.size(temporary) == 0)
                throw new IOException("FFmpeg no pudo preparar el archivo completo."
                        + errorSummary(errorLog));
            if (Files.size(temporary) > MAX_OUTPUT_BYTES)
                throw new IOException("El vídeo preparado supera el límite de 12 GB.");
            success = true;
            return temporary;
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversión cancelada.", interrupted);
        } catch (IOException problem) {
            if (problem.getMessage() != null && problem.getMessage().contains("Cannot run"))
                throw new IOException("FFmpeg no está disponible. Instálalo o configúralo "
                        + "con -Dcodetrace.ffmpeg=ruta.", problem);
            throw problem;
        } finally {
            if (process != null && process.isAlive()) {
                // Terminate process descendants as well as FFmpeg itself.
                process.descendants().forEach(child -> child.destroyForcibly());
                process.destroyForcibly();
            }
            if (!success) deleteTemporary(temporary);
            deleteTemporary(errorLog);
        }
    }

    /**
     * Do not limit duration or force an arbitrary 24 fps. Stream-copy is the
     * default fast path for compatible MKV/AVI/MOV videos. A deliberate HD
     * re-encode preserves source FPS and stays at or below 1080p.
     */
    static List<String> conversionCommand(Path source, Path temporary, Output output) {
        List<String> args = new ArrayList<>(List.of(
                executable(), "-hide_banner", "-nostdin", "-loglevel", "error",
                "-y", "-protocol_whitelist", "file,pipe", "-i", source.toString()));
        if (output == Output.AUDIO) {
            args.addAll(List.of("-map", "0:a:0", "-vn", "-c:a",
                    "libmp3lame", "-b:a", "160k"));
        } else if (output == Output.VIDEO_REMUX) {
            args.addAll(List.of("-map", "0:v:0", "-map", "0:a:0?",
                    "-c:v", "copy", "-c:a", "aac", "-b:a", "160k",
                    "-movflags", "+faststart"));
        } else {
            args.addAll(List.of("-map", "0:v:0", "-map", "0:a:0?",
                    "-vf", "scale=w=1920:h=1080:force_original_aspect_ratio=decrease:force_divisible_by=2",
                    "-c:v", "libx264", "-preset", "veryfast", "-crf", "20",
                    "-pix_fmt", "yuv420p",
                    "-c:a", "aac", "-b:a", "160k", "-ar", "48000", "-ac", "2",
                    "-movflags", "+faststart"));
        }
        args.add(temporary.toString());
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
