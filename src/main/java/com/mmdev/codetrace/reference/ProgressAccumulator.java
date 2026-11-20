package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Incremental, UI-owned progress snapshot for one immutable catalogue/filter.
 *
 * <p>Building the snapshot costs O(n + typed units). Subsequent edits update
 * only the changed file in O(1) bookkeeping, rather than three catalogue scans
 * on every keystroke. This class does not watch mutable workspace structures:
 * callers MUST discard it after a virtual tree, scope, exclusion, or catalogue
 * mutation. Use {@link #matches} to detect filtering/catalogue changes.</p>
  * <p><strong>Engineering rationale.</strong> Progress is derived state, not a mutable counter stored in the source files. The accumulator caches per-file contributions for rapid typing updates, but its identity depends on the catalogue, filters and exclusions; structural or scope changes require rebuilding that cache.</p>
*/
public final class ProgressAccumulator {
    private final ReferenceCatalogue.Snapshot catalogue;
    private final ProgressScope scope;
    private final String folder;
    private final ProgressExclusions exclusions;
    private final Map<String, Integer> creditedByFile = new HashMap<>();
    private final long total;
    private final long included;
    private long correct;

    public ProgressAccumulator(ReferenceCatalogue.Snapshot catalogue,
                               VirtualWorkspace workspace,
                               Map<String, TypingSession> sessions,
                               Map<String, String> hashes,
                               ProgressScope scope, String folder,
                               ProgressExclusions exclusions) {
        this.catalogue = Objects.requireNonNull(catalogue);
        this.scope = Objects.requireNonNull(scope);
        this.folder = Objects.requireNonNull(folder);
        this.exclusions = Objects.requireNonNull(exclusions);
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(sessions);
        Objects.requireNonNull(hashes);
        long totalUnits = 0;
        long fileCount = 0;
        for (var entry : catalogue.files().entrySet()) {
            String path = entry.getKey();
            var info = entry.getValue();
            if (!included(path, info)) continue;
            fileCount++;
            totalUnits += info.typeableUnits();
            int contribution = contribution(info, path, workspace,
                    sessions.get(path), hashes.get(path));
            if (contribution != 0) creditedByFile.put(path, contribution);
            correct += contribution;
        }
        this.total = totalUnits;
        this.included = fileCount;
    }

    public boolean matches(ReferenceCatalogue.Snapshot nextCatalogue,
                           ProgressScope nextScope, String nextFolder,
                           ProgressExclusions nextExclusions) {
        return catalogue == nextCatalogue && scope.equals(nextScope)
                && folder.equals(nextFolder) && exclusions.equals(nextExclusions);
    }

    /**
     * Apply one session change; call AFTER typing modifies the session/virtual
     * file. Workspace structural edits must invalidate the whole accumulator.
     */
    public void updateFile(String path, VirtualWorkspace workspace,
                           TypingSession session, String originalHash) {
        var info = catalogue.files().get(path);
        if (info == null || !included(path, info)) return;
        int previous = creditedByFile.getOrDefault(path, 0);
        int next = contribution(info, path, workspace, session, originalHash);
        if (previous == next) return;
        correct += next - previous;
        if (next == 0) creditedByFile.remove(path);
        else creditedByFile.put(path, next);
    }

    public ProgressSummary summary() {
        return new ProgressSummary(total, correct, included);
    }

    private boolean included(String path, ReferenceCatalogue.FileInfo info) {
        return info.typeable() && (folder.isEmpty() || path.startsWith(folder + "/"))
                && !exclusions.excludes(path)
                && scope.includes(ProgressScope.classify(path));
    }

    private static int contribution(ReferenceCatalogue.FileInfo info, String path,
                                    VirtualWorkspace workspace, TypingSession session,
                                    String originalHash) {
        if (info.typeableUnits() == 0 || session == null
                || !info.sha256().equals(originalHash)) return 0;
        var entry = workspace.entry(path);
        if (entry == null || entry.kind() != VirtualWorkspace.Kind.FILE) return 0;
        return Math.min(info.typeableUnits(), session.correctVisibleCount());
    }
}
