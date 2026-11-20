package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Regression: zoom no longer depends on a ScrollPane's finite content bounds. */
class DependencyGraphZoomTest {
    @Test
    void cursorAnchorWorksOnAnyPartOfTheWorkspace() {
        GraphCamera camera = new GraphCamera();
        camera.center(1950, 3600);
        for (double[] cursor : new double[][] {
                {15, 25}, {435, 290}, {950, 570}, {500, 50}
        }) {
            double worldX = camera.worldX(cursor[0], 980);
            double worldY = camera.worldY(cursor[1], 620);
            camera.zoomAt(.55, cursor[0], cursor[1], 980, 620);
            assertEquals(cursor[0], camera.screenX(worldX, 980), 1e-8);
            assertEquals(cursor[1], camera.screenY(worldY, 620), 1e-8);
            camera.zoomAt(1.5, cursor[0], cursor[1], 980, 620);
            assertEquals(cursor[0], camera.screenX(worldX, 980), 1e-8);
            assertEquals(cursor[1], camera.screenY(worldY, 620), 1e-8);
        }
    }

    @Test
    void canvasSizeDoesNotDefineCameraBounds() {
        GraphCamera camera = new GraphCamera();
        camera.center(120, 200);
        camera.panPixels(-50_000, -50_000);
        assertTrue(camera.centerX() > 50_000);
        assertTrue(camera.centerY() > 50_000);
        camera.zoomAt(.25, 0, 0, 1200, 720);
        assertTrue(Double.isFinite(camera.worldX(50, 1200)));
    }
}
