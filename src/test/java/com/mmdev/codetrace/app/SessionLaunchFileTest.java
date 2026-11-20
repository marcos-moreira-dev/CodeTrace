package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SessionLaunchFileTest {
    @TempDir Path dir;

    @Test void acceptsExistingCtracesWithSpacesAccentsAndUppercaseExtension() throws Exception {
        Path path = Files.write(dir.resolve("Mi sesión final.CTRACE"), new byte[] {1});
        assertEquals(path.toAbsolutePath().normalize(),
                SessionLaunchFile.fromArguments(List.of(path.toString())).orElseThrow());
    }

    @Test void ignoresOtherFilesMultipleArgumentsAndMissingSessions() throws Exception {
        Path other = Files.write(dir.resolve("video.mp3"), new byte[0]);
        assertTrue(SessionLaunchFile.fromArguments(List.of(other.toString())).isEmpty());
        assertTrue(SessionLaunchFile.fromArguments(List.of("no-existe.ctrace")).isEmpty());
        assertTrue(SessionLaunchFile.fromArguments(List.of("-debug", other.toString())).isEmpty());
        assertTrue(SessionLaunchFile.fromArguments(List.of()).isEmpty());
    }
}
