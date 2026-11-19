package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.app.AppSettings;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import com.mmdev.codetrace.commands.ProjectCommandStore;
import com.mmdev.codetrace.commands.ProjectCommandStore.Preset;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Desktop Preferences: persistent navigation on the left, functional settings
 * on the right, and conventional Apply/Cancel/OK actions.
 */
public final class PreferencesWindow {
    private PreferencesWindow() {
    }

    public static void open(Window owner, AppSettings current,
                            Consumer<AppSettings> onApply,
                            Function<Boolean, Boolean> previewSound) {
        create(owner, current, onApply, previewSound).showAndWait();
    }

    public static void open(Window owner, AppSettings current,
                            Consumer<AppSettings> onApply,
                            Function<Boolean, Boolean> previewSound,
                            Path projectRoot, ProjectCommandStore store,
                            Runnable onCommandsChanged) {
        create(owner, current, onApply, previewSound,
                projectRoot, store, onCommandsChanged).showAndWait();
    }

    /** Open Preferences directly at the relevant project-command category. */
    public static void openCommands(Window owner, AppSettings current,
                                    Consumer<AppSettings> onApply,
                                    Function<Boolean, Boolean> previewSound,
                                    Path projectRoot, ProjectCommandStore store,
                                    Runnable onCommandsChanged) {
        Stage dialog = create(owner, current, onApply, previewSound,
                projectRoot, store, onCommandsChanged);
        @SuppressWarnings("unchecked")
        ListView<String> navigation = (ListView<String>)
                dialog.getScene().getRoot().lookup(".prefs-navigation");
        if (navigation != null) {
            navigation.getSelectionModel().select("Comandos");
        }
        dialog.showAndWait();
    }

    /** Allows GUI smoke tests to inspect the scene before opening the modal. */
    public static Stage create(Window owner, AppSettings current,
                               Consumer<AppSettings> onApply,
                               Function<Boolean, Boolean> previewSound) {
        return create(owner, current, onApply, previewSound,
                null, null, () -> {});
    }

    public static Stage create(Window owner, AppSettings current,
                               Consumer<AppSettings> onApply,
                               Function<Boolean, Boolean> previewSound,
                               Path projectRoot, ProjectCommandStore store,
                               Runnable onCommandsChanged) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Preferencias — CodeTrace");
        WindowBranding.apply(dialog);
        dialog.setMinWidth(700);
        dialog.setMinHeight(500);

        BorderPane root = new BorderPane();
        root.getStyleClass().add("prefs-root");
        Label heading = new Label("Preferencias");
        heading.getStyleClass().add("prefs-window-heading");
        HBox header = new HBox(heading);
        header.getStyleClass().add("prefs-header");
        header.setAlignment(Pos.CENTER_LEFT);
        root.setTop(header);

