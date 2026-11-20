package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GraphEdgeRoutingTest {
    @Test
    void oppositeDirectionsUseSeparateStableCurvesWithoutMovingNodes() {
        var forward = new ProjectDependencyGraph.Edge("A", "B", "import");
        var backward = new ProjectDependencyGraph.Edge("B", "A", "import");
        Map<String, GraphLayout.Point> nodes = Map.of(
                "A", new GraphLayout.Point(200, 100),
                "B", new GraphLayout.Point(200, 500));
        var routed = GraphEdgeRouting.arrange(List.of(forward, backward), nodes);
        assertTrue(routed.get(forward).curved());
        assertTrue(routed.get(backward).curved());
        assertTrue(routed.get(forward).controlX() < 200);
        assertTrue(routed.get(backward).controlX() > 200);
        assertEquals(new GraphLayout.Point(200, 100), nodes.get("A"));
        assertEquals(new GraphLayout.Point(200, 500), nodes.get("B"));

        var reversed = GraphEdgeRouting.arrange(List.of(backward, forward), nodes);
        assertEquals(routed, reversed, "Edge offsets must not depend on input iteration order");
    }

    @Test
    void collinearOverlappingImportsBendButSeparatedSegmentsStayStraight() {
        var longEdge = new ProjectDependencyGraph.Edge("A", "C", "import");
        var nestedEdge = new ProjectDependencyGraph.Edge("B", "C", "import");
        var isolatedEdge = new ProjectDependencyGraph.Edge("D", "E", "import");
        var coords = Map.of(
                "A", new GraphLayout.Point(300, 0),
                "B", new GraphLayout.Point(300, 150),
                "C", new GraphLayout.Point(300, 400),
                "D", new GraphLayout.Point(300, 600),
                "E", new GraphLayout.Point(300, 700));
        var routed = GraphEdgeRouting.arrange(
                List.of(longEdge, nestedEdge, isolatedEdge), coords);
        assertTrue(routed.get(longEdge).curved());
        assertTrue(routed.get(nestedEdge).curved());
        assertNotEquals(routed.get(longEdge).controlX(),
                routed.get(nestedEdge).controlX());
        assertFalse(routed.get(isolatedEdge).curved());
    }

    @Test
    void routingIgnoresMissingOrCoincidentCoordinates() {
        var missing = new ProjectDependencyGraph.Edge("A", "absent", "import");
        var coincident = new ProjectDependencyGraph.Edge("A", "B", "import");
        assertTrue(GraphEdgeRouting.arrange(List.of(missing, coincident),
                Map.of("A", new GraphLayout.Point(0, 0),
                        "B", new GraphLayout.Point(0, 0))).isEmpty());
    }
}
