package com.mmdev.codetrace.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Compact, collision-free islands of source files around their directory nodes.
 *
 * <p>This is strictly a filesystem presentation: no semantic analysis, no
 * changes to the directory layout, and no new fields persisted in .ctrace.
 * A single bounding rectangle is reserved for every island. Whole-island
 * collision checks prevent unrelated files from interleaving and stop files
 * from covering directory labels, even after a directory is dragged.</p>
 */
public final class GraphFileLayout {
    public static final int MAX_VISIBLE_FILES = 1200;
    private static final double FILE_STEP_X = 252;
    private static final double FILE_STEP_Y = 44;
    private static final int MAX_COLUMNS = 12;
    private static final int SEARCH_RINGS = 260;
    private static final double SEARCH_STEP = 54;
    private static final double GAP = 12;
    private static final double WORLD_LIMIT = 100_000;

    /** World-space footprint of a directory label or an entire file island. */
    public record Bounds(double minX, double minY, double maxX, double maxY) {
        public boolean overlaps(Bounds other, double margin) {
            return minX < other.maxX + margin && maxX + margin > other.minX
                    && minY < other.maxY + margin && maxY + margin > other.minY;
        }
        public double centerX() { return (minX + maxX) / 2; }
        public double centerY() { return (minY + maxY) / 2; }
    }

    /** File positions and their group's reserved visual footprint. */
    public record Result(Map<String, GraphLayout.Point> positions,
                         Map<String, Bounds> islands,
                         int eligible, boolean limited) {
        public Result {
            positions = Collections.unmodifiableMap(new LinkedHashMap<>(positions));
            islands = Collections.unmodifiableMap(new LinkedHashMap<>(islands));
        }
    }

    private record IslandPlacement(double originX, double originY, Bounds bounds) {}

    private GraphFileLayout() {}

    public static Result arrange(ProjectDependencyGraph.Graph graph,
                                 Map<String, GraphLayout.Point> folders) {
        List<Bounds> reserved = new ArrayList<>(folders.size() + graph.nodes().size());
        // Full directory label extents, including text and node circle.
        for (GraphLayout.Point center : folders.values())
            reserved.add(new Bounds(center.x() - 13, center.y() - 18,
                    center.x() + 261, center.y() + 18));

        int eligible = 0;
        for (String folder : graph.nodes()) eligible += graph.files(folder).size();
        Map<String, GraphLayout.Point> placed = new LinkedHashMap<>();
        Map<String, Bounds> islands = new LinkedHashMap<>();

        // Small islands take the nearest available orbit first. Previously
        // placing huge folders first could exile single-file islands across
        // the entire graph. Larger clusters expand outward only if necessary.
        List<String> owners = graph.nodes().stream()
                .filter(dir -> folders.containsKey(dir) && !graph.files(dir).isEmpty())
                .sorted(Comparator.<String>comparingInt(
                        dir -> graph.files(dir).size())
                        .thenComparing(Comparator.naturalOrder()))
                .toList();

        boolean limited = false;
        for (String owner : owners) {
            int remaining = MAX_VISIBLE_FILES - placed.size();
            if (remaining <= 0) { limited = true; break; }
            List<String> files = graph.files(owner);
            int count = Math.min(remaining, files.size());
            if (count < files.size()) limited = true;
            int columns = Math.min(MAX_COLUMNS, Math.max(1,
                    (int) Math.ceil(Math.sqrt(count * FILE_STEP_Y / FILE_STEP_X))));
            int rows = (count + columns - 1) / columns;
            double width = (columns - 1) * FILE_STEP_X + 230;
            double height = (rows - 1) * FILE_STEP_Y + 30;

            IslandPlacement island = nearestFreeIsland(
                    folders.get(owner), width, height, reserved);
            if (island == null) { limited = true; continue; }
            reserved.add(island.bounds());
            islands.put(owner, island.bounds());
            for (int i = 0; i < count; i++) {
                double x = island.originX() + (i % columns) * FILE_STEP_X;
                double y = island.originY() + (i / columns) * FILE_STEP_Y;
                placed.put(files.get(i), new GraphLayout.Point(x, y));
            }
        }
        return new Result(placed, islands, eligible, limited || eligible > placed.size());
    }

    /** The shortest free position near the folder is preferred, not a global grid. */
    private static IslandPlacement nearestFreeIsland(
            GraphLayout.Point parent, double width, double height,
            List<Bounds> reserved) {
        double left = parent.x() - 13;
        double right = parent.x() + 261;
        double top = parent.y() - 18;
        double bottom = parent.y() + 18;
        double alignX = parent.x() + 124 - width / 2;

        for (int ring = 0; ring < SEARCH_RINGS; ring++) {
            double distance = GAP + 8 + ring * SEARCH_STEP;
            // Right, left, bottom, top, and four diagonal corners.
            // The candidate's entire rectangle (not just its center)
            // must fit, preserving the no-overlap guarantee.
            double[][] origins = {
                    {right + distance, parent.y() - height / 2},
                    {left - distance - width, parent.y() - height / 2},
                    {alignX, bottom + distance},
                    {alignX, top - distance - height},
                    {right + distance, bottom + distance},
                    {right + distance, top - distance - height},
                    {left - distance - width, bottom + distance},
                    {left - distance - width, top - distance - height}
            };
            IslandPlacement best = null;
            double bestDistance = Double.POSITIVE_INFINITY;
            for (double[] origin : origins) {
                double originX = Math.rint(origin[0] + 8);
                double originY = Math.rint(origin[1] + 14);
                Bounds candidate = new Bounds(originX - 8, originY - 14,
                        originX - 8 + width, originY - 14 + height);
                if (candidate.minX() < -WORLD_LIMIT || candidate.minY() < -WORLD_LIMIT
                        || candidate.maxX() > WORLD_LIMIT
                        || candidate.maxY() > WORLD_LIMIT) continue;
                boolean blocked = false;
                for (Bounds existing : reserved) {
                    if (candidate.overlaps(existing, GAP)) {
                        blocked = true;
                        break;
                    }
                }
                if (blocked) continue;
                double score = Math.hypot(candidate.centerX() - parent.x(),
                        candidate.centerY() - parent.y());
                if (score < bestDistance) {
                    bestDistance = score;
                    best = new IslandPlacement(originX, originY, candidate);
                }
            }
            if (best != null) return best;
        }
        return null;
    }
}
