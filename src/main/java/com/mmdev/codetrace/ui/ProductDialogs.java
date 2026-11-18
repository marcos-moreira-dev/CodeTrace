package com.mmdev.codetrace.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        createAbout(owner).showAndWait();
    }

    /** Separate creator for GUI tests; copy remains fully visible and scrollable. */
    public static Dialog<ButtonType> createAbout(Window owner) {
        Dialog<ButtonType> about = new Dialog<>();
        if (owner != null) about.initOwner(owner);
        about.setTitle("Acerca de CodeTrace");
        WindowBranding.apply(about);
        about.setResizable(true);
        DialogPane pane = about.getDialogPane();
        pane.getStyleClass().addAll("product-dialog", "about-dialog");
        pane.getStylesheets().add(ProductDialogs.class.getResource(
                "/css/codetrace.css").toExternalForm());
        pane.getButtonTypes().add(ButtonType.OK);
        pane.setPrefSize(680, 555);
        pane.setMinSize(560, 410);

        // The original small PNG remains for OS window chrome only. The
        // approved desktop icon has a dedicated 320px detail asset in About.
        ImageView brand = new ImageView(new Image(ProductDialogs.class
                .getResourceAsStream("/icons/codetrace-about-hires.png")));
        brand.setFitWidth(180);
        brand.setFitHeight(180);
        brand.setPreserveRatio(true);
        brand.setSmooth(true);
        brand.getStyleClass().add("about-logo-large");

        Label title = new Label("CodeTrace");
        title.getStyleClass().add("about-title");
        Label version = new Label("Versión 0.3.0");
        version.getStyleClass().add("about-version");
        VBox identity = new VBox(1, brand, title, version);
        identity.setAlignment(javafx.geometry.Pos.CENTER);
        identity.getStyleClass().add("about-identity-center");

        String description =
                "CodeTrace es una aplicación de escritorio para explorar, "
                + "comprender y reconstruir proyectos de software sin modificar "
                + "sus archivos originales.\n\n"
                + "Su propósito es desarrollar cultura, soltura y confianza "
                + "con el trabajo cotidiano del programador. Transcribir proyectos "
                + "permite reconocer los detalles de una implementación, "
                + "comprender las decisiones que la componen y disminuir la "
                + "resistencia a escribir código desde cero.\n\n"
                + "La práctica manual también ayuda a expresar con palabras "
                + "qué queremos construir: esa claridad de intención sigue "
                + "siendo importante incluso cuando utilizamos inteligencia "
                + "artificial como herramienta profesional.\n\n"
                + "CodeTrace compara cada carácter con una referencia, pero no "
                + "determina si su código original compila o funciona. "
                + "El proyecto de referencia permanece intacto.";
        Label body = new Label(description);
        body.setWrapText(true);
        body.setMaxWidth(Double.MAX_VALUE);
        body.setMinHeight(Region.USE_PREF_SIZE);
        body.getStyleClass().add("about-description");

        VBox contents = new VBox(8, identity, body);
        contents.getStyleClass().add("about-content");
        contents.setPadding(new Insets(10, 24, 20, 24));
        ScrollPane scroll = new ScrollPane(contents);
        scroll.getStyleClass().add("about-scroll");
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setContent(scroll);
        return about;
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
        WindowBranding.apply(result);
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
