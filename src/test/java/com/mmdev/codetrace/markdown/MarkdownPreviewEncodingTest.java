package com.mmdev.codetrace.markdown;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class MarkdownPreviewEncodingTest {
    @Test void encodesEmojiSequencesAsAsciiWithoutDamagingHtmlStructure() {
        String page = MarkdownRenderer.toHtmlPage(
                "# Código 😀 🇪🇨\n\nFamilia 👨‍👩‍👧‍👦, persona 👩🏽‍💻 y keycap 1️⃣."
                + "\n\n~~~java\nSystem.out.println(\"✅\");\n~~~");
        String encoded = MarkdownPreviewEncoding.asciiSafeHtml(page);

        assertTrue(StandardCharsets.US_ASCII.newEncoder().canEncode(encoded));
        assertTrue(encoded.contains("<h1>"));
        assertTrue(encoded.contains("&#x1f600;"));
        assertTrue(encoded.contains("&#x1f1ea;&#x1f1e8;"));
        assertTrue(encoded.contains("&#x1f468;&#x200d;&#x1f469;&#x200d;"));
        assertTrue(encoded.contains("&#x1f469;&#x1f3fd;&#x200d;&#x1f4bb;"));
        assertFalse(encoded.contains("&#x31;"),
                "Ordinary ASCII digits must remain unchanged");
        assertTrue(encoded.contains("&#xfe0f;"));
        assertFalse(encoded.contains("\ufffd"),
                "Do not replace non-ASCII characters with replacement glyphs");
    }

    @Test void preservesEscapedMarkupCspAndLocalImageData() {
        String html = "<!doctype html><html><head><meta charset=\"utf-8\">"
                + "<meta http-equiv=\"Content-Security-Policy\" "
                + "content=\"default-src 'none'; img-src data:\"></head>"
                + "<body>&lt;script&gt;á 💻"
                + "<img src=\"data:image/png;base64,aGVsbG8=\" /></body></html>";
        String encoded = MarkdownPreviewEncoding.asciiSafeHtml(html);
        assertTrue(encoded.contains("&lt;script&gt;"));
        assertTrue(encoded.contains("&#xe1; &#x1f4bb;"));
        assertTrue(encoded.contains("data:image/png;base64,aGVsbG8="));
        assertTrue(encoded.contains("default-src 'none'"));
        assertEquals("plain ASCII", MarkdownPreviewEncoding.asciiSafeHtml("plain ASCII"));
        assertEquals("", MarkdownPreviewEncoding.asciiSafeHtml(null));
    }

    @Test void surrogatePairRoundTripsWithoutSplitting() {
        String text = "A😀B";
        assertEquals("A&#x1f600;B", MarkdownPreviewEncoding.asciiSafeHtml(text));
    }
}
