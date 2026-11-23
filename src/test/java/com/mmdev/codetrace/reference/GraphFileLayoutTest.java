package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class GraphFileLayoutTest {

    @Test
    void fileSatellitesAreDeterministicAndDoNotMoveOrOverlapFolderLabels() {
        var graph = new ProjectDependencyGraph.Graph(".", List.of(".", "src", "src/ui"),
                List.of(), 4, false, "a".repeat(64),
                Map.of(".", List.of("Main.java"),
                       "src", List.of("src/Codigo.java", "src/Helper.py"),
                       "src/ui", List.of("src/ui/Panel.ts")),
                List.of(new ProjectDependencyGraph.FolderLink(".", "src"),
                        new ProjectDependencyGraph.FolderLink("src", "src/ui")),
                Map.of());
        Map<String, GraphLayout.Point> folders = new HashMap<>(Map.of(
                ".", new GraphLayout.Point(125, 125),
                "src", new GraphLayout.Point(415, 125),
                "src/ui", new GraphLayout.Point(705, 125)));
        Map<String, GraphLayout.Point> unchanged = Map.copyOf(folders);
        GraphFileLayout.Result first = GraphFileLayout.arrange(graph, folders);
        GraphFileLayout.Result second = GraphFileLayout.arrange(graph, folders);

        assertEquals(4, first.eligible());
        assertEquals(4, first.positions().size());
        assertEquals(first.positions(), second.positions());
        assertEquals(unchanged, folders, "Folder coordinates may never be changed.");
        assertFalse(first.limited());
        for (GraphLayout.Point file : first.positions().values()) {
            for (GraphLayout.Point folder : folders.values()) {
                // Same rectangles reserved by GraphFileLayout; at least 8px gap.
                assertFalse(file.x() + 218 >= folder.x() - 20
                        && file.x() - 8 <= folder.x() + 266
                        && Math.abs(file.y() - folder.y()) <= 39,
                        "A file label overlaps a real folder label.");
            }
        }
        List<GraphLayout.Point> points = new ArrayList<>(first.positions().values());
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                assertFalse(Math.abs(points.get(i).x() - points.get(j).x()) < 240
                        && Math.abs(points.get(i).y() - points.get(j).y()) < 30,
                        "File label rectangles must not overlap.");
            }
        }
    }

    @Test
    void largeFileSetsAreBoundedWithoutPretendingToShowEverything() {
        List<String> sources = IntStream.range(0, 1215)
                .mapToObj(i -> String.format("Source%04d.java", i)).toList();
        var graph = new ProjectDependencyGraph.Graph(".", List.of("."), List.of(),
                1215, false, "b".repeat(64), Map.of(".", sources),
                List.of(), Map.of());
        var result = GraphFileLayout.arrange(graph,
                Map.of(".", new GraphLayout.Point(100, 100)));
        assertEquals(1215, result.eligible());
        assertEquals(GraphFileLayout.MAX_VISIBLE_FILES, result.positions().size());
        assertTrue(result.limited());
        assertTrue(result.positions().keySet().stream()
                .allMatch(path -> path.endsWith(".java")));
    }

    @Test
    void nearMaximumWorldCoordinatesNeverThrowOrOverflow() {
        var graph = new ProjectDependencyGraph.Graph(".", List.of("."), List.of(),
                2, false, "c".repeat(64),
                Map.of(".", List.of("NearEdge.java", "Another.java")),
                List.of(), Map.of());
        var result = GraphFileLayout.arrange(graph,
                Map.of(".", new GraphLayout.Point(100_000, 100_000)));
        assertEquals(2, result.positions().size());
        for (var p : result.positions().values()) {
            assertTrue(Math.abs(p.x()) <= 100_000);
            assertTrue(Math.abs(p.y()) <= 100_000);
        }
    }
}
