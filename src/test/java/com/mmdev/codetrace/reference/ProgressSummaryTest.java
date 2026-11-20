package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProgressSummaryTest {
    @Test void calculatesRealCorrectCharactersWithoutIncludingWrongOrExcludedFiles() {
        var catalogue = new ReferenceCatalogue.Snapshot(Map.of(
                "src/Main.java", new ReferenceCatalogue.FileInfo("shaMain", 4, 4),
                "src/generated/Gen.java",
                    new ReferenceCatalogue.FileInfo("shaGen", 5, 5),
                "README.md", new ReferenceCatalogue.FileInfo("shaReadme", 5, 5)));
        var workspace = new VirtualWorkspace();
        workspace.ensureFile("src/Main.java");
        TypingSession typed = new TypingSession("abcd");
        typed.type("abXd");
        Map<String, TypingSession> sessions = Map.of("src/Main.java", typed);
        Map<String, String> hashes = Map.of("src/Main.java", "shaMain");
        var scope = ProgressScope.defaults();
        var exclusions = ProgressExclusions.empty().withFolder("src/generated", true);
        var summary = ProgressSummary.calculate(catalogue, workspace, sessions, hashes,
                scope, "src", exclusions);
        assertEquals(4, summary.totalUnits());
        assertEquals(3, summary.correctUnits());
        assertEquals(1, summary.includedFiles());
        assertEquals(.75, summary.ratio());
        assertTrue(summary.hasEvaluableText());

        var changed = ProgressSummary.calculate(catalogue, workspace, sessions,
                Map.of("src/Main.java", "outdated"), scope, "src", exclusions);
        assertEquals(0, changed.correctUnits());
        assertEquals(4, changed.totalUnits());
        var empty = ProgressSummary.calculate(catalogue, workspace, sessions, hashes,
                scope, "nothing", exclusions);
        assertEquals(0, empty.ratio());
        assertFalse(empty.hasEvaluableText());
    }
}
