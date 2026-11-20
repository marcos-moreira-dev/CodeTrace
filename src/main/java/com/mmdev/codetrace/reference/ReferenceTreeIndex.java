package com.mmdev.codetrace.reference;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Immutable file-tree index created OFF the JavaFX Application Thread.
 * Stores direct child paths and recursive file totals for every directory.
 * Symbolic links are excluded so a reference cannot escape its root.
  * <p><strong>Engineering rationale.</strong> Indexing is an explicit time-for-memory trade-off: retaining immediate-child adjacency and recursive totals allows repeated expansions and counters to avoid filesystem traversals. The snapshot is immutable and built off the UI thread; symbolic links are not traversed so inspection remains scoped to the selected reference.</p>
*/
public final class ReferenceTreeIndex {
    private final Map<String, List<String>> children;
    private final Map<String, Long> recursiveCounts;
    private final Set<String> directories;
    private final Set<String> files;
    private final Set<String> workableFiles;
    private final Set<String> workableDescendants;

    private ReferenceTreeIndex(Map<String, List<String>> children,
                               Map<String, Long> recursiveCounts,
                               Set<String> directories, Set<String> files,
                               Set<String> workableFiles, Set<String> workableDescendants) {
        this.workableFiles = Set.copyOf(workableFiles);
        this.workableDescendants = Set.copyOf(workableDescendants);
        this.children = Map.copyOf(children);
        this.recursiveCounts = Map.copyOf(recursiveCounts);
        this.directories = Set.copyOf(directories);
        this.files = Set.copyOf(files);
    }

    public List<String> children(String directory) {
        return children.getOrDefault(directory, List.of());
    }

    public long recursiveFiles(String directory) {
        return recursiveCounts.getOrDefault(directory, 0L);
    }

    /** Constant-time index sizes used to choose eager versus batched UI expansion. */
    /** Immutable directory paths for planning a logical expansion off the FX thread. */
    public Set<String> directoryPaths() { return directories; }

    public int directoryCount() { return directories.size(); }

    public int fileCount() { return files.size(); }

    public boolean hasDirectory(String relative) {
        return directories.contains(relative);
    }

    public boolean hasFile(String relative) {
        return files.contains(relative);
    }

    public boolean isWorkable(String relative) {
        return workableFiles.contains(relative);
    }

    public boolean hasWorkableDescendant(String relative) {
        return workableDescendants.contains(relative);
    }

    /** Nodes eligible for virtual recreation, excluding generated/cache trees. */
    public record Node(String path, boolean directory) {}

    public List<Node> recreationNodes() {
        List<Node> nodes = new ArrayList<>();
        for (String folder : directories) {
            if (!folder.isEmpty() && !ReferenceScanRules.generatedPath(folder))
                nodes.add(new Node(folder, true));
        }
        for (String file : files) {
            if (!ReferenceScanRules.generatedPath(file)) nodes.add(new Node(file, false));
        }
        nodes.sort(java.util.Comparator
                .comparingInt((Node n) -> n.path().split("/").length)
                .thenComparing((Node n) -> !n.directory())
                .thenComparing(Node::path));
        return List.copyOf(nodes);
    }

    /**
     * Reads directory topology once, preserving immediate children and
     * recursive totals in an immutable snapshot for later RAM-only queries.
     * Call from a worker; this potentially O(N) filesystem walk is unsuitable
     * for the JavaFX application thread. Symbolic links are not traversed.
     *
     * @param reference selected original reference directory
     * @return immutable tree topology, file counts and practice eligibility
     * @throws IOException if the reference root cannot be accessed
     */
    public static ReferenceTreeIndex scan(Path reference) throws IOException {
        abortIfInterrupted();
        long scanStart = PerformanceProbe.begin();
        if (!Files.isDirectory(reference)) {
            throw new IOException("Carpeta de referencia no disponible");
        }
        Path root = reference.toAbsolutePath().normalize();
        Map<String, List<String>> childPaths = new HashMap<>();
        Map<String, Long> totals = new HashMap<>();
        Set<String> folders = new HashSet<>();
        Set<String> regularFiles = new HashSet<>();
        Set<String> workable = new HashSet<>();
        Set<String> descendants = new HashSet<>();
        folders.add("");
        childPaths.put("", new ArrayList<>());
        totals.put("", 0L);

        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path directory,
                                                     BasicFileAttributes attributes) {
                abortIfInterrupted();
                if (!directory.equals(root) && Files.isSymbolicLink(directory)) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                String relative = relative(root, directory);
                folders.add(relative);
                totals.putIfAbsent(relative, 0L);
                childPaths.computeIfAbsent(relative, ignored -> new ArrayList<>());
                if (!relative.isEmpty()) {
                    childPaths.computeIfAbsent(parent(relative), ignored -> new ArrayList<>())
                            .add(relative);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                abortIfInterrupted();
                if (!attributes.isRegularFile() || Files.isSymbolicLink(file)) {
                    return FileVisitResult.CONTINUE;
                }
                String relative = relative(root, file);
                regularFiles.add(relative);
                if (PracticeEligibility.supports(file) && attributes.size() > 0
                        && !ReferenceScanRules.generatedPath(relative)) {
                    workable.add(relative);
                    // Mark the immediate parent; post-order directory visits
                    // propagate eligibility to ancestors in O(directories).
                    descendants.add(parent(relative));
                }
                childPaths.computeIfAbsent(parent(relative), ignored -> new ArrayList<>())
                        .add(relative);
                // Count locally, then propagate subtree totals only once
                // per directory. Avoid O(n * depth) updates on deep trees.
                totals.merge(parent(relative), 1L, Long::sum);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path directory, IOException error) {
                abortIfInterrupted();
                String relative = relative(root, directory);
                if (!relative.isEmpty()) {
                    String parent = parent(relative);
                    totals.merge(parent, totals.getOrDefault(relative, 0L), Long::sum);
                    if (descendants.contains(relative)) descendants.add(parent);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException error) {
                // Unreadable files are not included; other branches still load.
                return FileVisitResult.CONTINUE;
            }
        });

        abortIfInterrupted();
        Map<String, List<String>> stable = new HashMap<>();
        childPaths.forEach((path, values) -> {
            values.sort((left, right) -> {
                boolean leftDir = folders.contains(left);
                boolean rightDir = folders.contains(right);
                if (leftDir != rightDir) return leftDir ? -1 : 1;
                return left.compareToIgnoreCase(right);
            });
            stable.put(path, List.copyOf(values));
        });
        PerformanceProbe.finish("reference-tree", scanStart, regularFiles.size());
        return new ReferenceTreeIndex(stable, totals, folders, regularFiles,
                workable, descendants);
    }

    private static void abortIfInterrupted() {
        if (Thread.currentThread().isInterrupted())
            throw new java.util.concurrent.CancellationException("Tree scan cancelled");
    }

    private static String relative(Path root, Path child) {
        return root.relativize(child).toString().replace('\\', '/');
    }

    private static String parent(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? "" : path.substring(0, slash);
    }
}
