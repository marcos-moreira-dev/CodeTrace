package com.mmdev.codetrace.app;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.AutoCreatedWorkspaceCleanup;
import com.mmdev.codetrace.practice.ReconstructionStatus;
import com.mmdev.codetrace.practice.ReconstructionStatus.State;
import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.progress.PracticeSessionPaths;
import com.mmdev.codetrace.reference.ProgressSummary;
import com.mmdev.codetrace.reference.ProgressAccumulator;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.ProgressScope;
import com.mmdev.codetrace.reference.ProgressExclusions;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.ui.ReferenceTree;
import com.mmdev.codetrace.ui.ConsultationFolderTree;
import com.mmdev.codetrace.ui.ConsultationFolderPolicy;
import com.mmdev.codetrace.ui.DocumentTabs;
import com.mmdev.codetrace.ui.EmptyWorkspacePane;
import com.mmdev.codetrace.ui.StatusStopwatch;
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
import com.mmdev.codetrace.ui.ReadOnlyMarkdownPane;
import com.mmdev.codetrace.ui.UnsupportedReferencePane;
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
import com.mmdev.codetrace.practice.CompletionTransition;
import com.mmdev.codetrace.practice.CompletionSummary;
import com.mmdev.codetrace.practice.DependencyDeclarationRules;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import com.mmdev.codetrace.workspace.VirtualTreeRecreator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
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
import javafx.scene.layout.StackPane;
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
  * <p><strong>Engineering rationale.</strong> This is the composition root, not the domain model: it connects JavaFX events to dedicated services and pure reference/workspace/practice objects. Project changes invalidate in-flight work and release tabs before the replacement state becomes visible; keep long disk scans and binary decoding off the JavaFX application thread.</p>
*/
public final class CodeTraceApp extends Application {
    private final TabPane tabs = new TabPane();
    private final DocumentTabs documents = new DocumentTabs(tabs, () -> {
        refreshTabActions();
        refreshMetrics();
        refreshContextStatus();
    });
    private final Label status = new Label("Listo");
    private final PauseTransition statusReset = new PauseTransition(Duration.seconds(6));
    private final StatusStopwatch stopwatch = new StatusStopwatch();
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
    private final Accordion folderAccordion = new Accordion();
    private final ConsultationFolderTree consultationTree =
            new ConsultationFolderTree(this::openConsultationFile, this::setStatus);
    private TitledPane mainFolderPane;
    private TitledPane consultationPane;
    private Button addConsultationButton;
    private final ProjectCommandStore commandStore = new ProjectCommandStore();
    private final CommandConsolePane consolePane =
            new CommandConsolePane(() -> setConsoleVisible(false),
                    this::openCommandPreferences);
    private CheckMenuItem showConsole;
    private String progressFolder = "";
    private CheckBox onlySelectedFolder;
    private CheckBox excludeSelectedFolder;
    private boolean syncingExclusion;
    private ReferenceTreeIndex referenceIndex;
    private long autoCreateGeneration;
    /** Only paths created by this running session's automatic reconstruction. */
    private final java.util.Set<String> autoCreatedPaths = new java.util.LinkedHashSet<>();
    private Map<String, GraphLayout> graphLayouts = new LinkedHashMap<>();
    private TextField explorerSearch;
    private boolean syncingFolderScope;
     private CheckBox autoCreateCheck;
    private final Button refreshButton = new Button("", new FontIcon("fth-refresh-cw"));
    private final ToggleButton skipImportsButton =
            new ToggleButton("Omitir dependencias de código",
                    ToolbarIcons.graphic(ToolbarIcons.Icon.SKIP));
    private final CheckMenuItem skipImportsMenu =
            new CheckMenuItem("Omitir dependencias de código");
    private final VBox top = new VBox();
    private final MenuButton tabsActions = new MenuButton("Pestañas");

    private VirtualWorkspace workspace = new VirtualWorkspace();
    private Map<String, TypingSession> sessions = new LinkedHashMap<>();
    private Map<String, String> hashes = new LinkedHashMap<>();
    private Map<String, State> reconstructionStates = Map.of();
    private ReferenceCatalogue.Snapshot catalogue;
    /** Rebuilt only after structural/filter/catalogue changes. */
    private ProgressAccumulator progressAccumulator;
    private ProgressScope progressScope = ProgressScope.load();
    /** User-wide, reversible training preference. Existing .ctrace slots are intact. */
    private boolean includeComments = java.util.prefs.Preferences
            .userNodeForPackage(CodeTraceApp.class)
            .getBoolean("practice.includeComments", true);
    private CheckBox includeCommentsCheck;
    private boolean omitDependencies = java.util.prefs.Preferences
            .userNodeForPackage(CodeTraceApp.class)
            .getBoolean("practice.omitDependencies", false);
    private ProgressExclusions exclusions = ProgressExclusions.empty();
    private PracticeMode practiceMode = PracticeMode.RECONSTRUCTION;
    private RadioMenuItem manualModeMenu;
    private RadioMenuItem directModeMenu;
    private Menu practiceModeMenu;
    private final ReferenceCatalogueScanner catalogueScanner =
            new ReferenceCatalogueScanner();
    private final LatestTaskRunner<Integer> referenceChecks = new LatestTaskRunner<>();
    private final LatestTaskRunner<SessionArchive.State> sessionLoader =
            new LatestTaskRunner<>();
    private Path reference;
    private long referenceRequest;
    private Path sessionFile;
    private AppSettings settings;
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
        // Register bundled faces before reading defaults or laying out controls.
        FontResources.register();
        settings = AppSettings.load();
        stage = window;
        statusReset.setOnFinished(event -> refreshContextStatus());
        BorderPane root = new BorderPane();

