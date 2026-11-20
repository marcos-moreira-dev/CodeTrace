package com.mmdev.codetrace.reference;

import java.lang.management.ManagementFactory;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Opt-in, privacy-preserving wall-clock measurement of read-only operations.
 *
 * <p>Enable with JVM property {@code -Dcodetrace.profile=true}. When disabled,
 * no timestamps or formatted log messages are produced. Log only counts,
 * never absolute paths, source code or filenames. For CPU and file-I/O detail
 * use Java Flight Recorder instead of interpreting timings as benchmarks.</p>
 */
public final class PerformanceProbe {
    private static final boolean ENABLED = Boolean.getBoolean("codetrace.profile");
    private static final Logger LOG = Logger.getLogger(PerformanceProbe.class.getName());

    private PerformanceProbe() { }

    public static long begin() {
        return ENABLED ? System.nanoTime() : 0L;
    }

    public static void finish(String operation, long startNanos, long entries) {
        if (!ENABLED || startNanos == 0L) return;
        double milliseconds = (System.nanoTime() - startNanos) / 1_000_000.0;
        var heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long usedMiB = heap.getUsed() / (1024 * 1024);
        long committedMiB = heap.getCommitted() / (1024 * 1024);
        // These are JVM heap metrics, NOT total OS process memory/RSS.
        LOG.info(() -> String.format(Locale.ROOT,
                "Performance %s: %.1f ms, entries=%d, heapUsedMiB=%d, heapCommittedMiB=%d",
                operation, milliseconds, entries, usedMiB, committedMiB));
    }
}
