package com.mmdev.codetrace.reference;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
        long started = PerformanceProbe.begin();
        Map<String, GraphLayout.Point> result;
        if (mode == Mode.GRID) {
            Map<String, GraphLayout.Point> grid = new LinkedHashMap<>();
            for (int i = 0; i < graph.nodes().size(); i++) {
                grid.put(graph.nodes().get(i), new GraphLayout.Point(
                        130 + (i % 4) * GRID_COLUMN,
                        130 + (i / 4) * GRID_ROW));
            }
            result = Map.copyOf(grid);
        } else {
            result = organized(graph);
        }
        PerformanceProbe.finish(mode == Mode.GRID ? "graph-grid" : "graph-organized",
                started, graph.nodes().size());
        return result;
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
        // Arrange the connected backbone first. A vertex of degree one is
        // geometrically simpler: it is attached after its parent is placed.
        // Keeping leaves out of the force simulation avoids scattering them
        // due to repulsion from unrelated nodes.
        List<String> sorted = members.stream()
                .filter(node -> adjacent.get(node).size() >= 2)
                .sorted(Comparator.<String>comparingInt(
                        n -> adjacent.get(n).size()).reversed()
                        .thenComparing(Comparator.naturalOrder())).toList();
        if (sorted.isEmpty()) {
            // A connected component with no degree >= 2 is a singleton or
            // just one edge: use the first vertex as its backbone.
            sorted = List.of(members.stream().min(String::compareTo).orElseThrow());
        }
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
        Map<String, int[]> cells = new LinkedHashMap<>();
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
            cells.put(sorted.get(i), new int[]{bestColumn, bestRow});
        }
        // The force layout may leave a vertex shared by two or more parents
        // far from their geometric average after lattice quantization. Relax
        // only backbone vertices with at least two actual placed neighbors,
        // moving into free label-sized cells near their barycenter. A single
        // occupied cell reserves the *whole* filename rectangle.
        compactSharedNodes(cells, occupied, adjacent);

        // Degree-one nodes get the nearest unoccupied cell around their
        // single neighbor. In a star, the hub is laid out first and its
        // satellites fill expanding rings; there is no reason for an
        // isolated satellite to sit far away from that hub.
        List<String> satellites = members.stream()
                .filter(node -> !cells.containsKey(node))
                .sorted(Comparator.<String, String>comparing(
                        node -> adjacent.get(node).stream().min(String::compareTo).orElse(""))
                        .thenComparing(Comparator.naturalOrder())).toList();
        for (String leaf : satellites) {
            String parent = adjacent.get(leaf).stream()
                    .min(String::compareTo).orElseThrow();
            int[] center = cells.get(parent);
            if (center == null)
                throw new IllegalStateException("Leaf parent is not in backbone: " + parent);
            int col = center[0], row = center[1];
            int bestCol = col, bestRow = row;
            boolean placed = false;
            for (int radius = 1; radius <= members.size() + 1 && !placed; radius++) {
                double best = Double.POSITIVE_INFINITY;
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {
                        if (Math.max(Math.abs(dx), Math.abs(dy)) != radius) continue;
                        int candidateCol = col + dx;
                        int candidateRow = row + dy;
                        long key = (((long) candidateCol) << 32)
                                ^ (candidateRow & 0xffffffffL);
                        if (occupied.contains(key)) continue;
                        // Prefer a satellite just right of its parent, then
                        // above/below, then left. Keep all labels separated.
                        double score = dx * dx + dy * dy
                                + (dx < 0 ? 0.2 : 0) + (dy == 0 ? 0 : 0.05);
                        if (score < best) {
                            best = score;
                            bestCol = candidateCol;
                            bestRow = candidateRow;
                            placed = true;
                        }
                    }
                }
            }
            if (!placed) throw new IllegalStateException("No free satellite cell");
            long occupiedKey = (((long) bestCol) << 32)
                    ^ (bestRow & 0xffffffffL);
            occupied.add(occupiedKey);
            cells.put(leaf, new int[]{bestCol, bestRow});
        }
        // Two-pass layout: attraction toward the barycenter first, followed by
        // collision-safe legalization of leaf rectangles. Repeat once because
        // newly placed leaves also affect the centers of shared dependencies.
        // This is strictly local and bounded; never move an occupied label.
        for (int sweep = 0; sweep < 2; sweep++) {
            compactSharedNodes(cells, occupied, adjacent);
            settleSatellites(cells, occupied, adjacent);
        }
        // Local physics is centered on (0,0), but GraphLayout.Point represents
        // persisted non-negative canvas coordinates. Normalize BEFORE records.
        int minColumn = cells.values().stream().mapToInt(p -> p[0]).min().orElse(0);
        int minRow = cells.values().stream().mapToInt(p -> p[1]).min().orElse(0);
        Map<String, GraphLayout.Point> output = new LinkedHashMap<>();
        for (var entry : cells.entrySet()) {
            int[] cell = entry.getValue();
            output.put(entry.getKey(), new GraphLayout.Point(
                    (cell[0] - minColumn) * CELL_WIDTH,
                    (cell[1] - minRow) * CELL_HEIGHT));
        }
        return output;
    }

    /**
     * Barycentric local refinement for shared dependencies. For two linked
     * parents, minimizing the sum of squared edge lengths places their child
     * near the parents' midpoint, subject to label-cell occupancy. A small
     * number of deterministic sweeps is sufficient for the bounded graph.
     *
     * <p>Each candidate cell is a 290 × 96 px rectangle containing the node
     * and its filename label. Existing reservations are never overridden.</p>
     */
    private static void compactSharedNodes(Map<String, int[]> cells,
                                           Set<Long> occupied,
                                           Map<String, Set<String>> adjacent) {
        List<String> movable = cells.keySet().stream()
                .filter(node -> adjacent.get(node).size() >= 2)
                .sorted(Comparator.<String>comparingInt(
                        node -> adjacent.get(node).size())
                        .thenComparing(Comparator.naturalOrder())).toList();
        for (int pass = 0; pass < 4; pass++) {
            boolean changed = false;
            for (String node : movable) {
                List<int[]> linked = adjacent.get(node).stream()
                        .map(cells::get).filter(java.util.Objects::nonNull).toList();
                if (linked.size() < 2) continue;
                int[] current = cells.get(node);
                double meanColumn = linked.stream().mapToInt(cell -> cell[0])
                        .average().orElse(current[0]);
                double meanRow = linked.stream().mapToInt(cell -> cell[1])
                        .average().orElse(current[1]);
                int baseColumn = (int) Math.round(meanColumn);
                int baseRow = (int) Math.round(meanRow);
                int bestColumn = current[0], bestRow = current[1];
                double best = connectionCost(bestColumn, bestRow, linked);
                // Search only near the average, never allocate arbitrary
                // world-space cells or move an occupied label rectangle.
                for (int dx = -5; dx <= 5; dx++) {
                    for (int dy = -5; dy <= 5; dy++) {
                        int col = baseColumn + dx, row = baseRow + dy;
                        long key = cellKey(col, row);
                        if (occupied.contains(key)
                                && (col != current[0] || row != current[1])) continue;
                        double score = connectionCost(col, row, linked);
                        if (score + 0.001 < best) {
                            best = score;
                            bestColumn = col;
                            bestRow = row;
                        }
                    }
                }
                if (bestColumn != current[0] || bestRow != current[1]) {
                    occupied.remove(cellKey(current[0], current[1]));
                    occupied.add(cellKey(bestColumn, bestRow));
                    cells.put(node, new int[]{bestColumn, bestRow});
                    changed = true;
                }
            }
            if (!changed) break;
        }
    }

    /**
     * Second-phase collision repair. A leaf follows its single linked parent
     * only when a closer label-sized grid cell is actually available. This
     * preserves the occupied-cell invariant after shared vertices move.
     */
    private static void settleSatellites(Map<String, int[]> cells, Set<Long> occupied,
                                         Map<String, Set<String>> adjacent) {
        List<String> leaves = cells.keySet().stream()
                .filter(node -> adjacent.get(node).size() == 1)
                .sorted().toList();
        for (String leaf : leaves) {
            String parent = adjacent.get(leaf).iterator().next();
            int[] center = cells.get(parent);
            int[] current = cells.get(leaf);
            if (center == null || current == null) continue;
            // Already in a neighboring cell: keep the stable horizontal/
            // diagonal presentation instead of migrating to a vertical slot
            // just because its pixel distance is numerically shorter.
            if (Math.abs(current[0] - center[0]) <= 1
                    && Math.abs(current[1] - center[1]) <= 1) continue;
            int bestCol = current[0], bestRow = current[1];
            double bestCost = connectionCost(bestCol, bestRow, List.of(center));
            // First ring only: on a small star every leaf must remain an
            // immediate visual neighbor; searching a second vertical ring
            // can be shorter in pixels but violates that relationship.
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int col = center[0] + dx, row = center[1] + dy;
                    if (occupied.contains(cellKey(col, row))
                            && (col != current[0] || row != current[1])) continue;
                    double candidate = connectionCost(col, row, List.of(center));
                    if (candidate + 0.001 < bestCost) {
                        bestCost = candidate;
                        bestCol = col;
                        bestRow = row;
                    }
                }
            }
            if (bestCol != current[0] || bestRow != current[1]) {
                occupied.remove(cellKey(current[0], current[1]));
                occupied.add(cellKey(bestCol, bestRow));
                cells.put(leaf, new int[]{bestCol, bestRow});
            }
        }
    }

    private static double connectionCost(int col, int row, List<int[]> neighbors) {
        double total = 0;
        for (int[] other : neighbors) {
            double dx = (col - other[0]) * CELL_WIDTH;
            double dy = (row - other[1]) * CELL_HEIGHT;
            total += dx * dx + dy * dy;
        }
        return total;
    }

    private static long cellKey(int col, int row) {
        return (((long) col) << 32) ^ (row & 0xffffffffL);
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