        MenuItem openReference = new MenuItem("Abrir referencia...");
        openReference.setAccelerator(new KeyCodeCombination(
                KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        openReference.setOnAction(event -> chooseReference());
        MenuItem openSession = new MenuItem("Abrir proyecto CodeTrace (.ctrace)...");
        openSession.setOnAction(event -> openSession());
        MenuItem save = new MenuItem("Guardar proyecto");
        save.setAccelerator(new KeyCodeCombination(
                KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        save.setOnAction(event -> saveSession(false));
        MenuItem saveAs = new MenuItem("Guardar proyecto como...");
        saveAs.setOnAction(event -> saveSession(true));
        MenuItem quit = new MenuItem("Salir");
        quit.setOnAction(event -> stage.close());
        Menu fileMenu = new Menu("Archivo", null,
                openReference, openSession,
                new javafx.scene.control.SeparatorMenuItem(),
                save, saveAs, quit);

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

        skipImportsMenu.setSelected(omitDependencies);
        skipImportsMenu.setOnAction(event ->
                setOmitDependencies(skipImportsMenu.isSelected()));
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
        MenuItem dependencyMap = new MenuItem("Mapa de estructura y dependencias...");
        dependencyMap.setGraphic(new FontIcon("fth-map"));
        dependencyMap.setOnAction(event -> openDependencyGraphForSelectedFolder());
        Menu toolsMenu = new Menu("Herramientas", null, findCurrent, findProject,
                new javafx.scene.control.SeparatorMenuItem(), dependencyMap,
                new javafx.scene.control.SeparatorMenuItem(), preferences);

        MenuBar menu = new MenuBar(
                fileMenu, practiceMenu, viewMenu, toolsMenu, helpMenu);
        Button openButton = ToolbarIcons.button(
                "Abrir referencia", ToolbarIcons.Icon.OPEN,
                "Abrir una carpeta de referencia para explorar y practicar");
        openButton.setOnAction(event -> chooseReference());
        Button openProjectButton = new Button("Abrir proyecto", new FontIcon("fth-layers"));
        openProjectButton.getStyleClass().add("toolbar-action");
        openProjectButton.setTooltip(new Tooltip(
                "Restaurar un proyecto CodeTrace guardado (.ctrace)"));
        openProjectButton.setOnAction(event -> openSession());
        Button saveButton = ToolbarIcons.button(
                "Guardar proyecto", ToolbarIcons.Icon.SAVE,
                "Guardar progreso y estructura virtual como proyecto .ctrace");
        saveButton.setOnAction(event -> saveSession(false));
        refreshButton.setDisable(true);
        refreshButton.getStyleClass().add("tree-header-action");
        refreshButton.setTooltip(new Tooltip("Actualizar referencia: volver a analizar "
                + "los archivos originales sin modificar el proyecto guardado"));
        refreshButton.setAccessibleText("Actualizar referencia");
        refreshButton.setOnAction(event -> refreshReference());
        skipImportsButton.getStyleClass().addAll("toolbar-action", "turbo-toggle");
        skipImportsButton.setSelected(omitDependencies);
        skipImportsButton.setTooltip(new Tooltip(
                "Activar o desactivar la omisión de declaraciones de dependencias"));
        skipImportsButton.setOnAction(event ->
                setOmitDependencies(skipImportsButton.isSelected()));
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
        ToolBar toolbar = new ToolBar(openButton, openProjectButton, saveButton,
                new Separator(Orientation.VERTICAL), progressFilter, symbols,
                new Separator(Orientation.VERTICAL), tabsActions, consoleButton);
        top.getChildren().addAll(menu, toolbar);
        root.setTop(top);

        onlySelectedFolder = com.mmdev.codetrace.ui.AppControls.checkBox("Solo esta carpeta");
        onlySelectedFolder.getStyleClass().add("tree-scope-checkbox");
        onlySelectedFolder.setTooltip(new Tooltip(
                "Contabiliza solo la carpeta activa. Selecciona otra carpeta para cambiar "
                + "el ámbito o desmarca esta opción para incluir todo el proyecto."));
        onlySelectedFolder.setDisable(true);
        onlySelectedFolder.setOnAction(event -> {
            if (syncingFolderScope) return;
            var selected = tree.selectedExplorerItem();
            if (onlySelectedFolder.isSelected() && selected != null
                    && tree.isFolderPath(selected.getValue())) {
                setProgressFolder(tree.relativePath(selected.getValue()));
            } else {
                setProgressFolder("");
            }
        });
        excludeSelectedFolder = com.mmdev.codetrace.ui.AppControls.checkBox("Excluir del progreso");
        excludeSelectedFolder.getStyleClass().add("tree-exclude-checkbox");
        excludeSelectedFolder.setDisable(true);
        excludeSelectedFolder.setTooltip(new Tooltip(
                "No contabilizar la carpeta seleccionada; sus archivos siguen disponibles"));
        excludeSelectedFolder.setOnAction(event -> {
            if (syncingExclusion || reference == null) return;
            var selected = tree.selectedExplorerItem();
            if (selected == null || selected.getValue() == null
                    || !tree.isFolderPath(selected.getValue())) return;
            String relative = tree.relativePath(selected.getValue());
            if (relative.isEmpty()) return;
            changeFolderExclusion(relative, excludeSelectedFolder.isSelected());
        });
        Runnable updateExplorerSelection = () -> {
            var selected = tree.selectedExplorerItem();
            updateSelectedFolderExclusion();
            boolean folder = selected != null && tree.isFolderPath(selected.getValue());
            onlySelectedFolder.setDisable(!folder && !onlySelectedFolder.isSelected());
            if (folder && onlySelectedFolder.isSelected())
                setProgressFolder(tree.relativePath(selected.getValue()));
        };
        tree.getSelectionModel().selectedItemProperty().addListener(
                (obs, before, selected) -> updateExplorerSelection.run());
        tree.setOnProjectedSelectionChanged(updateExplorerSelection);
        ProgressIndicator treeActivity = new ProgressIndicator();
        treeActivity.setMaxSize(16, 16);
        treeActivity.setMinSize(16, 16);
        treeActivity.getStyleClass().add("tree-activity");
        treeActivity.setVisible(false);
        treeActivity.managedProperty().bind(treeActivity.visibleProperty());

        Button collapse = new Button("", new FontIcon("fth-minimize-2"));
        collapse.getStyleClass().add("tree-header-action");
        collapse.setTooltip(new Tooltip("Contraer todas las carpetas"));
        collapse.setAccessibleText("Contraer árbol");
        collapse.setOnAction(event -> tree.collapseAll());
        Button expand = new Button("", new FontIcon("fth-maximize-2"));
        expand.getStyleClass().add("tree-header-action");
        expand.setTooltip(new Tooltip("Expandir todas las carpetas"));
        expand.setAccessibleText("Expandir árbol");
        expand.setOnAction(event -> tree.expandAll());

        addConsultationButton = new Button("", new FontIcon("fth-plus"));
        addConsultationButton.getStyleClass().add("tree-header-action");
        addConsultationButton.setTooltip(new Tooltip(
                "Agregar una carpeta de consulta (máximo dos árboles)"));
        addConsultationButton.setAccessibleText("Agregar carpeta de consulta");
        addConsultationButton.setOnAction(event -> chooseConsultationFolder());

        Region headerFill = new Region();
        HBox.setHgrow(headerFill, Priority.ALWAYS);
        HBox header = new HBox(4, headerFill,
                treeActivity, refreshButton, collapse, expand, addConsultationButton);
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
        // The legacy TreeView handles short/manual navigation; the logical
        // projection uses a ListView VirtualFlow only for mass expansion.
        // Both share the same reference index without duplicating file bytes.
        // Disk indexing begins before the first tree rows exist. Present a
        // visible indeterminate indicator instead of a blank explorer. A mass
        // expansion keeps its existing rows usable and uses the small header
        // activity indicator instead of masking the list.
        ProgressIndicator initialLoadIndicator = new ProgressIndicator();
        initialLoadIndicator.setMaxSize(28, 28);
        Label initialLoadMessage = new Label("Preparando el explorador...");
        initialLoadMessage.getStyleClass().add("explorer-loading-label");
        VBox explorerLoading = new VBox(10, initialLoadIndicator, initialLoadMessage);
        explorerLoading.getStyleClass().add("explorer-loading");
        explorerLoading.setAlignment(javafx.geometry.Pos.CENTER);
        explorerLoading.setMouseTransparent(true);
        explorerLoading.setVisible(false);
        explorerLoading.managedProperty().bind(explorerLoading.visibleProperty());
        StackPane explorerHost = new StackPane(
                tree, tree.projectedExplorer(), explorerLoading);
        explorerHost.getStyleClass().add("reference-explorer-host");
        VBox mainContent = new VBox(header, searchRow, optionsRow, explorerHost);
        VBox.setVgrow(explorerHost, Priority.ALWAYS);
        mainContent.getStyleClass().add("main-project-section");
        mainFolderPane = new TitledPane("Proyecto principal", mainContent);
        mainFolderPane.setCollapsible(true);
        folderAccordion.getPanes().add(mainFolderPane);
        folderAccordion.setExpandedPane(mainFolderPane);
        folderAccordion.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(folderAccordion, Priority.ALWAYS);
        sidebar.getChildren().add(folderAccordion);
        tree.setOnProgressFolder(this::setProgressFolder);
        tree.setOnDependencyGraph(this::openDependencyGraphForFolder);
        tree.setOnIndexReady(index -> {
            referenceIndex = index;
            if (practiceMode == PracticeMode.DIRECT)
                autoCreateReferenceTree();
        });
        tree.setExclusionHandlers(folder -> exclusions.excludes(folder),
                folder -> exclusions.explicitlyExcludes(folder),
                this::changeFolderExclusion);
        // Keep the spinner visible through the first presentation pulse after
        // indexing, rather than declaring success immediately after the worker
        // completes. This is UI feedback, not an artificial disk delay.
        PauseTransition explorerReveal = new PauseTransition(Duration.millis(180));
        explorerReveal.setOnFinished(event -> {
            if (tree.isIndexing()) return;
            explorerLoading.setVisible(false);
            if (tabs.getTabs().isEmpty() && !tree.isBusy())
                refreshContextStatus();
        });
        tree.setOnBusyChange(busy -> {
            treeActivity.setVisible(busy);
            if (tree.isIndexing()) {
                explorerReveal.stop();
                explorerLoading.setVisible(true);
                if (tabs.getTabs().isEmpty()) {
                    statusReset.stop();
                    status.setText("Preparando el explorador...");
                }
            } else if (explorerLoading.isVisible()) {
                explorerReveal.playFromStart();
            } else if (!busy && tabs.getTabs().isEmpty()) {
                refreshContextStatus();
            }
            // Never block expansion or scrolling: logical rows are computed
            // outside JavaFX, while existing rows stay interactive.
        });
        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(165);
        sidebar.setPrefWidth(245);
        tree.setStatusLookup(path -> reconstructionStates.getOrDefault(path,
                workspace.exists(path) ? State.CREATED : State.PENDING));
        tree.setWorkspace(workspace);
        StackPane documentSurface = new StackPane(tabs, new EmptyWorkspacePane(tabs));
        documentSurface.getStyleClass().add("document-workspace");
        split.getItems().addAll(sidebar, documentSurface);
        if (!settings.showExplorer()) {
            split.getItems().remove(sidebar);
        } else {
            split.setDividerPositions(explorerRatio);
        }
        workspaceSplit.setOrientation(Orientation.VERTICAL);
        workspaceSplit.getStyleClass().add("workspace-split");
        workspaceSplit.getItems().add(split);
        root.setCenter(workspaceSplit);

        status.getStyleClass().add("status-context");
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
                new Separator(Orientation.VERTICAL), zoom, mediaZoom,
                new Separator(Orientation.VERTICAL), stopwatch);
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
                    refreshContextStatus();
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
                referenceChecks.cancel();
                catalogueScanner.cancel();
                sessionLoader.cancel();
                tree.cancelPendingWork();
                stopwatch.dispose();
            }
        });
        refreshMetrics();
        stage.show();
        // The installer supplies the selected .ctrace path on double-click.
        // Some embedders and the JavaFX smoke test invoke start(Stage)
        // directly without Application.launch, so getParameters() is null.
        // Capturing an empty argument list is safe and avoids a late FX NPE.
        var parameters = getParameters();
        var launchArguments = parameters == null ? java.util.List.<String>of()
                : java.util.List.copyOf(parameters.getRaw());
        javafx.application.Platform.runLater(() ->
                SessionLaunchFile.fromArguments(launchArguments)
                        .ifPresent(this::openSession));
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

