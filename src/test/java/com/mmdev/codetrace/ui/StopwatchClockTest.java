package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class StopwatchClockTest {
    @Test void startPauseAndResumeCountOnlyActiveTime() {
        AtomicLong now = new AtomicLong();
        StopwatchClock clock = new StopwatchClock(now::get);
        assertEquals("00:00:00", clock.display());
        clock.resume();
        now.set(TimeUnit.SECONDS.toNanos(65));
        assertEquals("00:01:05", clock.display());
        clock.pause();
        now.set(TimeUnit.MINUTES.toNanos(40));
        assertEquals("00:01:05", clock.display());
        clock.resume();
        now.addAndGet(TimeUnit.SECONDS.toNanos(2));
        assertEquals("00:01:07", clock.display());
    }

    @Test void restartBeginsAtZeroAndHoursDoNotWrap() {
        AtomicLong now = new AtomicLong(7);
        StopwatchClock clock = new StopwatchClock(now::get);
        clock.start();
        now.addAndGet(TimeUnit.HOURS.toNanos(27)
                + TimeUnit.MINUTES.toNanos(3) + TimeUnit.SECONDS.toNanos(5));
        assertEquals("27:03:05", clock.display());
        clock.start();
        assertEquals("00:00:00", clock.display());
        assertTrue(clock.isRunning());
        clock.pause();
        clock.pause();
        assertEquals("00:00:00", clock.display());
    }

    @Test void stopResetsAndRemainsIdleUntilStartedAgain() {
        AtomicLong now = new AtomicLong();
        StopwatchClock clock = new StopwatchClock(now::get);
        clock.resume();
        now.set(TimeUnit.MINUTES.toNanos(9) + TimeUnit.SECONDS.toNanos(12));
        assertEquals("00:09:12", clock.display());
        clock.stop();
        assertEquals("00:00:00", clock.display());
        assertFalse(clock.isRunning());
        assertFalse(clock.hasStarted());
        now.addAndGet(TimeUnit.HOURS.toNanos(2));
        assertEquals("00:00:00", clock.display());
        clock.stop();
        assertEquals("00:00:00", clock.display());
        clock.resume();
        assertTrue(clock.isRunning());
        now.addAndGet(TimeUnit.SECONDS.toNanos(3));
        assertEquals("00:00:03", clock.display());
    }

    @Test void stopFromPausedStateDiscardsAccumulatedTime() {
        AtomicLong now = new AtomicLong();
        StopwatchClock clock = new StopwatchClock(now::get);
        clock.start();
        now.set(TimeUnit.SECONDS.toNanos(42));
        clock.pause();
        clock.stop();
        assertFalse(clock.hasStarted());
        assertEquals("00:00:00", clock.display());
        clock.start();
        now.addAndGet(TimeUnit.SECONDS.toNanos(2));
        assertEquals("00:00:02", clock.display());
    }

    @Test void negativeClockDeltaIsNotCounted() {
        AtomicLong now = new AtomicLong(1000);
        StopwatchClock clock = new StopwatchClock(now::get);
        clock.start();
        now.set(900);
        assertEquals(0, clock.elapsedNanos());
    }
}
