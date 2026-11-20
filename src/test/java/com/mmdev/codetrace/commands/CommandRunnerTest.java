package com.mmdev.codetrace.commands;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Runs a harmless command in the platform's normal shell. */
class CommandRunnerTest {
    @TempDir Path project;

    @Test
    void userTriggeredCommandStreamsActualOutputAndExitStatus() throws Exception {
        CommandRunner runner = new CommandRunner();
        var command = new ProjectCommandStore.Preset("Ver salida",
                ProjectCommandStore.Shell.preferred(),
                "echo CODETRACE-OUTPUT-OK", ".");
        StringBuffer output = new StringBuffer();
        AtomicInteger exitCode = new AtomicInteger(-999);
        CountDownLatch finished = new CountDownLatch(1);
        assertTrue(runner.start(project, command, output::append,
                code -> { exitCode.set(code); finished.countDown(); }));
        try {
            assertTrue(finished.await(30, TimeUnit.SECONDS), "Process did not exit");
            assertEquals(0, exitCode.get(), output.toString());
            assertTrue(output.toString().contains("CODETRACE-OUTPUT-OK"),
                    "Console must expose real shell output");
        } finally {
            runner.stop();
        }
    }

    @Test
    void stoppingImmediatelyAfterStartDoesNotLeaveAnOrphanCommand() throws Exception {
        CommandRunner runner = new CommandRunner();
        boolean windows = System.getProperty("os.name", "")
                .toLowerCase(java.util.Locale.ROOT).contains("win");
        // A deliberately long command gives stop() an observable target,
        // including the race before ProcessBuilder has returned.
        String commandText = windows
                ? "ping -n 25 127.0.0.1 >nul" : "sleep 25";
        var command = new ProjectCommandStore.Preset("Prueba detener",
                ProjectCommandStore.Shell.preferred(), commandText, ".");
        CountDownLatch done = new CountDownLatch(1);
        AtomicInteger exit = new AtomicInteger(-999);
        assertTrue(runner.start(project, command, line -> {},
                code -> { exit.set(code); done.countDown(); }));
        runner.stop();
        assertTrue(done.await(10, TimeUnit.SECONDS),
                "A stopped command must not keep running in the background");
        assertEquals(-2, exit.get());
        assertFalse(runner.isRunning());
    }
    @Test
    void stoppingAnAlreadyRunningCommandReapsProcessAndReportsCancellation()
            throws Exception {
        CommandRunner runner = new CommandRunner();
        boolean windows = System.getProperty("os.name", "")
                .toLowerCase(java.util.Locale.ROOT).contains("win");
        String commandText = windows
                ? "Write-Output CODETRACE-COMMAND-STARTED; Start-Sleep -Seconds 25"
                : "printf 'CODETRACE-COMMAND-STARTED\\n'; sleep 25";
        var command = new ProjectCommandStore.Preset("Cancelable",
                ProjectCommandStore.Shell.preferred(), commandText, ".");
        CountDownLatch started = new CountDownLatch(1);
        CountDownLatch finished = new CountDownLatch(1);
        AtomicInteger exit = new AtomicInteger(-999);
        try {
            assertTrue(runner.start(project, command, text -> {
                if (text.contains("CODETRACE-COMMAND-STARTED")) started.countDown();
            }, code -> {
                exit.set(code);
                finished.countDown();
            }));
            assertTrue(started.await(12, TimeUnit.SECONDS),
                    "The command must actually start before cancellation");
            assertTrue(runner.isRunning());
            assertFalse(runner.start(project, command, text -> {}, code -> {}),
                    "Two commands cannot execute concurrently");
            runner.stop();
            assertTrue(finished.await(10, TimeUnit.SECONDS),
                    "Cancellation must stop a running shell without waiting 25 seconds");
            assertEquals(-2, exit.get());
            assertFalse(runner.isRunning());
        } finally {
            runner.stop();
        }
    }

}
