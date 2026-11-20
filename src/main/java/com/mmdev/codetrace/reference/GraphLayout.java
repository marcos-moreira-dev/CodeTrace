package com.mmdev.codetrace.reference;

import java.util.Map;
import java.util.Objects;

/**
 * Positions of graph nodes saved with a .ctrace session.
 * The layout is reused ONLY while the reference fingerprint is unchanged.
  * <p><strong>Engineering rationale.</strong> A saved layout contains positions in graph/world coordinates for a specific graph fingerprint and mode. It is session metadata, not a mutation of source imports or a snapshot of the current zoom/pan camera.</p>
*/
public record GraphLayout(String fingerprint, Map<String, Point> positions,
                          GraphLayoutEngine.Mode mode) {
    /** Sessions written before layout-selection support used the grid mode. */
    public GraphLayout(String fingerprint, Map<String, Point> positions) {
        this(fingerprint, positions, GraphLayoutEngine.Mode.GRID);
    }

    public record Point(double x, double y) {
        public Point {
            if (!Double.isFinite(x) || !Double.isFinite(y)
                    || x < -100_000 || y < -100_000
                    || x > 100_000 || y > 100_000)
                throw new IllegalArgumentException("Coordenadas de nodo inválidas");
        }
    }

    public GraphLayout {
        Objects.requireNonNull(fingerprint);
        Objects.requireNonNull(mode);
        if (!fingerprint.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Huella del grafo inválida");
        positions = Map.copyOf(positions);
        if (positions.size() > 180) throw new IllegalArgumentException("Demasiados nodos");
        for (var entry : positions.entrySet()) {
            if (entry.getKey().isBlank() || entry.getValue() == null)
                throw new IllegalArgumentException("Nodo inválido");
        }
    }
}
