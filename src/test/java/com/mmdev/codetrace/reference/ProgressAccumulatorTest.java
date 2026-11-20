package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProgressAccumulatorTest {
    @Test
    void updatesOnlyChangedFileWhileMatchingFullCatalogueProgress() {
        var catalogue = new ReferenceCatalogue.Snapshot(Map.of(
                "src/A.java", new ReferenceCatalogue.FileInfo("sha-a", 4, 4),
                "src/B.java", new ReferenceCatalogue.FileInfo("sha-b", 5, 5),
                "README.md", new ReferenceCatalogue.FileInfo("sha-readme", 3, 3)));
        var workspace = new VirtualWorkspace();
        workspace.ensureFile("src/A.java");
        workspace.ensureFile("src/B.java");
        var first = new TypingSession("abcd");
        var second = new TypingSession("12345");
        var sessions = new HashMap<String, TypingSession>();
        sessions.put("src/A.java", first);
        sessions.put("src/B.java", second);
        var hashes = new HashMap<String, String>();
        hashes.put("src/A.java", "sha-a");
        hashes.put("src/B.java", "sha-b");
        var exclusions = ProgressExclusions.empty();
        var scope = ProgressScope.defaults();

        var accumulator = new ProgressAccumulator(catalogue, workspace,
                sessions, hashes, scope, "", exclusions);
        assertEquals(9, accumulator.summary().totalUnits());
        assertEquals(2, accumulator.summary().includedFiles());
        assertEquals(0, accumulator.summary().correctUnits());

        first.type("abXd");
        accumulator.updateFile("src/A.java", workspace, first, hashes.get("src/A.java"));
        assertEquals(3, accumulator.summary().correctUnits());
        second.type("12345");
        accumulator.updateFile("src/B.java", workspace, second, hashes.get("src/B.java"));
        assertEquals(8, accumulator.summary().correctUnits());

        first.backspace();
        accumulator.updateFile("src/A.java", workspace, first, hashes.get("src/A.java"));
        assertEquals(7, accumulator.summary().correctUnits());
        first.type("d");
        accumulator.updateFile("src/A.java", workspace, first, "changed-hash");
        assertEquals(5, accumulator.summary().correctUnits());
        accumulator.updateFile("src/A.java", workspace, first, "sha-a");
        assertEquals(8, accumulator.summary().correctUnits());
        assertEquals(ProgressSummary.calculate(catalogue, workspace, sessions, hashes,
                scope, "", exclusions), accumulator.summary());
    }

    @Test
    void filtersAndWorkspaceChangesRequireRebuild() {
        var catalogue = new ReferenceCatalogue.Snapshot(Map.of(
                "src/A.java", new ReferenceCatalogue.FileInfo("sha-a", 3, 3),
                "generated/B.java", new ReferenceCatalogue.FileInfo("sha-b", 2, 2)));
        var workspace = new VirtualWorkspace();
        workspace.ensureFile("src/A.java");
        var typed = new TypingSession("abc");
        typed.type("abc");
        var sessions = Map.of("src/A.java", typed);
        var hashes = Map.of("src/A.java", "sha-a");
        var scope = ProgressScope.defaults();
        var exclusions = ProgressExclusions.empty();

        var accumulator = new ProgressAccumulator(catalogue, workspace,
                sessions, hashes, scope, "", exclusions);
        assertEquals(3, accumulator.summary().correctUnits());
        assertFalse(accumulator.matches(catalogue, scope, "src", exclusions));
        var scoped = new ProgressAccumulator(catalogue, workspace,
                sessions, hashes, scope, "src", exclusions);
        assertEquals(3, scoped.summary().totalUnits());
        assertEquals(3, scoped.summary().correctUnits());

        workspace.delete("src/A.java");
        var rebuilt = new ProgressAccumulator(catalogue, workspace,
                sessions, hashes, scope, "src", exclusions);
        assertEquals(0, rebuilt.summary().correctUnits());

        var otherExclusions = exclusions.withFolder("src", true);
        assertFalse(scoped.matches(catalogue, scope, "src", otherExclusions));
        var excluded = new ProgressAccumulator(catalogue, workspace,
                sessions, hashes, scope, "", otherExclusions);
        assertEquals(2, excluded.summary().totalUnits());
        assertEquals(1, excluded.summary().includedFiles());
        assertTrue(excluded.summary().correctUnits() <= excluded.summary().totalUnits());
    }
}
