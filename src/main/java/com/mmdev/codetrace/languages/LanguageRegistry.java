package com.mmdev.codetrace.languages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Built-in syntactic adapters. Adding another language does not change the
 * typing engine, file explorer or session format.
 */
public final class LanguageRegistry {
    private static final LanguageSupport PLAIN = new LanguageSupport() {
        @Override
        public String name() {
            return "Texto";
        }

        @Override
        public boolean accepts(String file) {
            return true;
        }

        @Override
        public List<Token> highlight(String source) {
            return List.of();
        }
    };

    private static final List<LanguageSupport> SUPPORTS = List.of(
            new RegexSupport("Java", List.of(".java"), javaPattern()),
            new RegexSupport("Python", List.of(".py", ".pyw"), pythonPattern()),
            new RegexSupport("TypeScript", List.of(".ts", ".tsx", ".js", ".jsx"),
                    cStylePattern("interface|type|let|const|var|function|class|export|import|"
                            + "from|async|await|return|new|if|else|switch|case|for|while|"
                            + "try|catch|throw|extends|implements|readonly|private|public")),
            new RegexSupport("C/C++", List.of(".c", ".h", ".cpp", ".hpp", ".cc", ".hh"),
                    cStylePattern("int|void|char|short|long|float|double|bool|struct|class|"
                            + "enum|union|typedef|const|static|extern|return|if|else|for|"
                            + "while|switch|case|break|continue|namespace|using|template|"
                            + "public|private|protected|new|delete|auto|constexpr|#include")),
            new RegexSupport("C#", List.of(".cs", ".csx"),
                    cStylePattern("using|namespace|class|struct|record|interface|enum|"
                            + "public|private|protected|internal|static|async|await|"
                            + "void|return|new|if|else|foreach|for|while|try|catch|"
                            + "throw|var|int|string|bool|decimal|null|true|false")),
            new RegexSupport("Go", List.of(".go"),
                    cStylePattern("package|import|func|var|const|type|struct|interface|"
                            + "if|else|for|range|switch|case|default|go|defer|"
                            + "return|map|chan|select|break|continue|nil|true|false")),
            new RegexSupport("Ruby", List.of(".rb", ".rake"), hashCommentPattern(
                    "def|class|module|end|if|elsif|else|unless|while|until|do|"
                            + "begin|rescue|ensure|return|yield|require|include|"
                            + "true|false|nil|self|case|when|then|super")),
            new RegexSupport("PowerShell", List.of(".ps1", ".psm1", ".psd1"),
                    shellPattern("param|function|filter|begin|process|end|if|elseif|"
                            + "else|foreach|for|while|switch|try|catch|finally|"
                            + "throw|return|break|continue|class|enum|using|"
                            + "Import-Module|Get-ChildItem|Write-Host|Set-Location")),
            new RegexSupport("Shell", List.of(".sh", ".bash", ".zsh"),
                    shellPattern("if|then|else|elif|fi|for|while|do|done|case|esac|"
                            + "in|function|export|local|readonly|return|exit|"
                            + "source|echo|printf|cd|mkdir|grep|sed|awk")),
            new RegexSupport("Windows CMD", List.of(".bat", ".cmd"), cmdPattern()),
            new RegexSupport("Markdown", List.of(".md", ".markdown"), markdownPattern())
    );

    private LanguageRegistry() {
    }

    public static LanguageSupport forFile(String filename) {
        for (LanguageSupport support : SUPPORTS) {
            if (support.accepts(filename)) {
                return support;
            }
        }
        return PLAIN;
    }

    private static Pattern javaPattern() {
        return cStylePattern("abstract|assert|boolean|break|byte|case|catch|char|class|"
                + "const|continue|default|do|double|else|enum|extends|final|finally|"
                + "float|for|if|implements|import|instanceof|int|interface|long|native|"
                + "new|package|private|protected|public|record|return|short|static|"
                + "strictfp|super|switch|synchronized|this|throw|throws|transient|try|"
                + "var|void|volatile|while|yield|sealed|permits|non-sealed");
    }

