package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.PerformanceProbe;
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
    // Time-box each FX pulse as well as limiting the number of directories.
    private static final long FX_BATCH_BUDGET_NS = 8_000_000L;
    private static final int MAX_EXPANDED_FOLDERS = 12_000;

    private final Consumer<String> notice;
    private final Runnable busyChanged;
    private Timeline timeline;
    private boolean expanding;
    private int visited;
    private int lastReported;
    private long started;

    BatchedTreeExpander(Consumer<String> notice, Runnable busyChanged) {
        this.notice = Objects.requireNonNull(notice);
        this.busyChanged = Objects.requireNonNull(busyChanged);
    }

    boolean isExpanding() {
        return expanding;
    }

    void expand(TreeItem<Path> node, boolean safeToExpandImmediately) {
        cancel();
        if (node == null) return;
        ArrayDeque<TreeItem<Path>> pending = new ArrayDeque<>();
        pending.add(node);
        visited = 0;
        lastReported = 0;
        started = PerformanceProbe.begin();
        expanding = true;
        busyChanged.run();
        notice.accept("Expandiendo carpetas en lotes; puedes seguir usando CodeTrace.");
        if (safeToExpandImmediately) {
            // Only small indexed references (< 40 folders, < 1,000 files)
            // take the synchronous path. Avoid needless animation latency,
            // including for tests that verify an immediate recursive action.
            while (!pending.isEmpty() && expanding) processBatch(pending);
            return;
        }
        processBatch(pending);
        if (pending.isEmpty() || !expanding) return;
        timeline = new Timeline(new KeyFrame(Duration.millis(30),
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
        long started = System.nanoTime();
        while (!pending.isEmpty() && processed < EXPANSION_BATCH
                && visited < MAX_EXPANDED_FOLDERS) {
            TreeItem<Path> node = pending.removeFirst();
            processed++;
            if (!node.isLeaf()) {
                node.setExpanded(true);
                visited++;
                for (TreeItem<Path> child : node.getChildren()) {
                    if (!child.isLeaf()) pending.addLast(child);
                }
            }
            if (System.nanoTime() - started >= FX_BATCH_BUDGET_NS) break;
        }
        if (visited - lastReported >= 192) {
            lastReported = visited;
            notice.accept("Expandiendo carpetas: " + visited + " procesadas...");
        }
        if (pending.isEmpty() || visited >= MAX_EXPANDED_FOLDERS) {
            boolean limited = !pending.isEmpty();
            int count = visited;
            PerformanceProbe.finish("expand-recursive", started, count);
            cancel();
            notice.accept(limited
                    ? "Se mostraron " + count + " carpetas; límite de seguridad alcanzado."
                    : "Expansión recursiva terminada: " + count + " carpetas.");
        }
    }
}
