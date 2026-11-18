package com.mmdev.codetrace.app;

import java.util.prefs.Preferences;

/** Window-level preferences are separate from each .ctrace training session. */
public record AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                          String keyboardLayout, boolean typingSounds,
                          int templateOpacity, boolean turboMode,
                          boolean completionSound) {
    private static final Preferences STORE =
            Preferences.userNodeForPackage(AppSettings.class);

    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds, 70, false, true);
    }

    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds, int opacity) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds, opacity, false, true);
    }

    /** Compatibility for callers constructed before completion sounds existed. */
    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds,
                       int templateOpacity, boolean turboMode) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds,
                templateOpacity, turboMode, true);
    }

    public AppSettings {
        if (templateOpacity < 20 || templateOpacity > 100) {
            throw new IllegalArgumentException("Template opacity must be 20–100 percent");
        }
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
                layout, STORE.getBoolean("practice.typingSounds", false),
                Math.max(20, Math.min(100, STORE.getInt("editor.opacity", 70))),
                STORE.getBoolean("practice.turbo", false),
                STORE.getBoolean("practice.completionSound", true));
    }

    public AppSettings withLayout(String layout) {
        return new AppSettings(fontFamily, fontSize, showExplorer, layout,
                typingSounds, templateOpacity, turboMode, completionSound);
    }

    public AppSettings withTurbo(boolean enabled) {
        return new AppSettings(fontFamily, fontSize, showExplorer,
                keyboardLayout, typingSounds, templateOpacity, enabled, completionSound);
    }

    public void save() {
        STORE.put("editor.font", fontFamily);
        STORE.putInt("editor.size", fontSize);
        STORE.putBoolean("explorer.visible", showExplorer);
        STORE.put("keyboard.layout", keyboardLayout);
        STORE.putBoolean("practice.typingSounds", typingSounds);
        STORE.putInt("editor.opacity", templateOpacity);
        STORE.putBoolean("practice.turbo", turboMode);
        STORE.putBoolean("practice.completionSound", completionSound);
    }
}
