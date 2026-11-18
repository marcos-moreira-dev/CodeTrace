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
}
