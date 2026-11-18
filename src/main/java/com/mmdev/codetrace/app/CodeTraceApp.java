package com.mmdev.codetrace.app;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.practice.ReconstructionStatus;
import com.mmdev.codetrace.practice.ReconstructionStatus.State;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.reference.ProgressScope;
import com.mmdev.codetrace.reference.ProgressExclusions;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.ui.ReferenceTree;
import com.mmdev.codetrace.ui.TypingEditor;
import com.mmdev.codetrace.ui.PreferencesWindow;
import com.mmdev.codetrace.ui.MarkdownPracticePane;
import com.mmdev.codetrace.ui.ReferenceMediaPane;
import com.mmdev.codetrace.ui.ProductDialogs;
import com.mmdev.codetrace.ui.ToolbarIcons;
import com.mmdev.codetrace.ui.SymbolPalette;
import com.mmdev.codetrace.ui.FileTypeIcons;
import com.mmdev.codetrace.ui.EditorZoomControl;
import com.mmdev.codetrace.ui.MediaZoomControl;
import com.mmdev.codetrace.ui.ProjectSearchWindow;
import com.mmdev.codetrace.ui.ReferenceTextPane;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import com.mmdev.codetrace.ui.DependencyGraphPane;
import com.mmdev.codetrace.reference.ProjectTextSearch.Hit;
import com.mmdev.codetrace.ui.CommandConsolePane;
import com.mmdev.codetrace.commands.ProjectCommandStore;
import com.mmdev.codetrace.library.BuiltInLibrary;
import com.mmdev.codetrace.ui.PracticeLibraryDialog;
import com.mmdev.codetrace.symbols.SymbolCatalog.Layout;
import com.mmdev.codetrace.sound.TypingSounds;
import com.mmdev.codetrace.sound.CompletionSound;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.FlowPane;
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
    private final MenuButton progressFilter = new MenuButton("Reglas de práctica");
    private final SplitPane split = new SplitPane();
    private final SplitPane workspaceSplit = new SplitPane();
    private double consoleDivider = 0.73;
    private final ReferenceTree tree = new ReferenceTree(
            this::openForPractice, this::onWorkspaceChanged, this::setStatus);
    private final VBox sidebar = new VBox();
    private final ProjectCommandStore commandStore = new ProjectCommandStore();
    private final CommandConsolePane consolePane =
            new CommandConsolePane(() -> setConsoleVisible(false),
                    this::openCommandPreferences);
    private CheckMenuItem showConsole;
    private String progressFolder = "";
    private CheckBox onlySelectedFolder;
    private CheckBox excludeSelectedFolder;
    private boolean syncingExclusion;
    private TextField explorerSearch;
    private boolean syncingFolderScope;
    private final Map<ProgressScope.Category, CheckBox> scopeChecks =
            new EnumMap<>(ProgressScope.Category.class);
    private CheckBox allScopes;
    private CheckBox autoCreateCheck;
    private final Button refreshButton = ToolbarIcons.button(
            "Actualizar referencia", ToolbarIcons.Icon.REFRESH,
            "Volver a leer los archivos originales del proyecto");
    private final Button skipImportsButton = ToolbarIcons.button(
            "Imports", ToolbarIcons.Icon.SKIP, "Saltar imports de Java");
    private final MenuItem skipImportsMenu = new MenuItem("Saltar imports");
    private final VBox top = new VBox();
    private final MenuButton tabsActions = new MenuButton("Pestañas");

    private VirtualWorkspace workspace = new VirtualWorkspace();
    private Map<String, TypingSession> sessions = new LinkedHashMap<>();
    private Map<String, String> hashes = new LinkedHashMap<>();
    private Map<String, State> reconstructionStates = Map.of();
    private ReferenceCatalogue.Snapshot catalogue;
    private ProgressScope progressScope = ProgressScope.load();
    private ProgressExclusions exclusions = ProgressExclusions.empty();
    private PracticeMode practiceMode = PracticeMode.RECONSTRUCTION;
    private RadioMenuItem manualModeMenu;
    private RadioMenuItem directModeMenu;
    private Menu practiceModeMenu;
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
    private MediaZoomControl mediaZoom;
    private ProjectSearchWindow searchWindow;
    private boolean soundProblemReported;
    private final TypingSounds typingSounds = new TypingSounds();
    private final CompletionSound completionSound = new CompletionSound();
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
        focus.setAccelerator(new KeyCodeCombination(KeyCode.F10));
        focus.selectedProperty().addListener((observed, before, enabled) -> {
            concentration = enabled;
            explorerToggle.setSelected(!enabled);
            if (top.getChildren().size() > 1) {
                top.getChildren().get(1).setVisible(!enabled);
                top.getChildren().get(1).setManaged(!enabled);
            }
            updateContextualZoom();
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
        ToggleGroup modes = new ToggleGroup();
        manualModeMenu = new RadioMenuItem("Reconstrucción manual");
        directModeMenu = new RadioMenuItem("Transcripción directa");
        manualModeMenu.setToggleGroup(modes);
        directModeMenu.setToggleGroup(modes);
        manualModeMenu.setSelected(true);
        manualModeMenu.setOnAction(event ->
                setPracticeMode(PracticeMode.RECONSTRUCTION));
        directModeMenu.setOnAction(event ->
                setPracticeMode(PracticeMode.DIRECT));
        practiceModeMenu = new Menu("Modalidad del proyecto", null,
                manualModeMenu, directModeMenu);
        practiceModeMenu.setDisable(true);
        MenuItem bundledLibrary = new MenuItem("Biblioteca de práctica...");
        bundledLibrary.setOnAction(event -> openPracticeLibrary());
        Menu practiceMenu = new Menu("Práctica", null,
                skipImportsMenu, turboMenu,
                new javafx.scene.control.SeparatorMenuItem(), practiceModeMenu,
                bundledLibrary);
        MenuItem about = new MenuItem("Acerca de CodeTrace");
        about.setOnAction(event -> showAbout());
        Menu helpMenu = new Menu("Ayuda", null, about);
        MenuItem preferences = new MenuItem("Preferencias...");
        preferences.setOnAction(event -> PreferencesWindow.open(
                stage, settings, this::applySettings, typingSounds::preview,
                reference, commandStore, this::refreshCommandPresets));
        MenuItem findCurrent = new MenuItem("Buscar en archivo...");
        findCurrent.setAccelerator(new KeyCodeCombination(
                KeyCode.F, KeyCombination.SHORTCUT_DOWN));
        findCurrent.setOnAction(event ->
                openSearch(ProjectSearchWindow.Scope.FILE));
        MenuItem findProject = new MenuItem("Buscar en proyecto...");
        findProject.setAccelerator(new KeyCodeCombination(KeyCode.F,
                KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        findProject.setOnAction(event ->
                openSearch(ProjectSearchWindow.Scope.PROJECT));
        MenuItem dependencyMap = new MenuItem("Mapa de dependencias de carpeta...");
        dependencyMap.setGraphic(new FontIcon("fth-share-2"));
        dependencyMap.setOnAction(event -> openDependencyGraphForSelectedFolder());
        Menu toolsMenu = new Menu("Herramientas", null, findCurrent, findProject,
                new javafx.scene.control.SeparatorMenuItem(), dependencyMap,
                new javafx.scene.control.SeparatorMenuItem(), preferences);

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
        progressFilter.getStyleClass().addAll("practice-rules-button", "toolbar-action");
        progressFilter.setTooltip(new Tooltip(
                "Configurar contenido evaluado y ayudas de escritura"));
        tabsActions.getStyleClass().addAll("toolbar-action", "tabs-actions");
        tabsActions.setGraphic(new FontIcon("fth-layers"));
        tabsActions.setTooltip(new Tooltip("Administrar pestañas abiertas"));
        installTabActions();
        ToolBar toolbar = new ToolBar(openButton, saveButton,
                new Separator(Orientation.VERTICAL), refreshButton,
                new Separator(Orientation.VERTICAL), progressFilter, symbols,
                new Separator(Orientation.VERTICAL), tabsActions, consoleButton);
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
        excludeSelectedFolder = new CheckBox("Excluir del progreso");
        excludeSelectedFolder.getStyleClass().add("tree-exclude-checkbox");
        excludeSelectedFolder.setDisable(true);
        excludeSelectedFolder.setTooltip(new Tooltip(
                "No contabilizar la carpeta seleccionada; sus archivos siguen disponibles"));
        excludeSelectedFolder.setOnAction(event -> {
            if (syncingExclusion || reference == null) return;
            var selected = tree.getSelectionModel().getSelectedItem();
            if (selected == null || selected.getValue() == null
                    || !tree.isFolderPath(selected.getValue())) return;
            String relative = tree.relativePath(selected.getValue());
            if (relative.isEmpty()) return;
            changeFolderExclusion(relative, excludeSelectedFolder.isSelected());
        });
        tree.getSelectionModel().selectedItemProperty().addListener(
                (obs, before, selected) -> {
                    updateSelectedFolderExclusion();
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
                treeActivity, collapse, expand);
        header.getStyleClass().add("project-tree-header");
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        explorerSearch = new TextField();
        explorerSearch.setPromptText("Buscar archivo o carpeta...");
        explorerSearch.getStyleClass().add("tree-name-search");
        explorerSearch.setAccessibleText("Buscar por nombre en el explorador");
        explorerSearch.textProperty().addListener((obs, old, current) ->
                tree.searchNames(current));
        Button clearSearch = new Button("×");
        clearSearch.getStyleClass().add("tree-search-clear");
        clearSearch.setTooltip(new Tooltip("Limpiar búsqueda del explorador"));
        clearSearch.setOnAction(event -> explorerSearch.clear());
        HBox searchRow = new HBox(4, explorerSearch, clearSearch);
        HBox.setHgrow(explorerSearch, Priority.ALWAYS);
        searchRow.getStyleClass().add("tree-search-row");
        FlowPane optionsRow = new FlowPane(13, 5,
                onlySelectedFolder, excludeSelectedFolder);
        optionsRow.getStyleClass().add("tree-options-row");
        sidebar.getChildren().addAll(header, searchRow, optionsRow, tree);
        tree.setOnProgressFolder(this::setProgressFolder);
        tree.setExclusionHandlers(folder -> exclusions.excludes(folder),
                folder -> exclusions.explicitlyExcludes(folder),
                this::changeFolderExclusion);
        tree.setOnBusyChange(busy -> {
            treeActivity.setVisible(busy);
            expand.setDisable(busy);
        });
        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(165);
        sidebar.setPrefWidth(245);
        VBox.setVgrow(tree, Priority.ALWAYS);
        tree.setStatusLookup(path -> reconstructionStates.getOrDefault(path,
                workspace.exists(path) ? State.CREATED : State.PENDING));
        tree.setWorkspace(workspace);
        split.getItems().addAll(sidebar, tabs);
        if (!settings.showExplorer()) {
            split.getItems().remove(sidebar);
        } else {
            split.setDividerPositions(explorerRatio);
        }
        workspaceSplit.setOrientation(Orientation.VERTICAL);
        workspaceSplit.getStyleClass().add("workspace-split");
        workspaceSplit.getItems().add(split);
        root.setCenter(workspaceSplit);

        fileInfo.getStyleClass().add("progress-info");
        fileProgress.getStyleClass().add("typing-progress");
        fileProgress.setPrefWidth(160);
        fileProgress.setVisible(false);
        fileProgress.managedProperty().bind(fileProgress.visibleProperty());
        status.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(status, Priority.ALWAYS);
        installProgressFilters();
        zoom = new EditorZoomControl(settings.fontSize(), this::setEditorZoom);
        zoom.setId("editor-font-zoom");
        mediaZoom = new MediaZoomControl(this::setSelectedMediaZoom);
        mediaZoom.setId("media-image-zoom");
        mediaZoom.setVisible(false);
        mediaZoom.setManaged(false);
        HBox footer = new HBox(9, status, fileInfo, projectInfo, fileProgress,
                new Separator(Orientation.VERTICAL), zoom, mediaZoom);
        footer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        footer.setMinHeight(34);
        status.setMinWidth(80);
        footer.setPadding(new Insets(4, 9, 4, 9));
        footer.getStyleClass().add("statusbar");
        root.setBottom(footer);

        tabs.getSelectionModel().selectedItemProperty()
                .addListener((observed, before, selected) -> {
                    refreshMetrics();
                    refreshTabActions();
                    updateContextualZoom();
                    // Once tab selection is processed, return focus to its
                    // typing surface rather than leaving it in the file tree.
                    if (selected != null) {
                        javafx.application.Platform.runLater(
                                () -> focusSelectedPracticeTab(selected));
                    }
                });

        Scene scene = new Scene(root, 1160, 760);
        scene.getStylesheets().add(getClass()
                .getResource("/css/codetrace.css").toExternalForm());
        stage.setTitle("CodeTrace");
        stage.getIcons().add(new Image(
                getClass().getResourceAsStream("/icons/codetrace-window.png")));
        stage.setMinWidth(760);
        stage.setMinHeight(480);
        stage.setScene(scene);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F3),
                () -> navigateSearch(false));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F3,
                KeyCombination.SHIFT_DOWN), () -> navigateSearch(true));
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

    private void openCommandPreferences() {
        PreferencesWindow.openCommands(stage, settings, this::applySettings,
                typingSounds::preview, reference, commandStore,
                this::refreshCommandPresets);
    }

    private void setConsoleVisible(boolean visible) {
        if (visible && !workspaceSplit.getItems().contains(consolePane)) {
            consolePane.setVisible(true);
            workspaceSplit.getItems().add(consolePane);
            javafx.application.Platform.runLater(() -> {
                if (workspaceSplit.getItems().size() == 2) {
                    workspaceSplit.setDividerPositions(consoleDivider);
                }
            });
        } else if (!visible && workspaceSplit.getItems().contains(consolePane)) {
            if (!workspaceSplit.getDividers().isEmpty()) {
                consoleDivider = workspaceSplit.getDividerPositions()[0];
            }
            workspaceSplit.getItems().remove(consolePane);
            consolePane.setVisible(false);
        }
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
        reconstructionStates = Map.of();
        progressFolder = "";
        sessionFile = null;
        dirty = false;
        clearOpenTabs();
        reference = folder;
        if (searchWindow != null) searchWindow.projectChanged(reference);
        exclusions = ProgressExclusions.load(reference);
        if (explorerSearch != null) explorerSearch.clear();
        updateSelectedFolderExclusion();
        selectPracticeMode(PracticeMode.forProject(reference));
        tree.setWorkspace(workspace);
        tree.open(reference);
        refreshReconstructionStatuses();
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
        if (!com.mmdev.codetrace.reference.PracticeEligibility.supports(file)) {
            if (ReferenceMediaPane.kindFor(file) != ReferenceMediaPane.Kind.UNSUPPORTED)
                openMediaReference(file, relative);
            else openTextReference(file, relative);
            return;
        }
        try {
            if (Files.size(file) == 0) {
                setStatus("Archivo vacío · sin ejercicio de mecanografía.");
                return;
            }
        } catch (IOException problem) {
            setStatus("No se pudo leer el archivo de referencia.");
            return;
        }
        // Every eligible reference text file can be inspected immediately.
        // Virtual folders and the virtual file are created only upon transcription.
        Tab existing = openedTabs.get(relative);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            javafx.application.Platform.runLater(
                    () -> focusSelectedPracticeTab(existing));
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
                // Direct mode virtualizes the source path on first writing,
                // not when a preview opens. Nothing touches the real files.
                if (practiceMode == PracticeMode.DIRECT
                        && !virtualFileExists(relative)
                        && (!typed.isEmpty() || current.skippedCount() > 0)) {
                    workspace.ensureFile(relative);
                    // Existing reference nodes already appear in the tree.
                    // Refresh just their visual status: no recursive rebuild
                    // or loss of scrolling/focus on the first typed character.
                    refreshReconstructionStatuses();
                }
                if (virtualFileExists(relative)) {
                    if (!workspace.text(relative).equals(typed)) {
                        workspace.setText(relative, typed);
                    }
                    // Skips, errors and corrections also change session data,
                    // even if the concatenated typed characters are unchanged.
                    dirty = true;
                    updateTitle();
                }
                refreshMetrics();
                refreshStatusIfCompletionChanged(relative, current);
            }, file.getFileName().toString(), settings.fontFamily(), settings.fontSize(),
                    matched -> {
                        if (settings.typingSounds() && !typingSounds.play(matched)
                                && !soundProblemReported) {
                            soundProblemReported = true;
                            setStatus("Sonidos no disponibles en este dispositivo.");
                        }
                    });
            editor.setOnNotice(this::setStatus);
            editor.setPracticePermission(
                    () -> practiceMode == PracticeMode.DIRECT || virtualFileExists(relative),
                    () -> requestExplicitCreation(relative));
            editor.setTemplateOpacity(settings.templateOpacity());
            editor.setTurboMode(settings.turboMode());
            boolean markdown = relative.toLowerCase().endsWith(".md")
                    || relative.toLowerCase().endsWith(".markdown");
            Tab tab = new Tab(file.getFileName().toString(),
                    markdown ? new MarkdownPracticePane(editor, file, reference) : editor);
            tab.setGraphic(FileTypeIcons.forPath(file, false));
            tab.setOnClosed(event -> {
                openedTabs.remove(relative);
                if (tab.getContent() instanceof MarkdownPracticePane preview) preview.dispose();
            });
            installTabContextMenu(tab);
            openedTabs.put(relative, tab);
            tabs.getTabs().add(tab);
            tabs.getSelectionModel().select(tab);
            javafx.application.Platform.runLater(
                    () -> focusSelectedPracticeTab(tab));
            setStatus(relative);
            refreshMetrics();
        } catch (IOException | SecurityException problem) {
            setStatus("No se pudo abrir " + file.getFileName());
        }
    }

    private void openTextReference(Path file, String relative) {
        Tab existing = openedTabs.get(relative);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            return;
        }
        try {
            TextPreview.Result text = TextPreview.load(file);
            if (!text.readable()) {
                setStatus(text.reason());
                return;
            }
            ReferenceTextPane viewer = new ReferenceTextPane(text.content(), file.getFileName().toString());
            Tab tab = new Tab(file.getFileName().toString(), viewer);
            tab.setGraphic(FileTypeIcons.forPath(file, false));
            tab.setOnClosed(event -> openedTabs.remove(relative));
            installTabContextMenu(tab);
            openedTabs.put(relative, tab);
            tabs.getTabs().add(tab);
            tabs.getSelectionModel().select(tab);
            setStatus("Consulta de solo lectura: " + relative);
        } catch (IOException error) {
            setStatus("No se pudo consultar " + relative);
        }
    }

    /**
     * The graph uses a selected directory only; source references remain
     * read-only. Imports outside the local project are deliberately omitted.
     */
    private void openDependencyGraphForSelectedFolder() {
        if (reference == null) {
            showGraphNotice("Abre primero un proyecto de referencia.");
            return;
        }
        var item = tree.getSelectionModel().getSelectedItem();
        if (item == null || item.getValue() == null
                || !tree.isFolderPath(item.getValue())) {
            showGraphNotice("Selecciona una carpeta en el explorador antes de crear el mapa.");
            return;
        }
        Path folder = item.getValue();
        Path root = reference;
        long request = referenceRequest;
        setStatus("Analizando imports internos de " + folder.getFileName() + "...");
        Task<ProjectDependencyGraph.Graph> task = new Task<>() {
            @Override protected ProjectDependencyGraph.Graph call() throws IOException {
                return ProjectDependencyGraph.analyze(root, folder, this::isCancelled);
            }
        };
        task.setOnSucceeded(event -> {
            if (request != referenceRequest || !root.equals(reference)) return;
            ProjectDependencyGraph.Graph graph = task.getValue();
            if (graph.edges().isEmpty()) {
                showGraphNotice("No se encontraron dependencias internas resolubles "
                        + "en la carpeta seleccionada. Los imports externos no "
                        + "se muestran como archivos ficticios.");
                return;
            }
            DependencyGraphPane view = new DependencyGraphPane(graph, relative -> {
                Path target = reference.resolve(relative).normalize();
                if (Files.isRegularFile(target) && target.startsWith(reference))
                    openForPractice(target);
            });
            Tab tab = new Tab("Mapa · " + folder.getFileName(), view);
            tab.setGraphic(new FontIcon("fth-share-2"));
            installTabContextMenu(tab);
            tabs.getTabs().add(tab);
            tabs.getSelectionModel().select(tab);
            setStatus(graph.edges().size() + " dependencias internas · "
                    + graph.inspected() + " archivos analizados"
                    + (graph.truncated() ? " · análisis parcial" : ""));
        });
        task.setOnFailed(event -> {
            if (request != referenceRequest || !root.equals(reference)) return;
            showGraphNotice("No se pudo generar el mapa: " + task.getException().getMessage());
        });
        Thread thread = new Thread(task, "codetrace-dependency-graph");
        thread.setDaemon(true);
        thread.start();
    }

    private void showGraphNotice(String message) {
        Alert info = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        info.initOwner(stage);
        info.setTitle("Mapa de dependencias — CodeTrace");
        info.setHeaderText("Exploración de relaciones");
        info.showAndWait();
    }

    private void openSearch(ProjectSearchWindow.Scope scope) {
        if (searchWindow == null) {
            searchWindow = new ProjectSearchWindow(stage, () -> reference,
                    this::selectedTextFile, this::navigateToSearchHit);
        }
        searchWindow.show(scope);
    }

    private void navigateSearch(boolean backward) {
        if (searchWindow == null) {
            openSearch(ProjectSearchWindow.Scope.FILE);
            return;
        }
        searchWindow.next(backward);
    }

    private String selectedTextFile() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        if (selected == null || (editorIn(selected) == null
                && !(selected.getContent() instanceof ReferenceTextPane)))
            return null;
        for (var entry : openedTabs.entrySet()) {
            if (entry.getValue() == selected) return entry.getKey();
        }
        return null;
    }

    private void navigateToSearchHit(Hit hit) {
        if (reference == null) return;
        Path file = reference.resolve(hit.relative()).normalize();
        if (!file.startsWith(reference.normalize())) return;
        openForPractice(file);
        Tab selected = openedTabs.get(hit.relative());
        if (selected == null) return;
        javafx.application.Platform.runLater(() -> {
            tabs.getSelectionModel().select(selected);
            if (selected.getContent() instanceof MarkdownPracticePane markdown)
                markdown.showSource();
            TypingEditor editor = editorIn(selected);
            if (editor != null) editor.showSearchMatch(
                    hit.line() - 1, hit.column() - 1, hit.length());
            else if (selected.getContent() instanceof ReferenceTextPane viewer)
                viewer.showSearchMatch(hit.line(), hit.column(), hit.length());
        });
    }

    private void openMediaReference(Path file, String relative) {
        Tab existing = openedTabs.get(relative);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            return;
        }
        ReferenceMediaPane media = new ReferenceMediaPane(file, reference);
        media.zoomPercentProperty().addListener((obs, old, value) -> {
            if (tabs.getSelectionModel().getSelectedItem() != null
                    && tabs.getSelectionModel().getSelectedItem().getContent() == media
                    && mediaZoom != null)
                mediaZoom.setValue(value.intValue());
        });
        Tab tab = new Tab(file.getFileName().toString(), media);
        tab.setGraphic(FileTypeIcons.forPath(file, false));
        tab.setOnClosed(event -> {
            openedTabs.remove(relative);
            media.dispose();
        });
        installTabContextMenu(tab);
        openedTabs.put(relative, tab);
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        setStatus("Vista previa · solo lectura: " + relative);
        refreshMetrics();
    }

    private void installTabActions() {
        MenuItem closeCurrent = new MenuItem("Cerrar pestaña actual");
        closeCurrent.setOnAction(event ->
                closeTab(tabs.getSelectionModel().getSelectedItem()));
        MenuItem closeOthers = new MenuItem("Cerrar las demás");
        closeOthers.setOnAction(event ->
                closeOtherTabs(tabs.getSelectionModel().getSelectedItem()));
        MenuItem closeAll = new MenuItem("Cerrar todas");
        closeAll.setOnAction(event -> closeAllTabs());
        tabsActions.getItems().setAll(closeCurrent, closeOthers, closeAll);
        refreshTabActions();
    }

    private void refreshTabActions() {
        tabsActions.setDisable(tabs.getTabs().isEmpty());
        if (tabsActions.getItems().size() < 3) return;
        tabsActions.getItems().get(1).setDisable(tabs.getTabs().size() < 2);
    }

    private void installTabContextMenu(Tab tab) {
        MenuItem close = new MenuItem("Cerrar");
        close.setOnAction(event -> closeTab(tab));
        MenuItem others = new MenuItem("Cerrar las demás");
        others.setOnAction(event -> closeOtherTabs(tab));
        MenuItem all = new MenuItem("Cerrar todas");
        all.setOnAction(event -> closeAllTabs());
        tab.setContextMenu(new ContextMenu(close, others, all));
    }

    private void closeTab(Tab tab) {
        if (tab == null) return;
        // Removing a Tab programmatically does not fire its usual onClosed
        // handler; release resources explicitly before removal.
        if (tab.getContent() instanceof ReferenceMediaPane media) media.dispose();
        if (tab.getContent() instanceof MarkdownPracticePane preview) preview.dispose();
        openedTabs.values().removeIf(candidate -> candidate == tab);
        tabs.getTabs().remove(tab);
        refreshTabActions();
        refreshMetrics();
    }

    private void closeOtherTabs(Tab keep) {
        if (keep == null) return;
        for (Tab tab : List.copyOf(tabs.getTabs())) {
            if (tab != keep) closeTab(tab);
        }
        tabs.getSelectionModel().select(keep);
    }

    private void closeAllTabs() {
        for (Tab tab : List.copyOf(tabs.getTabs())) closeTab(tab);
    }

    private void clearOpenTabs() {
        for (Tab tab : tabs.getTabs()) {
            if (tab.getContent() instanceof ReferenceMediaPane media) media.dispose();
            if (tab.getContent() instanceof MarkdownPracticePane preview) preview.dispose();
        }
        tabs.getTabs().clear();
        openedTabs.clear();
        refreshTabActions();
    }

    private void focusSelectedPracticeTab(Tab tab) {
        if (tabs.getSelectionModel().getSelectedItem() != tab) return;
        TypingEditor editor = editorIn(tab);
        if (editor != null) editor.focusEditor();
    }

    private boolean virtualFileExists(String relative) {
        VirtualWorkspace.Entry entry = workspace.entry(relative);
        return entry != null && entry.kind() == VirtualWorkspace.Kind.FILE;
    }

    private void requestExplicitCreation(String relative) {
        int slash = relative.lastIndexOf('/');
        String parent = slash < 0 ? "" : relative.substring(0, slash);
        String name = slash < 0 ? relative : relative.substring(slash + 1);
        String location = parent.isEmpty() ? "la raíz del proyecto" : "la carpeta " + parent;
        ProductDialogs.error(stage, "Crear archivo antes de practicar",
                "El archivo \"" + name + "\" todavía no existe en tu proyecto virtual.\n\n"
                + "Para empezar a escribir, haz clic derecho en " + location
                + " y selecciona «Crear archivo...», con el mismo nombre de la referencia.\n\n"
                + "Si la carpeta también está pendiente, créala primero.");
    }

    private void onWorkspaceChanged(ReferenceTree.Change change) {
        if (change.operation() == ReferenceTree.Operation.DELETE) {
            removeSessionsUnder(change.source());
        } else if (change.operation() == ReferenceTree.Operation.MOVE) {
            moveSessionsUnder(change.source(), change.target());
        }
        // A newly created file unlocks its existing, open preview immediately.
        if (change.operation() != ReferenceTree.Operation.CREATE) {
            clearOpenTabs();
        } else {
            // After a file is created via its context menu, allow immediate
            // typing in the existing preview without another mouse click.
            Tab createdTab = openedTabs.get(change.target());
            if (createdTab != null) {
                javafx.application.Platform.runLater(
                        () -> focusSelectedPracticeTab(createdTab));
            }
        }
        dirty = true;
        updateTitle();
        refreshReconstructionStatuses();
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

    /** Mode is per project; progress and file contents are preserved on switch. */
    private void selectPracticeMode(PracticeMode next) {
        practiceMode = next;
        if (practiceModeMenu != null) {
            practiceModeMenu.setDisable(reference == null);
            manualModeMenu.setSelected(next == PracticeMode.RECONSTRUCTION);
            directModeMenu.setSelected(next == PracticeMode.DIRECT);
        }
        if (autoCreateCheck != null) {
            autoCreateCheck.setSelected(next == PracticeMode.DIRECT);
            autoCreateCheck.setDisable(reference == null);
        }
    }

    private void setPracticeMode(PracticeMode next) {
        if (reference == null || practiceMode == next) return;
        selectPracticeMode(next);
        PracticeMode.remember(reference, next);
        dirty = true;
        updateTitle();
        setStatus(next == PracticeMode.DIRECT
                ? "Transcripción directa · sin crear carpetas ni archivos a mano"
                : "Reconstrucción manual · crea los archivos antes de escribir");
    }

    private void refreshReconstructionStatuses() {
        reconstructionStates = ReconstructionStatus.calculate(
                workspace, sessions, hashes, catalogue, progressScope, exclusions);
        tree.refreshStatuses();
    }

    /** Recompute folder states only when a file crosses the completed threshold. */
    private void refreshStatusIfCompletionChanged(String relative, TypingSession session) {
        boolean finished = session.finishedExercise()
                && catalogue != null
                && catalogue.files().containsKey(relative)
                && catalogue.files().get(relative).sha256().equals(hashes.get(relative))
                && virtualFileExists(relative);
        boolean wasFinished = reconstructionStates.get(relative) == State.COMPLETED;
        if (finished != wasFinished) {
            refreshReconstructionStatuses();
            // Only sound on a genuine incomplete -> completed transition.
            // Reopening tabs, loading sessions and refreshing the tree never play it.
            if (finished && reconstructionStates.get(relative) == State.COMPLETED
                    && settings.completionSound() && !completionSound.play()
                    && !soundProblemReported) {
                soundProblemReported = true;
                setStatus("Sonido de finalización no disponible en este dispositivo.");
            }
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
        long total = catalogue.expectedUnits(progressScope, progressFolder, exclusions);
        long resolved = catalogue.resolvedUnits(
                workspace, sessions, hashes, progressScope, progressFolder, exclusions);
        double ratio = total == 0 ? 0.0 : (double) resolved / total;
        long count = catalogue.includedFileCount(
                progressScope, progressFolder, exclusions);
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

    /** Both the context menu and the toolbar checkbox share this path. */
    private void changeFolderExclusion(String folder, boolean excluded) {
        try {
            exclusions = exclusions.withFolder(folder, excluded);
            exclusions.save(reference);
            refreshReconstructionStatuses();
            refreshMetrics();
            updateSelectedFolderExclusion();
            setStatus((excluded ? "Carpeta excluida del progreso: "
                    : "Carpeta incluida en el progreso: ") + folder);
        } catch (IllegalArgumentException error) {
            ProductDialogs.error(stage, "Exclusiones del proyecto", error.getMessage());
            updateSelectedFolderExclusion();
        }
    }

    /** Inherited exclusion is visible but cannot be changed on a child. */
    private void updateSelectedFolderExclusion() {
        if (excludeSelectedFolder == null) return;
        var selected = tree.getSelectionModel().getSelectedItem();
        boolean folder = selected != null && selected.getValue() != null
                && tree.isFolderPath(selected.getValue());
        String relative = folder ? tree.relativePath(selected.getValue()) : "";
        boolean inherited = folder && !relative.isEmpty()
                && exclusions.excludes(relative)
                && !exclusions.explicitlyExcludes(relative);
        syncingExclusion = true;
        try {
            excludeSelectedFolder.setSelected(folder && !relative.isEmpty()
                    && exclusions.excludes(relative));
            excludeSelectedFolder.setIndeterminate(inherited);
            excludeSelectedFolder.setDisable(reference == null
                    || !folder || relative.isEmpty() || inherited);
            excludeSelectedFolder.setTooltip(new Tooltip(inherited
                    ? "La exclusión se hereda de una carpeta superior. Cámbiala allí."
                    : "Excluir esta carpeta del cálculo de progreso, no de la exploración"));
        } finally {
            syncingExclusion = false;
        }
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
        Label contentCaption = new Label("CONTENIDO EVALUADO");
        contentCaption.getStyleClass().add("palette-caption");
        VBox options = new VBox(9, contentCaption, allScopes);
        options.getStyleClass().add("scope-options");
        addFilterOption(options, "Código fuente", ProgressScope.Category.SOURCE);
        addFilterOption(options, "Markdown", ProgressScope.Category.DOCUMENTATION);
        Label writingCaption = new Label("AYUDAS DE ESCRITURA");
        writingCaption.getStyleClass().add("palette-caption");
        skipImportsButton.setText("Omitir imports de Java (archivo actual)");
        autoCreateCheck = new CheckBox("Autocrear archivos y carpetas");
        autoCreateCheck.setTooltip(new Tooltip(
                "Al empezar a escribir, crea automáticamente el archivo y sus carpetas "
                + "en el espacio virtual. Nunca modifica el proyecto real."));
        autoCreateCheck.setSelected(practiceMode == PracticeMode.DIRECT);
        autoCreateCheck.setDisable(reference == null);
        autoCreateCheck.setOnAction(event -> setPracticeMode(
                autoCreateCheck.isSelected() ? PracticeMode.DIRECT
                        : PracticeMode.RECONSTRUCTION));
        options.getChildren().addAll(new Separator(Orientation.HORIZONTAL),
                writingCaption, autoCreateCheck, turboToggle, skipImportsButton);
        Label exclusionHint = new Label(
                "Autocrear solo afecta al proyecto virtual. Para excluir carpetas "
                + "del progreso, selecciónalas y utiliza la casilla del explorador.");
        exclusionHint.getStyleClass().add("scope-exclusion-hint");
        exclusionHint.setWrapText(true);
        options.getChildren().add(exclusionHint);
        updateAllCheck();

        allScopes.setOnAction(event -> {
            boolean active = allScopes.isSelected();
            progressScope = new ProgressScope(active, false, active, false);
            for (CheckBox choice : scopeChecks.values()) choice.setSelected(active);
            progressScope.save();
            refreshReconstructionStatuses();
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
            refreshReconstructionStatuses();
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
            if (searchWindow != null) searchWindow.projectChanged(reference);
            exclusions = ProgressExclusions.load(reference);
            selectPracticeMode(data.practiceMode());
            consolePane.setProject(reference);
            refreshCommandPresets();
            progressFolder = "";
            workspace = data.workspace();
            sessions = new LinkedHashMap<>(data.sessions());
            hashes = new LinkedHashMap<>(data.referenceHashes());
            sessionFile = selected.toPath();
            dirty = false;
            clearOpenTabs();
            tree.setWorkspace(workspace);
            tree.open(reference);
            refreshReconstructionStatuses();
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
                    reference, workspace, sessions, hashes, practiceMode));
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
        refreshReconstructionStatuses();
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
                refreshReconstructionStatuses();
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

    private void setSelectedMediaZoom(int percent) {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getContent() instanceof ReferenceMediaPane media)
            media.setZoomPercent(percent);
    }

    private void updateContextualZoom() {
        if (zoom == null || mediaZoom == null) return;
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        boolean visual = selected != null
                && selected.getContent() instanceof ReferenceMediaPane media
                && media.supportsZoom();
        zoom.setVisible(!visual && !concentration);
        zoom.setManaged(!visual && !concentration);
        mediaZoom.setVisible(visual && !concentration);
        mediaZoom.setManaged(visual && !concentration);
        if (visual) {
            ReferenceMediaPane media = (ReferenceMediaPane) selected.getContent();
            mediaZoom.setValue(media.getZoomPercent());
        }
    }

    private void setEditorZoom(int size) {
        if (size == settings.fontSize()) {
            return;
        }
        settings = new AppSettings(settings.fontFamily(), size,
                settings.showExplorer(), settings.keyboardLayout(), settings.typingSounds(),
                settings.templateOpacity(), settings.turboMode(),
                settings.completionSound());
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
                next.templateOpacity(), next.turboMode(), next.completionSound());
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
