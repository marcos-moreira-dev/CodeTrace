package com.mmdev.codetrace.markdown;

/**
 * Reliable Unicode transport for JavaFX WebEngine.loadContent(String).
 *
 * Some WebKit/JavaFX distributions interpret an in-memory HTML string with a
 * legacy single-byte charset even if its {@code <meta>} declares UTF-8. Keep the
 * sanitized MarkdownRenderer output unchanged and encode only non-ASCII
 * code points as HTML numeric character references at the WebView boundary.
 * The resulting transport is ASCII-only, independent of platform charset.
 * <p><strong>Engineering rationale.</strong> Preview decoding has to make text encoding choices explicit before markup rendering. Separating this boundary prevents a display concern from changing the immutable original bytes or from accidentally transforming the Unicode units used in transcription.</p>
 */
public final class MarkdownPreviewEncoding {
    private MarkdownPreviewEncoding() { }

    public static String asciiSafeHtml(String html) {
        if (html == null || html.isEmpty()) return "";
        StringBuilder safe = null;
        for (int index = 0; index < html.length();) {
            int codePoint = html.codePointAt(index);
            if (codePoint > 0x7f) {
                if (safe == null) {
                    safe = new StringBuilder(html.length() + 64);
                    safe.append(html, 0, index);
                }
                safe.append("&#x")
                        .append(Integer.toHexString(codePoint))
                        .append(';');
            } else if (safe != null) {
                safe.append((char) codePoint);
            }
            index += Character.charCount(codePoint);
        }
        return safe == null ? html : safe.toString();
    }
}
