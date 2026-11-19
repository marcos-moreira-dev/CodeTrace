package com.mmdev.codetrace.practice;

/**
 * A per-open-editor transition detector. It starts from the restored
 * session's actual state, so reopening a completed file never triggers an
 * unsolicited celebration. A completion event is independent from progress
 * indexing and does not control persistence.
 */
public final class CompletionTransition {
    private boolean completed;

    public CompletionTransition(boolean initiallyCompleted) {
        completed = initiallyCompleted;
    }

    /** True only on an incomplete-to-complete transition. */
    public boolean update(boolean nowCompleted) {
        boolean reached = nowCompleted && !completed;
        completed = nowCompleted;
        return reached;
    }
}
