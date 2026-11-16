package com.mmdev.codetrace.workspace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A project the user reconstructs in memory. No operation in this class touches
 * the reference repository or writes to the local filesystem.
 *
 * Paths are relative, separated by '/', and case-insensitively unique among
 * siblings to behave predictably on Windows.
 */
public final class VirtualWorkspace {
    public enum Kind { DIRECTORY, FILE }

    public record Entry(String path, Kind kind, String text) {
        public Entry {
            Objects.requireNonNull(path);
            Objects.requireNonNull(kind);
            if (kind == Kind.DIRECTORY && text != null) {
                throw new IllegalArgumentException("A directory cannot have file text");
            }
        }
    }

    private final Map<String, Entry> entries = new LinkedHashMap<>();

    public VirtualWorkspace() {
        entries.put("", new Entry("", Kind.DIRECTORY, null));
    }

    public boolean exists(String path) {
        return entries.containsKey(path);
    }

    public Entry entry(String path) {
        return entries.get(path);
    }

    public List<Entry> entries() {
        return List.copyOf(entries.values());
    }

    public void createDirectory(String path) {
        add(path, Kind.DIRECTORY, null);
    }

    public void createFile(String path) {
        add(path, Kind.FILE, "");
    }

    public void setText(String path, String text) {
        Entry current = requireFile(path);
        entries.put(path, new Entry(current.path(), Kind.FILE, Objects.requireNonNull(text)));
    }

    public String text(String path) {
        return requireFile(path).text();
    }

    public void delete(String path) {
        checkPath(path);
        if (!entries.containsKey(path)) {
            throw new IllegalArgumentException("Does not exist: " + path);
        }
        List<String> toRemove = entries.keySet().stream()
                .filter(key -> key.equals(path) || key.startsWith(path + "/"))
                .toList();
        toRemove.forEach(entries::remove);
    }

    /** Rename a node and every descendant, or move it to an existing directory. */
    public void move(String source, String destination) {
        rewriteSubtree(source, destination, false);
    }

    /** Copy a node and every descendant; all copied text remains in memory. */
    public void copy(String source, String destination) {
        rewriteSubtree(source, destination, true);
    }

    private void add(String path, Kind kind, String text) {
        checkPath(path);
        assertParentExists(path);
        if (conflicts(path, List.of())) {
            throw new IllegalArgumentException("Already exists: " + path);
        }
        entries.put(path, new Entry(path, kind, text));
    }

    private void rewriteSubtree(String source, String destination, boolean copy) {
        checkPath(source);
        checkPath(destination);
        Entry entry = entries.get(source);
        if (entry == null) {
            throw new IllegalArgumentException("Does not exist: " + source);
        }
        if (source.equals(destination)) {
            if (copy) {
                throw new IllegalArgumentException("Cannot copy onto itself");
            }
            return;
        }
        if (destination.startsWith(source + "/")) {
            throw new IllegalArgumentException("Cannot put a folder inside itself");
        }
        assertParentExists(destination);
        List<String> subtree = entries.keySet().stream()
                .filter(path -> path.equals(source) || path.startsWith(source + "/"))
                .toList();
        List<String> excluded = copy ? List.of() : subtree;
        List<Entry> rewritten = new ArrayList<>();
        for (String path : subtree) {
            String target = destination + path.substring(source.length());
            if (conflicts(target, excluded)) {
                throw new IllegalArgumentException("Destination exists: " + target);
            }
            Entry old = entries.get(path);
            rewritten.add(new Entry(target, old.kind(), old.text()));
        }
        if (!copy) {
            subtree.forEach(entries::remove);
        }
        rewritten.forEach(node -> entries.put(node.path(), node));
    }

    private boolean conflicts(String path, List<String> ignored) {
        return entries.keySet().stream()
                .filter(existing -> !ignored.contains(existing))
                .anyMatch(existing -> existing.equalsIgnoreCase(path));
    }

    private Entry requireFile(String path) {
        Entry node = entries.get(path);
        if (node == null || node.kind() != Kind.FILE) {
            throw new IllegalArgumentException("Not a file: " + path);
        }
        return node;
    }

    private void assertParentExists(String path) {
        String parent = parent(path);
        Entry node = entries.get(parent);
        if (node == null || node.kind() != Kind.DIRECTORY) {
            throw new IllegalArgumentException("Parent folder not created: " + parent);
        }
    }

    private static String parent(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? "" : path.substring(0, slash);
    }

    public static void checkPath(String path) {
        Objects.requireNonNull(path);
        if (path.isBlank() || path.startsWith("/") || path.endsWith("/") || path.contains("\\")) {
            throw new IllegalArgumentException("Invalid relative path: " + path);
        }
        for (String part : path.split("/", -1)) {
            if (part.isEmpty() || part.equals(".") || part.equals("..")
                    || part.matches(".*[<>:\"|?*\\p{Cntrl}].*")
                    || part.endsWith(".") || part.endsWith(" ")) {
                throw new IllegalArgumentException("Invalid path segment: " + part);
            }
        }
    }

    /** Import a session snapshot after validating folder relationships. */
    public static VirtualWorkspace fromEntries(List<Entry> snapshot) {
        VirtualWorkspace result = new VirtualWorkspace();
        snapshot.stream()
                .filter(entry -> !entry.path().isEmpty())
                .sorted(Comparator.comparingInt(node -> node.path().split("/").length))
                .forEach(node -> {
                    if (node.kind() == Kind.DIRECTORY) {
                        result.createDirectory(node.path());
                    } else {
                        result.createFile(node.path());
                        result.setText(node.path(), node.text());
                    }
                });
        return result;
    }
}
