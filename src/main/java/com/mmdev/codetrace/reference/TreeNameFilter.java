package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/** Name-only explorer filter. No file bytes are read and no state is changed. */
public final class TreeNameFilter {
    private TreeNameFilter() {}

    /**
     * Visible paths include every matching basename and all its ancestors,
     * including the root. Matches are case-insensitive and accent-insensitive.
     */
    public static Set<String> visible(ReferenceTreeIndex index,
                                      VirtualWorkspace workspace, String query) {
        String wanted = fold(query == null ? "" : query.trim());
        if (wanted.isEmpty()) return Set.of();
        Set<String> candidates = new HashSet<>();
        if (index != null) {
            ArrayDeque<String> pending = new ArrayDeque<>();
            pending.add("");
            Set<String> visited = new HashSet<>();
            while (!pending.isEmpty()) {
                String folder = pending.removeFirst();
                if (!visited.add(folder)) continue;
                for (String child : index.children(folder)) {
                    candidates.add(child);
                    if (index.hasDirectory(child)) pending.add(child);
                }
            }
        }
        for (VirtualWorkspace.Entry entry : workspace.entries()) {
            if (!entry.path().isEmpty()) candidates.add(entry.path());
        }
        Set<String> visible = new HashSet<>();
        visible.add("");
        for (String path : candidates) {
            String name = path.substring(path.lastIndexOf('/') + 1);
            if (!fold(name).contains(wanted)) continue;
            String ancestor = path;
            while (!ancestor.isEmpty() && visible.add(ancestor)) {
                int slash = ancestor.lastIndexOf('/');
                ancestor = slash < 0 ? "" : ancestor.substring(0, slash);
            }
        }
        return Set.copyOf(visible);
    }

    public static String fold(String input) {
        String normalized = java.text.Normalizer.normalize(input, 
                java.text.Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        return normalized.toLowerCase(Locale.ROOT);
    }
}
