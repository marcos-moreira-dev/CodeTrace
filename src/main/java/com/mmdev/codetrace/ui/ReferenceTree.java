package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.workspace.VirtualWorkspace;
import com.mmdev.codetrace.workspace.VirtualChildrenIndex;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.reference.PerformanceProbe;
import com.mmdev.codetrace.reference.ExplorerMenuPolicy;
import com.mmdev.codetrace.reference.TreeNameFilter;
import com.mmdev.codetrace.reference.FolderProgressFocus;
import javafx.animation.PauseTransition;
import com.mmdev.codetrace.reference.PracticeEligibility;
import com.mmdev.codetrace.practice.ReconstructionStatus.State;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.control.CheckMenuItem;
import javafx.concurrent.Task;
import javafx.util.Duration;
import java.io.IOException;
import java.awt.Desktop;
import java.util.Locale;
import javafx.application.Platform;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

/**
 * A tree combining read-only reference entries with the in-memory workspace.
 * Items absent from the virtual workspace are shown as uncreated references.
 */
public final class ReferenceTree extends TreeView<Path> {
    public enum Operation { CREATE, DELETE, MOVE, COPY }
    public record Change(Operation operation, String source, String target) { }

    private final Consumer<Path> onOpen;
    private final Consumer<Change> onChange;
    private final Consumer<String> onStatus;
    private VirtualWorkspace workspace = new VirtualWorkspace();
    private VirtualChildrenIndex virtualChildren = VirtualChildrenIndex.snapshot(workspace);
    private Path referenceRoot;
    private String clipboard;
    private boolean cut;
    private Consumer<String> onProgressFolder = folder -> {};
    private Consumer<Path> onDependencyGraph = ignored -> {};
    private Predicate<String> excluded = folder -> false;
    private Predicate<String> explicitlyExcluded = folder -> false;
    private BiConsumer<String, Boolean> onExcludedChanged = (folder, value) -> {};
    private Consumer<Boolean> onBusyChange = busy -> {};
    private Consumer<ReferenceTreeIndex> onIndexReady = ignored -> {};
    private ReferenceTreeIndex index;
    private Function<String, State> statusLookup = ignored -> State.PENDING;
    private String progressFocusFolder = "";
    private long indexGeneration;
    private Task<ReferenceTreeIndex> indexTask;
    private boolean loading;
    /** Expansion requested while the immutable disk index is still building. */
    private boolean pendingExpandAll;
    private final BatchedTreeExpander expander;
    private final VirtualExplorerView projectedView = new VirtualExplorerView(this);
    private Task<ExplorerRows.Result> projectionTask;
    private long projectionGeneration;
    private boolean projectionBusy, projectionVisible;
    private Set<String> logicalExpansion = Set.of();
    private Runnable projectedSelectionChanged = () -> {};
    private String nameFilter = "";
    private Set<String> filteredPaths = Set.of();
    private long filterGeneration;
    private Task<Set<String>> nameSearchTask;
    private final PauseTransition searchDebounce = new PauseTransition(Duration.millis(160));

