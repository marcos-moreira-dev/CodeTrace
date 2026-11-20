package com.mmdev.codetrace.ui;

/**
 * A camera over graph/world coordinates. The viewport is a fixed-size canvas:
 * navigation never ends at a ScrollPane or Canvas edge.
 *
 * World coordinates are session data. Camera coordinates are temporary UI
 * state and never rewrite sources or stored node positions.
  * <p><strong>Engineering rationale.</strong> Camera transforms are a view concern, not changes to the dependency graph's underlying positions. Keeping zoom, viewport center and coordinate conversion together avoids mixing screen pixels with persistent world coordinates and makes unconstrained panning possible.</p>
*/
public final class GraphCamera {
    private double centerX;
    private double centerY;
    private double scale = 1.0;

    public double centerX() { return centerX; }
    public double centerY() { return centerY; }
    public double scale() { return scale; }

    public void center(double worldX, double worldY) {
        requireFinite(worldX);
        requireFinite(worldY);
        centerX = worldX;
        centerY = worldY;
    }

    public double screenX(double worldX, double viewportWidth) {
        return (worldX - centerX) * scale + viewportWidth / 2.0;
    }

    public double screenY(double worldY, double viewportHeight) {
        return (worldY - centerY) * scale + viewportHeight / 2.0;
    }

    public double worldX(double pixelX, double viewportWidth) {
        return centerX + (pixelX - viewportWidth / 2.0) / scale;
    }

    public double worldY(double pixelY, double viewportHeight) {
        return centerY + (pixelY - viewportHeight / 2.0) / scale;
    }

    /** Positive screen deltas move content with the hand, like a map. */
    public void panPixels(double deltaX, double deltaY) {
        center(centerX - deltaX / scale, centerY - deltaY / scale);
    }

    /** Keep precisely the same world location beneath the cursor. */
    public void zoomAt(double nextScale, double pixelX, double pixelY,
                       double viewportWidth, double viewportHeight) {
        if (!Double.isFinite(nextScale) || nextScale < 0.25 || nextScale > 4.0)
            throw new IllegalArgumentException("Zoom fuera de rango");
        double anchorX = worldX(pixelX, viewportWidth);
        double anchorY = worldY(pixelY, viewportHeight);
        scale = nextScale;
        center(anchorX - (pixelX - viewportWidth / 2.0) / scale,
                anchorY - (pixelY - viewportHeight / 2.0) / scale);
    }

    private static void requireFinite(double value) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Coordenada de cámara inválida");
    }
}
