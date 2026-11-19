package com.mmdev.codetrace.app;

import java.io.InputStream;
import javafx.scene.text.Font;

/**
 * Bundled, unmodified IBM Plex typefaces. No OS font installation is needed:
 * Windows distributions obtain their pinned OFL files at packaging time.
 * If the fonts are absent, stock developer builds retain Windows fallbacks.
 */
public final class FontResources {
    private static volatile String uiFamily = "Segoe UI";
    private static volatile String codeFamily = "Consolas";
    private static volatile boolean loaded;
    private static volatile boolean codeAvailable;

    private FontResources() { }

    /** Call on the JavaFX application thread before constructing UI scenes. */
    public static synchronized void register() {
        if (loaded) return;
        loaded = true;
        Font sans = load("/fonts/IBMPlexSans-Regular.ttf");
        load("/fonts/IBMPlexSans-Bold.ttf");
        Font mono = load("/fonts/IBMPlexMono-Regular.ttf");
        load("/fonts/IBMPlexMono-Bold.ttf");
        if (sans != null) uiFamily = sans.getFamily();
        if (mono != null) {
            codeFamily = mono.getFamily();
            codeAvailable = true;
        }
    }

    public static String uiFamily() { return uiFamily; }
    public static String codeFamily() { return codeFamily; }
    public static boolean bundledCodeAvailable() { return codeAvailable; }

    private static Font load(String resource) {
        try (InputStream stream = FontResources.class.getResourceAsStream(resource)) {
            return stream == null ? null : Font.loadFont(stream, 14);
        } catch (Exception invalid) {
            System.err.println("[CodeTrace/fonts] Unable to load " + resource
                    + ": " + invalid.getMessage());
            return null;
        }
    }
}
