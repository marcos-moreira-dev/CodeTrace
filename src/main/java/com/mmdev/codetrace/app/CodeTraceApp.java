package com.mmdev.codetrace.app;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.ui.ReferenceTree;
import com.mmdev.codetrace.ui.TypingEditor;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Desktop shell for reconstructing a project in memory.
 * Only the saved .ctrace file is written; source references remain untouched.
 */
public final class CodeTraceApp extends Application {
    private final TabPane tabs = new TabPane();
    private final Map<String, Tab> openedTabs = new HashMap<>();
    private final Label status = new Label("Listo");
    private final Label fileInfo = new Label();
    private final ProgressBar fileProgress = new ProgressBar(0);
    private final SplitPane split = new SplitPane();
    private final ReferenceTree tree = new ReferenceTree(
            this::openForPractice, this::onWorkspaceChanged, this::setStatus);
    private final VBox sidebar = new VBox(new Label("Proyecto"), tree);
    private final Button refreshButton = new Button("Actualizar");
    private final Button skipImportsButton = new Button("Saltar imports");
    private final MenuItem skipImportsMenu = new MenuItem("Saltar imports");
    private final VBox top = new VBox();

    private VirtualWorkspace workspace = new VirtualWorkspace();
    private Map<String, TypingSession> sessions = new LinkedHashMap<>();
    private Map<String, String> hashes = new LinkedHashMap<>();
    private Path reference;
    private Path sessionFile;
    private boolean dirty;
    private boolean concentration;
    private double explorerRatio = 0.26;
    private Stage stage;

