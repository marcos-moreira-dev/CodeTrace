package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.app.AppSettings;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/** Traditional settings dialog, with only controls that already do something. */
public final class PreferencesWindow {
    private PreferencesWindow() {
    }

    public static void open(Window owner, AppSettings current,
                            Consumer<AppSettings> onApply) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Preferencias — CodeTrace");
        dialog.setMinWidth(540);
        dialog.setMinHeight(340);

        ListView<String> categories = new ListView<>(
                FXCollections.observableArrayList("Editor", "Ventana"));
        categories.setPrefWidth(155);
        categories.setMaxWidth(155);
        categories.getSelectionModel().selectFirst();

        ComboBox<String> families = new ComboBox<>(
                FXCollections.observableArrayList(Font.getFamilies()));
        families.setEditable(false);
        families.setMaxWidth(Double.MAX_VALUE);
        families.getSelectionModel().select(current.fontFamily());
        if (families.getValue() == null) {
            families.getSelectionModel().select("Consolas");
        }
        Slider size = new Slider(10, 36, current.fontSize());
        size.setBlockIncrement(1);
        size.setMajorTickUnit(2);
        size.setSnapToTicks(true);
        Label sizeValue = new Label(Integer.toString(current.fontSize()));
        size.valueProperty().addListener((observable, before, value) ->
                sizeValue.setText(Integer.toString((int) Math.round(value.doubleValue()))));

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(20));
        editor.setHgap(14);
        editor.setVgap(20);
        editor.add(new Label("Fuente"), 0, 0);
        editor.add(families, 1, 0);
        editor.add(new Label("Tamaño"), 0, 1);
        HBox sizeRow = new HBox(12, size, sizeValue);
        sizeRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(size, Priority.ALWAYS);
        editor.add(sizeRow, 1, 1);

        CheckBox explorer = new CheckBox("Mostrar explorador de proyectos");
        explorer.setSelected(current.showExplorer());
        VBox windowSettings = new VBox(explorer);
        windowSettings.setPadding(new Insets(20));

        BorderPane layout = new BorderPane();
        layout.setLeft(categories);
        layout.setCenter(editor);
        categories.getSelectionModel().selectedItemProperty().addListener(
                (observable, before, selected) ->
                        layout.setCenter("Ventana".equals(selected) ? windowSettings : editor));

        Runnable apply = () -> onApply.accept(new AppSettings(
                families.getValue() == null ? current.fontFamily() : families.getValue(),
                (int) Math.round(size.getValue()), explorer.isSelected()));
        Button applyButton = new Button("Aplicar");
        applyButton.setOnAction(event -> apply.run());
        Button cancel = new Button("Cancelar");
        cancel.setOnAction(event -> dialog.close());
        Button accept = new Button("Aceptar");
        accept.setDefaultButton(true);
        accept.setOnAction(event -> {
            apply.run();
            dialog.close();
        });
        HBox buttons = new HBox(8, accept, cancel, applyButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPadding(new Insets(12));
        layout.setBottom(buttons);

        Scene scene = new Scene(layout, 650, 380);
        scene.getStylesheets().add(PreferencesWindow.class
                .getResource("/css/codetrace.css").toExternalForm());
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
