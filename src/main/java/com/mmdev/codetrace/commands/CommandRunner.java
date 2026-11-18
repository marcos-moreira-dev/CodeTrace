package com.mmdev.codetrace.commands;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * One external command at a time, off the JavaFX Application Thread.
 * Never executes a preset during project opening, import, or session loading.
 */
public final class CommandRunner {
    private static final int OUTPUT_LIMIT = 180_000;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicReference<Process> current = new AtomicReference<>();

    public boolean isRunning() {
        return running.get();
    }

    public boolean start(Path projectRoot, ProjectCommandStore.Preset preset,
                         Consumer<String> output, IntConsumer finished) {
        Objects.requireNonNull(projectRoot);
        Objects.requireNonNull(preset);
        Objects.requireNonNull(output);
        Objects.requireNonNull(finished);
        if (!running.compareAndSet(false, true)) return false;
        Thread worker = new Thread(() -> {
            int code = -1;
            try {
                Path workdir = ProjectCommandStore.workingDirectory(projectRoot, preset);
                List<String> args = preset.shell().invocation(preset.command());
                Process process = new ProcessBuilder(args).directory(workdir.toFile())
                        .redirectErrorStream(true).start();
                current.set(process);
                output.accept("[" + preset.name() + "] " + preset.shell().label()
                        + " · " + workdir + System.lineSeparator());
                int emitted = 0;
                char[] buffer = new char[1024];
                try (Reader stream = new InputStreamReader(
                        process.getInputStream(), Charset.defaultCharset())) {
                    int count;
                    while ((count = stream.read(buffer)) != -1) {
                        if (emitted < OUTPUT_LIMIT) {
                            int limit = Math.min(count, OUTPUT_LIMIT - emitted);
                            output.accept(new String(buffer, 0, limit));
                            emitted += limit;
                            if (emitted == OUTPUT_LIMIT) {
                                output.accept(System.lineSeparator()
                                        + "[Salida acotada para proteger la interfaz]"
                                        + System.lineSeparator());
                            }
                        }
                    }
                }
                code = process.waitFor();
            } catch (InterruptedException cancelled) {
                Thread.currentThread().interrupt();
                output.accept(System.lineSeparator() + "[Interrumpido]" + System.lineSeparator());
                code = -2;
            } catch (IOException | IllegalArgumentException error) {
                output.accept(System.lineSeparator()
                        + "[No se pudo ejecutar: " + error.getMessage() + "]"
                        + System.lineSeparator());
            } finally {
                current.set(null);
                running.set(false);
                finished.accept(code);
            }
        }, "codetrace-command-runner");
        worker.setDaemon(true);
        worker.start();
        return true;
    }

    public void stop() {
        Process process = current.get();
        if (process == null) return;
        // Stop children too (Maven, compilers, etc.) instead of leaving them running.
        process.descendants().forEach(handle -> {
            if (handle.isAlive()) handle.destroy();
        });
        process.destroy();
    }
}
