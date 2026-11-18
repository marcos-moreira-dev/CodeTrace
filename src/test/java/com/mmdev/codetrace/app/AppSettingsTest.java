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
}
