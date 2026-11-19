package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.concurrent.Task;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

/**
 * Independent supplementary folder browser. No workspace, progress,
 * transcription, file mutation commands or project-index side effects.
 * Directory contents are loaded asynchronously when expanded.
 */
public final class ConsultationFolderTree extends TreeView<Path> {
    private final Consumer<Path> openForReading;
    private final Consumer<String> status;
    private Path folder;
    private long generation;

    private static final class Loading extends TreeItem<Path> {}

    public ConsultationFolderTree(Consumer<Path> onOpen, Consumer<String> onStatus) {
        openForReading = Objects.requireNonNull(onOpen);
        status = Objects.requireNonNull(onStatus);
        getStyleClass().addAll("consultation-folder-tree", "reference-tree");
        setShowRoot(true);
        setCellFactory(view -> new TreeCell<>() {
            @Override protected void updateItem(Path path, boolean empty) {
                super.updateItem(path, empty);
                if (getTreeItem() instanceof Loading) {
                    setGraphic(null);
                    setText("Leyendo carpeta…");
                    return;
                }
                if (empty || path == null) {
                    setGraphic(null);
                    setText(null);
                    setTooltip(null);
                    return;
                }
                setText(path.getFileName() == null
                        ? path.toString() : path.getFileName().toString());
                setGraphic(FileTypeIcons.forPath(path,
                        Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)));
                setTooltip(new Tooltip(path + "\nSolo consulta; no participa en la práctica."));
            }
        });
        setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY || event.getClickCount() != 2)
                return;
            TreeItem<Path> selected = getSelectionModel().getSelectedItem();
            if (selected == null || selected instanceof Loading
                    || selected.getValue() == null) return;
            Path candidate = selected.getValue();
            if (Files.isRegularFile(candidate, LinkOption.NOFOLLOW_LINKS)
                    && ConsultationFolderPolicy.contains(folder, candidate)) {
                openForReading.accept(candidate);
            }
        });
    }

    public Path folder() { return folder; }

    public boolean openFolder(Path requested) {
        if (requested == null) return false;
        try {
            Path real = requested.toRealPath();
            if (!Files.isDirectory(real, LinkOption.NOFOLLOW_LINKS)) return false;
            generation++;
            folder = real;
            TreeItem<Path> next = node(real, generation);
            setRoot(next);
            next.setExpanded(true);
            return true;
        } catch (IOException | SecurityException error) {
            status.accept("No se pudo abrir la carpeta secundaria.");
            return false;
        }
    }

    public void clearFolder() {
        generation++;
        folder = null;
        setRoot(null);
    }

    private TreeItem<Path> node(Path path, long token) {
        TreeItem<Path> item = new TreeItem<>(path);
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            item.getChildren().add(new Loading());
            item.expandedProperty().addListener((obs, wasExpanded, expanded) -> {
                if (expanded) loadChildren(item, token);
            });
        }
        return item;
    }

    private void loadChildren(TreeItem<Path> item, long token) {
        if (item.getChildren().isEmpty() ||
                !(item.getChildren().getFirst() instanceof Loading)) return;
        Path source = item.getValue();
        Path permittedRoot = folder;
        Task<List<Path>> task = new Task<>() {
            @Override protected List<Path> call() throws IOException {
                List<Path> children = new ArrayList<>();
                try (DirectoryStream<Path> entries = Files.newDirectoryStream(source)) {
                    for (Path entry : entries) {
                        if (isCancelled()) break;
                        // Ignore junctions/symlinks: no traversal outside this root.
                        if (Files.isSymbolicLink(entry)
                                || !ConsultationFolderPolicy.contains(permittedRoot, entry)) continue;
                        children.add(entry);
                    }
                }
                children.sort(Comparator
                        .comparing((Path path) -> !Files.isDirectory(
                                path, LinkOption.NOFOLLOW_LINKS))
                        .thenComparing(path -> path.getFileName().toString(),
                                String.CASE_INSENSITIVE_ORDER));
                return children;
            }
        };
        task.setOnSucceeded(event -> {
            if (token != generation || folder == null) return;
            item.getChildren().setAll(task.getValue().stream()
                    .map(path -> node(path, token)).toList());
        });
        task.setOnFailed(event -> {
            if (token != generation) return;
            item.getChildren().clear();
            status.accept("No se pudo leer " + source.getFileName());
        });
        Thread thread = new Thread(task, "codetrace-consultation-folder");
        thread.setDaemon(true);
        thread.start();
    }
}
