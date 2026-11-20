package com.mmdev.codetrace.practice;

/**
 * A per-open-editor transition detector. It starts from the restored
 * session's actual state, so reopening a completed file never triggers an
 * unsolicited celebration. A completion event is independent from progress
 * indexing and does not control persistence.
  * <p><strong>Engineering rationale.</strong> Finishing an exercise is an edge-triggered event, not a property to announce on every refresh. Encapsulating the previous/current completion state prevents repeated success sounds and dialogs when metrics or tabs are recomputed.</p>
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
