package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.workspace.VirtualWorkspace;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private Path referenceRoot;
    private String clipboard;
    private boolean cut;
    private Consumer<String> onProgressFolder = folder -> {};
    private Consumer<Boolean> onBusyChange = busy -> {};
    private ReferenceTreeIndex index;
    private long indexGeneration;
    private boolean loading;
    private boolean expanding;
    private Timeline expansion;

    public ReferenceTree(Consumer<Path> onOpen, Consumer<Change> onChange,
                         Consumer<String> onStatus) {
        this.onOpen = onOpen;
        this.onChange = onChange;
        this.onStatus = onStatus;
        setShowRoot(true);
        setCellFactory(view -> new TreeCell<>() {
            @Override
            protected void updateItem(Path path, boolean empty) {
                super.updateItem(path, empty);
                if (empty || path == null || referenceRoot == null) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                    setContextMenu(null);
                    return;
                }
                String relative = relative(path);
                boolean created = workspace.exists(relative);
                long fileCount = isFolder(path) && index != null
                        ? index.recursiveFiles(relative) : -1L;
                // Keep the icon in a dedicated cell graphic, not in the
                // TreeCell's overrun-prone text layout. Long folder names can
                // scroll horizontally without turning the icon into "...".
                setText(null);
                Label badge = new Label(created ? "✓" : "○");
                badge.getStyleClass().addAll("virtual-state",
                        created ? "virtual-created" : "virtual-pending");
                badge.setTooltip(new Tooltip(created
                        ? "Creado en el proyecto virtual" : "Pendiente de recrear"));
                Label nameLabel = new Label(name(path));
                nameLabel.getStyleClass().add("reference-name");
                nameLabel.setTextFill(created ? Color.web("#263747")
                        : Color.web("#667784"));
                nameLabel.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                HBox graphic = new HBox(6,
                        FileTypeIcons.forPath(path, isFolder(path)), nameLabel, badge);
                graphic.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                graphic.getStyleClass().add("reference-tree-row");
                if (fileCount >= 0) {
                    Label total = new Label("(" + fileCount
                            + (fileCount == 1 ? " archivo)" : " archivos)"));
                    total.getStyleClass().add("reference-file-count");
                    total.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                    graphic.getChildren().add(total);
                }
                graphic.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                setGraphic(graphic);
                setGraphicTextGap(0);
                String status = created
                        ? "Creado en el proyecto virtual" : "Pendiente de recrear";
                if (fileCount >= 0) {
                    status += "\n" + fileCount + (fileCount == 1
                            ? " archivo de referencia, incluidas subcarpetas"
                            : " archivos de referencia, incluidas subcarpetas");
                }
                setTooltip(new Tooltip(status));
                setContextMenu(contextFor(path));
            }
        });
        setOnMouseClicked(event -> {
            TreeItem<Path> selected = getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && selected != null
                    && selected.getValue() != null && !isFolder(selected.getValue())) {
                onOpen.accept(selected.getValue());
            }
        });
    }

    public void setOnProgressFolder(Consumer<String> listener) {
        onProgressFolder = listener == null ? folder -> {} : listener;
    }

    public void setOnBusyChange(Consumer<Boolean> listener) {
        onBusyChange = listener == null ? busy -> {} : listener;
        onBusyChange.accept(loading || expanding);
    }

    private void notifyBusy() {
        onBusyChange.accept(loading || expanding);
    }

    public boolean isBusy() {
        return loading || expanding;
    }

    /** Root's immediate children stay visible. Cancel any pending expansion. */
    public void collapseAll() {
        cancelExpansion();
        TreeItem<Path> root = getRoot();
        if (root == null) return;
        collapseExpanded(root);
        root.setExpanded(true);
    }

    private void collapseExpanded(TreeItem<Path> node) {
        if (!node.isExpanded()) return;
        for (TreeItem<Path> child : node.getChildren()) collapseExpanded(child);
        node.setExpanded(false);
    }

    /**
     * Expand a small batch every pulse. All file access was already performed
     * off-thread by ReferenceTreeIndex. The JavaFX thread only changes TreeItems.
     * The user can stop expansion immediately by choosing collapse.
     */
    public void expandAll() {
        cancelExpansion();
        if (getRoot() == null) return;
        getRoot().setExpanded(true);
        ArrayDeque<TreeItem<Path>> pending = new ArrayDeque<>();
        pending.add(getRoot());
        expanding = true;
        notifyBusy();
        expansion = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            int processed = 0;
            while (!pending.isEmpty() && processed++ < 18) {
                TreeItem<Path> node = pending.removeFirst();
                if (!node.isLeaf()) {
                    node.setExpanded(true);
                    for (TreeItem<Path> child : node.getChildren()) {
                        if (!child.isLeaf()) pending.addLast(child);
                    }
                }
            }
            if (pending.isEmpty()) cancelExpansion();
        }));
        expansion.setCycleCount(Animation.INDEFINITE);
        expansion.play();
    }

    private void cancelExpansion() {
        if (expansion != null) {
            expansion.stop();
            expansion = null;
        }
        expanding = false;
        notifyBusy();
    }

    private void scanReference() {
        if (referenceRoot == null) return;
        cancelExpansion();
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
        job.setOnSucceeded(event -> {
            if (generation != indexGeneration || !source.equals(referenceRoot)) return;
            index = job.getValue();
            loading = false;
            refreshTree();
            notifyBusy();
        });
        job.setOnFailed(event -> {
            if (generation != indexGeneration) return;
            loading = false;
            notifyBusy();
            onStatus.accept("No se pudo analizar el árbol de archivos.");
        });
        Thread worker = new Thread(job, "codetrace-tree-index");
        worker.setDaemon(true);
        worker.start();
    }

    /** Called by the explicit Refresh action, not on every keystroke. */
    public void reloadReference() {
        index = null;
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
        referenceRoot = folder;
        index = null;
        refreshTree();
        scanReference();
    }

    public void refreshTree() {
        if (referenceRoot == null) {
            return;
        }
        Set<Path> expanded = new HashSet<>();
        collectExpanded(getRoot(), expanded);
        TreeItem<Path> node = item(referenceRoot);
        setRoot(node);
        node.setExpanded(true);
        restoreExpanded(node, expanded);
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
                    for (VirtualWorkspace.Entry entry : workspace.entries()) {
                        if (!entry.path().isEmpty() && parentOf(entry.path()).equals(parent)) {
                            children.add(referenceRoot.resolve(entry.path()));
                        }
                    }
                    super.getChildren().setAll(children.stream()
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
        boolean created = workspace.exists(relative);
        boolean folder = isFolder(path);
        List<MenuItem> actions = new ArrayList<>();

        if (folder) {
            if (!created && !relative.isEmpty()) {
                MenuItem createThis = new MenuItem("Crear esta carpeta");
                createThis.setOnAction(event -> recreateKnown(relative, true));
                actions.add(createThis);
                actions.add(new javafx.scene.control.SeparatorMenuItem());
            }
            MenuItem newFolder = new MenuItem("Crear subcarpeta...");
            newFolder.setOnAction(event -> create(relative, true));
            MenuItem newFile = new MenuItem("Crear archivo...");
            newFile.setOnAction(event -> create(relative, false));
            actions.addAll(List.of(newFolder, newFile));
            actions.add(new javafx.scene.control.SeparatorMenuItem());
            MenuItem scope = new MenuItem(relative.isEmpty()
                    ? "Progreso: todo el proyecto"
                    : "Progreso: solo esta carpeta");
            scope.setOnAction(event -> onProgressFolder.accept(relative));
            actions.add(scope);
        } else {
            if (!created) {
                MenuItem createThis = new MenuItem("Crear este archivo");
                createThis.setOnAction(event -> recreateKnown(relative, false));
                actions.add(createThis);
                actions.add(new javafx.scene.control.SeparatorMenuItem());
            }
            MenuItem viewSource = new MenuItem("Mostrar en el Explorador");
            viewSource.setOnAction(event -> revealInFileExplorer(path));
            actions.add(viewSource);
        }
        // Files already created are a completed reconstruction milestone.
        // Destructive operations (delete, cut, rename) do not belong here.
        return new ContextMenu(actions.toArray(MenuItem[]::new));
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
                    new ProcessBuilder("explorer.exe",
                            "/select," + file).start();
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
        boolean validReference = index == null
                ? (directory ? Files.isDirectory(referenceRoot.resolve(relative))
                        : Files.isRegularFile(referenceRoot.resolve(relative)))
                : (directory ? index.hasDirectory(relative) : index.hasFile(relative));
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
