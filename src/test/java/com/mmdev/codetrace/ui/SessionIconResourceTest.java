package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class SessionIconResourceTest {
    @Test void savedSessionArtworkIsBundledAndDistinctFromAppLogo() throws IOException {
        byte[] session;
        byte[] window;
        try (var stream = FileTypeIcons.class.getResourceAsStream(
                "/icons/codetrace-session.png");
             var original = FileTypeIcons.class.getResourceAsStream(
                "/icons/codetrace-window.png")) {
            assertNotNull(stream, "Missing AI-generated saved-session icon");
            assertNotNull(original, "Missing application icon");
            session = stream.readAllBytes();
            window = original.readAllBytes();
        }
        assertTrue(session.length > 1_000);
        assertEquals((byte) 0x89, session[0]);
        assertEquals('P', (char) session[1]);
        assertEquals('N', (char) session[2]);
        assertEquals('G', (char) session[3]);
        assertFalse(java.util.Arrays.equals(session, window),
                "Session and application icons must be distinct");
    }
}
