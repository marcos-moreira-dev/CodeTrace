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
                // Reading a child process pipe is blocking on some systems.
                // Keep it in a dedicated daemon reader so cancellation always
                // completes even when grandchildren inherited stdout handles.
                AtomicBoolean outputActive = new AtomicBoolean(true);
                Process activeProcess = process;
                Thread outputReader = new Thread(() -> {
                    int emitted = 0;
                    char[] buffer = new char[1024];
                    try (Reader stream = new InputStreamReader(
                            activeProcess.getInputStream(), Charset.defaultCharset())) {
                        int count;
                        while (!stopRequested.get() && outputActive.get()
                                && (count = stream.read(buffer)) != -1) {
                            if (emitted >= OUTPUT_LIMIT) continue;
                            int limit = Math.min(count, OUTPUT_LIMIT - emitted);
                            if (!stopRequested.get() && outputActive.get())
                                output.accept(new String(buffer, 0, limit));
                            emitted += limit;
                            if (emitted == OUTPUT_LIMIT && outputActive.get()) {
                                output.accept(System.lineSeparator()
                                        + "[Salida acotada para proteger la interfaz]"
                                        + System.lineSeparator());
                            }
                        }
                    } catch (IOException ignored) {
                        // Process shutdown can close its output stream abruptly.
                    } catch (RuntimeException ignored) {
                        // UI callbacks must never prevent the command from exiting.
                    }
                }, "codetrace-command-output");
                outputReader.setDaemon(true);
                outputReader.start();
                code = process.waitFor();
                // Normal completion must drain the child's final output before
                // reporting "finished". Never wait for the pipe on cancellation,
                // because an inherited descriptor may outlive the shell.
                if (!stopRequested.get()) outputReader.join(1500);
                outputActive.set(false);
                if (stopRequested.get()) code = -2;
            } catch (InterruptedException cancelled) {
                Thread.currentThread().interrupt();
                output.accept(System.lineSeparator() + "[Interrumpido]" + System.lineSeparator());
                code = -2;
            } catch (IOException | IllegalArgumentException error) {
                if (stopRequested.get()) {
                    code = -2;
                    output.accept(System.lineSeparator() + "[Detenido]" + System.lineSeparator());
                } else {
                    output.accept(System.lineSeparator()
                            + "[No se pudo ejecutar: " + error.getMessage() + "]"
                            + System.lineSeparator());
                }
            } finally {
                Process process = current.getAndSet(null);
                // Windows can keep the command's working directory locked
                // briefly after destroyForcibly(). Reap the OS process
                // before telling observers that the command has finished.
                if (process != null) reapOnWorker(process);
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

    private static void reapOnWorker(Process process) {
        if (process.isAlive()) terminate(process);
        // stop() interrupts this worker to unblock waitFor(). That interrupt
        // must be cleared temporarily so process reaping can actually wait.
        boolean interrupted = Thread.interrupted();
        try {
            if (!process.waitFor(4, java.util.concurrent.TimeUnit.SECONDS)) {
                terminate(process);
                process.waitFor(2, java.util.concurrent.TimeUnit.SECONDS);
            }
        } catch (InterruptedException cancelled) {
            interrupted = true;
            // A second cancellation should not keep the shell alive.
            terminate(process);
            try {
                process.onExit().get(2, java.util.concurrent.TimeUnit.SECONDS);
            } catch (InterruptedException again) {
                interrupted = true;
            } catch (java.util.concurrent.ExecutionException
                     | java.util.concurrent.TimeoutException ignored) {
                // Process may be stuck in native teardown. Worker still exits.
            }
        } finally {
            if (interrupted) Thread.currentThread().interrupt();
        }
    }

    private static void terminate(Process process) {
        // Do not block the JavaFX thread waiting for child tools to exit.
        process.descendants().forEach(handle -> {
            if (handle.isAlive()) handle.destroyForcibly();
        });
        if (process.isAlive()) process.destroyForcibly();
    }
}
