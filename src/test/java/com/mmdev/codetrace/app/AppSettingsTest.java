package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppSettingsTest {
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
