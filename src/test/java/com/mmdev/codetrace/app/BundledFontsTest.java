package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class BundledFontsTest {
    @Test void redistributableFontLicenseShipsAsApplicationResource() throws Exception {
        try (var license = FontResources.class.getResourceAsStream("/fonts/OFL.txt")) {
            assertNotNull(license, "Bundled typefaces require license notice");
            String text = new String(license.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(text.contains("SIL OPEN FONT LICENSE"));
            assertTrue(text.contains("IBM Corp."));
        }
    }

    @Test void fallbackFontsAreDefinedEvenForOfflineMavenBuilds() {
        assertFalse(FontResources.codeFamily().isBlank());
        assertFalse(FontResources.uiFamily().isBlank());
    }
}
