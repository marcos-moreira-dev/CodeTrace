package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProgressExclusionsTest {
    @TempDir Path folder;

    @Test void includesOtherPathsWhileExcludingNestedFiles() {
        var model = ProgressExclusions.empty().withFolder("src/generated", true);
        assertTrue(model.excludes("src/generated"));
        assertTrue(model.excludes("src/generated/More.java"));
        assertFalse(model.excludes("src/generated2/More.java"));
        assertFalse(model.excludes("src/Other.java"));
        assertFalse(model.withFolder("src/generated", false).excludes("src/generated/More.java"));
        assertThrows(IllegalArgumentException.class, () -> model.withFolder("../secret", true));
    }

    @Test void remembersExclusionsIndependentlyForEachProject() {
        Path first = folder.resolve("first");
        Path second = folder.resolve("second");
        ProgressExclusions.empty().withFolder("generated", true).save(first);
        assertTrue(ProgressExclusions.load(first).excludes("generated/file.java"));
        assertFalse(ProgressExclusions.load(second).excludes("generated/file.java"));
        ProgressExclusions.empty().save(first);
    }
}
