package com.mmdev.codetrace.reference;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Predictable two-dimensional graph layout that reserves space for labels.
 *
 * Grid is the legacy arrangement. Organized starts from a reproducible radial
 * graph layout, relaxes dependency springs, then assigns each node a nearby
 * unoccupied label-sized cell. The last step guarantees non-overlapping
 * labels, even around high-degree hubs and cycles.
 */
public final class GraphLayoutEngine {
    public enum Mode { GRID, ORGANIZED }

    public static final double CELL_WIDTH = 290;
    public static final double CELL_HEIGHT = 96;
    public static final double LABEL_CLEARANCE_X = 258;
    public static final double LABEL_CLEARANCE_Y = 70;
    private static final double GRID_COLUMN = 445;
    private static final double GRID_ROW = 156;
    private static final int RELAXATION_STEPS = 115;
    private static final double GOLDEN_ANGLE = Math.PI * (3 - Math.sqrt(5));
    private static final double ROW_LIMIT = 3450;

    private GraphLayoutEngine() {}

    public static Map<String, GraphLayout.Point> arrange(
            ProjectDependencyGraph.Graph graph, Mode mode) {
        if (mode == Mode.GRID) {
            Map<String, GraphLayout.Point> grid = new LinkedHashMap<>();
            for (int i = 0; i < graph.nodes().size(); i++) {
                grid.put(graph.nodes().get(i), new GraphLayout.Point(
                        130 + (i % 4) * GRID_COLUMN,
                        130 + (i / 4) * GRID_ROW));
            }
            return Map.copyOf(grid);
        }
        return organized(graph);
    }

    private static Map<String, GraphLayout.Point> organized(ProjectDependencyGraph.Graph graph) {
        Map<String, Set<String>> adjacent = new HashMap<>();
        for (String node : graph.nodes()) adjacent.put(node, new HashSet<>());
        for (var edge : graph.edges()) {
            if (adjacent.containsKey(edge.source()) && adjacent.containsKey(edge.target())) {
                adjacent.get(edge.source()).add(edge.target());
                adjacent.get(edge.target()).add(edge.source());
            }
        }

        List<List<String>> components = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String start : graph.nodes().stream().sorted().toList()) {
            if (!visited.add(start)) continue;
            List<String> members = new ArrayList<>();
            ArrayDeque<String> pending = new ArrayDeque<>();
            pending.add(start);
            while (!pending.isEmpty()) {
                String current = pending.removeFirst();
                members.add(current);
                for (String connected : adjacent.get(current).stream().sorted().toList()) {
                    if (visited.add(connected)) pending.addLast(connected);
                }
            }
            components.add(members);
        }
        components.sort(Comparator.<List<String>>comparingInt(List::size)
                .reversed().thenComparing(c -> c.stream().min(String::compareTo).orElse("")));