    @Override
    public void start(Stage window) {
        stage = window;
        BorderPane root = new BorderPane();

        MenuItem openReference = new MenuItem("Abrir referencia...");
        openReference.setAccelerator(new KeyCodeCombination(
                KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        openReference.setOnAction(event -> chooseReference());
        MenuItem openSession = new MenuItem("Abrir sesión...");
        openSession.setOnAction(event -> openSession());
        MenuItem save = new MenuItem("Guardar sesión");
        save.setAccelerator(new KeyCodeCombination(
                KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        save.setOnAction(event -> saveSession(false));
        MenuItem saveAs = new MenuItem("Guardar sesión como...");
        saveAs.setOnAction(event -> saveSession(true));
        MenuItem quit = new MenuItem("Salir");
        quit.setOnAction(event -> stage.close());
        Menu fileMenu = new Menu("Archivo", null,
                openReference, openSession, save, saveAs, quit);

        CheckMenuItem showExplorer = new CheckMenuItem("Explorador");
        showExplorer.setSelected(true);
        showExplorer.selectedProperty().addListener((observed, oldValue, visible) -> {
            if (visible && !split.getItems().contains(sidebar)) {
                split.getItems().add(0, sidebar);
                split.setDividerPositions(explorerRatio);
            } else if (!visible && split.getItems().contains(sidebar)) {
                if (!split.getDividers().isEmpty()) {
                    explorerRatio = split.getDividerPositions()[0];
                }
                split.getItems().remove(sidebar);
            }
        });

        CheckMenuItem focus = new CheckMenuItem("Modo concentración");
        focus.setAccelerator(new KeyCodeCombination(KeyCode.F,
                KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        focus.selectedProperty().addListener((observed, before, enabled) -> {
            concentration = enabled;
            showExplorer.setSelected(!enabled);
            if (top.getChildren().size() > 1) {
                top.getChildren().get(1).setVisible(!enabled);
                top.getChildren().get(1).setManaged(!enabled);
            }
        });

        CheckMenuItem fullscreen = new CheckMenuItem("Pantalla completa");
        fullscreen.setAccelerator(new KeyCodeCombination(KeyCode.F11));
        fullscreen.selectedProperty().addListener(
                (observed, oldValue, enabled) -> stage.setFullScreen(enabled));
        stage.fullScreenProperty().addListener(
                (observed, oldValue, enabled) -> fullscreen.setSelected(enabled));
        Menu viewMenu = new Menu("Ver", null, showExplorer, focus, fullscreen);

        skipImportsMenu.setOnAction(event -> skipImports());
        Menu practiceMenu = new Menu("Práctica", null, skipImportsMenu);
        MenuItem about = new MenuItem("Acerca de CodeTrace");
        about.setOnAction(event -> showAbout());
        Menu helpMenu = new Menu("Ayuda", null, about);

        MenuBar menu = new MenuBar(fileMenu, practiceMenu, viewMenu, helpMenu);
        Button openButton = new Button("Abrir referencia");
        openButton.setOnAction(event -> chooseReference());
        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(event -> saveSession(false));
        refreshButton.setDisable(true);
        refreshButton.setOnAction(event -> refreshReference());
        skipImportsButton.setOnAction(event -> skipImports());
        ToolBar toolbar = new ToolBar(openButton, saveButton, refreshButton,
                skipImportsButton);
        top.getChildren().addAll(menu, toolbar);
        root.setTop(top);

        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(155);
        sidebar.getChildren().get(0).getStyleClass().add("sidebar-title");
        VBox.setVgrow(tree, Priority.ALWAYS);
        tree.setWorkspace(workspace);
        split.getItems().addAll(sidebar, tabs);
        split.setDividerPositions(explorerRatio);
        root.setCenter(split);

        fileInfo.getStyleClass().add("progress-info");
        fileProgress.getStyleClass().add("typing-progress");
        fileProgress.setPrefWidth(160);
        fileProgress.setVisible(false);
        HBox.setHgrow(status, Priority.ALWAYS);
        HBox footer = new HBox(12, status, fileInfo, fileProgress);
        footer.setPadding(new Insets(5, 10, 5, 10));
        footer.getStyleClass().add("statusbar");
        root.setBottom(footer);

        tabs.getSelectionModel().selectedItemProperty()
                .addListener((observed, before, selected) -> refreshMetrics());

        Scene scene = new Scene(root, 1160, 760);
        scene.getStylesheets().add(getClass()
                .getResource("/css/codetrace.css").toExternalForm());
        stage.setTitle("CodeTrace");
        stage.setMinWidth(760);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (!confirmDiscard()) {
                event.consume();
            }
        });
        refreshMetrics();
        stage.show();
    }

    private void chooseReference() {
        if (!confirmDiscard()) {
            return;
        }
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccionar carpeta de referencia");
        if (reference != null && Files.isDirectory(reference)) {
            chooser.setInitialDirectory(reference.toFile());
        }
        java.io.File chosen = chooser.showDialog(stage);
        if (chosen != null) {
            workspace = new VirtualWorkspace();
            sessions = new LinkedHashMap<>();
            hashes = new LinkedHashMap<>();
            sessionFile = null;
            dirty = false;
            tabs.getTabs().clear();
            openedTabs.clear();
            reference = chosen.toPath().toAbsolutePath().normalize();
            tree.setWorkspace(workspace);
            tree.open(reference);
            refreshButton.setDisable(false);
            setStatus("Referencia: " + reference);
            updateTitle();
            refreshMetrics();
        }
    }

    private void refreshReference() {
        if (reference == null) {
            return;
        }
        if (!Files.isDirectory(reference)) {
            setStatus("La carpeta de referencia no está disponible. Selecciona otra.");
            return;
        }
        tree.refreshTree();
        int changes = 0;
        for (var pair : hashes.entrySet()) {
            Path source = reference.resolve(pair.getKey());
            try {
                if (!Files.isRegularFile(source)
                        || !ReferenceFingerprint.sha256(source).equals(pair.getValue())) {
                    changes++;
                }
            } catch (IOException error) {
                changes++;
            }
        }
        setStatus(changes == 0 ? "Referencia actualizada"
                : changes + " archivo(s) cambiaron; transcripciones conservadas");
    }

    private void openForPractice(Path file) {
        if (reference == null || !Files.isRegularFile(file)) {
            setStatus("No hay archivo de referencia para esta entrada.");
            return;
        }
        String relative = relative(file);
        if (!workspace.exists(relative)) {
            setStatus("Crea primero el archivo " + file.getFileName()
                    + " en el proyecto virtual.");
            return;
        }
        if (workspace.entry(relative).kind() != VirtualWorkspace.Kind.FILE) {
            setStatus("La entrada virtual no es un archivo.");
            return;
        }
        Tab existing = openedTabs.get(relative);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            return;
        }
        try {
            TypingSession session = sessions.get(relative);
            if (session == null) {
                TextPreview.Result preview = TextPreview.load(file);
                if (!preview.readable()) {
                    setStatus(preview.reason());
                    return;
                }
                session = new TypingSession(preview.content());
                sessions.put(relative, session);
            }
            hashes.putIfAbsent(relative, ReferenceFingerprint.sha256(file));
            TypingSession current = session;
            TypingEditor editor = new TypingEditor(current, () -> {
                workspace.setText(relative, current.enteredText());
                dirty = true;
                updateTitle();
                refreshMetrics();
            });
            Tab tab = new Tab(file.getFileName().toString(), editor);
            tab.setOnClosed(event -> openedTabs.remove(relative));
            openedTabs.put(relative, tab);
            tabs.getTabs().add(tab);
            tabs.getSelectionModel().select(tab);
            editor.focusEditor();
            setStatus(relative);
            refreshMetrics();
        } catch (IOException | SecurityException problem) {
            setStatus("No se pudo abrir " + file.getFileName());
        }
    }

    private void onWorkspaceChanged(ReferenceTree.Change change) {
        if (change.operation() == ReferenceTree.Operation.DELETE) {
            removeSessionsUnder(change.source());
        } else if (change.operation() == ReferenceTree.Operation.MOVE) {
            moveSessionsUnder(change.source(), change.target());
        }
        tabs.getTabs().clear();
        openedTabs.clear();
        dirty = true;
        updateTitle();
        refreshMetrics();
        setStatus("Proyecto virtual actualizado");
    }

    private void removeSessionsUnder(String root) {
        sessions.keySet().removeIf(path -> path.equals(root) || path.startsWith(root + "/"));
        hashes.keySet().removeIf(path -> path.equals(root) || path.startsWith(root + "/"));
    }

    private void moveSessionsUnder(String from, String to) {
        Map<String, TypingSession> moved = new LinkedHashMap<>();
        Map<String, String> movedHashes = new LinkedHashMap<>();
        for (var pair : sessions.entrySet()) {
            String path = pair.getKey();
            String target = path.equals(from) || path.startsWith(from + "/")
                    ? to + path.substring(from.length()) : path;
            moved.put(target, pair.getValue());
        }
        for (var pair : hashes.entrySet()) {
            String path = pair.getKey();
            String target = path.equals(from) || path.startsWith(from + "/")
                    ? to + path.substring(from.length()) : path;
            movedHashes.put(target, pair.getValue());
        }
        sessions = moved;
        hashes = movedHashes;
    }

    private void skipImports() {
        TypingEditor editor = selectedEditor();
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        if (editor != null && selected != null
                && selected.getText().toLowerCase().endsWith(".java")) {
            int omitted = editor.skipJavaImports();
            setStatus(omitted == 0 ? "No hay imports pendientes"
                    : omitted + " caracteres omitidos");
        }
    }

    private void refreshMetrics() {
        TypingEditor editor = selectedEditor();
        boolean hasEditor = editor != null;
        skipImportsButton.setDisable(!hasEditor || !selectedJavaFile());
        skipImportsMenu.setDisable(!hasEditor || !selectedJavaFile());
        if (!hasEditor) {
            fileInfo.setText("");
            fileProgress.setVisible(false);
            return;
        }
        TypingSession typing = editor.session();
        fileInfo.setText("Archivo · " + typing.correctCount() + " correctos · "
                + editor.correctCpm() + " CPM");
        fileProgress.setProgress(editor.progress());
        fileProgress.setVisible(true);
    }

    private boolean selectedJavaFile() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        return selected != null && selected.getText().toLowerCase().endsWith(".java");
    }

    private TypingEditor selectedEditor() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        return selected != null && selected.getContent() instanceof TypingEditor editor
                ? editor : null;
    }

    private void openSession() {
        if (!confirmDiscard()) {
            return;
        }
        FileChooser chooser = sessionChooser("Abrir sesión CodeTrace");
        java.io.File selected = chooser.showOpenDialog(stage);
        if (selected == null) {
            return;
        }
        try {
            SessionArchive.State data = SessionArchive.load(selected.toPath());
            Path restoredReference = data.referenceRoot();
            if (!Files.isDirectory(restoredReference)) {
                DirectoryChooser folder = new DirectoryChooser();
                folder.setTitle("Buscar carpeta de referencia");
                java.io.File replacement = folder.showDialog(stage);
                if (replacement == null) {
                    return;
                }
                restoredReference = replacement.toPath().toAbsolutePath().normalize();
            }
            reference = restoredReference;
            workspace = data.workspace();
            sessions = new LinkedHashMap<>(data.sessions());
            hashes = new LinkedHashMap<>(data.referenceHashes());
            sessionFile = selected.toPath();
            dirty = false;
            tabs.getTabs().clear();
            openedTabs.clear();
            tree.setWorkspace(workspace);
            tree.open(reference);
            refreshButton.setDisable(false);
            refreshReference();
            updateTitle();
            refreshMetrics();
        } catch (IOException | SecurityException failure) {
            showFailure("No se pudo abrir la sesión", failure.getMessage());
        }
    }

    private boolean saveSession(boolean forceAs) {
        if (reference == null) {
            setStatus("Abre una referencia antes de guardar.");
            return false;
        }
        Path destination = sessionFile;
        if (destination == null || forceAs) {
            FileChooser chooser = sessionChooser("Guardar sesión CodeTrace");
            chooser.setInitialFileName(reference.getFileName() + ".ctrace");
            java.io.File selected = chooser.showSaveDialog(stage);
            if (selected == null) {
                return false;
            }
            destination = selected.toPath();
            if (!destination.getFileName().toString().toLowerCase().endsWith(".ctrace")) {
                destination = destination.resolveSibling(destination.getFileName() + ".ctrace");
            }
        }
        try {
            SessionArchive.save(destination, new SessionArchive.State(
                    reference, workspace, sessions, hashes));
            sessionFile = destination;
            dirty = false;
            updateTitle();
            setStatus("Sesión guardada: " + destination.getFileName());
            return true;
        } catch (IOException failure) {
            showFailure("No se pudo guardar la sesión", failure.getMessage());
            return false;
        }
    }

    private FileChooser sessionChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Sesión CodeTrace (*.ctrace)", "*.ctrace"));
        if (sessionFile != null && sessionFile.getParent() != null
                && Files.isDirectory(sessionFile.getParent())) {
            chooser.setInitialDirectory(sessionFile.getParent().toFile());
        }
        return chooser;
    }

