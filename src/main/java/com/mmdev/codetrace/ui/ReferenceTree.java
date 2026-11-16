package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/** Lazy tree: expanded directories are read only when required. */
public final class ReferenceTree extends TreeView<Path> {
    public ReferenceTree(Consumer<Path> openFile) {
        setShowRoot(true);
        setCellFactory(view -> new TreeCell<>() {
            @Override
            protected void updateItem(Path value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : name(value));
            }
        });
        setOnMouseClicked(event -> {
            TreeItem<Path> chosen = getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && chosen != null
                    && Files.isRegularFile(chosen.getValue())) {
                openFile.accept(chosen.getValue());
            }
        });
    }

    public void open(Path directory) {
        TreeItem<Path> item = lazyItem(directory);
        setRoot(item);
        item.setExpanded(true);
    }

    private static TreeItem<Path> lazyItem(Path path) {
        return new TreeItem<>(path) {
            private boolean loaded;

            @Override
            public boolean isLeaf() {
                return !Files.isDirectory(getValue()) || Files.isSymbolicLink(getValue());
            }

            @Override
            public ObservableList<TreeItem<Path>> getChildren() {
                if (!loaded && !isLeaf()) {
                    loaded = true;
                    try (Stream<Path> entries = Files.list(getValue())) {
                        super.getChildren().setAll(entries
                                .sorted(Comparator.comparing((Path p) -> !Files.isDirectory(p))
                                        .thenComparing(ReferenceTree::name,
                                                String.CASE_INSENSITIVE_ORDER))
                                .map(ReferenceTree::lazyItem).toList());
                    } catch (IOException | SecurityException ignored) {
                        // Keep the rest of the reference navigable.
                    }
                }
                return super.getChildren();
            }
        };
    }

    private static String name(Path path) {
        return path.getFileName() == null ? path.toString() : path.getFileName().toString();
    }
}