    private static Pattern cStylePattern(String keywords) {
        // Earlier alternatives take precedence so comments and strings are not
        // recolored by nested words that look like keywords.
        String expression =
                "(?<comment>//[^\\r\\n]*|/\\*[\\s\\S]*?\\*/)"
                + "|(?<string>\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')"
                + "|(?<annotation>@[A-Za-z_$][A-Za-z0-9_$]*)"
                + "|(?<keyword>\\b(?:" + keywords + ")\\b)"
                + "|(?<number>\\b\\d+(?:\\.\\d+)?\\b)";
        return Pattern.compile(expression, Pattern.MULTILINE);
    }

    private static Pattern pythonPattern() {
        return Pattern.compile(
                "(?<comment>#[^\\r\\n]*)"
                + "|(?<string>\"\"\"[\\s\\S]*?\"\"\"|'''[\\s\\S]*?'''|"
                + "\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')"
                + "|(?<annotation>@[A-Za-z_][A-Za-z0-9_]*)"
                + "|(?<keyword>\\b(?:def|class|return|if|elif|else|for|while|in|is|"
                + "not|and|or|try|except|finally|raise|with|as|import|from|"
                + "async|await|lambda|yield|None|True|False|pass|break|continue)\\b)"
                + "|(?<number>\\b\\d+(?:\\.\\d+)?\\b)", Pattern.MULTILINE);
    }

    private static Pattern hashCommentPattern(String keywords) {
        return Pattern.compile(
                "(?<comment>#[^\\r\\n]*)"
                + "|(?<string>\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')"
                + "|(?<keyword>\\b(?:" + keywords + ")\\b)"
                + "|(?<number>\\b\\d+(?:\\.\\d+)?\\b)",
                Pattern.MULTILINE);
    }

    private static Pattern shellPattern(String keywords) {
        return Pattern.compile(
                "(?<comment>#[^\\r\\n]*)"
                + "|(?<string>\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')"
                + "|(?<annotation>\\$[A-Za-z_][A-Za-z_0-9]*)"
                + "|(?<keyword>\\b(?:" + keywords + ")\\b)"
                + "|(?<number>\\b\\d+\\b)",
                Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    }

    private static Pattern cmdPattern() {
        return Pattern.compile(
                "(?<comment>^(?:[ \\t]*REM\\b|[ \\t]*::)[^\\r\\n]*)"
                + "|(?<string>\"[^\"\\r\\n]*\")"
                + "|(?<annotation>%[A-Za-z0-9_]+%)"
                + "|(?<keyword>\\b(?:echo|set|if|else|for|in|do|call|goto|"
                + "exit|start|cd|pushd|popd|copy|move|del|mkdir|rmdir|"
                + "setlocal|endlocal|errorlevel)\\b)",
                Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    }

    private static Pattern markdownPattern() {
        return Pattern.compile("(?<heading>^#{1,6}[ \\t]+[^\\r\\n]*$)"
                + "|(?<string>`[^`\\r\\n]+`)", Pattern.MULTILINE);
    }

    private static final class RegexSupport implements LanguageSupport {
        private final String name;
        private final List<String> extensions;
        private final Pattern pattern;

        RegexSupport(String name, List<String> extensions, Pattern pattern) {
            this.name = name;
            this.extensions = extensions;
            this.pattern = pattern;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean accepts(String filename) {
            String lower = filename.toLowerCase(Locale.ROOT);
            return extensions.stream().anyMatch(lower::endsWith);
        }

        @Override
        public List<Token> highlight(String source) {
            Matcher matcher = pattern.matcher(source);
            List<Token> matches = new ArrayList<>();
            while (matcher.find()) {
                for (var group : List.of("comment", "string", "annotation",
                        "keyword", "number", "heading")) {
                    try {
                        if (matcher.start(group) >= 0) {
                            Kind kind = switch (group) {
                                case "comment" -> Kind.COMMENT;
                                case "string" -> Kind.STRING;
                                case "annotation" -> Kind.ANNOTATION;
                                case "number" -> Kind.NUMBER;
                                case "heading" -> Kind.HEADING;
                                default -> Kind.KEYWORD;
                            };
                            matches.add(new Token(matcher.start(group),
                                    matcher.end(group), kind));
                            break;
                        }
                    } catch (IllegalArgumentException missingGroup) {
                        // A given language pattern does not necessarily define
                        // each optional named group.
                    }
                }
            }
            return List.copyOf(matches);
        }
    }
}
