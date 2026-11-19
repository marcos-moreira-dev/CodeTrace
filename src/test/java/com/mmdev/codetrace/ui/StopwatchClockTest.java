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

    @Test void negativeClockDeltaIsNotCounted() {
        AtomicLong now = new AtomicLong(1000);
        StopwatchClock clock = new StopwatchClock(now::get);
        clock.start();
        now.set(900);
        assertEquals(0, clock.elapsedNanos());
    }
}
