package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class GraphOverviewSpacingTest {

    @Test
    void fileModeReservesSpaceForLocalIslandsAndKeepsSameGraph() {
        var sources = IntStream.range(0, 11)
                .mapToObj(i -> "src/app/File" + i + ".java").toList();
        var graph = new ProjectDependencyGraph.Graph("src",
                List.of("src", "src/app", "src/test", "src/tools"),
                List.of(), 11, false, "a".repeat(64),
                Map.of("src/app", sources),
                List.of(new ProjectDependencyGraph.FolderLink("src", "src/app"),
                        new ProjectDependencyGraph.FolderLink("src", "src/test"),
                        new ProjectDependencyGraph.FolderLink("src", "src/tools")),
                Map.of());
        Map<String, GraphLayout.Point> base = Map.of(
                "src/app", new GraphLayout.Point(0, 0),
                "src/test", new GraphLayout.Point(290, 0),
                "src/tools", new GraphLayout.Point(0, 96),
                "src", new GraphLayout.Point(290, 96));
        var expanded = GraphOverviewSpacing.expand(graph, base);
        assertEquals(base.keySet(), expanded.keySet());
        assertEquals(290, base.get("src/test").x() - base.get("src/app").x());
        assertTrue(expanded.get("src/test").x() - expanded.get("src/app").x() >= 790,
                "A nearby code island must fit between folder columns");
        assertTrue(expanded.get("src/tools").y() - expanded.get("src/app").y() >= 345,
                "The corresponding row must leave room for file labels");
        assertEquals(base, Map.copyOf(base), "The persisted coordinates are untouched");
        assertEquals(expanded, GraphOverviewSpacing.expand(graph, base),
                "The expanded view is deterministic");
    }

    @Test
    void noFileFoldersRetainTheirOriginalDistances() {
        var graph = new ProjectDependencyGraph.Graph(".",
                List.of("root", "child"), List.of(), 0, false, "b".repeat(64),
                Map.of(), List.of(new ProjectDependencyGraph.FolderLink("root", "child")),
                Map.of());
        var base = Map.of("root", new GraphLayout.Point(0, 0),
                "child", new GraphLayout.Point(290, 96));
        var expanded = GraphOverviewSpacing.expand(graph, base);
        assertEquals(290, expanded.get("child").x() - expanded.get("root").x());
        assertEquals(96, expanded.get("child").y() - expanded.get("root").y());
    }

    @Test
    void expansionOfLargeGraphAlwaysStaysInsideWorldBounds() {
        var dirs = IntStream.range(0, 180).mapToObj(i -> "folder/" + i).toList();
        var positions = new java.util.LinkedHashMap<String, GraphLayout.Point>();
        var files = new java.util.LinkedHashMap<String, List<String>>();
        for (int i = 0; i < dirs.size(); i++) {
            positions.put(dirs.get(i), new GraphLayout.Point(i * 500.0, i * 500.0));
            files.put(dirs.get(i), IntStream.range(0, 35)
                    .mapToObj(n -> dirs.get(i) + "/F" + n + ".java").toList());
        }
        var graph = new ProjectDependencyGraph.Graph(".",
                dirs, List.of(), 1200, true, "c".repeat(64),
                files, List.of(), Map.of());
        var expanded = GraphOverviewSpacing.expand(graph, positions);
        assertEquals(180, expanded.size());
        assertTrue(expanded.values().stream().allMatch(p ->
                Math.abs(p.x()) <= 100_000 && Math.abs(p.y()) <= 100_000));
    }
}
