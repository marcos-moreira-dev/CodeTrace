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
    private final AtomicBoolean stopRequested = new AtomicBoolean();
    private final AtomicReference<Thread> workerThread = new AtomicReference<>();

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
        stopRequested.set(false);
        Thread worker = new Thread(() -> {
            int code = -1;
            try {
                Path workdir = ProjectCommandStore.workingDirectory(projectRoot, preset);
                List<String> args = preset.shell().invocation(preset.command());
                if (stopRequested.get()) { code = -2; return; }
                Process process = new ProcessBuilder(args).directory(workdir.toFile())
                        .redirectErrorStream(true).start();
                current.set(process);
                if (stopRequested.get()) terminate(process);
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
                if (stopRequested.get()) code = -2;
            } catch (InterruptedException cancelled) {
                Thread.currentThread().interrupt();
                output.accept(System.lineSeparator() + "[Interrumpido]" + System.lineSeparator());
                code = -2;
            } catch (IOException | IllegalArgumentException error) {
                output.accept(System.lineSeparator()
                        + "[No se pudo ejecutar: " + error.getMessage() + "]"
                        + System.lineSeparator());
            } finally {
                Process process = current.getAndSet(null);
                if (process != null && process.isAlive()) terminate(process);
                workerThread.set(null);
                running.set(false);
                finished.accept(code);
            }
        }, "codetrace-command-runner");
        worker.setDaemon(true);
        workerThread.set(worker);
        worker.start();
        return true;
    }

    public void stop() {
        stopRequested.set(true);
        Process process = current.get();
        if (process != null) terminate(process);
        Thread worker = workerThread.get();
        if (worker != null) worker.interrupt();
    }

    private static void terminate(Process process) {
        // Do not block the JavaFX thread waiting for child tools to exit.
        process.descendants().forEach(handle -> {
            if (handle.isAlive()) handle.destroyForcibly();
        });
        if (process.isAlive()) process.destroyForcibly();
    }
}
