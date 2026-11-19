package com.mmdev.codetrace.markdown;

import com.mmdev.codetrace.practice.EmojiUnits;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Offline SVG emoji replacement for the trusted, already-sanitized HTML body.
 * SVG files are official CC-BY-4.0 Twemoji graphics bundled at build time.
 * Only text between tags is changed; tag attributes, code/pre contents and
 * image URLs from user-authored Markdown remain untouched.
 */
public final class TwemojiSvgAssets {
    private static final int MAX_SVG_BYTES = 65_536;
    private static final Map<String, Optional<String>> CACHE = new ConcurrentHashMap<>();
    private TwemojiSvgAssets() { }

    public static String decorate(String html) {
        if (html == null || html.isEmpty()) return "";
        StringBuilder out = new StringBuilder(html.length() + 120);
        int index = 0;
        int codeDepth = 0;
        while (index < html.length()) {
            int open = html.indexOf('<', index);
            if (open < 0) {
                appendText(out, html.substring(index), codeDepth == 0);
                break;
            }
            appendText(out, html.substring(index, open), codeDepth == 0);
            int end = html.indexOf('>', open + 1);
            if (end < 0) {
                out.append(html.substring(open));
                break;
            }
            String tag = html.substring(open, end + 1);
            String lower = tag.toLowerCase(Locale.ROOT);
            if (lower.matches("</(?:code|pre)\\s*>")) {
                codeDepth = Math.max(0, codeDepth - 1);
            }
            out.append(tag);
            if (lower.matches("<(?:code|pre)(?:\\s[^>]*)?>"))
                codeDepth++;
            index = end + 1;
        }
        return out.toString();
    }

    private static void appendText(StringBuilder out, String text, boolean decorate) {
        if (text.isEmpty()) return;
        if (!decorate || text.codePoints().allMatch(cp -> cp <= 0x7f)) {
            out.append(text);
            return;
        }
        for (String cluster : EmojiUnits.graphemes(text)) {
            if (!EmojiUnits.isEmoji(cluster)) {
                out.append(cluster);
                continue;
            }
            String key = assetKey(cluster);
            Optional<String> svg = CACHE.computeIfAbsent(key, TwemojiSvgAssets::svgData);
            if (svg.isEmpty()) {
                out.append(cluster); // Font fallback remains available.
            } else {
                out.append("<img class=\"codetrace-emoji\" alt=\"")
                        .append(cluster)
                        .append("\" src=\"")
                        .append(svg.get())
                        .append("\" loading=\"lazy\" />");
            }
        }
    }

    /** Twemoji filenames omit U+FE0F except inside ZWJ compositions. */
    public static String assetKey(String grapheme) {
        boolean hasJoiner = grapheme.codePoints().anyMatch(cp -> cp == 0x200d);
        StringBuilder key = new StringBuilder();
        grapheme.codePoints().forEach(cp -> {
            if (cp == 0xfe0f && !hasJoiner) return;
            if (!key.isEmpty()) key.append('-');
            key.append(Integer.toHexString(cp));
        });
        return key.toString();
    }

    private static Optional<String> svgData(String key) {
        if (!key.matches("[0-9a-f]+(?:-[0-9a-f]+)*") || key.length() > 128)
            return Optional.empty();
        String path = "/emojis/twemoji/" + key + ".svg";
        try (InputStream in = TwemojiSvgAssets.class.getResourceAsStream(path)) {
            if (in == null) return Optional.empty();
            byte[] bytes = in.readNBytes(MAX_SVG_BYTES + 1);
            if (bytes.length > MAX_SVG_BYTES) return Optional.empty();
            // The images in this resource folder are vetted, unmodified upstream
            // Twemoji SVGs; the WebView's CSP still forbids executing scripts.
            return Optional.of("data:image/svg+xml;base64,"
                    + Base64.getEncoder().encodeToString(bytes));
        } catch (IOException error) {
            return Optional.empty();
        }
    }
}
