package com.mmdev.codetrace.markdown;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MarkdownRendererTest {
    @Test
    void rendersHeadingsCodeAndTables() {
        String page = MarkdownRenderer.toHtmlPage(
                "# Título\n\n`x`\n\n| A | B |\n|---|---|\n| 1 | 2 |");
        assertTrue(page.contains("<h1>Título</h1>"));
        assertTrue(page.contains("<code>x</code>"));
        assertTrue(page.contains("<table>"));
        assertTrue(page.contains("Content-Security-Policy"));
    }

    @Test
    void escapesRawHtmlAndSanitizesDangerousUrls() {
        String page = MarkdownRenderer.toHtmlPage(
                "<script>alert(1)</script>\n\n[click](javascript:alert%281%29)");
        assertFalse(page.contains("<script>alert(1)</script>"));
        assertFalse(page.contains("href=\"javascript:"));
    }
}
