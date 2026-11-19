package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Version displayed in About must come from Maven build metadata. */
class AppVersionTest {
    @Test
    void packagedVersionIsInjectedAndSnapshotIsLabelledDevelopment() {
        String value = AppVersion.value();
        assertNotEquals("no disponible", value,
                "Maven must filter codetrace-version.properties during process-resources");
        assertTrue(value.matches("[0-9]+\\.[0-9]+\\.[0-9]+(?:[-.][A-Za-z0-9.-]+)?"),
                "Unexpected Maven semantic version: " + value);
        assertTrue(AppVersion.display().startsWith("Versión "));
        if (value.endsWith("-SNAPSHOT"))
            assertTrue(AppVersion.display().contains("desarrollo"));
    }
}
