package com.mmdev.codetrace.app;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.concurrent.Task;

/**
 * One asynchronous JavaFX task per operation slot: starting a new operation
 * cancels the old one and ignores stale completion events. Used by both
 * catalogue scanning and reference fingerprint verification.
 *
 * Submit/cancel are called on the JavaFX application thread. Work runs on
 * a named daemon thread; result callbacks return to the application thread.
  * <p><strong>Engineering rationale.</strong> A cancellation request is cooperative and does not prove that the worker has stopped. The monotonically increasing generation is therefore just as important as thread interruption: only the most recent task is allowed to publish a result to a live screen.</p>
*/
public final class LatestTaskRunner<T> {
    private Task<T> active;
    private long generation;

    public void submit(Callable<T> work,
                       Consumer<T> success,
                       Consumer<Throwable> failure,
                       String workerName) {
        Objects.requireNonNull(work);
        Objects.requireNonNull(success);
        Objects.requireNonNull(failure);
        Objects.requireNonNull(workerName);
        cancel();
        long request = generation;
        Task<T> next = new Task<>() {
            @Override protected T call() throws Exception {
                return work.call();
            }
        };
        active = next;
        next.setOnSucceeded(event -> {
            if (!isCurrent(request, next)) return;
            active = null;
            success.accept(next.getValue());
        });
        next.setOnFailed(event -> {
            if (!isCurrent(request, next)) return;
            active = null;
            failure.accept(next.getException());
        });
        next.setOnCancelled(event -> {
            if (isCurrent(request, next)) active = null;
        });
        Thread thread = new Thread(next, workerName);
        thread.setDaemon(true);
        thread.start();
    }

    /** Invalidate callbacks before interrupting the worker. */
    public void cancel() {
        generation++;
        Task<T> previous = active;
        active = null;
        if (previous != null) previous.cancel(true);
    }

    private boolean isCurrent(long request, Task<T> task) {
        return request == generation && active == task;
    }
}
