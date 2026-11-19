package com.mmdev.codetrace.ui;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

/** Monotonic stopwatch independent of document tabs and wall-clock adjustments. */
public final class StopwatchClock {
    private final LongSupplier nanos;
    private long accumulated;
    private long resumedAt;
    private boolean started;
    private boolean running;

    public StopwatchClock() { this(System::nanoTime); }
    public StopwatchClock(LongSupplier nanos) { this.nanos = Objects.requireNonNull(nanos); }

    public void start() {
        accumulated = 0;
        resumedAt = nanos.getAsLong();
        started = true;
        running = true;
    }

    public void pause() {
        if (!running) return;
        accumulated = elapsedNanos();
        running = false;
    }

    /** Stop and reset to the initial idle state (00:00:00). */
    public void stop() {
        accumulated = 0;
        resumedAt = 0;
        started = false;
        running = false;
    }

    public void resume() {
        if (!started) start();
        else if (!running) {
            resumedAt = nanos.getAsLong();
            running = true;
        }
    }

    public boolean isRunning() { return running; }
    public boolean hasStarted() { return started; }

    public long elapsedNanos() {
        if (!running) return accumulated;
        return accumulated + Math.max(0, nanos.getAsLong() - resumedAt);
    }

    /** Hours grow beyond 24 rather than wrapping like a wall clock. */
    public String display() {
        long seconds = TimeUnit.NANOSECONDS.toSeconds(elapsedNanos());
        return String.format(Locale.ROOT, "%02d:%02d:%02d",
                seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }
}
