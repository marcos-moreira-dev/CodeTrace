package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProgressScopeTest {
    @Test
    void defaultsToSourceOnly() {
        var scope = ProgressScope.defaults();
        assertTrue(scope.includes(ProgressScope.Category.SOURCE));
        assertFalse(scope.includes(ProgressScope.Category.CONFIGURATION));
        assertFalse(scope.includes(ProgressScope.Category.DOCUMENTATION));
    }

    @Test
    void classifiesSpecialConfigurationNamesBeforeExtensions() {
        assertEquals(ProgressScope.Category.CONFIGURATION,
                ProgressScope.classify("src/pom.xml"));
        assertEquals(ProgressScope.Category.CONFIGURATION,
                ProgressScope.classify("requirements.txt"));
        assertEquals(ProgressScope.Category.SOURCE,
                ProgressScope.classify("src/Main.java"));
        assertEquals(ProgressScope.Category.SOURCE,
                ProgressScope.classify("web/app.tsx"));
        assertEquals(ProgressScope.Category.DOCUMENTATION,
                ProgressScope.classify("docs/README.md"));
        assertEquals(ProgressScope.Category.OTHER_TEXT,
                ProgressScope.classify("data/random.xyz"));
    }

    @Test
    void canEnableOptionalTextCategoriesIndependently() {
        var custom = ProgressScope.defaults().with(
                ProgressScope.Category.CONFIGURATION, true);
        assertTrue(custom.includes(ProgressScope.Category.CONFIGURATION));
        assertFalse(custom.includes(ProgressScope.Category.DOCUMENTATION));
    }
}
