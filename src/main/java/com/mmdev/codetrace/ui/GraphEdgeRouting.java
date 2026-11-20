package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Draw-only, deterministic separation of approximately collinear graph edges.
 *
 * <p>Nodes and saved graph coordinates are never changed. Edges are grouped
 * by quantized direction and line offset, then split into groups with
 * overlapping projection intervals. Only groups with multiple overlapping
 * segments receive small quadratic bends. Sorting the groups makes routing
 * independent of input edge order. The work is O(E log E) and is recalculated
 * when node positions change, never on a plain zoom/pan redraw.</p>
 */
final class GraphEdgeRouting {
    private static final double LANE_GAP = 14.0;
    private static final double MAX_BEND = 20.0;
    private static final double LINE_BUCKET = 18.0;
    private static final double DIRECTION_BUCKET = Math.PI / 36.0; // 5 degrees

    record Route(double controlX, double controlY, boolean curved) { }

    private record Segment(ProjectDependencyGraph.Edge edge, double start, double end,
                           double midX, double midY, double normalX, double normalY) { }

    private GraphEdgeRouting() { }

    static Map<ProjectDependencyGraph.Edge, Route> arrange(
            List<ProjectDependencyGraph.Edge> edges,
            Map<String, GraphLayout.Point> positions) {
        Map<String, List<Segment>> bands = new HashMap<>();
        Map<ProjectDependencyGraph.Edge, Route> routes = new LinkedHashMap<>();
        for (var edge : edges) {
            var a = positions.get(edge.source());
            var b = positions.get(edge.target());
            if (a == null || b == null) continue;
            double dx = b.x() - a.x(), dy = b.y() - a.y();
            double length = Math.hypot(dx, dy);
            if (length < 1.0) continue;
            // Choose a canonical orientation so A->B and B->A share a band.
            double tx = dx / length, ty = dy / length;
            if (tx < -1e-8 || (Math.abs(tx) <= 1e-8 && ty < 0)) {
                tx = -tx;
                ty = -ty;
            }
            double nx = -ty, ny = tx;
            double intercept = a.x() * nx + a.y() * ny;
            int direction = (int) Math.round(Math.atan2(ty, tx) / DIRECTION_BUCKET);
            long offset = Math.round(intercept / LINE_BUCKET);
            String band = direction + ":" + offset;
            double begin = a.x() * tx + a.y() * ty;
            double end = b.x() * tx + b.y() * ty;
            bands.computeIfAbsent(band, ignored -> new ArrayList<>())
                    .add(new Segment(edge, Math.min(begin, end), Math.max(begin, end),
                            (a.x() + b.x()) / 2.0, (a.y() + b.y()) / 2.0,
                            nx, ny));
        }
        for (var band : bands.values()) {
            band.sort(Comparator.comparingDouble(Segment::start)
                    .thenComparing(s -> s.edge().source())
                    .thenComparing(s -> s.edge().target())
                    .thenComparing(s -> s.edge().relation()));
            List<Segment> overlaps = new ArrayList<>();
            double extent = Double.NEGATIVE_INFINITY;
            for (var segment : band) {
                // Touching at a shared endpoint is not an overlapping stroke.
                if (!overlaps.isEmpty() && segment.start() >= extent - 1.0) {
                    place(overlaps, routes);
                    overlaps.clear();
                    extent = Double.NEGATIVE_INFINITY;
                }
                overlaps.add(segment);
                extent = Math.max(extent, segment.end());
            }
            place(overlaps, routes);
        }
        return Map.copyOf(routes);
    }

    private static void place(List<Segment> group,
                              Map<ProjectDependencyGraph.Edge, Route> routes) {
        int n = group.size();
        // Assign lanes with stable lexical order, not iteration/hash order.
        group.sort(Comparator.comparing((Segment s) -> s.edge().source())
                .thenComparing(s -> s.edge().target())
                .thenComparing(s -> s.edge().relation()));
        for (int i = 0; i < n; i++) {
            Segment segment = group.get(i);
            double shift = n == 1 ? 0 : Math.max(-MAX_BEND,
                    Math.min(MAX_BEND, (i - (n - 1) / 2.0) * LANE_GAP));
            // A quadratic Bezier reaches half its control-point displacement
            // at t=0.5, hence the factor of two.
            routes.put(segment.edge(), new Route(
                    segment.midX() + segment.normalX() * shift * 2,
                    segment.midY() + segment.normalY() * shift * 2,
                    shift != 0));
        }
    }
}
