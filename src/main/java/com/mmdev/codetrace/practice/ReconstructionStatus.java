package com.mmdev.codetrace.practice;

import com.mmdev.codetrace.reference.ProgressScope;
import com.mmdev.codetrace.reference.ProgressExclusions;
import com.mmdev.codetrace.reference.ReferenceCatalogue;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Recreation and transcription are separate achievements. The selected progress
 * scope affects folder completion, not whether an individual file is finished.
 */
public final class ReconstructionStatus {
    public enum State { NOT_APPLICABLE, PENDING, CREATED, COMPLETED }

    private ReconstructionStatus() {}

    public static boolean entirelyCorrect(TypingSession session) {
        return session != null && session.visibleUnitCount() > 0
                && session.correctVisibleCount() == session.visibleUnitCount();
    }

    public static Map<String, State> calculate(VirtualWorkspace workspace,
            Map<String, TypingSession> sessions, Map<String, String> hashes,
            ReferenceCatalogue.Snapshot catalog, ProgressScope scope) {
        return calculate(workspace, sessions, hashes, catalog, scope,
                ProgressExclusions.empty());
    }

    public static Map<String, State> calculate(VirtualWorkspace workspace,
            Map<String, TypingSession> sessions, Map<String, String> hashes,
            ReferenceCatalogue.Snapshot catalog, ProgressScope scope,
            ProgressExclusions exclusions) {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(sessions);
        Objects.requireNonNull(hashes);
        Objects.requireNonNull(scope);
        Objects.requireNonNull(exclusions);
        Map<String, State> state = new HashMap<>();
        for (VirtualWorkspace.Entry entry : workspace.entries()) {
            state.put(entry.path(), State.CREATED);
        }
        if (catalog == null) return Map.copyOf(state);

        Map<String, Integer> requiredByFolder = new HashMap<>();
        Map<String, Integer> finishedByFolder = new HashMap<>();
        for (var pair : catalog.files().entrySet()) {
            String path = pair.getKey();
            var info = pair.getValue();
            boolean created = workspace.entry(path) != null
                    && workspace.entry(path).kind() == VirtualWorkspace.Kind.FILE;
            // An unsupported/binary reference is not an exercise at all.
            if (!info.typeable()) {
                state.put(path, State.NOT_APPLICABLE);
                continue;
            }
            // An empty supported source needs no typing or virtual recreation.
            if (info.typeableUnits() == 0) {
                state.put(path, State.COMPLETED);
                continue;
            }
            boolean complete = created
                    && info.sha256().equals(hashes.get(path))
                    && sessions.get(path) != null
                    && sessions.get(path).finishedExercise();
            if (complete) state.put(path, State.COMPLETED);

            if (!info.typeable() || info.typeableUnits() == 0
                    || exclusions.excludes(path)
                    || !scope.includes(ProgressScope.classify(path))) continue;
            String parent = parent(path);
            while (true) {
                requiredByFolder.merge(parent, 1, Integer::sum);
                if (complete) finishedByFolder.merge(parent, 1, Integer::sum);
                if (parent.isEmpty()) break;
                parent = parent(parent);
            }
        }
        for (VirtualWorkspace.Entry entry : workspace.entries()) {
            if (entry.kind() == VirtualWorkspace.Kind.DIRECTORY) {
                String path = entry.path();
                int required = requiredByFolder.getOrDefault(path, 0);
                if (required > 0
                        && finishedByFolder.getOrDefault(path, 0) == required) {
                    state.put(path, State.COMPLETED);
                }
            }
        }
        return Map.copyOf(state);
    }

    private static String parent(String path) {
        int slash = path.lastIndexOf('/');
        return slash < 0 ? "" : path.substring(0, slash);
    }
}
