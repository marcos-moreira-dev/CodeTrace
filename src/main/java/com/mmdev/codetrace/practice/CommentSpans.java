package com.mmdev.codetrace.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Conservative lexical comment spans, expressed as UTF-16 offsets into the
 * immutable reference. No source file is rewritten and no compiler is invoked.
 *
 * <p>Strings, raw strings and shell here-documents take precedence over
 * comment markers. This is deliberately separate from syntax highlighting:
 * mistaking a string for a comment would silently remove typing requirements.
 * Unknown extensions have no comments to exclude.
 */
public final class CommentSpans {
    public record Span(int start, int end) {
        public Span {
            if (start < 0 || end < start) throw new IllegalArgumentException("Invalid span");
        }
    }

    private enum Kind { C, PYTHON, RUBY, POWERSHELL, SHELL, CMD, ASM,
                        CSS, XML, YAML, PROPERTIES, SQL, NONE }

    private CommentSpans() { }

    public static List<Span> find(String filename, String source) {
        if (filename == null || source == null || source.isEmpty()) return List.of();
        String name = filename.toLowerCase(Locale.ROOT);
        int dot = name.lastIndexOf('.');
        String extension = dot < 0 ? "" : name.substring(dot + 1);
        Kind kind = kind(extension);
        if (kind == Kind.NONE) return List.of();

        List<Span> spans = new ArrayList<>();
        int length = source.length();
        int cursor = 0;
        String hereDocument = null;
        while (cursor < length) {
            int startOfLine = source.lastIndexOf('\n', Math.max(0, cursor - 1)) + 1;
            boolean atLineStart = startOfLine == cursor;
            if (hereDocument != null && atLineStart) {
                int end = lineEnd(source, cursor);
                String value = source.substring(cursor, end).strip();
                if (value.equals(hereDocument)) hereDocument = null;
                cursor = end < length ? end + 1 : end;
                continue;
            }
            char c = source.charAt(cursor);
            int end = lineEnd(source, cursor);
            if (kind == Kind.CMD && atLineStart) {
                int first = firstNonSpace(source, cursor, end);
                String line = source.substring(first, end);
                if (line.startsWith("::") || line.regionMatches(true, 0, "REM", 0, 3)
                        && (line.length() == 3 || Character.isWhitespace(line.charAt(3)))) {
                    add(spans, source, first, end);
                    cursor = end < length ? end + 1 : end;
                    continue;
                }
            }
            if (kind == Kind.RUBY && atLineStart) {
                int first = firstNonSpace(source, cursor, end);
                if (source.startsWith("=begin", first) && wordBoundary(source, first + 6)) {
                    int stop = end;
                    while (stop < length) {
                        int nextStart = stop + 1;
                        int nextEnd = lineEnd(source, nextStart);
                        int firstNext = firstNonSpace(source, nextStart, nextEnd);
                        stop = nextEnd;
                        if (source.startsWith("=end", firstNext)
                                && wordBoundary(source, firstNext + 4)) break;
                    }
                    add(spans, source, first, stop);
                    cursor = stop;
                    continue;
                }
            }
            if (kind == Kind.POWERSHELL && (source.startsWith("@'", cursor)
                    || source.startsWith("@\"", cursor))) {
                char quote = source.charAt(cursor + 1);
                int marker = source.indexOf("\n" + quote + "@", cursor + 2);
                cursor = marker < 0 ? length : marker + 3;
                continue;
            }
            if (kind == Kind.C && extension.equals("cs") && source.startsWith("@\"", cursor)) {
                cursor = skipQuote(source, cursor + 1, '"', false, true);
                continue;
            }
            if (kind == Kind.C && (extension.equals("cpp") || extension.equals("cc")
                    || extension.equals("hpp") || extension.equals("h")
                    || extension.equals("cxx") || extension.equals("hh"))
                    && source.startsWith("R\"", cursor)) {
                int open = source.indexOf('(', cursor + 2);
                if (open >= 0 && open - cursor <= 18) {
                    String delimiter = source.substring(cursor + 2, open);
                    int close = source.indexOf(")" + delimiter + "\"", open + 1);
                    if (close >= 0) {
                        cursor = close + delimiter.length() + 2;
                        continue;
                    }
                }
            }
            // Triple-quoted Python strings, Kotlin strings and C# raw strings.
            if (c == '"' || c == '\'') {
                boolean triple = (kind == Kind.PYTHON
                        || kind == Kind.C && (extension.equals("kt")
                        || extension.equals("kts") || extension.equals("cs")))
                        && cursor + 2 < length && source.charAt(cursor + 1) == c
                        && source.charAt(cursor + 2) == c;
                cursor = skipQuote(source, cursor, c, triple, false);
                continue;
            }
            // JS template literals, Go raw strings, PowerShell escapes: do
            // not interpret comment markers inside literal bodies.
            if (c == 96 && (kind == Kind.C || kind == Kind.POWERSHELL)) {
                cursor = skipQuote(source, cursor, c, false, false);
                continue;
            }
            if (kind == Kind.SHELL && source.startsWith("<<", cursor)) {
                int p = cursor + 2;
                if (p < length && source.charAt(p) == '-') p++;
                while (p < end && Character.isWhitespace(source.charAt(p))) p++;
                char wrapper = p < end ? source.charAt(p) : '\0';
                if (wrapper == '\'' || wrapper == '"') p++;
                int begin = p;
                while (p < end && !Character.isWhitespace(source.charAt(p))
                        && (wrapper != '\'' && wrapper != '"' || source.charAt(p) != wrapper)
                        && source.charAt(p) != ';') p++;
                if (p > begin) hereDocument = source.substring(begin, p);
                cursor = end < length ? end + 1 : end;
                continue;
            }
            if (kind == Kind.XML && source.startsWith("<!--", cursor)) {
                int closing = source.indexOf("-->", cursor + 4);
                int to = closing < 0 ? length : closing + 3;
                add(spans, source, cursor, to);
                cursor = to;
                continue;
            }
            if ((kind == Kind.C || kind == Kind.CSS || kind == Kind.SQL)
                    && source.startsWith("/*", cursor)) {
                boolean nested = (extension.equals("kt") || extension.equals("kts")
                        || extension.equals("rs") || extension.equals("swift"));
                int to = blockEnd(source, cursor, nested);
                add(spans, source, cursor, to);
                cursor = to;
                continue;
            }
            if (kind == Kind.C && source.startsWith("//", cursor)
                    || kind == Kind.SQL && source.startsWith("--", cursor)) {
                add(spans, source, cursor, end);
                cursor = end < length ? end + 1 : end;
                continue;
            }
            if ((kind == Kind.PYTHON || kind == Kind.RUBY
                    || kind == Kind.POWERSHELL || kind == Kind.SHELL
                    || kind == Kind.YAML || kind == Kind.PROPERTIES)
                    && (c == '#' || kind == Kind.PROPERTIES && c == '!')) {
                boolean directive = cursor == 0 && source.startsWith("#!", cursor)
                        || kind == Kind.POWERSHELL && source.substring(startOfLine, end)
                                .stripLeading().toLowerCase(Locale.ROOT).startsWith("#requires");
                boolean boundary = kind != Kind.SHELL && kind != Kind.YAML
                        || cursor == startOfLine || Character.isWhitespace(source.charAt(cursor - 1));
                if (!directive && boundary) {
                    add(spans, source, cursor, end);
                    cursor = end < length ? end + 1 : end;
                    continue;
                }
            }
            if (kind == Kind.ASM && c == ';') {
                add(spans, source, cursor, end);
                cursor = end < length ? end + 1 : end;
                continue;
            }
            if (kind == Kind.C && (extension.equals("js") || extension.equals("jsx")
                    || extension.equals("ts") || extension.equals("tsx")) && c == '/'
                    && looksLikeRegexStart(source, cursor)) {
                cursor = regexEnd(source, cursor);
                continue;
            }
            cursor++;
        }
        return List.copyOf(spans);
    }

