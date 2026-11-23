package com.mmdev.codetrace.reference;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Optional file satellites for the existing directory graph. This is a
 * filesystem-only presentation: it never parses source code or changes folder
 * positions, dependencies, source files or the persisted .ctrace layout.
 *
 * <p>Engineering rationale: a coarse deterministic occupancy grid keeps labels
 * distinct without running an additional force simulation for up to 1,200
 * source files. Folder labels reserve their full rectangular footprint.
 * Satellites are recomputed only when files are shown or folder positions
 * change, never for camera movement or zoom.</p>
 */
public final class GraphFileLayout {
    public static final int MAX_VISIBLE_FILES = 1200;
    private static final double COLUMN = 270;
    private static final double ROW = 56;
    private static final int MAX_RING = 80;

    /** Positions only for source files, never for directory nodes. */
    public record Result(Map<String, GraphLayout.Point> positions,
                         int eligible, boolean limited) {
        public Result {
            positions = Collections.unmodifiableMap(new LinkedHashMap<>(positions));
        }
    }

    private GraphFileLayout() {}

    public static Result arrange(ProjectDependencyGraph.Graph graph,
                                 Map<String, GraphLayout.Point> folders) {
        Set<Long> occupied = new HashSet<>();
        for (GraphLayout.Point center : folders.values()) {
            // Folder footprint: x-12..x+258, y-16..y+16.
            // File footprint: x-8..x+218, y-15..y+15. Eight pixels
            // of clearance between a folder label and a file label.
            int minCol = (int) Math.ceil((center.x() - 238) / COLUMN);
            int maxCol = (int) Math.floor((center.x() + 274) / COLUMN);
            int minRow = (int) Math.ceil((center.y() - 39) / ROW);
            int maxRow = (int) Math.floor((center.y() + 39) / ROW);
            for (int col = minCol; col <= maxCol; col++) {
                for (int row = minRow; row <= maxRow; row++)
                    occupied.add(key(col, row));
            }
        }

        int eligible = 0;
        for (String folder : graph.nodes()) eligible += graph.files(folder).size();
        Map<String, GraphLayout.Point> placed = new LinkedHashMap<>();
        for (String folder : graph.nodes()) {
            GraphLayout.Point parent = folders.get(folder);
            if (parent == null) continue;
            int centerCol = (int) Math.round(parent.x() / COLUMN);
            int centerRow = (int) Math.round(parent.y() / ROW);
            for (String file : graph.files(folder)) {
                if (placed.size() >= MAX_VISIBLE_FILES)
                    return new Result(placed, eligible, true);
                boolean allocated = false;
                // Pack each file near its real parent, preferring free slots
                // to its right. All loops have explicit limits.
                for (int radius = 1; radius <= MAX_RING && !allocated; radius++) {
                    for (int dx = radius; dx >= -radius && !allocated; dx--) {
                        for (int dy = -radius; dy <= radius; dy++) {
                            if (Math.max(Math.abs(dx), Math.abs(dy)) != radius)
                                continue;
                            int col = centerCol + dx;
                            int row = centerRow + dy;
                            if (!occupied.add(key(col, row))) continue;
                            placed.put(file, new GraphLayout.Point(
                                    col * COLUMN, row * ROW));
                            allocated = true;
                            break;
                        }
                    }
                }
                if (!allocated) return new Result(placed, eligible, true);
            }
        }
        return new Result(placed, eligible, false);
    }

    private static long key(int column, int row) {
        return ((long) column << 32) ^ (row & 0xffffffffL);
    }
}