        ListView<String> navigation = new ListView<>(
                FXCollections.observableArrayList("Editor", "Ventana", "Grafo", "Sonido", "Comandos"));
        navigation.getStyleClass().add("prefs-navigation");
        navigation.setPrefWidth(168);
        navigation.setMinWidth(168);
        navigation.setMaxWidth(168);
        navigation.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    FontIcon icon = new FontIcon(switch (item) {
                        case "Editor" -> "fth-type";
                        case "Ventana" -> "fth-layout";
                        case "Grafo" -> "fth-share-2";
                        case "Sonido" -> "fth-volume-2";
                        default -> "fth-terminal";
                    });
                    icon.setIconSize(16);
                    icon.setIconColor(javafx.scene.paint.Color.web("#4A6076"));
                    setGraphic(icon);
                }
            }
        });

        ComboBox<String> families = new ComboBox<>(
                FXCollections.observableArrayList(Font.getFamilies()));
        families.getStyleClass().add("prefs-font-choice");
        families.setMaxWidth(Double.MAX_VALUE);
        families.setCellFactory(list -> new FontFamilyCell());
        families.setButtonCell(new FontFamilyCell());
        families.setValue(current.fontFamily());
        if (families.getValue() == null
                || !families.getItems().contains(families.getValue())) {
            families.setValue("Consolas");
        }

        Slider size = new Slider(10, 36, current.fontSize());
        size.setMajorTickUnit(1);
        size.setMinorTickCount(0);
        size.setBlockIncrement(1);
        size.setSnapToTicks(true);
        size.setMinWidth(120);
        HBox.setHgrow(size, Priority.ALWAYS);
        Label sizeValue = new Label(current.fontSize() + " pt");
        sizeValue.getStyleClass().add("prefs-size-value");
        sizeValue.setMinWidth(44);

        // Opacity is a continuous visual preference, so use a horizontal
        // Slider rather than a numeric Spinner. The value stays readable.
        Slider opacity = new Slider(20, 100, current.templateOpacity());
        opacity.getStyleClass().add("prefs-opacity-slider");
        opacity.setSnapToTicks(true);
        opacity.setMajorTickUnit(5);
        opacity.setMinorTickCount(4);
        opacity.setBlockIncrement(1);
        opacity.setMinWidth(130);
        HBox.setHgrow(opacity, Priority.ALWAYS);
        opacity.setTooltip(new Tooltip(
                "Opacidad de la plantilla pendiente; los aciertos y errores permanecen nítidos."));
        Label opacityValue = new Label(current.templateOpacity() + " %");
        opacityValue.setMinWidth(48);
        opacityValue.getStyleClass().add("prefs-size-value");
        HBox opacityRow = new HBox(10, opacity, opacityValue);
        opacityRow.setAlignment(Pos.CENTER_LEFT);

        Label preview = new Label("public class Main {\n    int valor = 21;\n}");
        preview.getStyleClass().add("prefs-code-preview");
        preview.setWrapText(false);
        preview.setMaxWidth(Double.MAX_VALUE);
        preview.setMinHeight(Region.USE_PREF_SIZE);
        Runnable updatePreview = () -> {
            String family = families.getValue() == null ? "Consolas" : families.getValue();
            int points = (int) Math.round(size.getValue());
            preview.setFont(Font.font(family, points));
            int percentage = (int) Math.round(opacity.getValue());
            preview.setOpacity(percentage / 100.0);
            opacityValue.setText(percentage + " %");
            sizeValue.setText(points + " pt");
        };
        size.valueProperty().addListener((obs, previous, value) -> updatePreview.run());
        families.valueProperty().addListener((obs, previous, value) -> updatePreview.run());
        opacity.valueProperty().addListener((obs, previous, value) -> updatePreview.run());
        updatePreview.run();

        CheckBox turbo = AppControls.checkBox("Omitir automáticamente símbolos y puntuación");
        turbo.setSelected(current.turboMode());

        VBox editorPanel = section("Editor");
        editorPanel.getChildren().addAll(
                field("Fuente de código", families),
                field("Tamaño de letra", new HBox(10, size, sizeValue)),
                field("Opacidad de la plantilla", opacityRow),
                groupHeading("Práctica"), turbo,
                groupHeading("Vista previa"), preview);

        CheckBox explorer = AppControls.checkBox("Mostrar explorador de proyectos");
        explorer.setSelected(current.showExplorer());
        VBox windowPanel = section("Ventana");
        windowPanel.getChildren().addAll(groupHeading("Paneles"), explorer);

        // Layout is an application preference, not permanent map toolbar clutter.
        ExclusiveChoiceBar<GraphLayoutEngine.Mode> graphLayout =
                new ExclusiveChoiceBar<>(
                        List.of(
                                new ExclusiveChoiceBar.Choice<>(GraphLayoutEngine.Mode.ORGANIZED,
                                        "Organizado", "Agrupa archivos conectados y evita etiquetas superpuestas"),
                                new ExclusiveChoiceBar.Choice<>(GraphLayoutEngine.Mode.GRID,
                                        "Cuadrícula", "Distribución simple en filas y columnas")),
                        current.graphLayoutMode(), mode -> {});
        graphLayout.getStyleClass().add("prefs-graph-layout");
        graphLayout.setMaxWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
        Label graphExplanation = new Label(
                "Organizado distribuye los archivos según sus dependencias. "
                + "La opción no modifica los imports ni los archivos originales. "
                + "Puedes arrastrar nodos y guardar su posición en la sesión.");
        graphExplanation.setWrapText(true);
        graphExplanation.getStyleClass().add("prefs-feedback");
        VBox graphPanel = section("Mapa de dependencias");
        graphPanel.getChildren().addAll(
                groupHeading("Distribución inicial"), graphLayout,
                graphExplanation);

        CheckBox sounds = AppControls.checkBox("Reproducir sonidos al escribir");
        sounds.setSelected(current.typingSounds());
        CheckBox completionSound = new CheckBox(
                "Reproducir sonido de éxito al completar cada archivo");
        completionSound.setSelected(current.completionSound());
        Button sampleCorrect = new Button("Probar acierto");
        Button sampleError = new Button("Probar error");
        Label sampleStatus = new Label();
        sampleStatus.getStyleClass().add("prefs-feedback");
        sampleStatus.setWrapText(true);
        sampleStatus.setMinHeight(24);
        sampleStatus.setMaxWidth(Double.MAX_VALUE);
        sampleCorrect.setOnAction(event -> sampleStatus.setText(
                previewSound.apply(true) ? "Reproducción solicitada."
                        : "No se pudo reproducir el sonido."));
        sampleError.setOnAction(event -> sampleStatus.setText(
                previewSound.apply(false) ? "Reproducción solicitada."
                        : "No se pudo reproducir el sonido."));
        HBox previews = new HBox(9, sampleCorrect, sampleError);
        VBox soundPanel = section("Sonido");
        soundPanel.getChildren().addAll(
                groupHeading("Mecanografía"), sounds,
                groupHeading("Finalización de archivos"), completionSound,
                groupHeading("Escuchar muestras"), previews, sampleStatus);

        CommandSettingsPane commandEditor = null;
        javafx.scene.Node commandsPage;
        if (projectRoot != null && store != null) {
            try {
                commandEditor = new CommandSettingsPane(store.load(projectRoot));
                commandsPage = commandEditor;
            } catch (IOException badConfiguration) {
                Label notice = new Label("No se pudieron abrir los comandos guardados: "
                        + badConfiguration.getMessage());
                notice.setWrapText(true);
                commandsPage = new VBox(notice);
                commandsPage.getStyleClass().add("prefs-section");
            }
        } else {
            Label notice = new Label(
                    "Abre una carpeta de referencia para configurar sus comandos.");
            notice.setWrapText(true);
            commandsPage = new VBox(notice);
            commandsPage.getStyleClass().add("prefs-section");
        }

        final javafx.scene.Node stableCommandsPage = commandsPage;

        BorderPane sectionContent = new BorderPane();
        sectionContent.getStyleClass().add("prefs-content");
        sectionContent.setCenter(editorPanel);
        navigation.getSelectionModel().selectedItemProperty().addListener(
                (obs, previous, selected) -> sectionContent.setCenter(
                        "Ventana".equals(selected) ? windowPanel
                                : "Grafo".equals(selected) ? graphPanel
                                : "Sonido".equals(selected) ? soundPanel
                                : "Comandos".equals(selected) ? stableCommandsPage : editorPanel));
        navigation.getSelectionModel().selectFirst();
        root.setLeft(navigation);
        root.setCenter(sectionContent);

        final CommandSettingsPane editableCommands = commandEditor;
        java.util.function.BooleanSupplier apply = () -> {
            if (editableCommands != null) {
                if (!editableCommands.commitDraft()) return false;
                try {
                    store.save(projectRoot, editableCommands.profiles());
                    onCommandsChanged.run();
                } catch (IOException error) {
                    ProductDialogs.error(dialog, "Comandos del proyecto",
                            "No se pudieron guardar: " + error.getMessage());
                    return false;
                }
            }
            onApply.accept(new AppSettings(
                    families.getValue() == null ? current.fontFamily() : families.getValue(),
                    (int) Math.round(size.getValue()),
                    explorer.isSelected(), current.keyboardLayout(), sounds.isSelected(),
                    (int) Math.round(opacity.getValue()), turbo.isSelected(),
                    completionSound.isSelected(), graphLayout.selected()));
            return true;
        };
        Button accept = new Button("Aceptar");
        accept.setDefaultButton(true);
        accept.setOnAction(event -> {
            if (apply.getAsBoolean()) dialog.close();
        });
        Button cancel = new Button("Cancelar");
        cancel.setCancelButton(true);
        cancel.setOnAction(event -> dialog.close());
        Button applyButton = new Button("Aplicar");
        applyButton.setOnAction(event -> apply.getAsBoolean());
        HBox buttons = new HBox(9, accept, cancel, applyButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.getStyleClass().add("prefs-actions");
        root.setBottom(buttons);

        Scene scene = new Scene(root, 830, 600);
        scene.getStylesheets().add(PreferencesWindow.class
                .getResource("/css/codetrace.css").toExternalForm());
        dialog.setScene(scene);
        return dialog;
    }

    /**
     * A single list-cell component for the opened catalogue and selected value.
     * The sample uses the actual installed family instead of an approximation.
     */
    private static final class FontFamilyCell extends ListCell<String> {
        @Override
        protected void updateItem(String family, boolean empty) {
            super.updateItem(family, empty);
            setText(null);
            if (empty || family == null) {
                setGraphic(null);
                return;
            }
            Label name = new Label(family);
            name.setMinWidth(160);
            name.setMaxWidth(160);
            name.getStyleClass().add("font-family-name");
            Label sample = new Label(family);
            sample.setFont(Font.font(family, 14));
            sample.setMaxWidth(170);
            sample.setTooltip(new Tooltip(family));
            sample.getStyleClass().add("font-family-preview");
            HBox row = new HBox(9, name, sample);
            row.setAlignment(Pos.CENTER_LEFT);
            setGraphic(row);
        }
    }

    private static VBox section(String title) {
        Label heading = new Label(title);
        heading.getStyleClass().add("prefs-section-heading");
        VBox panel = new VBox(16, heading);
        panel.getStyleClass().add("prefs-section");
        panel.setFillWidth(true);
        return panel;
    }

    private static Label groupHeading(String title) {
        Label heading = new Label(title);
        heading.getStyleClass().add("prefs-group-heading");
        return heading;
    }

    private static VBox field(String title, javafx.scene.Node control) {
        Label name = new Label(title);
        name.getStyleClass().add("prefs-field-label");
        VBox wrapper = new VBox(7, name, control);
        wrapper.setFillWidth(true);
        return wrapper;
    }
}
