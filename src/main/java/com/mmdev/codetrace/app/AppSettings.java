package com.mmdev.codetrace.app;

import java.util.prefs.Preferences;

/** Window-level preferences are separate from each .ctrace training session. */
public record AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                          String keyboardLayout, boolean typingSounds) {
    private static final Preferences STORE =
            Preferences.userNodeForPackage(AppSettings.class);

    public AppSettings {
        if (fontFamily == null || fontFamily.isBlank()) {
            throw new IllegalArgumentException("Font family is required");
        }
        if (!keyboardLayout.equals("LATAM") && !keyboardLayout.equals("US")) {
            throw new IllegalArgumentException("Unsupported keyboard layout");
        }
        if (fontSize < 10 || fontSize > 36) {
            throw new IllegalArgumentException("Font size must be between 10 and 36");
        }
    }

    public static AppSettings load() {
        String family = STORE.get("editor.font", "Consolas");
        int size = Math.max(10, Math.min(36, STORE.getInt("editor.size", 16)));
        String layout = STORE.get("keyboard.layout", "LATAM");
        if (!layout.equals("LATAM") && !layout.equals("US")) {
            layout = "LATAM";
        }
        return new AppSettings(family, size,
                STORE.getBoolean("explorer.visible", true),
                layout, STORE.getBoolean("practice.typingSounds", false));
    }

    public AppSettings withLayout(String layout) {
        return new AppSettings(fontFamily, fontSize, showExplorer, layout, typingSounds);
    }

    public void save() {
        STORE.put("editor.font", fontFamily);
        STORE.putInt("editor.size", fontSize);
        STORE.putBoolean("explorer.visible", showExplorer);
        STORE.put("keyboard.layout", keyboardLayout);
        STORE.putBoolean("practice.typingSounds", typingSounds);
    }
}
