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
    void readOnlyNoticeStartsEnabledAndCanBeReenabledAfterOptOut() {
        AppSettings original = new AppSettings("Consolas", 16, true,
                "LATAM", false, 70, false, true);
        assertTrue(original.readOnlyNotice(),
                "New installations should explain consultation before an opt-out");
        AppSettings suppressed = original.withReadOnlyNotice(false);
        assertFalse(suppressed.readOnlyNotice());
        assertFalse(suppressed.withLayout("US").readOnlyNotice());
        assertFalse(suppressed.withTurbo(true).readOnlyNotice());
        assertFalse(suppressed.withCompletionDialog(false).readOnlyNotice());
        assertFalse(suppressed.withGraphLayoutMode(
                com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID)
                .readOnlyNotice());
        assertTrue(suppressed.withReadOnlyNotice(true).readOnlyNotice(),
                "The notice must be restorable from Preferences");
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
