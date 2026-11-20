package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Generic, non-editable fallback. CodeTrace never attempts to parse unknown
 * binaries as text or automatically launches potentially writable programs.
  * <p><strong>Engineering rationale.</strong> When a file cannot be previewed safely, the UI should explain the limitation instead of displaying corrupt text or failing silently. Such a result is consultation-only and must never count as a failed typing attempt.</p>
*/
public final class UnsupportedReferencePane extends VBox {
    public UnsupportedReferencePane(Path file) {
        super(12);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(24));
        Label name = new Label(file.getFileName().toString());
        name.getStyleClass().add("media-audio-title");
        Label explanation = new Label(
                "Este formato no dispone de vista previa integrada.");
        explanation.setWrapText(true);
        String size = "Tamaño no disponible";
        try {
            size = String.format(java.util.Locale.ROOT, "%.1f MB",
                    Files.size(file) / 1_048_576.0);
        } catch (IOException | SecurityException ignored) { }
        Label details = new Label(size);
        details.getStyleClass().add("media-audio-subtitle");
        Button reveal = new Button("Mostrar en el Explorador", new FontIcon("fth-folder"));
        reveal.setOnAction(event -> {
            try {
                if (System.getProperty("os.name", "").startsWith("Windows")) {
                    new ProcessBuilder(ExplorerReveal.windowsCommand(file)).start();
                } else if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(file.getParent().toFile());
                }
            } catch (IOException | SecurityException failure) {
                explanation.setText("No se pudo localizar el archivo en el Explorador.");
            }
        });
        getChildren().addAll(name, explanation, details, reveal);
    }
}
