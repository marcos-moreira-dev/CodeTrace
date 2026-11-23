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
        assertEquals(first.islands(), second.islands());
        assertEquals(3, first.islands().size(),
                "Every owner has its own distinct file island");
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
        for (var island : first.islands().entrySet()) {
            var box = island.getValue();
            for (var folder : folders.values()) {
                var folderBox = new GraphFileLayout.Bounds(folder.x() - 13,
                        folder.y() - 18, folder.x() + 261, folder.y() + 18);
                assertFalse(box.overlaps(folderBox, 10),
                        "File islands must not overlap ANY folder label.");
            }
            for (var other : first.islands().entrySet()) {
                if (island.getKey().equals(other.getKey())) continue;
                assertFalse(box.overlaps(other.getValue(), 10),
                        "Distinct folders' islands must not overlap.");
            }
            for (String file : graph.files(island.getKey())) {
                GraphLayout.Point point = first.positions().get(file);
                assertNotNull(point);
                assertTrue(point.x() - 8 >= box.minX());
                assertTrue(point.x() + 220 <= box.maxX());
                assertTrue(point.y() - 14 >= box.minY());
                assertTrue(point.y() + 15 <= box.maxY());
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
    void aFolderWithElevenFilesFormsOneCompactNearbyGrid() {
        List<String> sources = IntStream.range(0, 11)
                .mapToObj(i -> "src/app/File" + i + ".java").toList();
        var graph = new ProjectDependencyGraph.Graph("src",
                List.of("src", "src/app"), List.of(), 11, false,
                "d".repeat(64), Map.of("src/app", sources),
                List.of(new ProjectDependencyGraph.FolderLink("src", "src/app")),
                Map.of());
        var folderPositions = Map.of("src",
                new GraphLayout.Point(0, 0), "src/app",
                new GraphLayout.Point(310, 100));
        var result = GraphFileLayout.arrange(graph, folderPositions);
        assertEquals(11, result.positions().size());
        assertEquals(1, result.islands().size());
        GraphFileLayout.Bounds island = result.islands().get("src/app");
        assertNotNull(island);
        assertTrue(island.maxX() - island.minX() < 600,
                "Eleven files should not stretch into a long horizontal row");
        assertTrue(island.maxY() - island.minY() < 300);
        assertTrue(Math.hypot(island.centerX() - 310,
                        island.centerY() - 100) < 850,
                "The island should orbit its owner, not a distant grid column");
    }

    @Test
    void crowdedNeighborsKeepDistinctIslandsAndFolderLabelsFree() {
        var owners = List.of("a", "b", "c", "d", "e", "f");
        Map<String, List<String>> files = new HashMap<>();
        Map<String, GraphLayout.Point> folders = new HashMap<>();
        for (int i = 0; i < owners.size(); i++) {
            String folder = owners.get(i);
            files.put(folder, IntStream.range(0, 19)
                    .mapToObj(n -> folder + "/Source" + n + ".java").toList());
            folders.put(folder, new GraphLayout.Point((i % 3) * 290,
                    (i / 3) * 96));
        }
        var graph = new ProjectDependencyGraph.Graph(".",
                owners, List.of(), 114, false, "e".repeat(64),
                files, List.of(), Map.of());
        var result = GraphFileLayout.arrange(graph, folders);
        assertFalse(result.limited());
        assertEquals(114, result.positions().size());
        assertEquals(6, result.islands().size());
        for (var a : result.islands().values()) {
            for (var b : result.islands().values()) {
                if (a == b) continue;
                assertFalse(a.overlaps(b, 10));
            }
            for (var p : folders.values()) {
                assertFalse(a.overlaps(new GraphFileLayout.Bounds(p.x() - 13,
                        p.y() - 18, p.x() + 261, p.y() + 18), 10));
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
