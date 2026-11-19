package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReferenceTreeRevealTest {
    @Test void windowsRevealUsesSeparateOptionAndLiteralFilePath() {
        Path source = Path.of("User Documents", "Ejercicios de José", "Program #1, 2026.ps1");
        var command = ReferenceTree.windowsRevealCommand(source);
        assertEquals(3, command.size());
        assertEquals("explorer.exe", command.get(0));
        assertEquals("/select,", command.get(1));
        assertEquals(source.toAbsolutePath().normalize().toString(), command.get(2));
        assertTrue(command.get(2).contains("Ejercicios de José"));
        assertTrue(command.get(2).contains("Program #1, 2026.ps1"));
    }
}