    /** Starting a project and restoring one share the same activation lifecycle. */
    private void activateReference(Path folder) {
        applyProjectState(ProjectActivation.fresh(folder), false);
    }

    /**
     * Replace the active project in one place. The source tree is read-only;
     * session state lives exclusively in the virtual workspace and .ctrace.
     *
     * Cancellation and generation increments precede any UI update, so
     * asynchronous results from the previous project cannot revive it.
     */
    private void applyProjectState(ProjectActivation next, boolean resumed) {
        catalogueScanner.cancel();
        referenceChecks.cancel();
        sessionLoader.cancel();
        referenceRequest++;
        autoCreateGeneration++;
        autoCreatedPaths.clear();
        clearOpenTabs();

        reference = next.root();
        workspace = next.workspace();
        sessions = new LinkedHashMap<>(next.sessions());
        sessions.forEach((path, session) -> {
            session.setIncludeComments(includeComments, path);
            session.setOmitDependencies(omitDependencies, path);
        });
        hashes = new LinkedHashMap<>(next.hashes());
        graphLayouts = new LinkedHashMap<>(next.graphLayouts());
        sessionFile = next.sessionFile();
        dirty = false;
        catalogue = null;
        reconstructionStates = Map.of();
        referenceIndex = null;
        progressFolder = "";
        tree.setProgressFocusFolder("");

        if (searchWindow != null) searchWindow.projectChanged(reference);
        exclusions = ProgressExclusions.load(reference);
        if (explorerSearch != null) explorerSearch.clear();
        updateSelectedFolderExclusion();
        selectPracticeMode(next.practiceMode());

        tree.setWorkspace(workspace);
        tree.open(reference);
        refreshReconstructionStatuses();
        consolePane.setProject(reference);
        refreshCommandPresets();
        indexReference();
        refreshButton.setDisable(false);
        updateTitle();
        refreshMetrics();
        if (resumed) reportReferenceChanges();
        else setStatus(tree.isIndexing()
                ? "Preparando el explorador..."
                : "Referencia: " + reference);
    }

    private void refreshReference() {
        if (reference == null) return;
        if (!Files.isDirectory(reference)) {
            setStatus("La carpeta de referencia no está disponible. Selecciona otra.");
            return;
        }
        tree.reloadReference();
        indexReference();
        reportReferenceChanges();
    }

    /** Reference verification does not re-index or mutate original files. */
    private void reportReferenceChanges() {
        if (reference == null) return;
        Path root = reference;
        long request = referenceRequest;
        Map<String, String> saved = Map.copyOf(hashes);
        setStatus("Verificando archivos de referencia...");
        referenceChecks.submit(() -> ReferenceChangeDetector.changedCount(root, saved),
                changes -> {
                    if (request != referenceRequest || !root.equals(reference)) return;
                    setStatus(changes == 0 ? "Referencia actualizada"
                            : changes + " archivo(s) cambiaron; transcripciones conservadas");
                }, error -> {
                    if (request == referenceRequest && root.equals(reference))
                        setStatus("No se pudieron verificar los archivos de referencia.");
                }, "codetrace-reference-verification");
    }

