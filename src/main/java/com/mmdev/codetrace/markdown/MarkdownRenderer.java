package com.mmdev.codetrace.markdown;

import com.vdurmont.emoji.EmojiParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.parsers.DocumentBuilderFactory;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Image;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Offline, read-only Markdown preview of untrusted reference projects.
 * Relative image files are embedded as bounded data: URIs, without allowing
 * WebView to open file:// URLs or make network requests.
 */
public final class MarkdownRenderer {
    private static final List<Extension> EXTENSIONS = List.of(TablesExtension.create());
    private static final long MAX_IMAGE_BYTES = 8L * 1024 * 1024;
    private static final long MAX_PAGE_IMAGE_BYTES = 24L * 1024 * 1024;

    private MarkdownRenderer() {
    }

    /** Text-only fallback for callers without a reference document. */
    public static String toHtmlPage(String markdown) {
        return toHtmlPage(markdown, null, null);
    }

    /**
     * @param markdownFile actual reference .md path (never a virtual workspace path)
     * @param referenceRoot immutable reference directory; images cannot escape it
     */
    public static String toHtmlPage(String markdown, Path markdownFile, Path referenceRoot) {
        Parser parser = Parser.builder().extensions(EXTENSIONS).build();
        var document = parser.parse(markdown);
        // Expand :rocket: / :white_check_mark: etc. only in text nodes.
        // Backtick spans and fenced code blocks retain their literal syntax.
        document.accept(new AbstractVisitor() {
            @Override public void visit(Text text) {
                text.setLiteral(EmojiParser.parseToUnicode(text.getLiteral()));
            }
        });

        AtomicLong bytesEmbedded = new AtomicLong();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(EXTENSIONS)
                .escapeHtml(true)
                .sanitizeUrls(true)
                .attributeProviderFactory(context -> (node, tag, attributes) -> {
                    if (!(node instanceof Image image) || !"img".equals(tag)) return;
                    String data = inlineImage(image.getDestination(), markdownFile,
                            referenceRoot, bytesEmbedded);
                    if (data != null) {
                        attributes.put("src", data);
                        attributes.put("loading", "lazy");
                    } else {
                        // Never retain remote, file:, or traversal image URLs.
                        attributes.remove("src");
                        attributes.put("class", "markdown-image-unavailable");
                        attributes.put("title", "Imagen no disponible en la referencia");
                    }
                })
                .build();
        String body = renderer.render(document);
        return "<!doctype html><html lang=\"es\"><head>"
                + "<meta charset=\"utf-8\">"
                + "<meta http-equiv=\"Content-Security-Policy\" "
                + "content=\"default-src 'none'; style-src 'unsafe-inline'; img-src data:; "
                + "font-src 'none'; form-action 'none'; base-uri 'none'\">"
                + "<style>"
                + "body{font-family:Segoe UI,Arial,sans-serif;color:#263747;"
                + "background:#fff;margin:26px auto;padding:0 30px;max-width:980px;"
                + "font-size:15px;line-height:1.55;overflow-wrap:break-word}"
                + "h1,h2,h3{color:#304a62;border-bottom:1px solid #d6dee4;"
                + "padding-bottom:5px}"
                + "pre,code{font-family:Consolas,monospace;background:#eef1f3}"
                + "pre{padding:12px;overflow:auto;border:1px solid #cdd7df}"
                + "code{padding:1px 3px}pre code{padding:0}"
                + "table{border-collapse:collapse}td,th{border:1px solid #b9c7d1;"
                + "padding:6px 9px;text-align:left}"
                + "blockquote{border-left:3px solid #4a6076;margin-left:0;"
                + "padding-left:15px;color:#596875}"
                + "a{color:#3c6b92;pointer-events:none;text-decoration:underline}"
                + "img{max-width:100%;height:auto}"
                + "img.markdown-image-unavailable{min-width:180px;min-height:25px;"
                + "padding:8px;border:1px dashed #b8c5cf;color:#6f8190}"
                + ".emoji{font-family:'Segoe UI Emoji','Apple Color Emoji',"
                + "'Noto Color Emoji',sans-serif}"
                + "</style></head><body>" + body + "</body></html>";
    }

    private static String inlineImage(String url, Path markdownFile, Path referenceRoot,
                                      AtomicLong used) {
        if (markdownFile == null || referenceRoot == null || url == null || url.isBlank()) {
            return null;
        }
        try {
            // A Markdown image is a *path inside the source repository*, never a URL.
            String raw = url.split("[?#]", 2)[0];
            if (raw.isEmpty() || raw.startsWith("/") || raw.startsWith("\\")
                    || raw.contains(":") || raw.indexOf('\0') >= 0) return null;
            String decoded = URLDecoder.decode(raw.replace("+", "%2B"), StandardCharsets.UTF_8)
                    .replace('\\', '/');
            if (decoded.isBlank() || decoded.startsWith("/") || decoded.startsWith("//")
                    || decoded.contains(":") || decoded.indexOf('\0') >= 0) return null;

            Path root = referenceRoot.toRealPath();
            Path document = markdownFile.toRealPath();
            if (!document.startsWith(root) || !Files.isRegularFile(document)) return null;
            Path image = document.getParent().resolve(decoded).normalize().toRealPath();
            if (!image.startsWith(root) || !Files.isRegularFile(image)) return null;
            String name = image.getFileName().toString().toLowerCase(Locale.ROOT);
            String mime = mimeType(name);
            if (mime == null) return null;
            long length = Files.size(image);
            if (length <= 0 || length > MAX_IMAGE_BYTES
                    || used.get() + length > MAX_PAGE_IMAGE_BYTES) return null;
            byte[] payload = Files.readAllBytes(image);
            if (payload.length > MAX_IMAGE_BYTES
                    || (mime.equals("image/svg+xml") && !safeSvg(payload))) return null;
            used.addAndGet(payload.length);
            return "data:" + mime + ";base64," + Base64.getEncoder().encodeToString(payload);
        } catch (IOException | RuntimeException problem) {
            return null;
        }
    }

    private static String mimeType(String name) {
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".webp")) return "image/webp";
        if (name.endsWith(".bmp")) return "image/bmp";
        if (name.endsWith(".svg")) return "image/svg+xml";
        return null;
    }

    /** Reject active SVG features and external references before embedding. */
    private static boolean safeSvg(byte[] payload) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            Node root = factory.newDocumentBuilder().parse(new ByteArrayInputStream(payload))
                    .getDocumentElement();
            return "svg".equals(root.getLocalName()) && safeSvgNode(root);
        } catch (Exception problem) {
            return false;
        }
    }

    private static boolean safeSvgNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String name = node.getLocalName();
            if (name == null) return false;
            if (List.of("script", "foreignObject", "iframe", "object", "embed",
                    "image", "use", "audio", "video", "a", "style").contains(name)) return false;
            NamedNodeMap attrs = node.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attribute = attrs.item(i);
                String attrName = attribute.getNodeName().toLowerCase(Locale.ROOT);
                String value = attribute.getNodeValue().toLowerCase(Locale.ROOT);
                if (attrName.startsWith("on") || attrName.endsWith(":href")
                        || attrName.equals("href") || value.contains("url(")
                        || value.contains("@import") || value.contains("javascript:")
                        || value.contains("https:") || value.contains("http:")
                        || value.contains("file:")) return false;
            }
        }
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (!safeSvgNode(child)) return false;
        }
        return true;
    }
}