        Map<String, GraphLayout.Point> result = new LinkedHashMap<>();
        double packingX = 125, packingY = 125, rowHeight = 0;
        for (List<String> component : components) {
            Map<String, GraphLayout.Point> part = arrangeComponent(component, adjacent);
            double minX = part.values().stream().mapToDouble(GraphLayout.Point::x)
                    .min().orElse(0);
            double minY = part.values().stream().mapToDouble(GraphLayout.Point::y)
                    .min().orElse(0);
            double maxX = part.values().stream().mapToDouble(GraphLayout.Point::x)
                    .max().orElse(0);
            double maxY = part.values().stream().mapToDouble(GraphLayout.Point::y)
                    .max().orElse(0);
            double width = maxX - minX + 270;
            double height = maxY - minY + 105;
            if (packingX > 125 && packingX + width > ROW_LIMIT) {
                packingX = 125;
                packingY += rowHeight + 165;
                rowHeight = 0;
            }
            for (String node : component) {
                GraphLayout.Point point = part.get(node);
                result.put(node, new GraphLayout.Point(
                        packingX + point.x() - minX,
                        packingY + point.y() - minY));
            }
            packingX += width + 170;
            rowHeight = Math.max(rowHeight, height);
        }
        return Map.copyOf(result);
    }

    private static Map<String, GraphLayout.Point> arrangeComponent(
            List<String> members, Map<String, Set<String>> adjacent) {
        List<String> sorted = members.stream()
                .sorted(Comparator.<String>comparingInt(
                        n -> adjacent.get(n).size()).reversed()
                        .thenComparing(Comparator.naturalOrder())).toList();
        int n = sorted.size();
        double[] x = new double[n], y = new double[n];
        Map<String, Integer> byId = new HashMap<>();
        for (int i = 0; i < n; i++) {
            byId.put(sorted.get(i), i);
            double angle = i * GOLDEN_ANGLE;
            double radius = 135 * Math.sqrt(i);
            x[i] = radius * Math.cos(angle);
            y[i] = radius * Math.sin(angle);
        }
        // Stable spring layout; no random seed, so reopening is reproducible.
        for (int step = 0; step < RELAXATION_STEPS && n > 1; step++) {
            double[] fx = new double[n], fy = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double dx = x[i] - x[j], dy = y[i] - y[j];
                    double dist2 = Math.max(1, dx * dx + dy * dy);
                    double dist = Math.sqrt(dist2);
                    double repel = Math.min(20, 190_000 / (dist2 + 9_000));
                    double unitX = dx / dist, unitY = dy / dist;
                    fx[i] += unitX * repel;
                    fy[i] += unitY * repel;
                    fx[j] -= unitX * repel;
                    fy[j] -= unitY * repel;
                }
            }
            for (int i = 0; i < n; i++) {
                for (String neighbor : adjacent.get(sorted.get(i))) {
                    Integer j = byId.get(neighbor);
                    if (j == null || j <= i) continue;
                    double dx = x[j] - x[i], dy = y[j] - y[i];
                    double distance = Math.max(1, Math.hypot(dx, dy));
                    double target = 260;
                    double pull = Math.max(-12, Math.min(13,
                            (distance - target) * 0.048));
                    double vx = pull * dx / distance, vy = pull * dy / distance;
                    fx[i] += vx;
                    fy[i] += vy;
                    fx[j] -= vx;
                    fy[j] -= vy;
                }
            }
            double attenuation = 1 - step / (double) (RELAXATION_STEPS + 20);
            for (int i = 0; i < n; i++) {
                fx[i] -= x[i] * 0.006;
                fy[i] -= y[i] * 0.006;
                x[i] += Math.max(-24, Math.min(24, fx[i])) * attenuation;
                y[i] += Math.max(-24, Math.min(24, fy[i])) * attenuation;
            }
        }
        // Occupancy grid reserves a whole rectangle for filename + node.
        // Choosing the nearest free cell minimizes displacement from forces.
        Map<String, GraphLayout.Point> output = new LinkedHashMap<>();
        Set<Long> occupied = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int targetColumn = (int) Math.round(x[i] / CELL_WIDTH);
            int targetRow = (int) Math.round(y[i] / CELL_HEIGHT);
            int bestColumn = targetColumn, bestRow = targetRow;
            double bestScore = Double.POSITIVE_INFINITY;
            boolean located = false;
            for (int radius = 0; radius < n + 2 && !located; radius++) {
                for (int dc = -radius; dc <= radius; dc++) {
                    for (int dr = -radius; dr <= radius; dr++) {
                        if (Math.max(Math.abs(dc), Math.abs(dr)) != radius) continue;
                        int col = targetColumn + dc;
                        int row = targetRow + dr;
                        long key = (((long) col) << 32) ^ (row & 0xffffffffL);
                        if (occupied.contains(key)) continue;
                        double sx = col * CELL_WIDTH - x[i];
                        double sy = row * CELL_HEIGHT - y[i];
                        double score = sx * sx + sy * sy;
                        if (score < bestScore) {
                            bestScore = score;
                            bestColumn = col;
                            bestRow = row;
                            located = true;
                        }
                    }
                }
                // Search the whole ring; do not stop at its first free slot.
            }
            long key = (((long) bestColumn) << 32) ^ (bestRow & 0xffffffffL);
            occupied.add(key);
            output.put(sorted.get(i), new GraphLayout.Point(
                    bestColumn * CELL_WIDTH, bestRow * CELL_HEIGHT));
        }
        return output;
    }

    /** Used to reject saved arrangements that place long labels on each other. */
    public static boolean hasOverlaps(Map<String, GraphLayout.Point> points) {
        var positions = List.copyOf(points.values());
        for (int i = 0; i < positions.size(); i++) {
            var a = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++) {
                var b = positions.get(j);
                if (Math.abs(a.x() - b.x()) < LABEL_CLEARANCE_X
                        && Math.abs(a.y() - b.y()) < LABEL_CLEARANCE_Y)
                    return true;
            }
        }
        return false;
    }
}
