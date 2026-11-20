package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceCatalogue;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Indexes the active reference asynchronously. The generic task slot owns
 * cancellation and discards results from obsolete requests.
  * <p><strong>Engineering rationale.</strong> The progress catalogue is different from the navigation tree index: it inspects eligible text and hashes for completion statistics, whereas the explorer index primarily organizes file paths. Their separate lifecycles prevent navigation performance from depending on expensive content analysis.</p>
*/
public final class ReferenceCatalogueScanner {
    private final LatestTaskRunner<ReferenceCatalogue.Snapshot> tasks =
            new LatestTaskRunner<>();

    public void scan(Path root,
                     Consumer<ReferenceCatalogue.Snapshot> success,
                     Consumer<Throwable> failure) {
        Objects.requireNonNull(root);
        tasks.submit(() -> ReferenceCatalogue.scan(root), success, failure,
                "codetrace-reference-index");
    }

    public void cancel() {
        tasks.cancel();
    }
}
