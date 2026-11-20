package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.io.InputStream;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/** VS Code-like welcome using the exact official PNG, desaturated without redrawing.  * <p><strong>Engineering rationale.</strong> An empty workspace is a deliberate navigation state, not an application error. Its subdued monochrome brand mark and concise prompt provide orientation while tab lifetime is still managed exclusively by the document controller.</p>
*/
public final class EmptyWorkspacePane extends StackPane {
    public EmptyWorkspacePane(TabPane tabs) {
        getStyleClass().add("empty-workspace");
        setAlignment(Pos.CENTER);
        VBox contents = new VBox(13);
        contents.setAlignment(Pos.CENTER);
        createLogoView(contents);
        Label heading = new Label("Abre un archivo para comenzar");
        heading.getStyleClass().add("empty-workspace-heading");
        Label hint = new Label(
                "Selecciona un archivo del explorador o abre una referencia desde Archivo.");
        hint.getStyleClass().add("empty-workspace-hint");
        hint.setWrapText(true);
        contents.getChildren().addAll(heading, hint);
        getChildren().add(contents);
        visibleProperty().bind(Bindings.isEmpty(tabs.getTabs()));
        managedProperty().bind(visibleProperty());
    }

    private static void createLogoView(VBox contents) {
        try (InputStream input = EmptyWorkspacePane.class.getResourceAsStream(
                "/icons/codetrace-about-hires.png")) {
            if (input == null) return;
            Image source = new Image(input);
            if (source.isError() || source.getPixelReader() == null) return;
            ImageView logo = new ImageView(toMonochrome(source));
            logo.setFitWidth(220);
            logo.setFitHeight(220);
            logo.setPreserveRatio(true);
            logo.setSmooth(true);
            // Keep the official monochrome artwork as a subtle watermark.
            logo.setOpacity(0.27);
            logo.setMouseTransparent(true);
            logo.setAccessibleText("Logotipo monocromático oficial de CodeTrace");
            logo.getStyleClass().add("empty-workspace-logo");
            contents.getChildren().add(logo);
        } catch (IOException ignored) {
            // Missing optional branding must not block opening the editor.
        }
    }

    static Image toMonochrome(Image source) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();
        WritableImage gray = new WritableImage(width, height);
        PixelReader pixels = source.getPixelReader();
        PixelWriter writer = gray.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixel = pixels.getColor(x, y);
                double brightness = pixel.getRed() * 0.2126
                        + pixel.getGreen() * 0.7152 + pixel.getBlue() * 0.0722;
                writer.setColor(x, y, Color.gray(0.19 + 0.43 * brightness,
                        pixel.getOpacity()));
            }
        }
        return gray;
    }
}