    private static Kind kind(String ext) {
        return switch (ext) {
            case "java", "kt", "kts", "c", "h", "cpp", "cc", "hh", "hpp", "cxx",
                    "cs", "csx", "go", "js", "jsx", "ts", "tsx",
                    "rs", "swift", "dart", "scala", "groovy", "php", "jsonc" -> Kind.C;
            case "py", "pyw" -> Kind.PYTHON;
            case "rb", "rake" -> Kind.RUBY;
            case "ps1", "psm1", "psd1" -> Kind.POWERSHELL;
            case "sh", "bash", "zsh" -> Kind.SHELL;
            case "bat", "cmd" -> Kind.CMD;
            case "asm", "nasm" -> Kind.ASM;
            case "css", "scss", "less", "sass" -> Kind.CSS;
            case "html", "htm", "xml", "svg", "md", "markdown" -> Kind.XML;
            case "yaml", "yml", "toml" -> Kind.YAML;
            case "properties", "ini", "conf", "env" -> Kind.PROPERTIES;
            case "sql" -> Kind.SQL;
            default -> Kind.NONE;
        };
    }

    private static int blockEnd(String s, int start, boolean nested) {
        int depth = 1, i = start + 2;
        while (i + 1 < s.length()) {
            if (nested && s.startsWith("/*", i)) { depth++; i += 2; continue; }
            if (s.startsWith("*/", i)) {
                depth--; i += 2;
                if (depth == 0) return i;
                continue;
            }
            i++;
        }
        return s.length();
    }

