package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConsultationFolderPolicyTest {
    @TempDir Path root;

    @Test void onlyReadsInsideAnIndependentSecondaryRoot() throws Exception {
        Path folder = Files.createDirectory(root.resolve("música"));
        Path song = Files.write(folder.resolve("canción.mp3"), new byte[] { 1, 2 });
        Path external = Files.write(root.resolve("fuera.mp3"), new byte[] { 3 });
        assertTrue(ConsultationFolderPolicy.contains(folder, song));
        assertTrue(ConsultationFolderPolicy.contains(folder, folder));
        assertFalse(ConsultationFolderPolicy.contains(folder, external));
        assertFalse(ConsultationFolderPolicy.contains(null, song));
    }
}
