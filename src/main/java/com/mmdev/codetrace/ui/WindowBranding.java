package com.mmdev.codetrace.ui;

import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** Shared product branding across desktop utility windows and dialogs. */
public final class WindowBranding {
    private WindowBranding() {}

    public static void apply(Stage stage) {
        if (stage == null) return;
        if (WindowBranding.class.getResource("/icons/codetrace-window.png") == null) return;
        try (var image = WindowBranding.class.getResourceAsStream(
                "/icons/codetrace-window.png")) {
            if (image != null) stage.getIcons().setAll(new Image(image));
        } catch (java.io.IOException ignored) {
            // Window chrome is noncritical; do not prevent the dialog opening.
        }
    }

    public static void apply(Dialog<?> dialog) {
        dialog.setOnShown(event -> {
            if (dialog.getDialogPane().getScene().getWindow() instanceof Stage stage) {
                apply(stage);
            }
        });
    }
}