    private void openForPractice(Path file) {
        if (reference == null || !Files.isRegularFile(file)) {
            setStatus("No hay archivo de referencia para esta entrada.");
            return;
        }
        // A .ctrace inside the project explorer is a saved application
        // session, not an arbitrary binary consultation document.
        if (file.getFileName().toString().toLowerCase(
                java.util.Locale.ROOT).endsWith(".ctrace")) {
            openSession(file);
            return;
        }
        String relative = relative(file);
        if (!com.mmdev.codetrace.reference.PracticeEligibility.supports(file)) {
            // Multimedia has its own read-only playback controls; the notice
            // belongs to non-media consultation documents, NOT to media.
            if (!com.mmdev.codetrace.ui.ConsultationNoticePolicy.requiresExplanation(file)) {
                openMediaReference(file, relative);
                return;
            }
            // Reopening an existing consultation tab does not repeat the notice.
            boolean alreadyOpen = documents.find(relative) != null;
            openTextReference(file, relative);
            if (!alreadyOpen) offerConsultationNotice(documents.find(relative));
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
        Tab existing = documents.find(relative);
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
            // Preserve reference text and saved slots; policy only changes
            // which source positions are required to complete the exercise.
            session.setIncludeComments(includeComments, file.getFileName().toString());
            session.setOmitDependencies(omitDependencies, file.getFileName().toString());
            hashes.putIfAbsent(relative, ReferenceFingerprint.sha256(file));
            TypingSession current = session;
            // Completion feedback tracks typing transitions, not the
            // asynchronous catalogue or progress-reporting infrastructure.
            CompletionTransition completion = new CompletionTransition(
                    current.finishedExercise());
            // The onChange callback is invoked after editor construction;
            // keep its reference outside the initializer for Java definite assignment.
            java.util.concurrent.atomic.AtomicReference<TypingEditor> activeEditor =
                    new java.util.concurrent.atomic.AtomicReference<>();
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
                // The user changed ONE practice file. Update its aggregate
                // contribution instead of rescanning the whole catalogue.
                if (progressAccumulator != null) {
                    progressAccumulator.updateFile(relative, workspace, current,
                            hashes.get(relative));
                }
                refreshMetrics();
                refreshStatusIfCompletionChanged(relative, current);
                if (completion.update(current.finishedExercise())) {
                    javafx.application.Platform.runLater(() -> {
                        if (current != sessions.get(relative)
                                || reference == null
                                || documents.find(relative) == null) return;
                        if (settings.completionSound() && !completionSound.play()
                                && !soundProblemReported) {
                            soundProblemReported = true;
                            setStatus("No se pudo reproducir el sonido de finalización.");
                        }
                        if (settings.completionDialog()) {
                            ProductDialogs.success(stage, "Archivo terminado",
                                    CompletionSummary.message(
                                            file.getFileName().toString(),
                                            current, activeEditor.get().practiceElapsedMillis(),
                                            activeEditor.get().correctTypedEvents(),
                                            activeEditor.get().incorrectTypedEvents()));
                        }
                    });
                }
            }, file.getFileName().toString(), settings.fontFamily(), settings.fontSize(),
                    matched -> {
                        if (settings.typingSounds() && !typingSounds.play(matched)
                                && !soundProblemReported) {
                            soundProblemReported = true;
                            setStatus("Sonidos no disponibles en este dispositivo.");
                        }
                    });
            activeEditor.set(editor);
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
            installTabContextMenu(tab);
            documents.open(relative, tab);
            javafx.application.Platform.runLater(
                    () -> focusSelectedPracticeTab(tab));
            setStatus(relative);
            refreshMetrics();
        } catch (IOException | SecurityException problem) {
            setStatus("No se pudo abrir " + file.getFileName());
        }
    }

    /**
     * Opening supplementary resources never creates a TypingSession, workspace
     * file, progress marker or automatic creation event.
     */
    private void openConsultationFile(Path file) {
        Path folder = consultationTree.folder();
        if (!ConsultationFolderPolicy.contains(folder, file)
                || !Files.isRegularFile(file)) {
            setStatus("La carpeta de consulta ya no contiene ese archivo.");
            return;
        }
        openReadOnlyFile(file, folder);
    }

    private void openReadOnlyFile(Path file, Path folder) {
        String key = "consulta://" + file.toAbsolutePath().normalize();
        Tab existing = documents.find(key);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            return;
        }
        if (ReferenceMediaPane.kindFor(file) != ReferenceMediaPane.Kind.UNSUPPORTED) {
            openMediaReference(file, key, folder);
            return;
        }
        javafx.scene.Node viewer;
        String name = file.getFileName().toString();
        try {
            TextPreview.Result data = TextPreview.load(file);
            if (!data.readable()) {
                viewer = new UnsupportedReferencePane(file);
            } else if (name.toLowerCase(java.util.Locale.ROOT).endsWith(".md")
                    || name.toLowerCase(java.util.Locale.ROOT).endsWith(".markdown")) {
                viewer = new ReadOnlyMarkdownPane(
                        data.content(), file, folder, settings.fontFamily(), settings.fontSize());
            } else {
                ReferenceTextPane text = new ReferenceTextPane(data.content(), name);
                text.setFontSettings(settings.fontFamily(), settings.fontSize());
                viewer = text;
            }
        } catch (IOException | SecurityException error) {
            viewer = new UnsupportedReferencePane(file);
        }
        Tab tab = new Tab(name, viewer);
        tab.setGraphic(FileTypeIcons.forPath(file, false));
        installTabContextMenu(tab);
        documents.open(key, tab);
        setStatus("Consulta de solo lectura: " + name);
        if (viewer instanceof ReferenceTextPane || viewer instanceof ReadOnlyMarkdownPane)
            offerConsultationNotice(tab);
    }

    /** Show informational guidance only for a newly opened, still-active view. */
    private void offerConsultationNotice(Tab opened) {
        if (opened == null || !settings.readOnlyNotice()) return;
        long request = referenceRequest;
        javafx.application.Platform.runLater(() -> {
            if (request != referenceRequest || !settings.readOnlyNotice()
                    || !tabs.getTabs().contains(opened)
                    || tabs.getSelectionModel().getSelectedItem() != opened) return;
            if (ProductDialogs.showConsultationNotice(stage)) {
                settings = settings.withReadOnlyNotice(false);
                settings.save();
            }
        });
    }

    private void chooseConsultationFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Agregar carpeta de consulta (solo lectura)");
        Path previous = consultationTree.folder();
        if (previous != null && Files.isDirectory(previous))
            chooser.setInitialDirectory(previous.toFile());
        java.io.File selected = chooser.showDialog(stage);
        if (selected == null || !consultationTree.openFolder(selected.toPath()))
            return;
        if (consultationPane == null) {
            Button change = new Button("", new FontIcon("fth-folder"));
            change.getStyleClass().add("tree-header-action");
            change.setTooltip(new Tooltip("Cambiar carpeta de consulta"));
            change.setOnAction(event -> chooseConsultationFolder());
            Button remove = new Button("", new FontIcon("fth-x"));
            remove.getStyleClass().add("tree-header-action");
            remove.setTooltip(new Tooltip("Quitar carpeta de consulta"));
            remove.setOnAction(event -> removeConsultationFolder());
            Label instruction = new Label("Doble clic para consultar un archivo");
            instruction.getStyleClass().add("consultation-tree-hint");
            Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            HBox controls = new HBox(5, instruction, space, change, remove);
            controls.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            controls.getStyleClass().add("project-tree-header");
            VBox content = new VBox(controls, consultationTree);
            VBox.setVgrow(consultationTree, Priority.ALWAYS);
            consultationPane = new TitledPane("Carpeta de consulta", content);
            folderAccordion.getPanes().add(consultationPane);
        }
        consultationPane.setText("Consulta · " + selected.getName());
        addConsultationButton.setDisable(true);
        folderAccordion.setExpandedPane(consultationPane);
        setStatus("Consulta de solo lectura: " + selected.getAbsolutePath());
    }

    private void removeConsultationFolder() {
        consultationTree.clearFolder();
        if (consultationPane != null) {
            folderAccordion.getPanes().remove(consultationPane);
            consultationPane = null;
        }
        addConsultationButton.setDisable(false);
        folderAccordion.setExpandedPane(mainFolderPane);
        setStatus("Carpeta de consulta retirada.");
    }

    private void openTextReference(Path file, String relative) {
        Tab existing = documents.find(relative);
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
            ReferenceTextPane viewer = new ReferenceTextPane(
                    text.content(), file.getFileName().toString());
            viewer.setFontSettings(settings.fontFamily(), settings.fontSize());
            Tab tab = new Tab(file.getFileName().toString(), viewer);
            tab.setGraphic(FileTypeIcons.forPath(file, false));
            installTabContextMenu(tab);
            documents.open(relative, tab);
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
        var item = tree.selectedExplorerItem();
        if (item == null || item.getValue() == null
                || !tree.isFolderPath(item.getValue())) {
            showGraphNotice("Selecciona una carpeta en el explorador antes de crear el mapa.");
            return;
        }
        openDependencyGraphForFolder(item.getValue());
    }

    /** Used by Tools and by the exact folder that received a right click. */
    private void openDependencyGraphForFolder(Path folder) {
        if (reference == null || folder == null || !Files.isDirectory(folder)
                || !folder.toAbsolutePath().normalize()
                          .startsWith(reference.toAbsolutePath().normalize())) {
            showGraphNotice("Selecciona una carpeta de la referencia actual.");
            return;
        }
        Path root = reference;
        long request = referenceRequest;
        setStatus("Preparando estructura y dependencias de " + folder.getFileName() + "...");
        Task<ProjectDependencyGraph.Graph> task = new Task<>() {
            @Override protected ProjectDependencyGraph.Graph call() throws IOException {
                return ProjectDependencyGraph.analyze(root, folder, this::isCancelled);
            }
        };
        task.setOnSucceeded(event -> {
            if (request != referenceRequest || !root.equals(reference)) return;
            ProjectDependencyGraph.Graph graph = task.getValue();
            if (graph.nodes().isEmpty() || graph.inspected() == 0) {
                showGraphNotice("No se encontraron archivos de código compatibles "
                        + "en la carpeta seleccionada.");
                return;
            }
            GraphLayout saved = graphLayouts.get(graph.folder());
            if (saved != null && !saved.fingerprint().equals(graph.fingerprint())) {
                graphLayouts.remove(graph.folder());
                dirty = true;
                updateTitle();
                saved = null;
            }
            DependencyGraphPane view = new DependencyGraphPane(graph, relative -> {
                Path target = reference.resolve(relative).normalize();
                if (Files.isRegularFile(target) && target.startsWith(reference))
                    openForPractice(target);
            }, saved, updated -> {
                graphLayouts.put(graph.folder(), updated);
                dirty = true;
                updateTitle();
            }, selectedPath -> {
                // Left: file path. Right: incoming/outgoing relationships.
                if (tabs.getSelectionModel().getSelectedItem() != null
                        && tabs.getSelectionModel().getSelectedItem().getContent()
                                instanceof DependencyGraphPane) {
                    setStatus(selectedPath == null
                            ? "Mapa · " + graph.folder()
                            : selectedPath);
                    refreshMetrics();
                }
            }, settings.graphLayoutMode(), hiddenFolder -> {
                Path child = reference.resolve(hiddenFolder).normalize();
                if (child.startsWith(reference) && Files.isDirectory(child))
                    openDependencyGraphForFolder(child);
            });
            view.zoomPercentProperty().addListener((obs, old, value) -> {
                if (tabs.getSelectionModel().getSelectedItem() != null
                        && tabs.getSelectionModel().getSelectedItem().getContent() == view)
                    mediaZoom.setValue(value.intValue());
            });
            Tab tab = new Tab("Estructura · " + folder.getFileName(), view);
            tab.setGraphic(new FontIcon("fth-share-2"));
            tab.setTooltip(new Tooltip(graph.truncated()
                    ? "Vista parcial: algunas carpetas o dependencias se omitieron por "
                      + "los límites de tamaño. No representa todo el proyecto."
                    : "Estructura y dependencias explícitas detectadas en " + graph.folder()));
            installTabContextMenu(tab);
            documents.open(null, tab);
            setStatus("Mapa · " + graph.folder()
                    + (graph.truncated() ? " · vista parcial" : ""));
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
        info.setTitle("Mapa de estructura y dependencias — CodeTrace");
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
        return documents.pathOf(selected);
    }

    private void navigateToSearchHit(Hit hit) {
        if (reference == null) return;
        Path file = reference.resolve(hit.relative()).normalize();
        if (!file.startsWith(reference.normalize())) return;
        openForPractice(file);
        Tab selected = documents.find(hit.relative());
        if (selected == null) return;
        var location = hit.range();
        Path root = reference;
        long request = referenceRequest;
        javafx.application.Platform.runLater(() -> {
            if (request != referenceRequest || !root.equals(reference)
                    || !tabs.getTabs().contains(selected)) return;
            tabs.getSelectionModel().select(selected);
            if (selected.getContent() instanceof MarkdownPracticePane markdown)
                markdown.showSource();
            TypingEditor editor = editorIn(selected);
            if (editor != null) editor.showSearchMatch(location);
            else if (selected.getContent() instanceof ReferenceTextPane viewer)
                viewer.showSearchMatch(location);
        });
    }

    private void openMediaReference(Path file, String relative) {
        openMediaReference(file, relative, reference);
    }

    private void openMediaReference(Path file, String relative, Path referenceRoot) {
        Tab existing = documents.find(relative);
        if (existing != null) {
            tabs.getSelectionModel().select(existing);
            return;
        }
        ReferenceMediaPane media = new ReferenceMediaPane(file, referenceRoot);
        media.zoomPercentProperty().addListener((obs, old, value) -> {
            if (tabs.getSelectionModel().getSelectedItem() != null
                    && tabs.getSelectionModel().getSelectedItem().getContent() == media
                    && mediaZoom != null)
                mediaZoom.setValue(value.intValue());
        });
        Tab tab = new Tab(file.getFileName().toString(), media);
        tab.setGraphic(FileTypeIcons.forPath(file, false));
        installTabContextMenu(tab);
        documents.open(relative, tab);
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
        documents.close(tab);
    }

    private void closeOtherTabs(Tab keep) {
        documents.closeOthers(keep);
    }

    private void closeAllTabs() {
        documents.closeAll();
    }

    private void clearOpenTabs() {
        documents.closeAll();
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
        // A user-initiated operation revokes automatic provenance for that path.
        // Prevents accidentally removing manually recreated or relocated nodes.
        if (change.source() != null && !change.source().isEmpty())
            autoCreatedPaths.removeIf(p -> p.equals(change.source())
                    || p.startsWith(change.source() + "/"));
        if (change.target() != null && !change.target().isEmpty())
            autoCreatedPaths.removeIf(p -> p.equals(change.target())
                    || p.startsWith(change.target() + "/"));
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
            Tab createdTab = documents.find(change.target());
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
        PracticeSessionPaths remaining = new PracticeSessionPaths(sessions, hashes)
                .withoutSubtree(root);
        sessions = remaining.sessions();
        hashes = remaining.hashes();
    }

    private void moveSessionsUnder(String from, String to) {
        PracticeSessionPaths relocated = new PracticeSessionPaths(sessions, hashes)
                .movedSubtree(from, to);
        sessions = relocated.sessions();
        hashes = relocated.hashes();
    }

    /**
     * Reversible project-wide training rule, shared by menu and toolbar.
     * Old skip slots remain untouched; only the derived dependency mask changes.
     */
    private void setOmitDependencies(boolean enabled) {
        if (omitDependencies == enabled) {
            skipImportsButton.setSelected(enabled);
            skipImportsMenu.setSelected(enabled);
            return;
        }
        omitDependencies = enabled;
        java.util.prefs.Preferences.userNodeForPackage(CodeTraceApp.class)
                .putBoolean("practice.omitDependencies", enabled);
        skipImportsButton.setSelected(enabled);
        skipImportsMenu.setSelected(enabled);
        sessions.forEach((path, session) -> session.setOmitDependencies(enabled, path));
        for (Tab tab : tabs.getTabs()) {
            TypingEditor editor = editorIn(tab);
            if (editor != null) editor.refreshCommentPolicy();
        }
        refreshReconstructionStatuses();
        refreshMetrics();
        setStatus(enabled ? "Dependencias omitidas de la práctica."
                : "Dependencias incluidas nuevamente en la práctica.");
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
        // Cancel queued creation batches before reverting anything.
        if (next == PracticeMode.RECONSTRUCTION) autoCreateGeneration++;
        selectPracticeMode(next);
        PracticeMode.remember(reference, next);
        int removed = 0;
        if (next == PracticeMode.RECONSTRUCTION) {
            removed = AutoCreatedWorkspaceCleanup.removeUnused(
                    workspace, autoCreatedPaths, sessions);
            autoCreatedPaths.clear();
            if (removed > 0) tree.setWorkspace(workspace);
            refreshReconstructionStatuses();
            refreshMetrics();
        } else autoCreateReferenceTree();
        dirty = true;
        updateTitle();
        setStatus(next == PracticeMode.DIRECT
                ? "Autocreación activada · se recreará el árbol virtual completo"
                : "Reconstrucción manual · " + removed
                    + " elementos automáticos vacíos retirados; tu trabajo se conserva");
    }

    /**
     * Materialize reference names in memory only. Enabling autocreation does
     * not create text content, move the typing caret or award progress.
     * Batches prevent large projects from blocking the JavaFX event thread.
     */
    private void autoCreateReferenceTree() {
        if (reference == null || referenceIndex == null
                || practiceMode != PracticeMode.DIRECT) return;
        VirtualTreeRecreator recreator = new VirtualTreeRecreator(
                workspace, referenceIndex.recreationNodes(), autoCreatedPaths::add);
        long generation = ++autoCreateGeneration;
        long request = referenceRequest;
        Path root = reference;
        Runnable batch = new Runnable() {
            @Override public void run() {
                if (generation != autoCreateGeneration
                        || request != referenceRequest
                        || !root.equals(reference)
                        || practiceMode != PracticeMode.DIRECT) return;
                VirtualTreeRecreator.Progress progress = recreator.nextBatch(150);
                if (!progress.finished()) {
                    javafx.application.Platform.runLater(this);
                } else {
                    if (progress.created() > 0) {
                        dirty = true;
                        updateTitle();
                        refreshReconstructionStatuses();
                        refreshMetrics();
                    }
                    tree.refreshStatuses();
                    setStatus("Autocreación virtual: " + progress.created()
                            + " elementos nuevos · " + progress.omitted()
                            + " omitidos · transcripción pendiente");
                }
            }
        };
        javafx.application.Platform.runLater(batch);
    }

    private void refreshReconstructionStatuses() {
        // Any structural/eligibility change invalidates incremental totals.
        // Normal keystrokes do not take this path unless completion changes.
        progressAccumulator = null;
        reconstructionStates = ReconstructionStatus.calculate(
                workspace, sessions, hashes, catalogue, progressScope, exclusions,
                includeComments, omitDependencies);
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
        }
    }

    private void refreshMetrics() {
        TypingEditor editor = selectedEditor();
        boolean hasEditor = editor != null;
        skipImportsButton.setDisable(!hasEditor || !selectedDependencyFile());
        skipImportsMenu.setDisable(!hasEditor || !selectedDependencyFile());
        skipImportsButton.setSelected(omitDependencies);
        skipImportsMenu.setSelected(omitDependencies);
        Tab active = tabs.getSelectionModel().getSelectedItem();
        if (!hasEditor && active != null
                && active.getContent() instanceof DependencyGraphPane map) {
            fileInfo.setText((map.isPartial() ? "Vista parcial · " : "")
                    + map.selectedNodeSummary());
            fileInfo.setTooltip(new Tooltip(
                    (map.isPartial() ? "IMPORTANTE: análisis parcial; no todas las "
                            + "relaciones caben en el mapa.\n" : "")
                    + map.nodeCount() + " carpetas · " + map.connectionCount()
                            + " dependencias explícitas\n"
                            + "Arrastra un círculo o su nombre para mover la carpeta. "
                            + "Los archivos de código también se pueden arrastrar. "
                            + "Arrastra el fondo para desplazarte."));
        } else if (!hasEditor) {
            fileInfo.setText("");
            fileInfo.setTooltip(null);
        } else {
            fileInfo.setTooltip(null);
            TypingSession typing = editor.session();
            fileInfo.setText(typing.correctCount() + " correctos · "
                    + editor.correctCpm() + " CPM");
        }
        if (catalogue == null) {
            projectInfo.setText("");
            projectInfo.setTooltip(null);
            fileProgress.setVisible(false);
            return;
        }
        if (progressAccumulator == null || !progressAccumulator.matches(
                catalogue, progressScope, progressFolder, exclusions,
                includeComments, omitDependencies)) {
            progressAccumulator = new ProgressAccumulator(catalogue, workspace,
                    sessions, hashes, progressScope, progressFolder, exclusions,
                    includeComments, omitDependencies);
        }
        ProgressSummary summary = progressAccumulator.summary();
        long total = summary.totalUnits();
        long resolved = summary.correctUnits();
        double ratio = summary.ratio();
        long count = summary.includedFiles();
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
        var selected = tree.selectedExplorerItem();
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
        tree.setProgressFocusFolder(progressFolder);
        if (onlySelectedFolder != null) {
            syncingFolderScope = true;
            try {
                onlySelectedFolder.setSelected(!progressFolder.isEmpty());
                onlySelectedFolder.setTooltip(new Tooltip(progressFolder.isEmpty()
                        ? "Progreso: todo el proyecto. Selecciona una carpeta para activar Solo."
                        : "Solo: " + progressFolder
                                + "\nLas demás carpetas se atenúan, pero siguen accesibles."
                                + "\nSelecciona otra carpeta para cambiar el ámbito. Desmarca para volver al proyecto completo."));
                onlySelectedFolder.setDisable(progressFolder.isEmpty()
                        && !tree.isFolderPath(tree.selectedExplorerItem() == null
                            ? null : tree.selectedExplorerItem().getValue()));
            } finally {
                syncingFolderScope = false;
            }
        }
        refreshMetrics();
        setStatus(progressFolder.isEmpty()
                ? "Progreso: todo el proyecto"
                : "Progreso: " + progressFolder);
    }

    /** One reversible policy for every open and saved typing session. */
    private void setIncludeComments(boolean enabled) {
        if (includeComments == enabled) return;
        includeComments = enabled;
        java.util.prefs.Preferences.userNodeForPackage(CodeTraceApp.class)
                .putBoolean("practice.includeComments", enabled);
        sessions.forEach((path, session) -> session.setIncludeComments(enabled, path));
        for (Tab tab : tabs.getTabs()) {
            TypingEditor editor = editorIn(tab);
            if (editor != null) editor.refreshCommentPolicy();
        }
        refreshReconstructionStatuses();
        refreshMetrics();
        setStatus(enabled
                ? "Los comentarios vuelven a formar parte de la práctica."
                : "Comentarios visibles, pero excluidos de la mecanografía.");
    }

    /** Persistent filter gallery: checkboxes do NOT close after each click. */
    private void installProgressFilters() {
        Label contentCaption = new Label("CONTENIDO EVALUADO");
        contentCaption.getStyleClass().add("palette-caption");
        VBox options = new VBox(9, contentCaption);
        options.getStyleClass().add("scope-options");
        addFilterOption(options, "Código fuente", ProgressScope.Category.SOURCE);
        addFilterOption(options, "Markdown", ProgressScope.Category.DOCUMENTATION);
        Label writingCaption = new Label("AYUDAS DE ESCRITURA");
        writingCaption.getStyleClass().add("palette-caption");
        skipImportsButton.setText("Omitir dependencias de código");
        autoCreateCheck = com.mmdev.codetrace.ui.AppControls.checkBox("Autocrear archivos y carpetas");
        autoCreateCheck.setTooltip(new Tooltip(
                "Al activar, recrea virtualmente las carpetas y los archivos de referencia "
                + "(excepto cachés y compilados). Al desactivar, se retiran "
                + "solo los elementos automáticos vacíos; no se borra tu trabajo."));
        autoCreateCheck.setSelected(practiceMode == PracticeMode.DIRECT);
        autoCreateCheck.setDisable(reference == null);
        autoCreateCheck.setOnAction(event -> setPracticeMode(
                autoCreateCheck.isSelected() ? PracticeMode.DIRECT
                        : PracticeMode.RECONSTRUCTION));
        includeCommentsCheck = com.mmdev.codetrace.ui.AppControls
                .checkBox("Practicar comentarios");
        includeCommentsCheck.setSelected(includeComments);
        includeCommentsCheck.setTooltip(new Tooltip(
                "Si se desactiva, los comentarios permanecen visibles, pero "
                + "no se deben mecanografiar ni cuentan para el progreso. "
                + "Se aplica a todos los lenguajes reconocidos."));
        includeCommentsCheck.setOnAction(event ->
                setIncludeComments(includeCommentsCheck.isSelected()));
        options.getChildren().addAll(new Separator(Orientation.HORIZONTAL),
                writingCaption, autoCreateCheck, includeCommentsCheck,
                turboToggle, skipImportsButton);
        Label exclusionHint = new Label(
                "Autocrear marca los archivos de práctica como creados (amarillo); "
                + "solo terminan (verde) al transcribirlos. Los filtros de "
                + "contenido deciden qué se contabiliza.");
        exclusionHint.getStyleClass().add("scope-exclusion-hint");
        exclusionHint.setWrapText(true);
        options.getChildren().add(exclusionHint);

        CustomMenuItem panel = new CustomMenuItem(options, false);
        panel.setHideOnClick(false);
        progressFilter.getItems().setAll(panel);
    }

    private void addFilterOption(VBox parent, String label, ProgressScope.Category category) {
        CheckBox control = com.mmdev.codetrace.ui.AppControls.checkBox(label);
        control.setSelected(progressScope.includes(category));
        control.setOnAction(event -> {
            progressScope = progressScope.with(category, control.isSelected());
            progressScope.save();
            refreshReconstructionStatuses();
            refreshMetrics();
        });
        parent.getChildren().add(control);
    }

     private boolean selectedDependencyFile() {
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        return selected != null && DependencyDeclarationRules.supports(selected.getText());
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
        // Read before confirmation: invalid archives never discard active work.
        // Never let an older load replace a newer request while a chooser is open.
        sessionLoader.cancel();
        FileChooser chooser = sessionChooser("Abrir proyecto CodeTrace (.ctrace)");
        java.io.File selected = chooser.showOpenDialog(stage);
        if (selected == null) return;

        openSession(selected.toPath());
    }

    private void openSession(Path selectedArchive) {
        // Shared by the File menu and the Windows .ctrace association.
        sessionLoader.cancel();
        Path archive = selectedArchive.toAbsolutePath().normalize();
        long request = referenceRequest;
        setStatus("Abriendo proyecto " + archive.getFileName() + "...");
        sessionLoader.submit(() -> SessionArchive.load(archive), state -> {
            if (request != referenceRequest) return;
            Path restoredReference = state.referenceRoot();
            if (!Files.isDirectory(restoredReference)) {
                DirectoryChooser folder = new DirectoryChooser();
                folder.setTitle("Buscar carpeta de referencia");
                java.io.File replacement = folder.showDialog(stage);
                if (replacement == null) {
                    setStatus("Restauración cancelada: se conserva el proyecto actual.");
                    return;
                }
                restoredReference = replacement.toPath().toAbsolutePath().normalize();
            }
            // The user may have typed while the session was loading.
            if (!confirmDiscard()) {
                setStatus("Restauración cancelada: se conserva el proyecto actual.");
                return;
            }
            applyProjectState(ProjectActivation.resumed(
                    restoredReference, archive, state), true);
        }, error -> {
            if (request == referenceRequest)
                showFailure("No se pudo abrir el proyecto", error.getMessage());
        }, "codetrace-session-loader");
    }

    private boolean saveSession(boolean forceAs) {
        if (reference == null) {
            setStatus("Abre una referencia antes de guardar.");
            return false;
        }
        Path destination = sessionFile;
        if (destination == null || forceAs) {
            FileChooser chooser = sessionChooser("Guardar proyecto CodeTrace (.ctrace)");
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
                    reference, workspace, sessions, hashes, practiceMode,
                    graphLayouts));
            sessionFile = destination;
            dirty = false;
            updateTitle();
            setStatus("Proyecto guardado: " + destination.getFileName());
            return true;
        } catch (IOException failure) {
            showFailure("No se pudo guardar el proyecto", failure.getMessage());
            return false;
        }
    }

    private void indexReference() {
        if (reference == null) return;
        Path root = reference;
        ReferenceCatalogue.Snapshot previous = catalogue;
        catalogue = null;
        refreshReconstructionStatuses();
        refreshMetrics();

        catalogueScanner.scan(root, updated -> {
            if (!root.equals(reference)) return;
            catalogue = updated;
            if (previous != null && !previous.files().equals(updated.files())) {
                if (!graphLayouts.isEmpty()) {
                    graphLayouts.clear();
                    dirty = true;
                    updateTitle();
                }
                for (Tab tab : List.copyOf(tabs.getTabs())) {
                    if (tab.getContent() instanceof DependencyGraphPane)
                        closeTab(tab);
                }
                setStatus("Cambió la referencia: distribuciones del mapa restablecidas");
            }
            refreshReconstructionStatuses();
            refreshMetrics();
        }, error -> {
            if (root.equals(reference))
                setStatus("No se pudo analizar la referencia.");
        });
    }

    private FileChooser sessionChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Proyecto CodeTrace (*.ctrace)", "*.ctrace"));
        // Prefer the previously used project folder. On a first launch,
        // Windows otherwise opens "Este equipo" and enumerates mounted drives
        // and cloud providers before its native chooser becomes responsive.
        Path home = Path.of(System.getProperty("user.home", "."));
        Path downloads = home.resolve("Downloads");
        Path lastProjectFolder = sessionFile == null ? null : sessionFile.getParent();
        Path preferred = lastProjectFolder != null && Files.isDirectory(lastProjectFolder)
                ? lastProjectFolder : Files.isDirectory(downloads) ? downloads : home;
        if (Files.isDirectory(preferred))
            chooser.setInitialDirectory(preferred.toFile());
        return chooser;
    }

    private boolean confirmDiscard() {
        if (!dirty) {
            return true;
        }
        ButtonType answer = ProductDialogs.confirm(stage, "Proyecto modificado",
                "¿Guardar los cambios del proyecto antes de continuar?",
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
        if (selected != null) {
            if (selected.getContent() instanceof ReferenceMediaPane media)
                media.setZoomPercent(percent);
            else if (selected.getContent() instanceof DependencyGraphPane graph)
                graph.setZoomPercent(percent);
        }
    }

    private void updateContextualZoom() {
        if (zoom == null || mediaZoom == null) return;
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        boolean graph = selected != null
                && selected.getContent() instanceof DependencyGraphPane;
        boolean visual = graph || (selected != null
                && selected.getContent() instanceof ReferenceMediaPane media
                && media.supportsZoom());
        mediaZoom.setGraphMode(graph);
        zoom.setVisible(!visual && !concentration);
        zoom.setManaged(!visual && !concentration);
        mediaZoom.setVisible(visual && !concentration);
        mediaZoom.setManaged(visual && !concentration);
        if (visual) {
            if (graph)
                mediaZoom.setValue(((DependencyGraphPane) selected.getContent())
                        .getZoomPercent());
            else mediaZoom.setValue(((ReferenceMediaPane) selected.getContent())
                    .getZoomPercent());
        }
    }

    private void setEditorZoom(int size) {
        if (size == settings.fontSize()) {
            return;
        }
        settings = new AppSettings(settings.fontFamily(), size,
                settings.showExplorer(), settings.keyboardLayout(), settings.typingSounds(),
                settings.templateOpacity(), settings.turboMode(),
                settings.completionSound(), settings.completionDialog(),
                settings.readOnlyNotice(), settings.graphLayoutMode());
        settings.save();
        for (Tab tab : tabs.getTabs()) {
            if (tab.getContent() instanceof TypingEditor editor) {
                editor.setFontSettings(settings.fontFamily(), size);
            } else if (tab.getContent() instanceof MarkdownPracticePane markdown) {
                markdown.editor().setFontSettings(settings.fontFamily(), size);
            } else if (tab.getContent() instanceof ReferenceTextPane referenceText) {
                referenceText.setFontSettings(settings.fontFamily(), size);
            }
        }
    }

    private void applySettings(AppSettings next) {
        var previousGraphMode = settings.graphLayoutMode();
        settings = new AppSettings(next.fontFamily(), next.fontSize(),
                next.showExplorer(), settings.keyboardLayout(), next.typingSounds(),
                next.templateOpacity(), next.turboMode(), next.completionSound(),
                next.completionDialog(), next.readOnlyNotice(), next.graphLayoutMode());
        settings.save();
        consolePane.resetExecutionAcknowledgement();
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
            } else if (tab.getContent() instanceof ReferenceTextPane referenceText) {
                referenceText.setFontSettings(next.fontFamily(), next.fontSize());
            } else if (tab.getContent() instanceof DependencyGraphPane graph
                    && previousGraphMode != next.graphLayoutMode()) {
                graph.setLayoutMode(next.graphLayoutMode());
            }
        }
        refreshMetrics();
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

    /**
     * Contextual status follows the selected tab, never the last tab opened.
     * Progress and stopwatch are independent components in the footer.
     */
    private void refreshContextStatus() {
        statusReset.stop();
        Tab selected = tabs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            // A reference can already be active while its disk index and first
            // visible rows are still pending. "Proyecto listo" must not win
            // this race against the explorer's loading state.
            status.setText(tree.isBusy() ? "Preparando el explorador..."
                    : StatusBarText.empty(reference != null));
            return;
        }
        if (selected.getContent() instanceof DependencyGraphPane graph) {
            status.setText(StatusBarText.graph(graph.folder(), graph.getSelectedNode()));
            return;
        }
        String registered = documents.pathOf(selected);
        String name = registered == null || registered.startsWith("consulta://")
                ? selected.getText() : registered;
        if (editorIn(selected) != null) {
            status.setText(StatusBarText.practice(name));
        } else if (selected.getContent() instanceof ReferenceMediaPane) {
            status.setText(StatusBarText.preview(name));
        } else {
            status.setText(StatusBarText.consultation(name));
        }
    }

    /**
     * Action notifications expire after six seconds, and navigation replaces
     * them immediately. Thus a closed file cannot leave a stale status behind.
     */
    private void setStatus(String value) {
        if (value == null || value.isBlank()) {
            refreshContextStatus();
            return;
        }
        status.setText(value);
        statusReset.playFromStart();
    }
}
