package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;

class PracticeModeTest {
    @Test
    void explicitModeIdentifiersRemainStableForSessionFiles() {
        assertEquals(PracticeMode.RECONSTRUCTION, PracticeMode.fromCode(0));
        assertEquals(PracticeMode.DIRECT, PracticeMode.fromCode(1));
        assertThrows(IllegalArgumentException.class, () -> PracticeMode.fromCode(2));
    }

    @TempDir Path temp;

    @Test
    void externalProjectsDefaultToManualReconstruction() {
        Path external = temp.resolve("new-unconfigured-project");
        assertEquals(PracticeMode.RECONSTRUCTION, PracticeMode.forProject(external));
    }
}