    private boolean confirmDiscard() {
        if (!dirty) {
            return true;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Guardar los cambios de la sesión antes de continuar?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        confirmation.initOwner(stage);
        confirmation.setTitle("Sesión modificada");
        ButtonType answer = confirmation.showAndWait().orElse(ButtonType.CANCEL);
        if (answer == ButtonType.YES) {
            return saveSession(false);
        }
        return answer == ButtonType.NO;
    }

    private void showAbout() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.initOwner(stage);
        dialog.setTitle("Acerca de CodeTrace");
        dialog.setHeaderText("CodeTrace · 0.1.0");
        dialog.setContentText("Herramienta de escritorio para practicar la "
                + "reconstrucción manual de proyectos de software.\n\n"
                + "La práctica fortalece la familiaridad y la confianza "
                + "al escribir y organizar código por cuenta propia.");
        dialog.showAndWait();
    }

    private void showFailure(String title, String text) {
        Alert dialog = new Alert(Alert.AlertType.ERROR,
                text == null ? "Error de archivo" : text, ButtonType.OK);
        dialog.initOwner(stage);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.showAndWait();
    }

    private String relative(Path path) {
        return reference.relativize(path).toString().replace('\\', '/');
    }

    private void updateTitle() {
        String name = sessionFile == null ? "CodeTrace" : sessionFile.getFileName().toString();
        stage.setTitle((dirty ? "* " : "") + name + " — CodeTrace");
    }

    private void setStatus(String value) {
        status.setText(value);
    }
}
