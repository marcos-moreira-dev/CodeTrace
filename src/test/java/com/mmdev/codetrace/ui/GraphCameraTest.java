package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GraphCameraTest {
    @Test
    void worldPointRemainsUnderCursorForEveryZoom() {
        GraphCamera camera = new GraphCamera();
        camera.center(1210, 670);
        for (double zoom : new double[]{0.25, 0.55, 0.8, 1.0, 1.75, 4.0}) {
            double worldX = camera.worldX(615, 980);
            double worldY = camera.worldY(255, 620);
            camera.zoomAt(zoom, 615, 255, 980, 620);
            assertEquals(615, camera.screenX(worldX, 980), 1e-8);
            assertEquals(255, camera.screenY(worldY, 620), 1e-8);
        }
    }

    @Test
    void panningIsTrulyUnboundedButDoesNotChangeZoomOrWorldCoordinates() {
        GraphCamera camera = new GraphCamera();
        camera.center(180, 210);
        camera.zoomAt(.55, 200, 150, 900, 700);
        double scale = camera.scale();
        double oldX = camera.centerX(), oldY = camera.centerY();
        camera.panPixels(50_000, -30_000);
        assertEquals(oldX - 50_000 / scale, camera.centerX(), 1e-7);
        assertEquals(oldY + 30_000 / scale, camera.centerY(), 1e-7);
        camera.panPixels(-50_000, 30_000);
        assertEquals(oldX, camera.centerX(), 1e-7);
        assertEquals(oldY, camera.centerY(), 1e-7);
        assertEquals(scale, camera.scale());
    }

    @Test
    void switchingZoomAtViewportCenterPreservesWorldCenter() {
        GraphCamera camera = new GraphCamera();
        camera.center(800, 900);
        camera.zoomAt(.25, 500, 350, 1000, 700);
        camera.zoomAt(3.5, 500, 350, 1000, 700);
        assertEquals(800, camera.centerX(), 1e-9);
        assertEquals(900, camera.centerY(), 1e-9);
        assertThrows(IllegalArgumentException.class,
                () -> camera.zoomAt(5.0, 0, 0, 100, 100));
    }
}
