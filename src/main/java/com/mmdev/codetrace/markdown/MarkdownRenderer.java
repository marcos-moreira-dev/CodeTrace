package com.mmdev.codetrace.markdown;

import java.util.List;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/** Renders untrusted reference Markdown as a restricted, offline HTML page. */
public final class MarkdownRenderer {
    private static final List<Extension> EXTENSIONS = List.of(TablesExtension.create());

    private MarkdownRenderer() {
    }

    public static String toHtmlPage(String markdown) {
        Parser parser = Parser.builder().extensions(EXTENSIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(EXTENSIONS)
                .escapeHtml(true)
                .sanitizeUrls(true)
                .build();
        String body = renderer.render(parser.parse(markdown));
        return "<!doctype html><html lang=\"es\"><head>"
                + "<meta charset=\"utf-8\">"
                + "<meta http-equiv=\"Content-Security-Policy\" "
                + "content=\"default-src 'none'; style-src 'unsafe-inline'; img-src data:\">"
                + "<style>"
                + "body{font-family:Segoe UI,Arial,sans-serif;color:#263747;"
                + "background:#fff;margin:26px auto;padding:0 30px;max-width:980px;"
                + "font-size:15px;line-height:1.55}"
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
                + "img{max-width:100%}"
                + "</style></head><body>" + body + "</body></html>";
    }
}
