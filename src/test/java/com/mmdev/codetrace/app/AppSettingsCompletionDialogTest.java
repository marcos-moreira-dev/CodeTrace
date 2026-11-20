package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppSettingsCompletionDialogTest {
    @Test void canDisableCompletionDialogIndependentlyFromSoundAndOtherSettings() {
        AppSettings initial = new AppSettings("Consolas", 16, true,
                "LATAM", false, 70, false, true);
        assertTrue(initial.completionDialog());
        AppSettings quiet = initial.withCompletionDialog(false);
        assertFalse(quiet.completionDialog());
        assertTrue(quiet.completionSound());
        assertFalse(quiet.withLayout("US").completionDialog());
        assertFalse(quiet.withTurbo(true).completionDialog());
        assertFalse(quiet.withGraphLayoutMode(
                com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID)
                .completionDialog());
    }
}
