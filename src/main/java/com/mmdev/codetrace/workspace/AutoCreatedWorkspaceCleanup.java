package com.mmdev.codetrace.workspace;

import com.mmdev.codetrace.practice.TypingSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Reverts only untouched nodes created by the current automatic run.
 * Existing/manual/restored nodes are not tracked. Never touches disk.
 */
public final class AutoCreatedWorkspaceCleanup {
    private AutoCreatedWorkspaceCleanup() {}

    public static int removeUnused(VirtualWorkspace workspace,
                                   Set<String> autoCreated,
                                   Map<String, TypingSession> sessions) {
        Set<String> present = new HashSet<>();
        Map<String, Integer> children = new HashMap<>();
        for (var node : workspace.entries()) {
            present.add(node.path());
            if (!node.path().isEmpty())
                children.merge(parent(node.path()), 1, Integer::sum);
        }
        ArrayList<String> candidates = new ArrayList<>(autoCreated);
        candidates.sort(Comparator.comparingInt(String::length).reversed());
        int removed = 0;
        for (String path : candidates) {
            if (path.isEmpty() || !present.contains(path)) continue;
            var entry = workspace.entry(path);
            if (entry == null) continue;
            if (entry.kind() == VirtualWorkspace.Kind.DIRECTORY) {
                if (children.getOrDefault(path, 0) > 0) continue;
            } else {
                TypingSession session = sessions.get(path);
                if (!entry.text().isEmpty() || session != null
                        && (!session.enteredText().isEmpty()
                            || session.skippedCount() > 0)) continue;
            }
            workspace.delete(path);
            present.remove(path);
            children.merge(parent(path), -1, Integer::sum);
            removed++;
        }
        return removed;
    }

    private static String parent(String path) {
        int split = path.lastIndexOf('/');
        return split < 0 ? "" : path.substring(0, split);
    }
}
