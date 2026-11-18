package com.mmdev.codetrace.markdown;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MarkdownRendererTest {
    @TempDir Path temp;

    @Test
    void rendersHeadingsCodeTablesAndEmojiAliases() {
        String page = MarkdownRenderer.toHtmlPage(
                "# Título :rocket:\n\n`x` and :white_check_mark:\n\n"
                + "| A | B |\n|---|---|\n| 1 | 2 |");
        assertTrue(page.contains("<h1>Título 🚀</h1>"), page);
        assertTrue(page.contains("<code>x</code>"));
        assertTrue(page.contains("✅"), page);
        assertTrue(page.contains("<table>"));
        assertTrue(page.contains("Content-Security-Policy"));
    }

    @Test
    void keepsCodeSamplesUnchangedWhileRenderingTextEmojis() {
        String page = MarkdownRenderer.toHtmlPage(
                "An emoji :smile: is fine.\n\n`:rocket:`\n\n```bash\necho :rocket:\n```");
        assertTrue(page.contains("😄") || page.contains("😃") || page.contains("😆"));
        assertTrue(page.contains("<code>:rocket:</code>"));
        assertTrue(page.contains("echo :rocket:"));
    }

    @Test
    void embedsReferenceImagesRelativeToDocumentWithoutNetwork(@TempDir Path folder)
            throws Exception {
        Path root = folder.resolve("source");
        Path docs = root.resolve("docs");
        Path media = root.resolve("assets");
        Files.createDirectories(docs);
        Files.createDirectories(media);
        Path document = Files.writeString(docs.resolve("README.md"), "# Demo");
        byte[] png = Base64.getDecoder().decode(
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQIHWP4"
                + "z8DwHwAFgAI/ScLttAAAAABJRU5ErkJggg==");
        Files.write(media.resolve("foto con espacio.png"), png);
        String page = MarkdownRenderer.toHtmlPage(
                "![Fotografía](../assets/foto%20con%20espacio.png)",
                document, root);
        assertTrue(page.contains("data:image/png;base64," + Base64.getEncoder()
                .encodeToString(png)), page);
        assertTrue(page.contains("alt=\"Fotografía\""));
        assertFalse(page.contains("src=\"file:"));
    }

    @Test
    void blocksEscapesRemoteLinksAndMissingAssets(@TempDir Path folder)
            throws Exception {
        Path root = Files.createDirectory(folder.resolve("source"));
        Path document = Files.writeString(root.resolve("README.md"), "# Demo");
        Files.write(folder.resolve("secret.png"), "private".getBytes());
        String page = MarkdownRenderer.toHtmlPage(
                "![outside](../secret.png)\n\n![web](https://example.com/tracker.png)"
                + "\n\n![missing](images/no.png)",
                document, root);
        assertFalse(page.contains("private"));
        assertFalse(page.contains("src=\"https:"));
        assertFalse(page.contains("src=\"file:"));
        assertFalse(page.contains("data:image/png;base64,"));
        assertTrue(page.contains("markdown-image-unavailable"));
    }

    @Test
    void embedsSafeSvgButNotSvgScripts(@TempDir Path folder) throws Exception {
        Path root = Files.createDirectory(folder.resolve("source"));
        Path doc = Files.writeString(root.resolve("README.md"), "# Demo");
        Files.writeString(root.resolve("ok.svg"),
                "<svg xmlns=\"http://www.w3.org/2000/svg\">"
                + "<rect width=\"30\" height=\"20\" /></svg>");
        Files.writeString(root.resolve("bad.svg"),
                "<svg xmlns=\"http://www.w3.org/2000/svg\">"
                + "<script>evil()</script></svg>");
        String page = MarkdownRenderer.toHtmlPage(
                "![good](ok.svg)\n\n![bad](bad.svg)", doc, root);
        assertTrue(page.contains("data:image/svg+xml;base64,"));
        assertTrue(page.contains("markdown-image-unavailable"));
        assertFalse(page.contains("<script>evil()</script>"));
    }

    @Test
    void escapesRawHtmlAndSanitizesDangerousUrls() {
        String page = MarkdownRenderer.toHtmlPage(
                "<script>alert(1)</script>\n\n[click](javascript:alert%281%29)");
        assertFalse(page.contains("<script>alert(1)</script>"));
        assertFalse(page.contains("href=\"javascript:"));
    }
}
