package com.mmdev.codetrace.app;

import java.util.prefs.Preferences;
import com.mmdev.codetrace.reference.GraphLayoutEngine;

/** Window-level preferences are separate from each .ctrace training session. */
public record AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                          String keyboardLayout, boolean typingSounds,
                          int templateOpacity, boolean turboMode,
                          boolean completionSound, boolean completionDialog,
                          boolean readOnlyNotice, GraphLayoutEngine.Mode graphLayoutMode) {
    /** Retain the former 10-argument constructor for existing callers/tests. */
    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds,
                       int templateOpacity, boolean turboMode,
                       boolean completionSound, boolean completionDialog,
                       GraphLayoutEngine.Mode graphLayoutMode) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds,
                templateOpacity, turboMode, completionSound, completionDialog,
                true, graphLayoutMode);
    }

    private static final Preferences STORE =
            Preferences.userNodeForPackage(AppSettings.class);
    public static final GraphLayoutEngine.Mode DEFAULT_GRAPH_MODE =
            GraphLayoutEngine.Mode.ORGANIZED;

    /** Preserve the established 9-argument API for older settings callers. */
    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds,
                       int templateOpacity, boolean turboMode,
                       boolean completionSound, GraphLayoutEngine.Mode graphLayoutMode) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds,
                templateOpacity, turboMode, completionSound, true, graphLayoutMode);
    }

    /** Existing settings clients keep the organized graph default. */
    public AppSettings(String fontFamily, int fontSize, boolean showExplorer,
                       String keyboardLayout, boolean typingSounds,
                       int templateOpacity, boolean turboMode, boolean completionSound) {
        this(fontFamily, fontSize, showExplorer, keyboardLayout, typingSounds,
                templateOpacity, turboMode, completionSound, DEFAULT_GRAPH_MODE);
    }

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
        if (graphLayoutMode == null)
            throw new IllegalArgumentException("Graph layout mode is required");
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
        String family = STORE.get("editor.font", FontResources.codeFamily());
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
                STORE.getBoolean("practice.completionSound", true),
                STORE.getBoolean("practice.completionDialog", true),
                STORE.getBoolean("notice.readOnlyConsultation.v2", true),
                storedGraphMode());
    }

    private static GraphLayoutEngine.Mode storedGraphMode() {
        try {
            return GraphLayoutEngine.Mode.valueOf(
                    STORE.get("graph.layout.v2", DEFAULT_GRAPH_MODE.name()));
        } catch (IllegalArgumentException invalid) {
            return DEFAULT_GRAPH_MODE;
        }
    }

    public AppSettings withGraphLayoutMode(GraphLayoutEngine.Mode mode) {
        return new AppSettings(fontFamily, fontSize, showExplorer,
                keyboardLayout, typingSounds, templateOpacity, turboMode,
                completionSound, completionDialog, readOnlyNotice, mode);
    }

    public AppSettings withLayout(String layout) {
        return new AppSettings(fontFamily, fontSize, showExplorer, layout,
                typingSounds, templateOpacity, turboMode, completionSound,
                completionDialog, readOnlyNotice, graphLayoutMode);
    }

    public AppSettings withTurbo(boolean enabled) {
        return new AppSettings(fontFamily, fontSize, showExplorer,
                keyboardLayout, typingSounds, templateOpacity, enabled,
                completionSound, completionDialog, readOnlyNotice, graphLayoutMode);
    }

    public AppSettings withCompletionDialog(boolean visible) {
        return new AppSettings(fontFamily, fontSize, showExplorer, keyboardLayout,
                typingSounds, templateOpacity, turboMode, completionSound,
                visible, readOnlyNotice, graphLayoutMode);
    }

    public AppSettings withReadOnlyNotice(boolean enabled) {
        return new AppSettings(fontFamily, fontSize, showExplorer, keyboardLayout,
                typingSounds, templateOpacity, turboMode, completionSound,
                completionDialog, enabled, graphLayoutMode);
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
        STORE.putBoolean("practice.completionDialog", completionDialog);
        STORE.putBoolean("notice.readOnlyConsultation.v2", readOnlyNotice);
        // v2 intentionally does not inherit the older experimental GRID choice.
        // New and migrated installations start organized until explicitly changed.
        STORE.put("graph.layout.v2", graphLayoutMode.name());
    }
}
