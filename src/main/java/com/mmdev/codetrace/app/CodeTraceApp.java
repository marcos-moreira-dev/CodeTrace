package com.mmdev.codetrace.app;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.reference.ProgressScope;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.ui.ReferenceTree;
import com.mmdev.codetrace.ui.TypingEditor;
import com.mmdev.codetrace.ui.PreferencesWindow;
import com.mmdev.codetrace.ui.MarkdownPracticePane;
import com.mmdev.codetrace.ui.ProductDialogs;
import com.mmdev.codetrace.ui.ToolbarIcons;
import com.mmdev.codetrace.ui.SymbolPalette;
import com.mmdev.codetrace.ui.FileTypeIcons;
import com.mmdev.codetrace.ui.EditorZoomControl;
import com.mmdev.codetrace.ui.CommandConsolePane;
import com.mmdev.codetrace.commands.ProjectCommandStore;
import com.mmdev.codetrace.library.BuiltInLibrary;
import com.mmdev.codetrace.ui.PracticeLibraryDialog;
import com.mmdev.codetrace.symbols.SymbolCatalog.Layout;
import com.mmdev.codetrace.sound.TypingSounds;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import java.util.EnumMap;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleButton;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private final Label projectInfo = new Label();
    private final ProgressBar fileProgress = new ProgressBar(0);
    private final MenuButton progressFilter = new MenuButton("Filtro");
    private final SplitPane split = new SplitPane();
    private final ReferenceTree tree = new ReferenceTree(
            this::openForPractice, this::onWorkspaceChanged, this::setStatus);
    private final VBox sidebar = new VBox();
    private final ProjectCommandStore commandStore = new ProjectCommandStore();
    private final CommandConsolePane consolePane =
            new CommandConsolePane(() -> setConsoleVisible(false));
    private CheckMenuItem showConsole;
    private String progressFolder = "";
    private CheckBox onlySelectedFolder;
    private boolean syncingFolderScope;
    private final Map<ProgressScope.Category, CheckBox> scopeChecks =
            new EnumMap<>(ProgressScope.Category.class);
    private CheckBox allScopes;
    private final Button refreshButton = ToolbarIcons.button(
            "", ToolbarIcons.Icon.REFRESH, "Actualizar referencia");
    private final Button skipImportsButton = ToolbarIcons.button(
            "Imports", ToolbarIcons.Icon.SKIP, "Saltar imports de Java");
    private final MenuItem skipImportsMenu = new MenuItem("Saltar imports");
    private final VBox top = new VBox();

    private VirtualWorkspace workspace = new VirtualWorkspace();
    private Map<String, TypingSession> sessions = new LinkedHashMap<>();
    private Map<String, String> hashes = new LinkedHashMap<>();
    private ReferenceCatalogue.Snapshot catalogue;
    private ProgressScope progressScope = ProgressScope.load();
    private long catalogueGeneration;
    private Path reference;
    private long referenceRequest;
    private Path sessionFile;
    private AppSettings settings = AppSettings.load();
    private CheckMenuItem explorerToggle;
    private CheckMenuItem turboMenu;
    private ToggleButton turboToggle;
    private SymbolPalette symbols;
    private EditorZoomControl zoom;
    private boolean soundProblemReported;
    private final TypingSounds typingSounds = new TypingSounds();
    private boolean dirty;
    private boolean concentration;
    private double explorerRatio = 0.21;
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

        explorerToggle = new CheckMenuItem("Explorador");
        explorerToggle.setSelected(settings.showExplorer());
        explorerToggle.selectedProperty().addListener((observed, oldValue, visible) -> {
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
            explorerToggle.setSelected(!enabled);
            if (top.getChildren().size() > 1) {
                top.getChildren().get(1).setVisible(!enabled);
                top.getChildren().get(1).setManaged(!enabled);
            }
            if (zoom != null) {
                zoom.setVisible(!enabled);
                zoom.setManaged(!enabled);
            }
        });

        CheckMenuItem fullscreen = new CheckMenuItem("Pantalla completa");
        fullscreen.setAccelerator(new KeyCodeCombination(KeyCode.F11));
        fullscreen.selectedProperty().addListener(
                (observed, oldValue, enabled) -> stage.setFullScreen(enabled));
        stage.fullScreenProperty().addListener(
                (observed, oldValue, enabled) -> fullscreen.setSelected(enabled));
        showConsole = new CheckMenuItem("Consola");
        showConsole.setAccelerator(new KeyCodeCombination(
                KeyCode.F12, KeyCombination.ALT_DOWN));
        showConsole.setOnAction(event -> setConsoleVisible(showConsole.isSelected()));
        Menu viewMenu = new Menu("Ver", null,
                explorerToggle, showConsole, focus, fullscreen);

        skipImportsMenu.setOnAction(event -> skipImports());
        turboMenu = new CheckMenuItem("Modo Turbo · omitir símbolos");
        turboMenu.setSelected(settings.turboMode());
        turboMenu.setOnAction(event -> setTurboMode(turboMenu.isSelected()));
        MenuItem bundledLibrary = new MenuItem("Biblioteca de práctica...");
        bundledLibrary.setOnAction(event -> openPracticeLibrary());
        Menu practiceMenu = new Menu("Práctica", null,
                skipImportsMenu, turboMenu,
                new javafx.scene.control.SeparatorMenuItem(), bundledLibrary);
        MenuItem about = new MenuItem("Acerca de CodeTrace");
        about.setOnAction(event -> showAbout());
        Menu helpMenu = new Menu("Ayuda", null, about);
        MenuItem preferences = new MenuItem("Preferencias...");
        preferences.setOnAction(event -> PreferencesWindow.open(
                stage, settings, this::applySettings, typingSounds::preview,
                reference, commandStore, this::refreshCommandPresets));
        Menu toolsMenu = new Menu("Herramientas", null, preferences);

        MenuBar menu = new MenuBar(
                fileMenu, practiceMenu, viewMenu, toolsMenu, helpMenu);
        Button openButton = ToolbarIcons.button(
                "Abrir", ToolbarIcons.Icon.OPEN, "Abrir carpeta de referencia");
        openButton.setOnAction(event -> chooseReference());
        Button saveButton = ToolbarIcons.button(
                "Guardar", ToolbarIcons.Icon.SAVE, "Guardar sesión CodeTrace");
        saveButton.setOnAction(event -> saveSession(false));
        refreshButton.setDisable(true);
        refreshButton.setOnAction(event -> refreshReference());
        skipImportsButton.setOnAction(event -> skipImports());
        symbols = new SymbolPalette(Layout.valueOf(settings.keyboardLayout()), next -> {
            settings = settings.withLayout(next.name());
            settings.save();
        }, () -> {
            TypingEditor editor = selectedEditor();
            if (editor != null) editor.focusEditor();
            setStatus("Símbolo copiado. Usa Ctrl+V para practicarlo.");
        });
        turboToggle = new ToggleButton("Turbo", new FontIcon("fth-zap"));
        turboToggle.getStyleClass().addAll("toolbar-action", "turbo-toggle");
        turboToggle.setTooltip(new Tooltip("Omitir automáticamente puntuación y símbolos al teclear"));
        turboToggle.setSelected(settings.turboMode());
        turboToggle.setOnAction(event -> setTurboMode(turboToggle.isSelected()));
        Button consoleButton = new Button("Consola", new FontIcon("fth-terminal"));
        consoleButton.getStyleClass().add("toolbar-action");
        consoleButton.setTooltip(new Tooltip("Mostrar u ocultar la consola de comandos"));
        consoleButton.setOnAction(event -> setConsoleVisible(!consolePane.isVisible()));
        ToolBar toolbar = new ToolBar(openButton, saveButton,
                new Separator(Orientation.VERTICAL), refreshButton,
                new Separator(Orientation.VERTICAL), skipImportsButton,
                new Separator(Orientation.VERTICAL), symbols, turboToggle,
                new Separator(Orientation.VERTICAL), consoleButton);
        top.getChildren().addAll(menu, toolbar);
        root.setTop(top);

        Label projectHeading = new Label("Proyecto");
        projectHeading.getStyleClass().add("sidebar-title");
        onlySelectedFolder = new CheckBox("Solo");
        onlySelectedFolder.getStyleClass().add("tree-scope-checkbox");
        onlySelectedFolder.setTooltip(new Tooltip(
                "Contabilizar solo los archivos de la carpeta seleccionada"));
        onlySelectedFolder.setDisable(true);
        onlySelectedFolder.setOnAction(event -> {
            if (syncingFolderScope) return;
            var selected = tree.getSelectionModel().getSelectedItem();
            if (onlySelectedFolder.isSelected() && selected != null
                    && tree.isFolderPath(selected.getValue())) {
                setProgressFolder(tree.relativePath(selected.getValue()));
            } else {
                setProgressFolder("");
            }
        });
        tree.getSelectionModel().selectedItemProperty().addListener(
                (obs, before, selected) -> {
                    boolean folder = selected != null
                            && tree.isFolderPath(selected.getValue());
                    onlySelectedFolder.setDisable(!folder && !onlySelectedFolder.isSelected());
                    if (folder && onlySelectedFolder.isSelected()) {
                        setProgressFolder(tree.relativePath(selected.getValue()));
                    }
                });
        ProgressIndicator treeActivity = new ProgressIndicator();
        treeActivity.setMaxSize(16, 16);
        treeActivity.setMinSize(16, 16);
        treeActivity.getStyleClass().add("tree-activity");
        treeActivity.setVisible(false);
        treeActivity.managedProperty().bind(treeActivity.visibleProperty());

        Button collapse = new Button("−");
        collapse.getStyleClass().add("tree-header-action");
        collapse.setTooltip(new Tooltip("Contraer todas las carpetas"));
        collapse.setAccessibleText("Contraer árbol");
        collapse.setOnAction(event -> tree.collapseAll());
        Button expand = new Button("+");
        expand.getStyleClass().add("tree-header-action");
        expand.setTooltip(new Tooltip("Expandir todas las carpetas"));
        expand.setAccessibleText("Expandir árbol");
        expand.setOnAction(event -> tree.expandAll());

        Region headerFill = new Region();
        HBox.setHgrow(headerFill, Priority.ALWAYS);
        HBox header = new HBox(4, projectHeading, headerFill,
                onlySelectedFolder, treeActivity, collapse, expand);
        header.getStyleClass().add("project-tree-header");
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        sidebar.getChildren().addAll(header, tree);
        tree.setOnProgressFolder(this::setProgressFolder);
        tree.setOnBusyChange(busy -> {
            treeActivity.setVisible(busy);
            expand.setDisable(busy);
        });
        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(165);
        sidebar.setPrefWidth(245);
        VBox.setVgrow(tree, Priority.ALWAYS);
        tree.setWorkspace(workspace);
        split.getItems().addAll(sidebar, tabs);
        if (!settings.showExplorer()) {
            split.getItems().remove(sidebar);
        } else {
            split.setDividerPositions(explorerRatio);
        }
        VBox workspaceArea = new VBox(split, consolePane);
        VBox.setVgrow(split, Priority.ALWAYS);
        root.setCenter(workspaceArea);

        fileInfo.getStyleClass().add("progress-info");
        fileProgress.getStyleClass().add("typing-progress");
        fileProgress.setPrefWidth(160);
        fileProgress.setVisible(false);
        fileProgress.managedProperty().bind(fileProgress.visibleProperty());
        status.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(status, Priority.ALWAYS);
        progressFilter.getStyleClass().add("status-progress-filter");
        progressFilter.setTooltip(new Tooltip(
                "Seleccionar tipos de archivo incluidos en el progreso"));
        installProgressFilters();
        zoom = new EditorZoomControl(settings.fontSize(), this::setEditorZoom);
        zoom.setId("editor-font-zoom");
        HBox footer = new HBox(9, status, fileInfo, projectInfo, fileProgress,
                progressFilter, new Separator(Orientation.VERTICAL), zoom);
        footer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        footer.setMinHeight(34);
        status.setMinWidth(80);
        footer.setPadding(new Insets(4, 9, 4, 9));
        footer.getStyleClass().add("statusbar");
        root.setBottom(footer);

        tabs.getSelectionModel().selectedItemProperty()
                .addListener((observed, before, selected) -> refreshMetrics());

        Scene scene = new Scene(root, 1160, 760);
        scene.getStylesheets().add(getClass()
                .getResource("/css/codetrace.css").toExternalForm());
        stage.setTitle("CodeTrace");
        stage.getIcons().add(new Image(
                getClass().getResourceAsStream("/icons/codetrace-window.png")));
        stage.setMinWidth(760);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (!confirmDiscard()) {
                event.consume();
            } else {
                consolePane.stop();
            }
        });
        refreshMetrics();
        stage.show();
    }

    private void setConsoleVisible(boolean visible) {
        consolePane.setVisible(visible);
        if (showConsole != null) showConsole.setSelected(visible);
    }

    private void refreshCommandPresets() {
        if (reference == null) {
            consolePane.setPresets(java.util.List.of());
            return;
        }
        try {
            consolePane.setPresets(commandStore.load(reference));
        } catch (IOException error) {
            setStatus("No se pudieron leer los comandos del proyecto.");
        }
    }

    private void chooseReference() {
        if (!confirmDiscard()) return;
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccionar carpeta de referencia");
        if (reference != null && Files.isDirectory(reference)) {
            chooser.setInitialDirectory(reference.toFile());
        }
        java.io.File chosen = chooser.showDialog(stage);
        if (chosen != null) {
            activateReference(chosen.toPath().toAbsolutePath().normalize());
        }
    }

    private void openPracticeLibrary() {
        final BuiltInLibrary library;
        try {
            library = new BuiltInLibrary();
        } catch (IOException error) {
            showFailure("Biblioteca de práctica", error.getMessage());
            return;
        }
        var selection = PracticeLibraryDialog.choose(stage, library);
        if (selection.isEmpty() || !confirmDiscard()) return;
        BuiltInLibrary.Pack selectedPack = selection.get();
        long requestId = ++referenceRequest;
        setStatus("Abriendo biblioteca " + selectedPack.name() + "...");
        Task<Path> prepare = new Task<>() {
            @Override
            protected Path call() throws Exception {
                return library.open(selectedPack);
            }
        };
        prepare.setOnSucceeded(event -> {
            if (requestId != referenceRequest) return;
            Path root = prepare.getValue();
            activateReference(root);
            // An immediately open template makes the built-in library useful
            // without an extra file-picker step or an external download.
            String first = library.entries(selectedPack).getFirst();
            openForPractice(root.resolve(first));
            setStatus("Biblioteca de práctica · " + selectedPack.name());
        });
        prepare.setOnFailed(event -> {
            if (requestId == referenceRequest) {
                showFailure("Biblioteca de práctica",
                        "No se pudo preparar la biblioteca interna: "
                                + prepare.getException().getMessage());
            }
        });
        Thread loader = new Thread(prepare, "codetrace-practice-library-loader");
        loader.setDaemon(true);
        loader.start();
    }

    /** Shared reference activation for an external folder or bundled pack. */
    private void activateReference(Path folder) {
        referenceRequest++;
        workspace = new VirtualWorkspace();
        sessions = new LinkedHashMap<>();
        hashes = new LinkedHashMap<>();
        catalogue = null;
        progressFolder = "";
        sessionFile = null;
        dirty = false;
        tabs.getTabs().clear();
        openedTabs.clear();
        reference = folder;
        tree.setWorkspace(workspace);
        tree.open(reference);
        consolePane.setProject(reference);
        refreshCommandPresets();
        indexReference();
        refreshButton.setDisable(false);
        setStatus("Referencia: " + reference);
        updateTitle();
        refreshMetrics();
    }

    private void refreshReference() {
        if (reference == null) {
            return;
        }
        if (!Files.isDirectory(reference)) {
            setStatus("La carpeta de referencia no está disponible. Selecciona otra.");
            return;
        }
        tree.reloadReference();
        indexReference();
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
        // Every reference text file can be opened for inspection immediately.
        // Virtual folders and the virtual file are created only upon transcription.
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
                String typed = current.enteredText();
                if (!typed.isEmpty() || current.skippedCount() > 0) {
                    boolean wasCreated = !workspace.exists(relative);
                    workspace.ensureFile(relative);
                    workspace.setText(relative, typed);
                    if (wasCreated) {
                        tree.refreshTree();
                    }
                    dirty = true;
                    updateTitle();
                }
                refreshMetrics();
            }, file.getFileName().toString(), settings.fontFamily(), settings.fontSize(),
                    matched -> {
                        if (settings.typingSounds() && !typingSounds.play(matched)
                                && !soundProblemReported) {
                            soundProblemReported = true;
                            setStatus("Sonidos no disponibles en este dispositivo.");
                        }
                    });
            editor.setOnNotice(this::setStatus);
            editor.setTemplateOpacity(settings.templateOpacity());
            editor.setTurboMode(settings.turboMode());
            boolean markdown = relative.toLowerCase().endsWith(".md")
                    || relative.toLowerCase().endsWith(".markdown");
            Tab tab = new Tab(file.getFileName().toString(),
                    markdown ? new MarkdownPracticePane(editor) : editor);
            tab.setGraphic(FileTypeIcons.forPath(file, false));
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
            editor.focusEditor();
        }
    }

    private void refreshMetrics() {
        TypingEditor editor = selectedEditor();
        boolean hasEditor = editor != null;
        skipImportsButton.setDisable(!hasEditor || !selectedJavaFile());
        skipImportsMenu.setDisable(!hasEditor || !selectedJavaFile());
        if (!hasEditor) {
            fileInfo.setText("");
        } else {
            TypingSession typing = editor.session();
            fileInfo.setText(typing.correctCount() + " correctos · "
                    + editor.correctCpm() + " CPM");
        }
        if (catalogue == null) {
            projectInfo.setText("");
            fileProgress.setVisible(false);
            return;
        }
        long total = catalogue.expectedUnits(progressScope, progressFolder);
        long resolved = catalogue.resolvedUnits(
                workspace, sessions, hashes, progressScope, progressFolder);
        double ratio = total == 0 ? 0.0 : (double) resolved / total;
        long count = catalogue.includedFileCount(progressScope, progressFolder);
        String area = progressFolder.isEmpty() ? "Proyecto" : "Carpeta";
        projectInfo.setText(count == 0 || total == 0
                ? area + " · sin código contabilizable"
                : String.format(java.util.Locale.ROOT, "%s · %.3f %%",
                        area, ratio * 100.0));
        projectInfo.setTooltip(new Tooltip(
                (progressFolder.isEmpty() ? "Todo el proyecto" : progressFolder)
                        + "\n" + resolved + " / " + total
                        + " caracteres correctos\n"
                        + count + " archivos incluidos"));
        fileProgress.setProgress(ratio);
        fileProgress.setVisible(count > 0 && total > 0);
    }

    private void setProgressFolder(String folder) {
        progressFolder = folder == null ? "" : folder;
        if (onlySelectedFolder != null) {
            syncingFolderScope = true;
            try {
                onlySelectedFolder.setSelected(!progressFolder.isEmpty());
                onlySelectedFolder.setTooltip(new Tooltip(progressFolder.isEmpty()
                        ? "Progreso: todo el proyecto"
                        : "Progreso: " + progressFolder));
                onlySelectedFolder.setDisable(progressFolder.isEmpty()
                        && !tree.isFolderPath(tree.getSelectionModel().getSelectedItem() == null
                            ? null : tree.getSelectionModel().getSelectedItem().getValue()));
            } finally {
                syncingFolderScope = false;
            }
        }
        refreshMetrics();
        setStatus(progressFolder.isEmpty()
                ? "Progreso: todo el proyecto"
                : "Progreso: " + progressFolder);
    }

    /** Persistent filter gallery: checkboxes do NOT close after each click. */
    private void installProgressFilters() {
        allScopes = new CheckBox("Todos");
        allScopes.getStyleClass().add("scope-all");
        VBox options = new VBox(9, allScopes);
        options.getStyleClass().add("scope-options");
        addFilterOption(options, "Código fuente", ProgressScope.Category.SOURCE);
        addFilterOption(options, "Configuración", ProgressScope.Category.CONFIGURATION);
        addFilterOption(options, "Documentación / Markdown",
                ProgressScope.Category.DOCUMENTATION);
        addFilterOption(options, "Otros textos", ProgressScope.Category.OTHER_TEXT);
        updateAllCheck();

        allScopes.setOnAction(event -> {
            boolean active = allScopes.isSelected();
            progressScope = new ProgressScope(active, active, active, active);
            for (CheckBox choice : scopeChecks.values()) choice.setSelected(active);
            progressScope.save();
            refreshMetrics();
        });

        CustomMenuItem panel = new CustomMenuItem(options, false);
        panel.setHideOnClick(false);
        progressFilter.getItems().setAll(panel);
    }

    private void addFilterOption(VBox parent, String label, ProgressScope.Category category) {
        CheckBox control = new CheckBox(label);
        control.setSelected(progressScope.includes(category));
        control.setOnAction(event -> {
            progressScope = progressScope.with(category, control.isSelected());
            progressScope.save();
            updateAllCheck();
            refreshMetrics();
        });
        scopeChecks.put(category, control);
        parent.getChildren().add(control);
    }

    private void updateAllCheck() {
        allScopes.setSelected(scopeChecks.values().stream().allMatch(CheckBox::isSelected));
    }

    private boolean selectedJavaFile() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        return selected != null && selected.getText().toLowerCase().endsWith(".java");
    }

    private TypingEditor selectedEditor() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return null;
        }
        if (selected.getContent() instanceof TypingEditor editor) {
            return editor;
        }
        if (selected.getContent() instanceof MarkdownPracticePane markdown) {
            return markdown.editor();
        }
        return null;
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
            referenceRequest++;
            reference = restoredReference;
            consolePane.setProject(reference);
            refreshCommandPresets();
            progressFolder = "";
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

    private void indexReference() {
        if (reference == null) {
            return;
        }
        Path root = reference;
        long generation = ++catalogueGeneration;
        catalogue = null;
        refreshMetrics();
        Task<ReferenceCatalogue.Snapshot> scan = new Task<>() {
            @Override
            protected ReferenceCatalogue.Snapshot call() throws Exception {
                return ReferenceCatalogue.scan(root);
            }
        };
        scan.setOnSucceeded(event -> {
            if (generation == catalogueGeneration && root.equals(reference)) {
                catalogue = scan.getValue();
                refreshMetrics();
            }
        });
        scan.setOnFailed(event -> {
            if (generation == catalogueGeneration) {
                setStatus("No se pudo analizar la referencia.");
            }
        });
        Thread worker = new Thread(scan, "codetrace-reference-index");
        worker.setDaemon(true);
        worker.start();
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
        ButtonType answer = ProductDialogs.confirm(stage, "Sesión modificada",
                "¿Guardar los cambios de la sesión antes de continuar?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        if (answer == ButtonType.YES) {
            return saveSession(false);
        }
        return answer == ButtonType.NO;
    }

    private void setTurboMode(boolean enabled) {
        settings = settings.withTurbo(enabled);
        settings.save();
        turboToggle.setSelected(enabled);
        turboMenu.setSelected(enabled);
        for (Tab tab : tabs.getTabs()) {
            TypingEditor editor = editorIn(tab);
            if (editor != null) editor.setTurboMode(enabled);
        }
        setStatus(enabled ? "Turbo activado · símbolos omitidos, no contados"
                : "Turbo desactivado · escribe todos los símbolos");
    }

    private TypingEditor editorIn(Tab tab) {
        if (tab.getContent() instanceof TypingEditor editor) return editor;
        if (tab.getContent() instanceof MarkdownPracticePane pane) return pane.editor();
        return null;
    }

    private void setEditorZoom(int size) {
        if (size == settings.fontSize()) {
            return;
        }
        settings = new AppSettings(settings.fontFamily(), size,
                settings.showExplorer(), settings.keyboardLayout(), settings.typingSounds(),
                settings.templateOpacity(), settings.turboMode());
        settings.save();
        for (Tab tab : tabs.getTabs()) {
            if (tab.getContent() instanceof TypingEditor editor) {
                editor.setFontSettings(settings.fontFamily(), size);
            } else if (tab.getContent() instanceof MarkdownPracticePane markdown) {
                markdown.editor().setFontSettings(settings.fontFamily(), size);
            }
        }
    }

    private void applySettings(AppSettings next) {
        settings = new AppSettings(next.fontFamily(), next.fontSize(),
                next.showExplorer(), settings.keyboardLayout(), next.typingSounds(),
                next.templateOpacity(), next.turboMode());
        settings.save();
        turboToggle.setSelected(settings.turboMode());
        turboMenu.setSelected(settings.turboMode());
        zoom.setValue(next.fontSize());
        if (!concentration) {
            explorerToggle.setSelected(next.showExplorer());
        }
        for (Tab tab : tabs.getTabs()) {
            if (tab.getContent() instanceof TypingEditor editor) {
                editor.setFontSettings(next.fontFamily(), next.fontSize());
                editor.setTemplateOpacity(next.templateOpacity());
                editor.setTurboMode(next.turboMode());
            } else if (tab.getContent() instanceof MarkdownPracticePane pane) {
                pane.editor().setFontSettings(next.fontFamily(), next.fontSize());
                pane.editor().setTemplateOpacity(next.templateOpacity());
                pane.editor().setTurboMode(next.turboMode());
            }
        }
    }

    private void showAbout() {
        ProductDialogs.about(stage);
    }

    private void showFailure(String title, String text) {
        ProductDialogs.error(stage, title, text);
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
