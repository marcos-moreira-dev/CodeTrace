package com.mmdev.codetrace.markdown;

import com.vdurmont.emoji.EmojiParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
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
  * <p><strong>Engineering rationale.</strong> Markdown is interpreted as untrusted reference content, not as an unrestricted web page. Asset resolution, local-file boundaries and WebView policy must prevent arbitrary remote loads or script execution; rendering is independent of typing correctness.</p>
*/
public final class MarkdownRenderer {
    private static final List<Extension> EXTENSIONS = List.of(TablesExtension.create());
    private static final long MAX_IMAGE_BYTES = 8L * 1024 * 1024;
    private static final long MAX_PAGE_IMAGE_BYTES = 24L * 1024 * 1024;
    private static final Pattern HTML_IMAGE = Pattern.compile(
            "(?is)^\\s*(?:(?:<p|<div)\\b[^>]*>\\s*)?<img\\b[^>]*?/?>"
            + "\\s*(?:(?:</p>|</div>)\\s*)?$");
    private static final Pattern HTML_SRC = Pattern.compile(
            "(?i)\\bsrc\\s*=\\s*(?:\\\"([^\\\"]+)\\\"|'([^']+)'|([^\\s>]+))");
    private static final Pattern HTML_ALT = Pattern.compile(
            "(?i)\\balt\\s*=\\s*(?:\\\"([^\\\"]*)\\\"|'([^']*)'|([^\\s>]+))");

    public record RenderedPage(String html, int localImages, int externalImages,
                               int unavailableImages) {} 

    private MarkdownRenderer() {
    }

    /** Text-only fallback for callers without a reference document. */
    public static String toHtmlPage(String markdown) {
        return render(markdown, null, null, false).html();
    }

    /**
     * @param markdownFile actual reference .md path (never a virtual workspace path)
     * @param referenceRoot immutable reference directory; images cannot escape it
     */
    public static String toHtmlPage(String markdown, Path markdownFile, Path referenceRoot) {
        return render(markdown, markdownFile, referenceRoot, false).html();
    }

    /**
     * External HTTPS images are an explicit reader choice. No project can cause
     * a network request simply by being opened in CodeTrace.
     */
    public static RenderedPage render(String markdown, Path markdownFile,
                                      Path referenceRoot, boolean externalAllowed) {
        Parser parser = Parser.builder().extensions(EXTENSIONS).build();
        var document = parser.parse(markdown);
        // Expand :rocket: / :white_check_mark: etc. only in text nodes.
        // Backtick spans and fenced code blocks retain their literal syntax.
        document.accept(new AbstractVisitor() {
            @Override public void visit(Text text) {
                text.setLiteral(EmojiParser.parseToUnicode(text.getLiteral()));
            }
            @Override public void visit(HtmlInline html) {
                convertImageHtml(html, html.getLiteral());
            }
            @Override public void visit(HtmlBlock html) {
                convertImageHtml(html, html.getLiteral());
            }
        });

        AtomicLong bytesEmbedded = new AtomicLong();
        AtomicInteger local = new AtomicInteger();
        AtomicInteger external = new AtomicInteger();
        AtomicInteger unavailable = new AtomicInteger();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(EXTENSIONS)
                .escapeHtml(true)
                .sanitizeUrls(true)
                .attributeProviderFactory(context -> (node, tag, attributes) -> {
                    if (!(node instanceof Image image) || !"img".equals(tag)) return;
                    String data = inlineImage(image.getDestination(), markdownFile,
                            referenceRoot, bytesEmbedded);
                    if (data != null) {
                        local.incrementAndGet();
                        attributes.put("src", data);
                    } else if (safeExternalImage(image.getDestination())) {
                        external.incrementAndGet();
                        if (externalAllowed) {
                            attributes.put("src", image.getDestination());
                            attributes.put("referrerpolicy", "no-referrer");
                        } else {
                            attributes.remove("src");
                            attributes.put("class", "markdown-image-unavailable");
                            attributes.put("title", "Imagen externa: habilita su carga para verla");
                        }
                    } else {
                        unavailable.incrementAndGet();
                        // The reference is never permitted to read outside its root.
                        attributes.remove("src");
                        attributes.put("class", "markdown-image-unavailable");
                        attributes.put("title", "No se encontró una imagen local válida");
                    }
                })
                .build();
        String body = TwemojiSvgAssets.decorate(renderer.render(document));
        String allowedImages = externalAllowed ? "data: https:" : "data:";
        String html = "<!doctype html><html lang=\"es\"><head>"
                + "<meta charset=\"utf-8\">"
                + "<meta name=\"referrer\" content=\"no-referrer\">"
                + "<meta http-equiv=\"Content-Security-Policy\" "
                + "content=\"default-src 'none'; style-src 'unsafe-inline'; img-src "
                + allowedImages + "; font-src 'none'; form-action 'none'; "
                + "base-uri 'none'; script-src 'none'; object-src 'none'\">"
                + "<style>"
                + "body{font-family:'Segoe UI','Segoe UI Emoji',Arial,sans-serif;color:#263747;"
                + "background:#fff;margin:26px auto;padding:0 30px;max-width:980px;"
                + "font-size:15px;line-height:1.55;overflow-wrap:break-word}"
                + "h1,h2,h3{color:#304a62;border-bottom:1px solid #d6dee4;"
                + "padding-bottom:5px}"
                + "pre,code{font-family:Consolas,'Segoe UI Emoji',monospace;background:#eef1f3}"
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
                + "img.codetrace-emoji{display:inline-block;width:1.12em;"
                + "height:1.12em;vertical-align:-0.19em;border:0}"
                + "</style></head><body>" + body + "</body></html>";
        return new RenderedPage(html, local.get(), external.get(), unavailable.get());
    }

    private static void convertImageHtml(org.commonmark.node.Node original, String literal) {
        if (literal == null || !HTML_IMAGE.matcher(literal).matches()) return;
        Matcher src = HTML_SRC.matcher(literal);
        if (!src.find()) return;
        String url = group(src);
        if (url == null || url.isBlank()) return;
        url = url.replace("&amp;", "&");
        Matcher alt = HTML_ALT.matcher(literal);
        String caption = alt.find() ? group(alt) : "Imagen";
        Image image = new Image(url, null);
        image.appendChild(new Text(caption == null ? "" : caption));
        original.insertBefore(image);
        original.unlink();
    }

    private static String group(Matcher match) {
        for (int i = 1; i <= match.groupCount(); i++) {
            if (match.group(i) != null) return match.group(i);
        }
        return null;
    }

    private static boolean safeExternalImage(String url) {
        try {
            URI uri = URI.create(url);
            if (!"https".equalsIgnoreCase(uri.getScheme()) || uri.getUserInfo() != null
                    || (uri.getPort() != -1 && uri.getPort() != 443)) return false;
            String host = uri.getHost();
            if (host == null) return false;
            host = host.toLowerCase(Locale.ROOT);
            // No private hosts or numeric addresses (including IPv4/IPv6).
            return host.matches("[a-z0-9.-]+") && host.contains(".")
                    && !host.endsWith(".local") && !host.endsWith(".localhost")
                    && !host.endsWith(".internal") && !host.matches("[0-9.]+");
        } catch (IllegalArgumentException problem) {
            return false;
        }
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
                // Namespace declarations identify the SVG vocabulary; they are
                // not resource fetches (their values are ordinarily HTTP URIs).
                if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) continue;
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