    private static int skipQuote(String s, int start, char quote,
                                 boolean triple, boolean verbatim) {
        int i = start + (triple ? 3 : 1);
        while (i < s.length()) {
            if (triple && i + 2 < s.length() && s.charAt(i) == quote
                    && s.charAt(i + 1) == quote && s.charAt(i + 2) == quote)
                return i + 3;
            if (!triple && s.charAt(i) == quote) {
                if (verbatim && i + 1 < s.length() && s.charAt(i + 1) == quote) {
                    i += 2;
                    continue;
                }
                return i + 1;
            }
            if (!verbatim && s.charAt(i) == '\\' && i + 1 < s.length()) i += 2;
            else i++;
        }
        return s.length();
    }

    private static boolean looksLikeRegexStart(String s, int slash) {
        int prior = slash - 1;
        while (prior >= 0 && Character.isWhitespace(s.charAt(prior))) prior--;
        if (prior < 0) return true;
        return "=([{,:;!?&|".indexOf(s.charAt(prior)) >= 0;
    }

    private static int regexEnd(String s, int start) {
        boolean group = false;
        for (int i = start + 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') { i++; continue; }
            if (c == '[') group = true;
            else if (c == ']') group = false;
            else if (c == '/' && !group) return i + 1;
            else if (c == '\n') return i;
        }
        return s.length();
    }

    private static int lineEnd(String s, int from) {
        int n = s.indexOf('\n', from);
        return n < 0 ? s.length() : n;
    }

    private static int firstNonSpace(String s, int start, int end) {
        while (start < end && (s.charAt(start) == ' ' || s.charAt(start) == '\t')) start++;
        return start;
    }

    private static boolean wordBoundary(String s, int index) {
        return index >= s.length() || Character.isWhitespace(s.charAt(index));
    }

    private static void add(List<Span> spans, String source, int first, int last) {
        int row = source.lastIndexOf('\n', Math.max(0, first - 1)) + 1;
        // Ignore indentation on comment-only rows so it cannot block typing.
        boolean alone = source.substring(row, first).isBlank();
        int start = alone ? row : first;
        int end = last;
        // Trailing comments also consume the structural newline, allowing
        // continuous code-only typing into the next line without an Enter.
        int lineEnd = lineEnd(source, end);
        if (source.substring(end, lineEnd).isBlank())
            end = lineEnd < source.length() ? lineEnd + 1 : lineEnd;
        if (!spans.isEmpty() && spans.get(spans.size() - 1).end() >= start) {
            Span previous = spans.remove(spans.size() - 1);
            spans.add(new Span(previous.start(), Math.max(previous.end(), end)));
        } else spans.add(new Span(start, end));
    }
}
