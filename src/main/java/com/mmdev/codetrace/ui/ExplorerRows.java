package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.reference.PerformanceProbe;
import com.mmdev.codetrace.workspace.VirtualChildrenIndex;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Pure logical projection of an indexed reference into ordered visible rows.
 *
 * <p>This model knows nothing about JavaFX, disk I/O or TreeItem. Expansion
 * is a set of relative directory paths, not thousands of control mutations.
 * Build it on a worker and publish the immutable result once on the FX thread.
 * Cost O(visible paths + sorting sibling groups), memory O(visible paths).</p>
 */
public final class ExplorerRows {
    public static final int MAX_ROWS = 150_000;
    public record Row(String relative, int depth, boolean directory) { }
    public record Result(List<Row> rows, boolean truncated) { }
    private record Pending(String path, int depth) { }
    private ExplorerRows() { }

    public static Set<String> allDirectories(ReferenceTreeIndex index,
                                              VirtualChildrenIndex virtual) {
        HashSet<String> paths = new HashSet<>(index.directoryPaths());
        paths.addAll(virtual.directoryPaths());
        paths.add("");
        return Set.copyOf(paths);
    }

    public static Result project(ReferenceTreeIndex index, VirtualChildrenIndex virtual,
                                 Set<String> expanded, Set<String> filter) {
        long started = PerformanceProbe.begin();
        Objects.requireNonNull(index);
        Objects.requireNonNull(virtual);
        Objects.requireNonNull(expanded);
        boolean filtering = filter != null && !filter.isEmpty();
        var rows = new ArrayList<Row>(Math.min(MAX_ROWS, index.fileCount() + 1));
        var pending = new ArrayDeque<Pending>();
        pending.add(new Pending("", 0));
        boolean truncated = false;
        while (!pending.isEmpty()) {
            if (Thread.currentThread().isInterrupted())
                throw new java.util.concurrent.CancellationException("Explorer projection cancelled");
            Pending current = pending.removeLast();
            String path = current.path();
            if (filtering && !filter.contains(path)) continue;
            boolean directory = index.hasDirectory(path) || virtual.isDirectory(path)
                    || path.isEmpty();
            if (rows.size() >= MAX_ROWS) { truncated = true; break; }
            rows.add(new Row(path, current.depth(), directory));
            if (!directory || !expanded.contains(path)) continue;
            var children = new HashSet<>(index.children(path));
            children.addAll(virtual.childrenOf(path));
            var ordered = new ArrayList<>(children);
            ordered.sort(Comparator.<String, Boolean>comparing(child ->
                            !(index.hasDirectory(child) || virtual.isDirectory(child)))
                    .thenComparing(child -> child.substring(child.lastIndexOf('/') + 1),
                            String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Comparator.naturalOrder()));
            for (int i = ordered.size() - 1; i >= 0; i--)
                pending.add(new Pending(ordered.get(i), current.depth() + 1));
        }
        PerformanceProbe.finish("explorer-projection", started, rows.size());
        return new Result(List.copyOf(rows), truncated);
    }
}
