package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.library.BuiltInLibrary;
import com.mmdev.codetrace.library.BuiltInLibrary.Pack;
import java.nio.file.Path;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

/** A small, conventional picker for bundled language practice projects. */
public final class PracticeLibraryDialog {
    private PracticeLibraryDialog() {}

    public static Optional<Pack> choose(Window owner, BuiltInLibrary library) {
        Dialog<Pack> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Biblioteca de práctica — CodeTrace");
        ButtonType open = new ButtonType("Abrir práctica", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(open, ButtonType.CANCEL);

        ListView<Pack> languages = new ListView<>(
                FXCollections.observableArrayList(library.packs()));
        languages.getStyleClass().add("practice-library-list");
        languages.setPrefHeight(380);
        languages.setPrefWidth(460);
        languages.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(Pack pack, boolean empty) {
                super.updateItem(pack, empty);
                setText(null);
                if (pack == null || empty) {
                    setGraphic(null);
                    return;
                }
                Label title = new Label(pack.name());
                title.getStyleClass().add("library-pack-title");
                Label count = new Label(library.size(pack) + " ejercicios");
                count.getStyleClass().add("library-pack-count");
                javafx.scene.layout.Region fill = new javafx.scene.layout.Region();
                HBox.setHgrow(fill, Priority.ALWAYS);
                HBox row = new HBox(10,
                        FileTypeIcons.forPath(Path.of("example" + pack.extension()), false),
                        title, fill, count);
                row.setAlignment(Pos.CENTER_LEFT);
                setGraphic(row);
            }
        });

        Label title = new Label("Biblioteca de práctica");
        title.getStyleClass().add("prefs-section-heading");
        Label detail = new Label(
                "Ejercicios de código incluidos en CodeTrace. Funciona sin conexión.");
        detail.getStyleClass().add("prefs-feedback");
        detail.setWrapText(true);

        VBox area = new VBox(12, title, languages, detail);
        area.getStyleClass().add("practice-library-panel");
        VBox.setVgrow(languages, Priority.ALWAYS);
        dialog.getDialogPane().setContent(area);
        dialog.getDialogPane().getStylesheets().add(
                PracticeLibraryDialog.class.getResource("/css/codetrace.css")
                        .toExternalForm());

        Button openButton = (Button) dialog.getDialogPane().lookupButton(open);
        openButton.disableProperty().bind(
                languages.getSelectionModel().selectedItemProperty().isNull());
        languages.getSelectionModel().selectFirst();
        languages.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2
                    && languages.getSelectionModel().getSelectedItem() != null) {
                openButton.fire();
            }
        });
        dialog.setResultConverter(button -> button == open
                ? languages.getSelectionModel().getSelectedItem() : null);
        return dialog.showAndWait();
    }
}
