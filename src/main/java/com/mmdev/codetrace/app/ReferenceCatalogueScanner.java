package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceCatalogue;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.concurrent.Task;

/**
 * Owns one asynchronous catalogue scan for the active project. A new scan
 * invalidates and cancels the old task; stale completion callbacks cannot
 * publish an index belonging to a previously opened reference.
 *
 * All public methods are called on the JavaFX application thread.
 */
public final class ReferenceCatalogueScanner {
    private Task<ReferenceCatalogue.Snapshot> active;
    private long generation;

    public void scan(Path root,
                     Consumer<ReferenceCatalogue.Snapshot> success,
                     Consumer<Throwable> failure) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(success);
        Objects.requireNonNull(failure);
        cancel();
        long request = generation;
        Task<ReferenceCatalogue.Snapshot> task = new Task<>() {
            @Override protected ReferenceCatalogue.Snapshot call() throws Exception {
                return ReferenceCatalogue.scan(root);
            }
        };
        active = task;
        task.setOnSucceeded(event -> {
            if (!isCurrent(request, task)) return;
            active = null;
            success.accept(task.getValue());
        });
        task.setOnFailed(event -> {
            if (!isCurrent(request, task)) return;
            active = null;
            failure.accept(task.getException());
        });
        task.setOnCancelled(event -> {
            if (isCurrent(request, task)) active = null;
        });
        Thread worker = new Thread(task, "codetrace-reference-index");
        worker.setDaemon(true);
        worker.start();
    }

    public void cancel() {
        generation++;
        Task<ReferenceCatalogue.Snapshot> previous = active;
        active = null;
        if (previous != null) previous.cancel(true);
    }

    private boolean isCurrent(long request, Task<ReferenceCatalogue.Snapshot> task) {
        return request == generation && active == task;
    }
}
