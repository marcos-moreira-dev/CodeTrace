package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
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
                    setContextMenu(null);
                    return;
                }
                String relative = relative(path);
                boolean created = workspace.exists(relative);
                setText(name(path));
                setGraphic(FileTypeIcons.forPath(path, isFolder(path)));
                setTextFill(created ? Color.web("#263747") : Color.web("#85929C"));
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

    public void setWorkspace(VirtualWorkspace newWorkspace) {
        workspace = newWorkspace;
        refreshTree();
    }

    public void open(Path folder) {
        referenceRoot = folder;
        refreshTree();
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
                    if (Files.isDirectory(getValue()) && !Files.isSymbolicLink(getValue())) {
                        try (Stream<Path> stream = Files.list(getValue())) {
                            stream.forEach(children::add);
                        } catch (IOException | SecurityException error) {
                            onStatus.accept("No se pudo leer " + getValue().getFileName());
                        }
                    }
                    String parent = relative(getValue());
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
        List<MenuItem> actions = new ArrayList<>();
        if (created && isFolder(path)) {
            MenuItem folder = new MenuItem("Nueva carpeta...");
            folder.setOnAction(event -> create(relative, true));
            MenuItem file = new MenuItem("Nuevo archivo...");
            file.setOnAction(event -> create(relative, false));
            actions.addAll(List.of(folder, file));
            if (clipboard != null) {
                MenuItem paste = new MenuItem("Pegar");
                paste.setOnAction(event -> paste(relative));
                actions.add(paste);
            }
        }
        if (created && !relative.isEmpty()) {
            MenuItem rename = new MenuItem("Cambiar nombre...");
            rename.setOnAction(event -> rename(relative));
            MenuItem copy = new MenuItem("Copiar");
            copy.setOnAction(event -> {
                clipboard = relative;
                cut = false;
            });
            MenuItem move = new MenuItem("Cortar");
            move.setOnAction(event -> {
                clipboard = relative;
                cut = true;
            });
            MenuItem delete = new MenuItem("Eliminar");
            delete.setOnAction(event -> delete(relative));
            actions.addAll(List.of(rename, copy, move, delete));
        }
        return actions.isEmpty() ? null : new ContextMenu(actions.toArray(MenuItem[]::new));
    }

    private void create(String parent, boolean directory) {
        String value = prompt(directory ? "Nueva carpeta" : "Nuevo archivo", "");
        if (value == null) {
            return;
        }
        if (!validName(value)) {
            onStatus.accept("Nombre de archivo o carpeta no válido.");
            return;
        }
        String target = child(parent, value);
        try {
            if (directory) {
                workspace.createDirectory(target);
            } else {
                workspace.createFile(target);
            }
            changed(new Change(Operation.CREATE, "", target));
        } catch (IllegalArgumentException problem) {
            onStatus.accept(problem.getMessage());
        }
    }

    private void rename(String oldPath) {
        String value = prompt("Cambiar nombre", name(Path.of(oldPath)));
        if (value == null) {
            return;
        }
        if (!validName(value)) {
            onStatus.accept("Nombre no válido.");
            return;
        }
        String destination = child(parentOf(oldPath), value);
        try {
            workspace.move(oldPath, destination);
            changed(new Change(Operation.MOVE, oldPath, destination));
        } catch (IllegalArgumentException problem) {
            onStatus.accept(problem.getMessage());
        }
    }

    private void paste(String parent) {
        if (clipboard == null) {
            return;
        }
        String destination = child(parent, name(Path.of(clipboard)));
        try {
            if (cut) {
                workspace.move(clipboard, destination);
                changed(new Change(Operation.MOVE, clipboard, destination));
            } else {
                workspace.copy(clipboard, destination);
                changed(new Change(Operation.COPY, clipboard, destination));
            }
            clipboard = null;
        } catch (IllegalArgumentException problem) {
            onStatus.accept(problem.getMessage());
        }
    }

    private void delete(String relative) {
        ButtonType answer = ProductDialogs.confirm(getScene().getWindow(),
                "Eliminar elemento virtual",
                "¿Eliminar " + relative + " de la construcción virtual?",
                ButtonType.OK, ButtonType.CANCEL);
        if (answer != ButtonType.OK) {
            return;
        }
        workspace.delete(relative);
        changed(new Change(Operation.DELETE, relative, ""));
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
