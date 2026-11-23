package com.mmdev.codetrace.reference;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Expands the SAME folder graph for the optional file-island presentation.
 *
 * <p>Normal folder-only coordinates remain the persisted source of truth.
 * This is a temporary, deterministic display transform: neighboring columns
 * and rows receive only the extra space needed by the direct source files
 * near those folder nodes. With no sources, the original coordinate gaps are
 * preserved. Dependencies and parent-child relationships never change.</p>
 */
public final class GraphOverviewSpacing {
    private static final double FILE_STEP_X = 252;
    private static final double FILE_STEP_Y = 44;
    private static final double MAX_WORLD_SPAN = 190_000;

    private GraphOverviewSpacing() {}

    public static Map<String, GraphLayout.Point> expand(
            ProjectDependencyGraph.Graph graph, Map<String, GraphLayout.Point> base) {
        if (base.isEmpty()) return Map.of();
        Map<Double, Double> neededX = new HashMap<>();
        Map<Double, Double> neededY = new HashMap<>();
        for (var entry : base.entrySet()) {
            int count = Math.min(GraphFileLayout.MAX_VISIBLE_FILES,
                    graph.files(entry.getKey()).size());
            if (count <= 0) continue;
            int cols = Math.min(12, Math.max(1,
                    (int) Math.ceil(Math.sqrt(count * FILE_STEP_Y / FILE_STEP_X))));
            int rows = (count + cols - 1) / cols;
            double width = (cols - 1) * FILE_STEP_X + 230;
            double height = (rows - 1) * FILE_STEP_Y + 30;
            // Reserve a usable orbit to the right and down. The island
            // allocator still chooses any nearest clear side if necessary.
            neededX.merge(entry.getValue().x(), 310 + width + 40, Math::max);
            neededY.merge(entry.getValue().y(), height + 105, Math::max);
        }
        Map<Double, Double> columns = expandAxis(
                base.values().stream().map(GraphLayout.Point::x).distinct().sorted().toList(),
                neededX);
        Map<Double, Double> rows = expandAxis(
                base.values().stream().map(GraphLayout.Point::y).distinct().sorted().toList(),
                neededY);

        double minX = columns.values().stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double maxX = columns.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double minY = rows.values().stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double maxY = rows.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double scale = Math.min(1, Math.min(
                MAX_WORLD_SPAN / Math.max(1, maxX - minX),
                MAX_WORLD_SPAN / Math.max(1, maxY - minY)));
        double centerX = (minX + maxX) / 2, centerY = (minY + maxY) / 2;
        Map<String, GraphLayout.Point> output = new LinkedHashMap<>();
        for (var entry : base.entrySet()) {
            GraphLayout.Point p = entry.getValue();
            output.put(entry.getKey(), new GraphLayout.Point(
                    (columns.get(p.x()) - centerX) * scale,
                    (rows.get(p.y()) - centerY) * scale));
        }
        return Map.copyOf(output);
    }

    private static Map<Double, Double> expandAxis(
            List<Double> coordinates, Map<Double, Double> requiredAfter) {
        Map<Double, Double> transformed = new HashMap<>();
        if (coordinates.isEmpty()) return transformed;
        double previous = coordinates.getFirst();
        double positioned = previous;
        transformed.put(previous, positioned);
        for (int i = 1; i < coordinates.size(); i++) {
            double next = coordinates.get(i);
            positioned += Math.max(next - previous, requiredAfter.getOrDefault(previous, 0.0));
            transformed.put(next, positioned);
            previous = next;
        }
        return transformed;
    }
}
