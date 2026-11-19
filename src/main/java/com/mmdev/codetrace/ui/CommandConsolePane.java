package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.commands.CommandRunner;
import com.mmdev.codetrace.commands.CommandWarningPreferences;
import com.mmdev.codetrace.commands.ProjectCommandStore.Preset;
import java.nio.file.Path;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.text.Font;

/**
 * Collapsible, read-only terminal output. External processes run only after
 * clicking Execute; they never receive virtual reconstruction files.
 */
public final class CommandConsolePane extends BorderPane {
    private final CommandRunner executor = new CommandRunner();
    private final ComboBox<Preset> presets = new ComboBox<>();
    private final TextArea output = new TextArea();
    private final Button run = new Button("Ejecutar");
    private final Button stop = new Button("Detener");
    private final Label emptyMessage = new Label();
    private final Button configure = new Button("Configurar comandos...");
    private final VBox emptyState = new VBox(12, emptyMessage, configure);
    private final Runnable onConfigure;
    private Path project;
    private boolean commandExecutionAcknowledged;
    private final Label realProjectWarning = new Label(
            "Los comandos se ejecutan sobre los archivos reales, no sobre la reconstrucción virtual.");
    private long outputGeneration;

    public CommandConsolePane(Runnable onClose) {
        this(onClose, () -> {});
    }

    public CommandConsolePane(Runnable onClose, Runnable onConfigure) {
        this.onConfigure = onConfigure;
        getStyleClass().add("command-console-pane");
        setPrefHeight(195);
        setMinHeight(116);
        setMaxHeight(Double.MAX_VALUE);
        setVisible(false);
        managedProperty().bind(visibleProperty());

        presets.setPromptText("Comando guardado");
        presets.setMinWidth(180);
        presets.setPrefWidth(235);
        presets.setMaxWidth(380);
        presets.setOnAction(event -> updateActions());
        HBox.setHgrow(presets, Priority.ALWAYS);

        run.setOnAction(event -> runSelected());
        stop.setOnAction(event -> executor.stop());
        Button clear = new Button("Limpiar");
        clear.setOnAction(event -> output.clear());
        Button close = new Button("×");
        close.setTooltip(new Tooltip("Ocultar consola"));
        close.getStyleClass().add("console-close");
        close.setOnAction(event -> onClose.run());

        HBox header = new HBox(8, new Label("Consola"), presets,
                run, stop, clear, close);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("console-header");
        setTop(header);
        realProjectWarning.getStyleClass().add("console-real-files-warning");
        realProjectWarning.setWrapText(true);
        realProjectWarning.setTooltip(new Tooltip(
                "El programa externo puede modificar los archivos originales. Usa una copia para probar."));
        realProjectWarning.setVisible(false);
        realProjectWarning.managedProperty().bind(realProjectWarning.visibleProperty());
        setBottom(realProjectWarning);

        output.setEditable(false);
        output.setWrapText(false);
        output.setFocusTraversable(true);
        output.setFont(Font.font("Consolas", 12));
        output.getStyleClass().add("console-output");
        emptyState.getStyleClass().add("console-empty-state");
        emptyMessage.getStyleClass().add("console-empty-message");
        emptyMessage.setWrapText(true);
        emptyMessage.setMaxWidth(420);
        emptyState.setAlignment(Pos.CENTER);
        configure.getStyleClass().add("console-configure");
        configure.setOnAction(event -> this.onConfigure.run());
        StackPane center = new StackPane(output, emptyState);
        center.getStyleClass().add("console-output-area");
        setCenter(center);
        output.textProperty().addListener((obs, previous, current) -> updateEmptyState());
        updateActions();
    }

    public void setProject(Path selected) {
        // A new reference must explicitly acknowledge the real-file boundary.
        if (!java.util.Objects.equals(project, selected))
            commandExecutionAcknowledged = false;
        realProjectWarning.setVisible(selected != null);
        // Ignore log callbacks already queued for the previous project.
        outputGeneration++;
        if (executor.isRunning()) executor.stop();
        project = selected;
        output.clear();
        presets.getSelectionModel().clearSelection();
        updateActions();
    }

    public void setPresets(List<Preset> commands) {
        Preset previous = presets.getValue();
        presets.getItems().setAll(commands);
        if (previous != null && commands.contains(previous)) presets.setValue(previous);
        else if (!commands.isEmpty()) presets.getSelectionModel().selectFirst();
        updateActions();
    }

    public void stop() {
        executor.stop();
    }

    /** Re-arm the warning when Preferences restores its default protection. */
    public void resetExecutionAcknowledgement() {
        commandExecutionAcknowledged = false;
    }

    public boolean isRunning() {
        return executor.isRunning();
    }

    private void updateActions() {
        run.setDisable(project == null || executor.isRunning() || presets.getValue() == null);
        stop.setDisable(!executor.isRunning());
        updateEmptyState();
    }

    private void updateEmptyState() {
        boolean show = !executor.isRunning() && output.getText().isBlank();
        emptyState.setVisible(show);
        emptyState.setManaged(show);
        configure.setVisible(show && project != null);
        configure.setManaged(show && project != null);
        if (project == null) {
            emptyMessage.setText("Abre una referencia para utilizar la consola.");
        } else if (presets.getItems().isEmpty()) {
            emptyMessage.setText("Aún no hay comandos guardados para este proyecto.");
        } else {
            emptyMessage.setText("Selecciona un comando guardado y pulsa Ejecutar.");
        }
    }

    private void runSelected() {
        if (project == null || presets.getValue() == null) return;
        Preset selected = presets.getValue();
        Path currentRoot = project;
        if (!commandExecutionAcknowledged && CommandWarningPreferences.mustConfirm()) {
            ButtonType runConfirmed = new ButtonType("Ejecutar comando",
                    ButtonBar.ButtonData.OK_DONE);
            ProductDialogs.Confirmation decision = ProductDialogs.confirmRealFiles(
                    getScene() == null ? null : getScene().getWindow(),
                    "Confirmar ejecución sobre archivos reales",
                    "Este comando se ejecutará en el proyecto ORIGINAL: "
                    + currentRoot + "\n\n"
                    + "Puede crear, modificar o eliminar archivos reales. "
                    + "No ejecuta la reconstrucción virtual de CodeTrace. "
                    + "Utiliza una copia del proyecto si vas a probar operaciones de escritura.",
                    runConfirmed);
            if (decision.choice() != runConfirmed) return;
            if (decision.doNotShowAgain()) CommandWarningPreferences.setMustConfirm(false);
            commandExecutionAcknowledged = true;
        }
        long generation = ++outputGeneration;
        output.appendText(System.lineSeparator() + "▶ " + selected.name()
                + System.lineSeparator());
        boolean started = executor.start(currentRoot, selected,
                text -> Platform.runLater(() -> {
                    if (generation != outputGeneration) return;
                    output.appendText(text);
                    // Bounded history, separate from the reference project.
                    if (output.getLength() > 200_000) {
                        output.deleteText(0, output.getLength() - 160_000);
                    }
                    output.positionCaret(output.getLength());
                }),
                code -> Platform.runLater(() -> {
                    if (generation != outputGeneration) return;
                    output.appendText(System.lineSeparator()
                            + "[Proceso finalizado · código " + code + "]"
                            + System.lineSeparator());
                    updateActions();
                }));
        if (started) {
            setVisible(true);
            updateActions();
        }
    }
}
