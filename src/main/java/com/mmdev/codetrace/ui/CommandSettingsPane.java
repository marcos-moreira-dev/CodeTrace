package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.commands.ProjectCommandStore.Preset;
import com.mmdev.codetrace.commands.ProjectCommandStore.Shell;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/** Editable command presets on the Preferences > Comandos page. */
public final class CommandSettingsPane extends VBox {
    private final ObservableList<Preset> profiles;
    private final ListView<Preset> list = new ListView<>();
    private final TextField title = new TextField();
    private final ComboBox<Shell> shell = new ComboBox<>();
    private final TextField workingDirectory = new TextField(".");
    private final TextArea command = new TextArea();
    private final Label validation = new Label();

    public CommandSettingsPane(List<Preset> initial) {
        profiles = FXCollections.observableArrayList(initial);
        getStyleClass().add("command-settings-pane");
        setSpacing(12);

        Label heading = new Label("Comandos del proyecto");
        heading.getStyleClass().add("prefs-section-heading");

        list.setItems(profiles);
        list.setPrefWidth(175);
        list.setMinWidth(175);
        list.setMaxWidth(175);
        list.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(list, Priority.ALWAYS);

        Button create = new Button("Nuevo");
        create.setOnAction(event -> {
            list.getSelectionModel().clearSelection();
            title.clear();
            command.clear();
            workingDirectory.setText(".");
            shell.setValue(Shell.preferred());
            validation.setText("");
            title.requestFocus();
        });
        Button delete = new Button("Eliminar");
        delete.setOnAction(event -> {
            Preset selected = list.getSelectionModel().getSelectedItem();
            if (selected != null) profiles.remove(selected);
            list.getSelectionModel().clearSelection();
            title.clear();
            command.clear();
            validation.setText("");
        });
        VBox controls = new VBox(8, list, new HBox(7, create, delete));

        shell.getItems().setAll(Shell.values());
        shell.setValue(Shell.preferred());
        shell.setMaxWidth(Double.MAX_VALUE);
        title.setPromptText("Nombre corto, por ejemplo Compilar");
        workingDirectory.setPromptText("Ruta relativa: . o src");
        command.setPromptText("mvn clean verify");
        command.setPrefRowCount(5);
        command.setWrapText(false);
        validation.getStyleClass().add("prefs-feedback");
        validation.setWrapText(true);

        Button savePreset = new Button("Guardar comando");
        savePreset.setOnAction(event -> commitDraft());

        VBox form = new VBox(8,
                field("Nombre", title),
                field("Intérprete", shell),
                field("Carpeta de trabajo (relativa al proyecto)", workingDirectory),
                field("Instrucción de consola", command),
                savePreset, validation);
        form.setFillWidth(true);
        HBox.setHgrow(form, Priority.ALWAYS);

        list.getSelectionModel().selectedItemProperty().addListener(
                (observable, previous, chosen) -> {
                    if (chosen == null) return;
                    title.setText(chosen.name());
                    shell.setValue(chosen.shell());
                    command.setText(chosen.command());
                    workingDirectory.setText(chosen.directory());
                    validation.setText("");
                });

        Label caution = new Label("Los comandos se ejecutan sobre la carpeta real "
                + "del proyecto. No utilizan el código virtual.");
        caution.setWrapText(true);
        caution.getStyleClass().add("prefs-feedback");
        HBox body = new HBox(13, controls, form);
        HBox.setHgrow(form, Priority.ALWAYS);
        VBox.setVgrow(body, Priority.ALWAYS);
        getChildren().addAll(heading, body, caution);
    }

    private VBox field(String label, javafx.scene.Node control) {
        Label caption = new Label(label);
        caption.getStyleClass().add("prefs-field-label");
        VBox field = new VBox(5, caption, control);
        field.setFillWidth(true);
        return field;
    }

    /**
     * Commit the visible draft before Preferences Apply/OK. The actual write
     * to disk happens only through that parent dialog action.
     */
    public boolean commitDraft() {
        if (title.getText().isBlank() && command.getText().isBlank()) {
            validation.setText("");
            return true;
        }
        try {
            Preset preset = new Preset(title.getText().trim(), shell.getValue(),
                    command.getText(), workingDirectory.getText().trim());
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                profiles.set(selectedIndex, preset);
            } else {
                if (profiles.size() >= 20) {
                    throw new IllegalArgumentException("Máximo 20 comandos por proyecto");
                }
                profiles.add(preset);
            }
            list.getSelectionModel().select(preset);
            validation.setText("Comando preparado para guardar.");
            return true;
        } catch (IllegalArgumentException error) {
            validation.setText("Indica nombre e instrucción válidos (máximo 3.000 caracteres).");
            return false;
        }
    }

    public List<Preset> profiles() {
        return List.copyOf(new ArrayList<>(profiles));
    }
}
