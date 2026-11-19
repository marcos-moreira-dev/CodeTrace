package com.mmdev.codetrace.markdown;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TwemojiSvgAssetsTest {
    @Test void rendersBundledEmojiAsLocalVectorImagesButKeepsCodeUntouched() {
        String html = "<p>Hola 😀 🇪🇨 👨‍👩‍👧‍👦</p>"
                + "<pre><code>System.out.println(\"✅\");</code></pre>"
                + "<p>También 👩🏽‍💻</p>";
        String decorated = TwemojiSvgAssets.decorate(html);
        assertTrue(decorated.contains("data:image/svg+xml;base64,"));
        assertTrue(decorated.contains("alt=\"😀\""));
        assertTrue(decorated.contains("alt=\"🇪🇨\""));
        assertTrue(decorated.contains("alt=\"👨‍👩‍👧‍👦\""));
        assertTrue(decorated.contains("<code>System.out.println(\"✅\");</code>"));
        assertFalse(decorated.contains("https://"));
    }

    @Test void preservesAttributesAndRejectsUnsupportedCharacters() {
        assertEquals("1f469-1f3fd-200d-1f4bb",
                TwemojiSvgAssets.assetKey("👩🏽‍💻"));
        assertEquals("1f1ea-1f1e8", TwemojiSvgAssets.assetKey("🇪🇨"));
        assertEquals("31-20e3", TwemojiSvgAssets.assetKey("1️⃣"));
        String raw = "<img alt=\"😀\" src=\"data:image/png;base64,AAA=\">"
                + "<p>&lt;script&gt; ✅ </p>";
        String processed = TwemojiSvgAssets.decorate(raw);
        assertTrue(processed.startsWith("<img alt=\"😀\" src=\"data:image/png;base64,AAA=\">"));
        assertTrue(processed.contains("&lt;script&gt;"));
        assertTrue(processed.contains("class=\"codetrace-emoji\""));
        assertEquals("ASCII", TwemojiSvgAssets.decorate("ASCII"));
    }
}
