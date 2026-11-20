package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.Map;
import java.util.Objects;

/** Snapshot of the counts driving the progress bar, independent of JavaFX.  * <p><strong>Engineering rationale.</strong> A progress percentage is a derived ratio of expected and correctly resolved typing units over an explicit scope. Computing it independently of JavaFX makes empty projects, changed references, exclusions and partial typing testable as mathematical cases.</p>
*/
public record ProgressSummary(long totalUnits, long correctUnits, long includedFiles) {
    public ProgressSummary {
        if (totalUnits < 0 || correctUnits < 0 || includedFiles < 0
                || correctUnits > totalUnits)
            throw new IllegalArgumentException("Invalid progress counts");
    }

    public static ProgressSummary calculate(ReferenceCatalogue.Snapshot catalogue,
                                            VirtualWorkspace workspace,
                                            Map<String, TypingSession> sessions,
                                            Map<String, String> hashes,
                                            ProgressScope scope,
                                            String folder,
                                            ProgressExclusions exclusions) {
        Objects.requireNonNull(catalogue);
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(sessions);
        Objects.requireNonNull(hashes);
        Objects.requireNonNull(scope);
        Objects.requireNonNull(exclusions);
        return new ProgressSummary(
                catalogue.expectedUnits(scope, folder, exclusions),
                catalogue.resolvedUnits(workspace, sessions, hashes,
                        scope, folder, exclusions),
                catalogue.includedFileCount(scope, folder, exclusions));
    }

    public double ratio() {
        return totalUnits == 0 ? 0.0 : (double) correctUnits / totalUnits;
    }

    public boolean hasEvaluableText() {
        return includedFiles > 0 && totalUnits > 0;
    }
}
