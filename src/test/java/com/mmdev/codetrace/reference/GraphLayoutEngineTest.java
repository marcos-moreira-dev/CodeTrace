package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GraphLayoutEngineTest {
    private static ProjectDependencyGraph.Graph sample(int nodes) {
        List<String> names = new ArrayList<>();
        List<ProjectDependencyGraph.Edge> edges = new ArrayList<>();
        for (int i = 0; i < nodes; i++) names.add("src/Node" + i + ".java");
        for (int i = 1; i < nodes; i++)
            edges.add(new ProjectDependencyGraph.Edge(names.get(i - 1),
                    names.get(i), "import"));
        return new ProjectDependencyGraph.Graph("src", names, edges,
                nodes, false, "a".repeat(64));
    }

    @Test
    void gridPreservesFourColumnsWhileOrganizedAvoidsNodeLabelOverlap() {
        var graph = sample(62);
        var grid = GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.GRID);
        assertEquals(grid.get("src/Node0.java").y(),
                grid.get("src/Node3.java").y());
        assertTrue(grid.get("src/Node4.java").y() >
                grid.get("src/Node0.java").y());
        var organized = GraphLayoutEngine.arrange(graph,
                GraphLayoutEngine.Mode.ORGANIZED);
        assertEquals(62, organized.size());
        assertNotEquals(grid, organized);
        assertNoOverlaps(organized);
    }

    @Test
    void resultIsDeterministicForCyclesAndDisconnectedComponents() {
        var nodes = List.of("a/A.kt", "a/B.kt", "a/C.kt", "z/X.kt", "z/Y.kt");
        var edges = List.of(
                new ProjectDependencyGraph.Edge(nodes.get(0), nodes.get(1), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(1), nodes.get(2), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(2), nodes.get(0), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(3), nodes.get(4), "import"));
        var graph = new ProjectDependencyGraph.Graph("", nodes, edges, 5,
                false, "a".repeat(64));
        var result = GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.ORGANIZED);
        assertEquals(result, GraphLayoutEngine.arrange(graph,
                GraphLayoutEngine.Mode.ORGANIZED));
        assertNoOverlaps(result);
    }

    @Test
    void largeStarStillReservesDistinctLanesForEveryNode() {
        List<String> nodes = new ArrayList<>();
        List<ProjectDependencyGraph.Edge> edges = new ArrayList<>();
        nodes.add("src/Hub.java");
        for (int i = 0; i < 80; i++) {
            String name = "src/Service" + i + ".java";
            nodes.add(name);
            edges.add(new ProjectDependencyGraph.Edge(nodes.getFirst(), name, "import"));
        }
        var graph = new ProjectDependencyGraph.Graph("src", nodes, edges,
                81, false, "a".repeat(64));
        assertNoOverlaps(GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.ORGANIZED));
    }

    @Test
    void singleConnectionSatellitesStayNextToTheirOnlyParent() {
        List<String> nodes = List.of("src/Core.java", "src/LeafA.java",
                "src/LeafB.java", "src/LeafC.java", "src/Other.java");
        List<ProjectDependencyGraph.Edge> edges = List.of(
                new ProjectDependencyGraph.Edge(nodes.get(0), nodes.get(1), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(0), nodes.get(2), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(0), nodes.get(3), "import"),
                new ProjectDependencyGraph.Edge(nodes.get(0), nodes.get(4), "import"));
        var graph = new ProjectDependencyGraph.Graph("src", nodes, edges,
                nodes.size(), false, "a".repeat(64));
        var layout = GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.ORGANIZED);
        assertNoOverlaps(layout);
        var center = layout.get(nodes.get(0));
        for (int i = 1; i < nodes.size(); i++) {
            var leaf = layout.get(nodes.get(i));
            assertTrue(Math.abs(center.x() - leaf.x())
                    <= GraphLayoutEngine.CELL_WIDTH + 0.01);
            assertTrue(Math.abs(center.y() - leaf.y())
                    <= GraphLayoutEngine.CELL_HEIGHT + 0.01,
                    "A single-neighbor leaf must occupy a nearby cell, not orbit far away");
        }
    }

    @Test
    void sharedDependencyUsesNeighborBarycenterWithoutOverlappingLabels() {
        var names = List.of("src/Alpha.java", "src/Beta.java",
                "src/Shared.java", "src/AlphaLeaf.java", "src/BetaLeaf.java");
        var edges = List.of(
                new ProjectDependencyGraph.Edge(names.get(0), names.get(2), "import"),
                new ProjectDependencyGraph.Edge(names.get(1), names.get(2), "import"),
                new ProjectDependencyGraph.Edge(names.get(0), names.get(3), "import"),
                new ProjectDependencyGraph.Edge(names.get(1), names.get(4), "import"));
        var graph = new ProjectDependencyGraph.Graph("src", names, edges,
                names.size(), false, "a".repeat(64));
        var positions = GraphLayoutEngine.arrange(graph,
                GraphLayoutEngine.Mode.ORGANIZED);
        assertNoOverlaps(positions);
        var alpha = positions.get(names.get(0));
        var beta = positions.get(names.get(1));
        var shared = positions.get(names.get(2));
        double centroidX = (alpha.x() + beta.x()) / 2;
        double centroidY = (alpha.y() + beta.y()) / 2;
        assertTrue(Math.abs(shared.x() - centroidX)
                        <= GraphLayoutEngine.CELL_WIDTH * 1.5
                && Math.abs(shared.y() - centroidY)
                        <= GraphLayoutEngine.CELL_HEIGHT * 2.0,
                "A shared dependency should stay near its parents, not orbit far away");
        assertEquals(positions, GraphLayoutEngine.arrange(graph,
                GraphLayoutEngine.Mode.ORGANIZED), "Barycentric layout must be reproducible");
    }

    @Test
    void twoFilesConnectedByOneImportRemainDirectNeighbors() {
        var nodes = List.of("z/A.py", "z/B.py");
        var graph = new ProjectDependencyGraph.Graph("z", nodes,
                List.of(new ProjectDependencyGraph.Edge(
                        nodes.get(0), nodes.get(1), "import")),
                2, false, "a".repeat(64));
        var result = GraphLayoutEngine.arrange(graph, GraphLayoutEngine.Mode.ORGANIZED);
        assertEquals(GraphLayoutEngine.CELL_WIDTH,
                Math.abs(result.get(nodes.get(0)).x()
                        - result.get(nodes.get(1)).x()), 0.01);
        assertEquals(result, GraphLayoutEngine.arrange(
                graph, GraphLayoutEngine.Mode.ORGANIZED));
    }

    private static void assertNoOverlaps(Map<String, GraphLayout.Point> points) {
        var entries = List.copyOf(points.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                var a = entries.get(i).getValue();
                var b = entries.get(j).getValue();
                // A 220px name label and a 20px node fit inside a 260x76 box.
                boolean overlap = Math.abs(a.x() - b.x()) < 260
                        && Math.abs(a.y() - b.y()) < 76;
                assertFalse(overlap, entries.get(i).getKey() + " overlaps "
                        + entries.get(j).getKey());
            }
        }
    }
}
