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
 * Deterministic, geometry-aware automatic dependency layouts.
 *
 * GRID retains the familiar four-column view. ORGANIZED groups connected
 * components and places nearby dependency layers together. Barycentric sweeps
 * reduce many edge crossings; stable lanes keep node labels from overlapping.
 * There is intentionally no random layout or dependency on Graphviz.
 */
public final class GraphLayoutEngine {
    public enum Mode { GRID, ORGANIZED }

    private static final double GRID_COLUMN = 445;
    private static final double GRID_ROW = 156;
    private static final double LAYER_COLUMN = 290;
    private static final double LANE_ROW = 110;
    private static final double COMPONENT_GAP = 180;

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
        Set<String> seen = new HashSet<>();
        for (String start : graph.nodes()) {
            if (!seen.add(start)) continue;
            List<String> component = new ArrayList<>();
            ArrayDeque<String> pending = new ArrayDeque<>();
            pending.add(start);
            while (!pending.isEmpty()) {
                String node = pending.removeFirst();
                component.add(node);
                for (String other : adjacent.getOrDefault(node, Set.of()).stream()
                        .sorted().toList()) {
                    if (seen.add(other)) pending.add(other);
                }
            }
            components.add(component);
        }
        components.sort(Comparator.<List<String>>comparingInt(List::size)
                .reversed().thenComparing(c -> c.stream().min(String::compareTo).orElse("")));
        Map<String, GraphLayout.Point> result = new LinkedHashMap<>();
        double nextY = 130;
        for (List<String> component : components) {
            String center = component.stream()
                    .sorted(Comparator.<String>comparingInt(
                            n -> adjacent.get(n).size()).reversed()
                            .thenComparing(Comparator.naturalOrder()))
                    .findFirst().orElseThrow();
            Map<String, Integer> depths = new HashMap<>();
            depths.put(center, 0);
            ArrayDeque<String> pending = new ArrayDeque<>();
            pending.add(center);
            while (!pending.isEmpty()) {
                String node = pending.removeFirst();
                for (String other : adjacent.get(node).stream().sorted().toList()) {
                    if (!depths.containsKey(other)) {
                        depths.put(other, depths.get(node) + 1);
                        pending.add(other);
                    }
                }
            }
            int layerCount = depths.values().stream().max(Integer::compareTo).orElse(0) + 1;
            List<List<String>> layers = new ArrayList<>();
            for (int i = 0; i < layerCount; i++) layers.add(new ArrayList<>());
            for (String node : component) layers.get(depths.get(node)).add(node);
            for (var layer : layers) layer.sort(String::compareTo);
            // Successive barycenter sweeps: maintain one non-overlapping lane per
            // node, while putting strongly related nodes next to each other.
            for (int sweep = 0; sweep < 5; sweep++) {
                for (int column = 1; column < layers.size(); column++)
                    sortNear(layers.get(column), layers.get(column - 1), adjacent);
                for (int column = layers.size() - 2; column >= 0; column--)
                    sortNear(layers.get(column), layers.get(column + 1), adjacent);
            }
            int heightSlots = layers.stream().mapToInt(List::size).max().orElse(1);
            double blockHeight = Math.max(180, heightSlots * LANE_ROW);
            for (int column = 0; column < layers.size(); column++) {
                List<String> layer = layers.get(column);
                double top = nextY + (blockHeight - layer.size() * LANE_ROW) * 0.5;
                for (int row = 0; row < layer.size(); row++) {
                    result.put(layer.get(row), new GraphLayout.Point(
                            130 + column * LAYER_COLUMN,
                            top + row * LANE_ROW + LANE_ROW * 0.5));
                }
            }
            nextY += blockHeight + COMPONENT_GAP;
        }
        return Map.copyOf(result);
    }

    private static void sortNear(List<String> current, List<String> adjacentLayer,
                                 Map<String, Set<String>> neighbors) {
        Map<String, Integer> order = new HashMap<>();
        for (int i = 0; i < adjacentLayer.size(); i++)
            order.put(adjacentLayer.get(i), i);
        Map<String, Double> barycenter = new HashMap<>();
        for (String node : current) {
            double weighted = 0;
            int count = 0;
            for (String other : neighbors.get(node)) {
                Integer index = order.get(other);
                if (index != null) {
                    weighted += index;
                    count++;
                }
            }
            barycenter.put(node, count == 0 ? Double.POSITIVE_INFINITY
                    : weighted / count);
        }
        current.sort(Comparator.comparingDouble((String n) -> barycenter.get(n))
                .thenComparing(Comparator.naturalOrder()));
    }
}
