package com.mmdev.codetrace.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.Window;

/** Shared readable dialogs. Long messages scroll instead of being ellipsized. */
public final class ProductDialogs {
    private ProductDialogs() {}

    public static void about(Window owner) {
        dialog(owner, "Acerca de CodeTrace", "CodeTrace  ·  versión 0.2.0",
                "Herramienta de escritorio para practicar la reconstrucción manual "
                + "de proyectos de software.\n\n"
                + "Su propósito es desarrollar familiaridad, soltura y confianza "
                + "con el trabajo cotidiano del programador. La práctica manual "
                + "permite reconocer los detalles de una implementación y expresar "
                + "con mayor precisión lo que se desea construir.\n\n"
                + "CodeTrace compara lo transcrito con la referencia, pero no "
                + "comprueba si el código original compila o funciona.",
                ButtonType.OK).showAndWait();
    }

    public static void error(Window owner, String title, String message) {
        dialog(owner, title, title, message == null ? "Operación no disponible." : message,
                ButtonType.OK).showAndWait();
    }

    public static ButtonType confirm(Window owner, String title, String message,
                                     ButtonType... choices) {
        return dialog(owner, title, title, message, choices)
                .showAndWait().orElse(ButtonType.CANCEL);
    }

    private static Dialog<ButtonType> dialog(Window owner, String title,
                                             String heading, String message,
                                             ButtonType... choices) {
        Dialog<ButtonType> result = new Dialog<>();
        if (owner != null) result.initOwner(owner);
        result.setTitle(title);
        result.setOnShown(event -> {
            if (result.getDialogPane().getScene().getWindow() instanceof Stage stage) {
                stage.getIcons().setAll(new Image(ProductDialogs.class
                        .getResourceAsStream("/icons/codetrace-window.png")));
            }
        });
        result.setResizable(true);
        DialogPane pane = result.getDialogPane();
        pane.getStyleClass().add("product-dialog");
        pane.getStylesheets().add(ProductDialogs.class.getResource(
                "/css/codetrace.css").toExternalForm());
        pane.getButtonTypes().addAll(choices);
        pane.setMinWidth(470);
        pane.setPrefWidth(600);
        pane.setMinHeight(260);
        pane.setPrefHeight(355);
        Label headingLabel = new Label(heading);
        headingLabel.getStyleClass().add("dialog-heading");
        headingLabel.setWrapText(true);
        headingLabel.setMinHeight(Region.USE_PREF_SIZE);
        Label text = new Label(message);
        text.getStyleClass().add("dialog-body");
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMinHeight(Region.USE_PREF_SIZE);
        ScrollPane scroll = new ScrollPane(text);
        scroll.getStyleClass().add("dialog-message-scroll");
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        VBox body = new VBox(12, headingLabel, scroll);
        body.setPadding(new Insets(14, 18, 8, 18));
        pane.setContent(body);
        return result;
    }
}
