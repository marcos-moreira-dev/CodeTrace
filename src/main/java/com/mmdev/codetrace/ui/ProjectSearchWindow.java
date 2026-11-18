package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.ProjectTextSearch;
import com.mmdev.codetrace.reference.ProjectTextSearch.Hit;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Non-modal Notepad++-style search without Replace: project or selected file.
 * Results are a read-only navigation overlay, not typing progress.
 */
public final class ProjectSearchWindow {
    public enum Scope { FILE, PROJECT }

    private final Stage stage = new Stage();
    private final Supplier<Path> project;
    private final Supplier<String> selectedFile;
    private final Consumer<Hit> navigate;
    private final ComboBox<String> scope = new ComboBox<>();
    private final TextField expression = new TextField();
    private final CheckBox caseSensitive = new CheckBox("Distinguir mayúsculas");
    private final CheckBox wholeWords = new CheckBox("Solo palabras completas");
    private final CheckBox regex = new CheckBox("Expresión regular");
    private final Label state = new Label("Introduce un texto para buscar.");
    private final ListView<Hit> results = new ListView<>();
    private final Button search = new Button("Buscar");
    private final Button stop = new Button("Detener");
    private Task<List<Hit>> active;
    private long generation;
    private Path lastProject;
    private int currentHit = -1;

    public ProjectSearchWindow(Stage owner, Supplier<Path> project,
                               Supplier<String> selectedFile, Consumer<Hit> navigate) {
        this.project = project;
        this.selectedFile = selectedFile;
        this.navigate = navigate;
        stage.initOwner(owner);
        stage.setTitle("Buscar — CodeTrace");
        WindowBranding.apply(stage);
        stage.setMinWidth(590);
        stage.setMinHeight(340);
        scope.getItems().setAll("Archivo actual", "Todo el proyecto");
        scope.getSelectionModel().select(1);
        scope.setMaxWidth(Double.MAX_VALUE);
        expression.setPromptText("Texto o patrón, sin reemplazos");
        expression.setOnAction(event -> runSearch());
        search.setDefaultButton(true);
        search.setOnAction(event -> runSearch());
        stop.setOnAction(event -> cancel());
        stop.setDisable(true);
        HBox form = new HBox(8, new Label("Qué buscar"), expression, search, stop);
        form.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(expression, Priority.ALWAYS);
        HBox options = new HBox(15, caseSensitive, wholeWords, regex);
        options.setAlignment(Pos.CENTER_LEFT);
        HBox filter = new HBox(8, new Label("Ámbito"), scope, options);
        filter.setAlignment(Pos.CENTER_LEFT);
        VBox header = new VBox(9, form, filter);
        header.setPadding(new Insets(12));
        header.getStyleClass().add("search-window-header");

        results.setPlaceholder(new Label("Sin coincidencias por mostrar."));
        results.getStyleClass().add("search-results-list");
        results.setCellFactory(view -> new RecordListCell<>(hit ->
                new RecordListCell.Content(
                        Path.of(hit.relative()).getFileName().toString(),
                        hit.relative() + " · línea " + hit.line()
                                + ", columna " + hit.column(),
                        hit.excerpt(), "fth-search")));
        results.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) navigateSelected();
        });
        results.getSelectionModel().selectedIndexProperty().addListener(
                (obs, old, selected) -> currentHit = selected.intValue());
        Button jump = new Button("Ir a coincidencia");
        jump.setOnAction(event -> navigateSelected());
        HBox footer = new HBox(10, state, jump);
        footer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(state, Priority.ALWAYS);
        footer.setPadding(new Insets(8, 12, 9, 12));
        footer.getStyleClass().add("search-window-footer");
        BorderPane root = new BorderPane();
        root.getStyleClass().add("project-search-window");
        root.setTop(header);
        root.setCenter(results);
        root.setBottom(footer);
        Scene scene = new Scene(root, 810, 510);
        scene.getStylesheets().add(ProjectSearchWindow.class.getResource(
                "/css/codetrace.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> cancel());
    }

    public void show(Scope requested) {
        Path current = project.get();
        if (!java.util.Objects.equals(lastProject, current)) {
            cancel();
            results.getItems().clear();
            currentHit = -1;
            lastProject = current;
        }
        scope.getSelectionModel().select(requested == Scope.FILE ? 0 : 1);
        stage.show();
        stage.toFront();
        expression.requestFocus();
        expression.selectAll();
        if (current == null) state.setText("Abre una referencia antes de buscar.");
    }

    public void next(boolean backward) {
        if (results.getItems().isEmpty()) {
            show(Scope.FILE);
            return;
        }
        int size = results.getItems().size();
        currentHit = currentHit < 0 ? (backward ? size - 1 : 0)
                : (currentHit + (backward ? size - 1 : 1)) % size;
        results.getSelectionModel().select(currentHit);
        results.scrollTo(currentHit);
        navigateSelected();
    }

    /** Called when the user opens a different reference or restores a session. */
    public void projectChanged(Path current) {
        cancel();
        lastProject = current;
        results.getItems().clear();
        currentHit = -1;
        state.setText("Referencia actualizada. Ejecuta una búsqueda nueva.");
    }

    private void runSearch() {
        Path root = project.get();
        if (root == null) {
            state.setText("Abre una referencia antes de buscar.");
            return;
        }
        String relative = scope.getSelectionModel().getSelectedIndex() == 0
                ? selectedFile.get() : null;
        if (scope.getSelectionModel().getSelectedIndex() == 0 && relative == null) {
            state.setText("Selecciona primero una pestaña con texto.");
            return;
        }
        ProjectTextSearch.Options opts;
        try {
            opts = new ProjectTextSearch.Options(expression.getText(), regex.isSelected(),
                    caseSensitive.isSelected(), wholeWords.isSelected());
        } catch (IllegalArgumentException error) {
            state.setText(error.getMessage());
            return;
        }
        cancel();
        long request = ++generation;
        results.getItems().clear();
        state.setText("Buscando en archivos legibles…");
        search.setDisable(true);
        stop.setDisable(false);
        Task<List<Hit>> task = new Task<>() {
            @Override protected List<Hit> call() throws Exception {
                return ProjectTextSearch.search(root, relative, opts, this::isCancelled);
            }
        };
        active = task;
        task.setOnSucceeded(event -> {
            if (request != generation) return;
            List<Hit> matches = task.getValue();
            results.getItems().setAll(matches);
            currentHit = -1;
            state.setText(matches.size() + (matches.size() == 1
                    ? " coincidencia" : " coincidencias")
                    + (matches.size() >= 1500 ? " (límite alcanzado)" : ""));
            finish();
        });
        task.setOnFailed(event -> {
            if (request != generation) return;
            state.setText("Búsqueda no disponible: " + task.getException().getMessage());
            finish();
        });
        task.setOnCancelled(event -> {
            if (request != generation) return;
            state.setText("Búsqueda cancelada.");
            finish();
        });
        Thread worker = new Thread(task, "codetrace-project-text-search");
        worker.setDaemon(true);
        worker.start();
    }

    private void finish() {
        active = null;
        search.setDisable(false);
        stop.setDisable(true);
    }

    private void cancel() {
        generation++;
        if (active != null) active.cancel(true);
        active = null;
        search.setDisable(false);
        stop.setDisable(true);
    }

    private void navigateSelected() {
        Hit selected = results.getSelectionModel().getSelectedItem();
        if (selected != null) navigate.accept(selected);
    }
}
