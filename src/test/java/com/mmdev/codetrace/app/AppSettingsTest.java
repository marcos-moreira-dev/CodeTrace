package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppSettingsTest {
    @Test
    void completionSoundCanBeDisabledWithoutChangingTypingSounds() {
        AppSettings options = new AppSettings(
                "Consolas", 16, true, "LATAM", false, 70, false, false);
        assertFalse(options.completionSound());
        assertFalse(options.typingSounds());
        assertFalse(options.withLayout("US").completionSound());
        assertFalse(options.withTurbo(true).completionSound());
        assertTrue(new AppSettings("Consolas", 16, true,
                "LATAM", false, 70, false).completionSound());
    }

    @Test
    void validatesTemplateOpacityAndPreservesItWhenChangingLayout() {
        AppSettings options = new AppSettings("Consolas", 16, true,
                "LATAM", false, 45);
        assertEquals(45, options.templateOpacity());
        assertEquals(45, options.withLayout("US").templateOpacity());
        assertThrows(IllegalArgumentException.class,
                () -> new AppSettings("Consolas", 16, true, "LATAM", false, 10));
        assertThrows(IllegalArgumentException.class,
                () -> new AppSettings("Consolas", 16, true, "LATAM", false, 101));
    }

    @Test
    void readOnlyNoticeCanBeTurnedOffAndSurvivesOtherChanges() {
        AppSettings original = new AppSettings("Consolas", 16, true,
                "LATAM", false, 70, false, true);
        assertFalse(original.readOnlyNotice(),
                "Consultation popups should be opt-in, not interrupt every file");
        AppSettings enabled = original.withReadOnlyNotice(true);
        assertTrue(enabled.readOnlyNotice());
        assertTrue(enabled.withLayout("US").readOnlyNotice());
        assertTrue(enabled.withTurbo(true).readOnlyNotice());
        assertTrue(enabled.withCompletionDialog(false).readOnlyNotice());
        assertTrue(enabled.withGraphLayoutMode(
                com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID)
                .readOnlyNotice());
        assertFalse(enabled.withReadOnlyNotice(false).readOnlyNotice());
    }

    @Test
    void organizedIsDefaultAndGraphChoiceSurvivesOtherSettingChanges() {
        AppSettings initial = new AppSettings(
                "Consolas", 16, true, "LATAM", false, 70, false, true);
        assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.ORGANIZED,
                initial.graphLayoutMode());
        var grid = initial.withGraphLayoutMode(
                com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID);
        assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID,
                grid.withLayout("US").graphLayoutMode());
        assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID,
                grid.withTurbo(true).graphLayoutMode());
        assertThrows(IllegalArgumentException.class,
                () -> initial.withGraphLayoutMode(null));
    }
}
