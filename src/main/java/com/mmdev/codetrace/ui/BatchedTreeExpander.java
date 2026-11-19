package com.mmdev.codetrace.ui;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TreeItem;
import javafx.util.Duration;

/**
 * Owns the lifecycle and safety budget of recursive TreeView expansion.
 * The tree still owns items, selection, collapse semantics, and indexing.
 * This coordinator only schedules bounded work on the JavaFX thread.
 */
final class BatchedTreeExpander {
    private static final int EXPANSION_BATCH = 48;
    private static final int MAX_EXPANDED_FOLDERS = 12_000;

    private final Consumer<String> notice;
    private final Runnable busyChanged;
    private Timeline timeline;
    private boolean expanding;
    private int visited;

    BatchedTreeExpander(Consumer<String> notice, Runnable busyChanged) {
        this.notice = Objects.requireNonNull(notice);
        this.busyChanged = Objects.requireNonNull(busyChanged);
    }

    boolean isExpanding() {
        return expanding;
    }

    void expand(TreeItem<Path> node) {
        cancel();
        if (node == null) return;
        ArrayDeque<TreeItem<Path>> pending = new ArrayDeque<>();
        pending.add(node);
        visited = 0;
        expanding = true;
        busyChanged.run();
        processBatch(pending);
        if (pending.isEmpty() || !expanding) return;
        timeline = new Timeline(new KeyFrame(Duration.millis(20),
                event -> processBatch(pending)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void cancel() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        expanding = false;
        busyChanged.run();
    }

    private void processBatch(ArrayDeque<TreeItem<Path>> pending) {
        int processed = 0;
        while (!pending.isEmpty() && processed++ < EXPANSION_BATCH
                && visited < MAX_EXPANDED_FOLDERS) {
            TreeItem<Path> node = pending.removeFirst();
            if (node.isLeaf()) continue;
            node.setExpanded(true);
            visited++;
            for (TreeItem<Path> child : node.getChildren()) {
                if (!child.isLeaf()) pending.addLast(child);
            }
        }
        if (pending.isEmpty() || visited >= MAX_EXPANDED_FOLDERS) {
            boolean limited = !pending.isEmpty();
            int count = visited;
            cancel();
            notice.accept(limited
                    ? "Se mostraron " + count + " carpetas; límite de seguridad alcanzado."
                    : "Expansión recursiva terminada: " + count + " carpetas.");
        }
    }
}
