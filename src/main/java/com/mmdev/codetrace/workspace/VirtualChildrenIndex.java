package com.mmdev.codetrace.workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Read-only snapshot of the virtual workspace's immediate children.
 * Built only when the explorer model refreshes, not for every expanded node.
  * <p><strong>Engineering rationale.</strong> Repeatedly scanning the complete in-memory reconstruction for each expanded folder would turn navigation into quadratic work. A snapshot of immediate children spends O(N) memory once per structural refresh to make subsequent parent lookups cheap and deterministic.</p>
*/
public final class VirtualChildrenIndex {
    private final Map<String, List<String>> byParent;
    private final Set<String> directories;

    private VirtualChildrenIndex(Map<String, List<String>> byParent,
                                 Set<String> directories) {
        this.byParent = Map.copyOf(byParent);
        this.directories = Set.copyOf(directories);
    }

    public static VirtualChildrenIndex snapshot(VirtualWorkspace workspace) {
        Objects.requireNonNull(workspace);
        Map<String, List<String>> grouped = new HashMap<>();
        Set<String> folders = new HashSet<>();
        for (VirtualWorkspace.Entry entry : workspace.entries()) {
            String path = entry.path();
            if (path.isEmpty()) continue;
            int slash = path.lastIndexOf('/');
            String parent = slash < 0 ? "" : path.substring(0, slash);
            grouped.computeIfAbsent(parent, ignored -> new ArrayList<>()).add(path);
            if (entry.kind() == VirtualWorkspace.Kind.DIRECTORY) folders.add(path);
        }
        grouped.replaceAll((parent, items) -> List.copyOf(items));
        return new VirtualChildrenIndex(grouped, folders);
    }

    /** All virtual directories, without filesystem calls. */
    public Set<String> directoryPaths() { return directories; }

    public boolean isDirectory(String path) { return directories.contains(path); }

    public List<String> childrenOf(String parent) {
        return byParent.getOrDefault(parent, List.of());
    }

    public boolean hasDirectoryChild(String parent) {
        for (String child : childrenOf(parent)) {
            if (directories.contains(child)) return true;
        }
        return false;
    }
}
