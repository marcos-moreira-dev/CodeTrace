package com.mmdev.codetrace.workspace;

import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Stateful, UI-independent batch processing for direct virtual recreation.
 * The caller schedules batches; no real source file is ever created.
  * <p><strong>Engineering rationale.</strong> Direct mode may need to create thousands of virtual paths, but performing it in one FX callback would freeze input and painting. This stateful cursor processes bounded logical batches, leaving scheduling and cancellation to the caller and never touching source files.</p>
*/
public final class VirtualTreeRecreator {
    public static final int MAX_NODES = 10_000;
    private final VirtualWorkspace workspace;
    private final List<ReferenceTreeIndex.Node> nodes;
    private final int limit;
    private final Consumer<String> onCreated;
    private int cursor;
    private int added;
    private int rejected;

    public VirtualTreeRecreator(VirtualWorkspace workspace,
                                List<ReferenceTreeIndex.Node> nodes) {
        this(workspace, nodes, ignored -> {});
    }

    /** Tracks only nodes newly created by this pass. */
    public VirtualTreeRecreator(VirtualWorkspace workspace,
                                List<ReferenceTreeIndex.Node> nodes,
                                Consumer<String> onCreated) {
        this.workspace = Objects.requireNonNull(workspace);
        this.nodes = List.copyOf(Objects.requireNonNull(nodes));
        this.onCreated = Objects.requireNonNull(onCreated);
        limit = Math.min(MAX_NODES, this.nodes.size());
    }

    public record Progress(int processed, int created, int omitted, boolean finished) { }

    public Progress nextBatch(int batchSize) {
        if (batchSize < 1) throw new IllegalArgumentException("Batch size must be positive");
        int stop = Math.min(limit, cursor + batchSize);
        while (cursor < stop) {
            ReferenceTreeIndex.Node node = nodes.get(cursor++);
            if (workspace.exists(node.path())) continue;
            try {
                if (node.directory()) workspace.createDirectory(node.path());
                else workspace.createFile(node.path());
                added++;
                onCreated.accept(node.path());
            } catch (IllegalArgumentException rejectedNode) {
                rejected++;
            }
        }
        return new Progress(cursor, added, nodes.size() - limit + rejected, cursor == limit);
    }
}
