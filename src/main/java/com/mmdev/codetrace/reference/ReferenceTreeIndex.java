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

    public static ReferenceTreeIndex scan(Path reference) throws IOException {
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
                if (Thread.currentThread().isInterrupted()) {
                    return FileVisitResult.TERMINATE;
                }
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
                if (Thread.currentThread().isInterrupted()) {
                    return FileVisitResult.TERMINATE;
                }
                if (!attributes.isRegularFile() || Files.isSymbolicLink(file)) {
                    return FileVisitResult.CONTINUE;
                }
                String relative = relative(root, file);
                regularFiles.add(relative);
                if (PracticeEligibility.supports(file) && attributes.size() > 0) {
                    workable.add(relative);
                    String ancestor = parent(relative);
                    while (true) {
                        descendants.add(ancestor);
                        if (ancestor.isEmpty()) break;
                        ancestor = parent(ancestor);
                    }
                }
                childPaths.computeIfAbsent(parent(relative), ignored -> new ArrayList<>())
                        .add(relative);
                String ancestor = parent(relative);
                while (true) {
                    totals.merge(ancestor, 1L, Long::sum);
                    if (ancestor.isEmpty()) break;
                    ancestor = parent(ancestor);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException error) {
                // Unreadable files are not included; other branches still load.
                return FileVisitResult.CONTINUE;
            }
        });

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
        return new ReferenceTreeIndex(stable, totals, folders, regularFiles,
                workable, descendants);
    }

    private static String relative(Path root, Path child) {
        return root.relativize(child).toString().replace('\\', '/');
    }

    private static String parent(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? "" : path.substring(0, slash);
    }
}