    public ReferenceTree(Consumer<Path> onOpen, Consumer<Change> onChange,
                         Consumer<String> onStatus) {
        this.onOpen = onOpen;
        this.onChange = onChange;
        this.onStatus = onStatus;
        this.expander = new BatchedTreeExpander(onStatus, this::notifyBusy);
        projectedView.setVisible(false);
        projectedView.setManaged(false);
        setShowRoot(true);
        setCellFactory(view -> new TreeCell<>() {
            // TreeView virtualizes cells, so each cell is reused on scroll.
            // Construct its graphics and tooltip ONCE rather than allocating
            // a fresh HBox, Labels, bindings and ContextMenu per visible row.
            private final HBox row = new HBox(6);
            private final Label nameLabel = new Label();
            private final Label countLabel = new Label();
            private final Tooltip rowTooltip = new Tooltip();
            private boolean rowExcluded;
            private boolean rowCreated;

            {
                row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                row.getStyleClass().add("reference-tree-row");
                row.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                nameLabel.getStyleClass().add("reference-name");
                nameLabel.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                countLabel.getStyleClass().add("reference-file-count");
                countLabel.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                selectedProperty().addListener((obs, previous, selected) ->
                        refreshTextColors());
                // Context menus can be expensive: create one only on an
                // actual right-click, never for every row during scrolling.
                setOnContextMenuRequested(event -> {
                    Path current = getItem();
                    if (isEmpty() || current == null || referenceRoot == null) return;
                    ContextMenu menu = contextFor(current);
                    if (menu.getItems().isEmpty()) return;
                    menu.show(this, event.getScreenX(), event.getScreenY());
                    event.consume();
                });
            }

            private void refreshTextColors() {
                nameLabel.setTextFill(isSelected() ? Color.web("#203748")
                        : rowExcluded ? Color.web("#758997")
                        : rowCreated ? Color.web("#263747")
                        : Color.web("#667784"));
                countLabel.setTextFill(isSelected()
                        ? Color.web("#203748") : Color.web("#637889"));
            }

            @Override
            protected void updateItem(Path path, boolean empty) {
                super.updateItem(path, empty);
                if (empty || path == null || referenceRoot == null) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                    getStyleClass().remove("reference-outside-scope");
                    return;
                }
                String relative = relative(path);
                boolean folder = isFolder(path);
                boolean isExcluded = excluded.test(relative);
                boolean outsideScope = FolderProgressFocus.outside(progressFocusFolder, relative);
                getStyleClass().remove("reference-outside-scope");
                if (outsideScope) getStyleClass().add("reference-outside-scope");
                boolean practiceable = folder
                        ? (index == null || index.hasWorkableDescendant(relative))
                        : (index != null ? index.isWorkable(relative)
                                : PracticeEligibility.supports(path));
                State state = practiceable ? statusLookup.apply(relative)
                        : State.NOT_APPLICABLE;
                rowExcluded = isExcluded;
                rowCreated = state == State.CREATED || state == State.COMPLETED;
                long fileCount = folder && index != null
                        ? index.recursiveFiles(relative) : -1L;
                setText(null);
                nameLabel.setText(name(path));
                nameLabel.getStyleClass().remove("reference-match-name");
                nameLabel.getStyleClass().remove("reference-pending-name");
                if (!nameFilter.isBlank() && TreeNameFilter.fold(name(path))
                        .contains(TreeNameFilter.fold(nameFilter))) {
                    nameLabel.getStyleClass().add("reference-match-name");
                }
                if (state == State.PENDING)
                    nameLabel.getStyleClass().add("reference-pending-name");
                refreshTextColors();

                row.getChildren().clear();
                row.getChildren().add(FileTypeIcons.forPath(path, folder));
                if (state != State.NOT_APPLICABLE)
                    row.getChildren().add(ReconstructionStateIcons.forState(state));
                if (folder && isExcluded) {
                    FontIcon excludedIcon = new FontIcon("fth-eye-off");
                    excludedIcon.setIconSize(14);
                    excludedIcon.setIconColor(Color.web("#7A8B98"));
                    row.getChildren().add(excludedIcon);
                }
                row.getChildren().add(nameLabel);
                if (fileCount >= 0) {
                    countLabel.setText("(" + fileCount
                            + (fileCount == 1 ? " archivo)" : " archivos)"));
                    row.getChildren().add(countLabel);
                }
                setGraphic(row);
                setGraphicTextGap(0);
                String status = switch (state) {
                    case NOT_APPLICABLE -> "No requiere práctica de mecanografía";
                    case PENDING -> "Pendiente de recrear";
                    case CREATED -> "Creado virtualmente · transcripción pendiente";
                    case COMPLETED -> "Ejercicio terminado";
                };
                if (isExcluded) status += "\nExcluida del progreso; sigue disponible para consulta";
                if (outsideScope) status += "\nFuera del ámbito Solo: " + progressFocusFolder
                        + "\nSigue disponible. Selecciona otra carpeta para cambiar el ámbito "
                        + "o desmarca Solo para volver a todo el proyecto.";
                if (fileCount >= 0) {
                    status += "\n" + fileCount + (fileCount == 1
                            ? " archivo de referencia, incluidas subcarpetas"
                            : " archivos de referencia, incluidas subcarpetas");
                }
                rowTooltip.setText(status);
                setTooltip(rowTooltip);
            }
        });
        setOnMouseClicked(event -> {
            TreeItem<Path> selected = getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && selected != null
                    && selected.getValue() != null && !isFolder(selected.getValue())) {
                onOpen.accept(selected.getValue());
            }
        });
        installFrameProbe();
    }

    /** Diagnostics only: capture JavaFX rendering stalls without inspecting files. */
    private void installFrameProbe() {
        if (!Boolean.getBoolean("codetrace.profile")) return;
        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            private long previousPulse;
            private int reported;
            @Override public void handle(long now) {
                var scene = getScene();
                if (scene == null || scene.getWindow() == null
                        || !scene.getWindow().isFocused()) {
                    previousPulse = 0;
                    return;
                }
                if (previousPulse > 0 && reported < 30) {
                    double gapMs = (now - previousPulse) / 1_000_000.0;
                    if (gapMs >= 120) {
                        reported++;
                        PerformanceProbe.fxFrameGap(gapMs,
                                index == null ? 0 : index.fileCount());
                    }
                }
                previousPulse = now;
            }
        };
        sceneProperty().addListener((obs, oldScene, scene) -> {
            if (scene == null) timer.stop();
            else timer.start();
        });
        if (getScene() != null) timer.start();
    }

    public void setStatusLookup(Function<String, State> lookup) {
        statusLookup = java.util.Objects.requireNonNull(lookup);
        refreshStatuses();
    }

    public void refreshStatuses() {
        refresh();
        if (projectionVisible) projectedView.refresh();
    }

    public void setOnProgressFolder(Consumer<String> listener) {
        onProgressFolder = listener == null ? folder -> {} : listener;
    }

    /** Map requests originate from the folder that was right-clicked. */
    public void setOnDependencyGraph(Consumer<Path> listener) {
        onDependencyGraph = listener == null ? ignored -> {} : listener;
    }

    /**
     * Only de-emphasize the graphic of out-of-scope nodes. Do not disable the
     * TreeCell: users must remain free to browse, open files and switch folders.
     */
    public void setProgressFocusFolder(String relative) {
        String next = relative == null ? "" : relative;
        if (progressFocusFolder.equals(next)) return;
        progressFocusFolder = next;
        refreshStatuses();
    }

    public String progressFocusFolder() { return progressFocusFolder; }

    public void setExclusionHandlers(Predicate<String> excluded,
            Predicate<String> explicitlyExcluded,
            BiConsumer<String, Boolean> onChange) {
        this.excluded = excluded;
        this.explicitlyExcluded = explicitlyExcluded;
        this.onExcludedChanged = onChange;
        refreshStatuses();
    }

    public void setOnBusyChange(Consumer<Boolean> listener) {
        onBusyChange = listener == null ? busy -> {} : listener;
        onBusyChange.accept(loading || expander.isBusy() || projectionBusy);
    }

    public void setOnIndexReady(Consumer<ReferenceTreeIndex> callback) {
        onIndexReady = callback == null ? ignored -> {} : callback;
    }

    private void notifyBusy() {
        onBusyChange.accept(loading || expander.isBusy() || projectionBusy);
    }

    public boolean isBusy() {
        return loading || expander.isBusy() || projectionBusy;
    }

    /**
     * Collapse even a huge expanded tree in one structural update.
     *
     * <p>Walking every expanded TreeItem and calling setExpanded(false) used
     * to synchronously recalculate thousands of visible rows on the JavaFX
     * application thread, causing Windows to report "No responde". Instead
     * discard the materialized expansion subtree and create one fresh lazy
     * root. The immutable disk index is preserved, with zero disk I/O.</p>
     */
    public void collapseAll() {
        cancelProjection();
        hideProjection();
        pendingExpandAll = false;
        expander.cancel();
        TreeItem<Path> oldRoot = getRoot();
        if (oldRoot == null || referenceRoot == null) return;
        long started = PerformanceProbe.begin();
        TreeItem<Path> selected = getSelectionModel().getSelectedItem();
        Path selectedPath = selected == null ? null : selected.getValue();
        TreeItem<Path> freshRoot = item(referenceRoot);
        setRoot(freshRoot);
        freshRoot.setExpanded(true);
        // Preserve a visible first-level selection without rebuilding the
        // complete previously expanded tree.
        TreeItem<Path> visible = freshRoot;
        if (selectedPath != null && selectedPath.startsWith(referenceRoot)
                && !selectedPath.equals(referenceRoot)) {
            Path firstChild = referenceRoot.resolve(
                    referenceRoot.relativize(selectedPath).getName(0));
            for (TreeItem<Path> child : freshRoot.getChildren()) {
                if (firstChild.equals(child.getValue())) {
                    visible = child;
                    break;
                }
            }
        }
        getSelectionModel().select(visible);
        PerformanceProbe.finish("collapse-all", started, index == null ? 0 : index.recursiveFiles(""));
    }

    /**
     * Reset only the requested branch. Other expanded branches retain their
     * TreeItems, while descendants of the replaced node are released instead
     * of being visited one by one on the UI thread.
     */
    public void collapseRecursively(Path directory) {
        if (projectionVisible && directory != null && isFolder(directory)) {
            changeProjectedFolder(relative(directory), false, true);
            return;
        }
        pendingExpandAll = false;
        expander.cancel();
        // A branch operation changes the visible expansion independently.
        TreeItem<Path> clicked = findVisibleNode(getRoot(), directory);
        if (clicked == null || !isFolder(directory)) return;
        if (clicked == getRoot()) {
            collapseAll();
            return;
        }
        long started = PerformanceProbe.begin();
        TreeItem<Path> parent = clicked.getParent();
        if (parent == null) return;
        int slot = parent.getChildren().indexOf(clicked);
        if (slot < 0) return;
        TreeItem<Path> selected = getSelectionModel().getSelectedItem();
        boolean selectedInside = selected != null && selected.getValue() != null
                && selected.getValue().startsWith(directory);
        TreeItem<Path> replacement = item(directory);
        parent.getChildren().set(slot, replacement);
        if (selectedInside) getSelectionModel().select(replacement);
        PerformanceProbe.finish("collapse-branch", started, 1);
    }

    /**
     * Follow just the requested path, not every visible branch: O(depth ×
     * siblings) instead of O(all visible nodes) for contextual actions.
     */
    private static TreeItem<Path> findVisibleNode(TreeItem<Path> from, Path path) {
        if (from == null || path == null || !path.startsWith(from.getValue())) return null;
        TreeItem<Path> current = from;
        Path walk = from.getValue();
        for (Path segment : walk.relativize(path)) {
            if (!current.isExpanded()) return null;
            walk = walk.resolve(segment);
            TreeItem<Path> childMatch = null;
            for (TreeItem<Path> child : current.getChildren()) {
                if (walk.equals(child.getValue())) {
                    childMatch = child;
                    break;
                }
            }
            if (childMatch == null) return null;
            current = childMatch;
        }
        return current;
    }

    /**
     * Expand EVERY nested directory in the selected branch, not only direct
     * children. Small trees become visible immediately; larger trees expand in
     * short batches so JavaFX remains responsive. Never follows symlinks:
     * reference paths come from the indexed, read-only tree.
     */
    public void expandRecursively(Path directory) {
        if (projectionVisible && directory != null && isFolder(directory)) {
            changeProjectedFolder(relative(directory), true, true);
            return;
        }
        if (referenceRoot == null || directory == null || !isFolder(directory)) return;
        if (index == null || loading) {
            // A click during a cold scan is not a no-op. Once the index is
            // ready, reopen the intended tree instead of expanding empty nodes.
            pendingExpandAll = true;
            onStatus.accept("Indexando archivos; expansión programada al terminar.");
            return;
        }
        if (index.directoryCount() > 40 || index.fileCount() > 1_000) {
            // Large branch actions use the same logical projection as
            // Expandir todo. Keep other manually opened branches visible
            // without forcing JavaFX to materialize the requested subtree.
            String requested = relative(directory);
            Set<String> expand = new HashSet<>();
            expand.add("");
            Set<Path> alreadyOpen = new HashSet<>();
            collectExpanded(getRoot(), alreadyOpen);
            for (Path path : alreadyOpen) expand.add(relative(path));
            for (String path : ExplorerRows.allDirectories(index, virtualChildren))
                if (requested.isEmpty() || path.equals(requested)
                        || path.startsWith(requested + "/")) expand.add(path);
            String ancestor = requested;
            while (!ancestor.isEmpty()) {
                int slash = ancestor.lastIndexOf('/');
                ancestor = slash < 0 ? "" : ancestor.substring(0, slash);
                expand.add(ancestor);
            }
            projectRows(Set.copyOf(expand));
            return;
        }
        if (expander.isExpanding()) {
            onStatus.accept("La expansión de carpetas ya está en curso.");
            return;
        }
        TreeItem<Path> clicked = findVisibleNode(getRoot(), directory);
        if (clicked == null) {
            onStatus.accept("La carpeta no está visible en el explorador.");
            return;
        }
        boolean smallReference = index.directoryCount() <= 40
                && index.fileCount() <= 1_000;
        expander.expand(clicked, smallReference);
    }

    /** Expand logically, never by mutating thousands of JavaFX TreeItems. */
    public void expandAll() {
        if (referenceRoot == null) {
            onStatus.accept("Abre una referencia antes de expandir sus carpetas.");
        } else if (index == null || loading) {
            pendingExpandAll = true;
            onStatus.accept("Indexando; expansión programada.");
        } else if (!projectionBusy) {
            projectRows(ExplorerRows.allDirectories(index, virtualChildren));
        }
    }

    public javafx.scene.Node projectedExplorer() { return projectedView; }

    public TreeItem<Path> selectedExplorerItem() {
        if (!projectionVisible) return getSelectionModel().getSelectedItem();
        var row = projectedView.getSelectionModel().getSelectedItem();
        return row == null ? null : new TreeItem<>(absoluteProjectedPath(row.relative()));
    }

    public void setOnProjectedSelectionChanged(Runnable callback) {
        projectedSelectionChanged = callback == null ? () -> {} : callback;
    }

    void projectedSelectionChanged() { projectedSelectionChanged.run(); }
    Path absoluteProjectedPath(String path) {
        return path.isEmpty() ? referenceRoot : referenceRoot.resolve(path);
    }
    void openProjectedFile(String path) {
        Path file = absoluteProjectedPath(path);
        if (!isFolder(file)) onOpen.accept(file);
    }
    ContextMenu menuForProjectedPath(String path) {
        return contextFor(absoluteProjectedPath(path));
    }
    String projectedFileCount(String path) {
        long n = index == null ? 0 : index.recursiveFiles(path);
        return "(" + n + (n == 1 ? " archivo)" : " archivos)");
    }
    State projectedState(String path) {
        boolean folder = path.isEmpty() || isFolder(absoluteProjectedPath(path));
        boolean practiceable = folder
                ? (index == null || index.hasWorkableDescendant(path))
                : (index != null ? index.isWorkable(path)
                        : PracticeEligibility.supports(absoluteProjectedPath(path)));
        return practiceable ? statusLookup.apply(path) : State.NOT_APPLICABLE;
    }
    boolean projectedExcluded(String path) { return excluded.test(path); }
    boolean projectedOutsideScope(String path) {
        return FolderProgressFocus.outside(progressFocusFolder, path);
    }
    boolean projectedExpanded(String path) { return logicalExpansion.contains(path); }
    void toggleProjectedFolder(String path) {
        changeProjectedFolder(path, !logicalExpansion.contains(path), false);
    }
    private void changeProjectedFolder(String relative, boolean expand, boolean recursive) {
        if (!projectionVisible || index == null) return;
        var next = new HashSet<>(logicalExpansion);
        if (expand) {
            next.add(relative);
            if (recursive) for (String path :
                    ExplorerRows.allDirectories(index, virtualChildren))
                if (relative.isEmpty() || path.equals(relative) || path.startsWith(relative + "/"))
                    next.add(path);
        } else if (recursive) {
            next.removeIf(path -> path.equals(relative) || path.startsWith(relative + "/"));
        } else next.remove(relative);
        projectRows(Set.copyOf(next));
    }
    private void cancelProjection() {
        projectionGeneration++;
        if (projectionTask != null) projectionTask.cancel(true);
        projectionTask = null;
        projectionBusy = false;
        notifyBusy();
    }
    private void hideProjection() {
        if (!projectionVisible) return;
        projectionVisible = false;
        projectedView.setVisible(false);
        projectedView.setManaged(false);
        // Restore just a lazy root; never resurrect the thousands of TreeItems
        // that were intentionally discarded for mass expansion.
        if (getRoot() == null && referenceRoot != null) {
            TreeItem<Path> shallow = item(referenceRoot);
            setRoot(shallow);
            shallow.setExpanded(true);
        }
        setVisible(true);
        setManaged(true);
        projectedSelectionChanged.run();
    }

    /**
     * Immutable projection prepared off the FX thread, installed atomically.
     * ListView's VirtualFlow builds only the onscreen cells and does no disk I/O.
     */
    private void projectRows(Set<String> expanded) {
        if (referenceRoot == null || index == null) return;
        cancelProjection();
        long generation = projectionGeneration;
        Path root = referenceRoot;
        ReferenceTreeIndex snapshot = index;
        VirtualChildrenIndex virtual = virtualChildren;
        Set<String> filter = nameFilter.isBlank() ? Set.of() : filteredPaths;
        projectionBusy = true;
        notifyBusy();
        onStatus.accept("Preparando filas del explorador...");
        Task<ExplorerRows.Result> task = new Task<>() {
            @Override protected ExplorerRows.Result call() {
                return ExplorerRows.project(snapshot, virtual, expanded, filter);
            }
        };
        projectionTask = task;
        task.setOnSucceeded(event -> {
            if (projectionGeneration != generation || projectionTask != task
                    || !root.equals(referenceRoot) || snapshot != index) return;
            projectionTask = null;
            logicalExpansion = expanded;
            if (!projectionVisible) {
                setVisible(false);
                setManaged(false);
                // Free the old materialized TreeItems before showing the
                // virtual list. This prevents duplicate UI trees in memory.
                setRoot(null);
            }
            projectionVisible = true;
            projectedView.publish(task.getValue());
            projectedView.setVisible(true);
            projectedView.setManaged(true);
            projectionBusy = false;
            notifyBusy();
            projectedSelectionChanged.run();
            onStatus.accept("Explorador listo: " + task.getValue().rows().size()
                    + " filas" + (task.getValue().truncated() ? " (límite)" : "") + ".");
        });
        task.setOnFailed(event -> {
            if (generation != projectionGeneration) return;
            projectionTask = null;
            projectionBusy = false;
            notifyBusy();
            onStatus.accept("No se pudo preparar la vista del explorador.");
        });
        task.setOnCancelled(event -> {
            if (generation != projectionGeneration) return;
            projectionTask = null;
            projectionBusy = false;
            notifyBusy();
        });
        Thread thread = new Thread(task, "codetrace-logical-explorer");
        thread.setDaemon(true);
        thread.start();
    }

    private void scanReference() {
        cancelProjection();
        hideProjection();
        if (referenceRoot == null) return;
        expander.cancel();
        if (indexTask != null) indexTask.cancel(true);
        Path source = referenceRoot;
        long generation = ++indexGeneration;
        loading = true;
        notifyBusy();
        Task<ReferenceTreeIndex> job = new Task<>() {
            @Override
            protected ReferenceTreeIndex call() throws Exception {
                return ReferenceTreeIndex.scan(source);
            }
        };
        indexTask = job;
        job.setOnSucceeded(event -> {
            if (generation != indexGeneration || indexTask != job
                    || !source.equals(referenceRoot)) return;
            indexTask = null;
            index = job.getValue();
            loading = false;
            if (nameFilter.isBlank()) {
                refreshTree();
            } else {
                searchNames(nameFilter);
            }
            notifyBusy();
            onIndexReady.accept(index);
            if (pendingExpandAll && nameFilter.isBlank()) {
                pendingExpandAll = false;
                Platform.runLater(() -> {
                    if (source.equals(referenceRoot) && !loading) expandAll();
                });
            }
        });
        job.setOnFailed(event -> {
            if (generation != indexGeneration || indexTask != job) return;
            indexTask = null;
            loading = false;
            pendingExpandAll = false;
            notifyBusy();
            onStatus.accept("No se pudo analizar el árbol de archivos.");
        });
        job.setOnCancelled(event -> {
            if (generation != indexGeneration || indexTask != job) return;
            indexTask = null;
            loading = false;
            pendingExpandAll = false;
            notifyBusy();
        });
        Thread worker = new Thread(job, "codetrace-tree-index");
        worker.setDaemon(true);
        worker.start();
    }

    /** Called by the explicit Refresh action, not on every keystroke. */
    public void reloadReference() {
        pendingExpandAll = false;
        cancelNameSearch();
        index = null;
        filteredPaths = Set.of();
        refreshTree();
        scanReference();
    }

    public boolean isFolderPath(Path path) {
        return path != null && isFolder(path);
    }

    public String relativePath(Path path) {
        return relative(path);
    }

    public void setWorkspace(VirtualWorkspace newWorkspace) {
        workspace = newWorkspace;
        refreshTree();
    }

    public void open(Path folder) {
        pendingExpandAll = false;
        cancelNameSearch();
        referenceRoot = folder;
        index = null;
        filteredPaths = Set.of();
        refreshTree();
        scanReference();
    }

    private void cancelNameSearch() {
        filterGeneration++;
        searchDebounce.stop();
        Task<Set<String>> previous = nameSearchTask;
        nameSearchTask = null;
        if (previous != null) previous.cancel(true);
    }

    /** Stop indexing, searching and expansion when CodeTrace is closing. */
    public void cancelPendingWork() {
        cancelProjection();
        hideProjection();
        pendingExpandAll = false;
        indexGeneration++;
        Task<ReferenceTreeIndex> previous = indexTask;
        indexTask = null;
        if (previous != null) previous.cancel(true);
        cancelNameSearch();
        expander.cancel();
        loading = false;
        notifyBusy();
    }

    /** Search file/folder NAMES only; content searching is a separate service. */
    public void searchNames(String query) {
        cancelProjection();
        hideProjection();
        cancelNameSearch();
        nameFilter = query == null ? "" : query.strip();
        long generation = filterGeneration;
        if (nameFilter.isEmpty() || referenceRoot == null) {
            filteredPaths = Set.of();
            refreshTree();
            return;
        }
        ReferenceTreeIndex snapshot = index;
        if (snapshot == null || loading) {
            // The user may type before the cold disk scan finishes. The
            // index-ready handler will restart this exact filter, so avoid
            // scheduling a null-index background search or losing the text.
            filteredPaths = Set.of();
            onStatus.accept("Indexando referencia; la búsqueda comenzará al terminar.");
            return;
        }
        VirtualWorkspace currentWorkspace = workspace;
        Path root = referenceRoot;
        String requested = nameFilter;
        searchDebounce.setOnFinished(event -> {
            if (generation != filterGeneration || !root.equals(referenceRoot)) return;
            Task<Set<String>> search = new Task<>() {
                @Override protected Set<String> call() {
                    return TreeNameFilter.visible(snapshot, currentWorkspace, requested);
                }
            };
            nameSearchTask = search;
            search.setOnSucceeded(done -> {
                if (generation != filterGeneration || nameSearchTask != search
                        || !root.equals(referenceRoot)) return;
                nameSearchTask = null;
                filteredPaths = search.getValue();
                refreshTree();
                if (pendingExpandAll && !loading) {
                    pendingExpandAll = false;
                    Platform.runLater(() -> {
                        if (root.equals(referenceRoot) && !loading) expandAll();
                    });
                }
            });
            search.setOnFailed(done -> {
                if (generation != filterGeneration || nameSearchTask != search) return;
                nameSearchTask = null;
                onStatus.accept("No se pudo filtrar el explorador.");
            });
            search.setOnCancelled(done -> {
                if (generation == filterGeneration && nameSearchTask == search)
                    nameSearchTask = null;
            });
            Thread worker = new Thread(search, "codetrace-tree-name-search");
            worker.setDaemon(true);
            worker.start();
        });
        searchDebounce.playFromStart();
    }

    public void refreshTree() {
        cancelProjection();
        hideProjection();
        if (referenceRoot == null) {
            return;
        }
        // One O(n) index per model refresh, not an O(n) workspace scan for
        // every lazily expanded folder in a large reference tree.
        virtualChildren = VirtualChildrenIndex.snapshot(workspace);
        Set<Path> expanded = new HashSet<>();
        collectExpanded(getRoot(), expanded);
        TreeItem<Path> node = item(referenceRoot);
        setRoot(node);
        node.setExpanded(true);
        if (nameFilter.isBlank()) restoreExpanded(node, expanded);
        else {
            // Expand a bounded number of matching ancestor folders; a search
            // for a common letter must not freeze the FX thread.
            ArrayDeque<TreeItem<Path>> queue = new ArrayDeque<>();
            queue.add(node);
            int expandedCount = 0;
            while (!queue.isEmpty() && expandedCount < 160) {
                TreeItem<Path> current = queue.removeFirst();
                if (!current.isLeaf()) {
                    current.setExpanded(true);
                    expandedCount++;
                    for (TreeItem<Path> child : current.getChildren()) {
                        if (!child.isLeaf()) queue.addLast(child);
                    }
                }
            }
        }
        refresh();
    }

    private void collectExpanded(TreeItem<Path> node, Set<Path> expanded) {
        if (node == null || !node.isExpanded()) {
            return;
        }
        expanded.add(node.getValue());
        for (TreeItem<Path> child : node.getChildren()) {
            collectExpanded(child, expanded);
        }
    }

    private void restoreExpanded(TreeItem<Path> node, Set<Path> expanded) {
        if (!expanded.contains(node.getValue())) {
            return;
        }
        node.setExpanded(true);
        for (TreeItem<Path> child : node.getChildren()) {
            restoreExpanded(child, expanded);
        }
    }

    private TreeItem<Path> item(Path path) {
        return new TreeItem<>(path) {
            private boolean loaded;

            @Override
            public boolean isLeaf() {
                return !isFolder(getValue());
            }

            @Override
            public ObservableList<TreeItem<Path>> getChildren() {
                if (!loaded && !isLeaf()) {
                    loaded = true;
                    Set<Path> children = new LinkedHashSet<>();
                    String parent = relative(getValue());
                    if (index != null) {
                        for (String child : index.children(parent)) {
                            children.add(referenceRoot.resolve(child));
                        }
                    }
                    for (String child : virtualChildren.childrenOf(parent)) {
                        children.add(referenceRoot.resolve(child));
                    }
                    super.getChildren().setAll(children.stream()
                            .filter(child -> nameFilter.isBlank()
                                    || filteredPaths.contains(relative(child)))
                            .sorted(Comparator.comparing((Path child) -> !isFolder(child))
                                    .thenComparing(ReferenceTree::name,
                                            String.CASE_INSENSITIVE_ORDER))
                            .map(ReferenceTree.this::item).toList());
                }
                return super.getChildren();
            }
        };
    }

    private ContextMenu contextFor(Path path) {
        String relative = relative(path);
        boolean folder = isFolder(path);
        ExplorerMenuPolicy.Actions allowed = ExplorerMenuPolicy.forPath(
                relative, folder, index, virtualChildren, workspace);
        List<MenuItem> actions = new ArrayList<>();

        if (folder && index != null) {
            if (allowed.recursiveExpansion()) {
                MenuItem expandBranch = new MenuItem("Expandir todas las subcarpetas");
                expandBranch.setGraphic(new FontIcon("fth-maximize-2"));
                expandBranch.setOnAction(event -> expandRecursively(path));
                actions.add(expandBranch);
                MenuItem collapseBranch = new MenuItem("Colapsar todas las subcarpetas");
                collapseBranch.setGraphic(new FontIcon("fth-minimize-2"));
                collapseBranch.setOnAction(event -> collapseRecursively(path));
                actions.add(collapseBranch);
            }
            MenuItem dependencies = new MenuItem("Mapa de dependencias...");
            dependencies.setGraphic(new FontIcon("fth-map"));
            dependencies.setOnAction(event -> onDependencyGraph.accept(path));
            actions.add(dependencies);
            actions.add(new javafx.scene.control.SeparatorMenuItem());
            if (allowed.createThisFolder()) {
                MenuItem makeCurrent = new MenuItem("Crear esta carpeta");
                makeCurrent.setOnAction(event -> recreateKnown(relative, true));
                actions.add(makeCurrent);
            }
            if (allowed.createSubfolder()) {
                MenuItem newFolder = new MenuItem("Crear subcarpeta...");
                newFolder.setOnAction(event -> create(relative, true));
                actions.add(newFolder);
            }
            if (allowed.createFile()) {
                MenuItem newFile = new MenuItem("Crear archivo...");
                newFile.setOnAction(event -> create(relative, false));
                actions.add(newFile);
            }
            if (allowed.progressScope()) {
                if (!actions.isEmpty()) actions.add(new javafx.scene.control.SeparatorMenuItem());
                MenuItem scope = new MenuItem(relative.isEmpty()
                        ? "Progreso: todo el proyecto"
                        : "Progreso: solo esta carpeta");
                scope.setOnAction(event -> onProgressFolder.accept(relative));
                actions.add(scope);
                if (!relative.isEmpty()) {
                    CheckMenuItem excludedFolder = new CheckMenuItem(
                            "Excluir carpeta del progreso");
                    excludedFolder.setSelected(explicitlyExcluded.test(relative));
                    excludedFolder.setOnAction(event ->
                            onExcludedChanged.accept(relative, excludedFolder.isSelected()));
                    actions.add(excludedFolder);
                }
            }
        } else if (!folder) {
            if (allowed.createThisFile()) {
                MenuItem createThis = new MenuItem("Crear este archivo");
                createThis.setOnAction(event -> recreateKnown(relative, false));
                actions.add(createThis);
            }
            MenuItem reveal = new MenuItem("Mostrar en el Explorador");
            reveal.setOnAction(event -> revealInFileExplorer(path));
            actions.add(reveal);
        }

        // Avoid a dangling divider for folders with no typing exercises.
        while (!actions.isEmpty()
                && actions.getLast() instanceof javafx.scene.control.SeparatorMenuItem)
            actions.removeLast();
        return actions.isEmpty() ? null
                : new ContextMenu(actions.toArray(MenuItem[]::new));
    }

    /**
     * Reveal the ORIGINAL reference file in the operating-system explorer.
     * This action never opens, rewrites or materializes a virtual file.
     */
    private void revealInFileExplorer(Path selected) {
        Path file = selected.toAbsolutePath().normalize();
        if (referenceRoot == null
                || !file.startsWith(referenceRoot.toAbsolutePath().normalize())
                || !Files.isRegularFile(file)) {
            ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                    "Referencia no disponible",
                    "No se encontró el archivo original en la carpeta de referencia.");
            return;
        }
        Thread worker = new Thread(() -> {
            try {
                String system = System.getProperty("os.name", "")
                        .toLowerCase(Locale.ROOT);
                if (system.contains("win")) {
                    // Explorer treats /select, and the path as separate
                    // arguments. Combining them loses space-containing paths
                    // (e.g. the user's Documents folder) on Windows.
                    new ProcessBuilder(ExplorerReveal.windowsCommand(file)).start();
                } else if (system.contains("mac")) {
                    new ProcessBuilder("open", "-R", file.toString()).start();
                } else if (Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(file.getParent().toFile());
                } else {
                    new ProcessBuilder("xdg-open",
                            file.getParent().toString()).start();
                }
            } catch (IOException | RuntimeException problem) {
                Platform.runLater(() -> onStatus.accept(
                        "No se pudo abrir el Explorador: " + problem.getMessage()));
            }
        }, "codetrace-explorer-reveal");
        worker.setDaemon(true);
        worker.start();
    }

    private void create(String parent, boolean directory) {
        if (!workspace.exists(parent)) {
            missingParent(parent);
            return;
        }
        String value = prompt(directory ? "Crear subcarpeta" : "Crear archivo", "");
        if (value == null) return;
        if (!validName(value)) {
            ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                    "Nombre no válido", "Introduce un nombre de archivo o carpeta válido.");
            return;
        }
        recreateKnown(child(parent, value), directory);
    }

    /**
     * Rebuild precisely one real reference node. Explicit parent reconstruction
     * is required; this operation never creates ancestor directories implicitly.
     */
    private void recreateKnown(String relative, boolean directory) {
        String parent = parentOf(relative);
        if (!workspace.exists(parent)) {
            missingParent(parent);
            return;
        }
        if (workspace.exists(relative)) {
            ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                    "Elemento existente", "El elemento ya existe en el proyecto virtual: "
                            + relative);
            return;
        }
        boolean validReference = index != null
                && (directory ? index.hasDirectory(relative)
                    && index.hasWorkableDescendant(relative)
                    : index.isWorkable(relative));
        if (!validReference) {
            ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                    "Elemento fuera de la referencia",
                    "Solo puedes recrear archivos y carpetas que existan en "
                    + "el repositorio de práctica.\n\nElemento solicitado: " + relative);
            return;
        }
        try {
            if (directory) workspace.createDirectory(relative);
            else workspace.createFile(relative);
            changed(new Change(Operation.CREATE, "", relative));
            onStatus.accept((directory ? "Carpeta creada: " : "Archivo creado: ") + relative);
        } catch (IllegalArgumentException problem) {
            ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                    "No se pudo crear", problem.getMessage());
        }
    }

    private void missingParent(String relative) {
        ProductDialogs.error(getScene() == null ? null : getScene().getWindow(),
                "Primero crea la carpeta",
                "Esta carpeta todavía no existe en el proyecto virtual: "
                + relative + "\n\nHaz clic derecho sobre ella y selecciona "
                + "«Crear esta carpeta». Después podrás crear sus archivos y subcarpetas.");
    }

    private void changed(Change change) {
        onChange.accept(change);
        refreshTree();
    }

    private static String prompt(String title, String initialValue) {
        TextInputDialog dialog = new TextInputDialog(initialValue);
        dialog.setTitle(title);
        WindowBranding.apply(dialog);
        dialog.getDialogPane().getStylesheets().add(
                ReferenceTree.class.getResource("/css/codetrace.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("product-dialog");
        dialog.setHeaderText(null);
        dialog.setContentText("Nombre:");
        return dialog.showAndWait().orElse(null);
    }

    private boolean isFolder(Path path) {
        if (referenceRoot == null) {
            return false;
        }
        VirtualWorkspace.Entry entry = workspace.entry(relative(path));
        if (entry != null) {
            return entry.kind() == VirtualWorkspace.Kind.DIRECTORY;
        }
        if (index != null) return index.hasDirectory(relative(path));
        return Files.isDirectory(path) && !Files.isSymbolicLink(path);
    }

    private String relative(Path path) {
        String value = referenceRoot.relativize(path).toString().replace('\\', '/');
        return value.equals(".") ? "" : value;
    }

    private static boolean validName(String name) {
        return !name.isBlank() && !name.contains("/") && !name.contains("\\");
    }

    private static String child(String parent, String name) {
        return parent.isEmpty() ? name : parent + "/" + name;
    }

    private static String parentOf(String path) {
        int index = path.lastIndexOf('/');
        return index < 0 ? "" : path.substring(0, index);
    }

    private static String name(Path path) {
        Path filename = path.getFileName();
        return filename == null ? path.toString() : filename.toString();
    }
}
