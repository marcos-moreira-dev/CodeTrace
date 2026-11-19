package com.mmdev.codetrace.reference;

import java.util.Map;
import java.util.Objects;

/**
 * Positions of graph nodes saved with a .ctrace session.
 * The layout is reused ONLY while the reference fingerprint is unchanged.
 */
public record GraphLayout(String fingerprint, Map<String, Point> positions) {
    public record Point(double x, double y) {
        public Point {
            if (!Double.isFinite(x) || !Double.isFinite(y)
                    || x < 0 || y < 0 || x > 100_000 || y > 100_000)
                throw new IllegalArgumentException("Coordenadas de nodo inválidas");
        }
    }

    public GraphLayout {
        Objects.requireNonNull(fingerprint);
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
