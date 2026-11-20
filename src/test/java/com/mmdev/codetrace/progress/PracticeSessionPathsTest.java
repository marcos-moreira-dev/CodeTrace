package com.mmdev.codetrace.progress;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PracticeSessionPathsTest {
    @Test void subtreeOperationsPreserveOtherPrefixNeighborsAndHashAlignment() {
        TypingSession one = new TypingSession("a");
        TypingSession two = new TypingSession("b");
        Map<String, TypingSession> sessions = new LinkedHashMap<>();
        sessions.put("src/A.java", one);
        sessions.put("src2/B.java", two);
        sessions.put("src/nested/C.java", new TypingSession("c"));
        var original = new PracticeSessionPaths(sessions,
                Map.of("src/A.java", "shaA", "src2/B.java", "shaB",
                        "src/nested/C.java", "shaC"));
        sessions.clear();
        assertEquals(3, original.sessions().size(), "Registry snapshots its input maps");

        var removed = original.withoutSubtree("src");
        assertEquals(Map.of("src2/B.java", two), removed.sessions());
        assertEquals(Map.of("src2/B.java", "shaB"), removed.hashes());
        assertEquals(3, original.sessions().size(), "Original state is unchanged");

        var moved = original.movedSubtree("src", "lib");
        assertSame(one, moved.sessions().get("lib/A.java"));
        assertTrue(moved.sessions().containsKey("lib/nested/C.java"));
        assertTrue(moved.sessions().containsKey("src2/B.java"));
        assertEquals("shaA", moved.hashes().get("lib/A.java"));
        assertEquals("shaC", moved.hashes().get("lib/nested/C.java"));
        assertFalse(moved.sessions().containsKey("src/A.java"));
        assertThrows(UnsupportedOperationException.class,
                () -> moved.sessions().clear());
    }

    @Test void movingJustOneFileDoesNotMoveSiblings() {
        var original = new PracticeSessionPaths(
                Map.of("A.java", new TypingSession("a"),
                       "AA.java", new TypingSession("aa")),
                Map.of("A.java", "one", "AA.java", "two"));
        var moved = original.movedSubtree("A.java", "B.java");
        assertTrue(moved.sessions().containsKey("B.java"));
        assertTrue(moved.sessions().containsKey("AA.java"));
        assertEquals("two", moved.hashes().get("AA.java"));
    }
}
