package com.mmdev.codetrace.ui;

import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** Shared product branding across desktop utility windows and dialogs.  * <p><strong>Engineering rationale.</strong> Window and dialog branding is shared presentation infrastructure, separate from per-document file associations and their icon. Centralization keeps the native title/icon behavior consistent while packaging scripts own the operating-system integration.</p>
*/
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
