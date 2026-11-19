package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Mathematical regression for cursor-anchored graph zoom. */
class DependencyGraphZoomTest {
    @Test
    void mouseAnchorKeepsSameLogicalPointUnderCursorAcrossZoomLevels() {
        double logical = 1030;
        double viewportPixel = 235;
        double contentExtent = 3300;
        double viewportExtent = 960;
        for (double zoom : new double[]{0.40, 0.55, 0.80, 1.0, 1.6, 2.0}) {
            double h = DependencyGraphPane.anchoredScrollValue(
                    logical, viewportPixel, contentExtent, viewportExtent, zoom);
            double effectiveLeft = h * (contentExtent * zoom - viewportExtent);
            assertEquals(viewportPixel, logical * zoom - effectiveLeft, 1e-9,
                    "The same map point must stay below the mouse cursor at any zoom");
        }
    }

    @Test
    void tinyContentAndEdgesClampScrollSafely() {
        assertEquals(0, DependencyGraphPane.anchoredScrollValue(
                20, 500, 300, 1200, 0.5));
        assertEquals(0, DependencyGraphPane.anchoredScrollValue(
                -300, 100, 3300, 960, 1.0));
        assertEquals(1, DependencyGraphPane.anchoredScrollValue(
                99999, 200, 3300, 960, 1.0));
    }
}
