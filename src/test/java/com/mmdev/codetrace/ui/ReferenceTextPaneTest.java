package com.mmdev.codetrace.ui;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.languages.LanguageSupport;
import org.junit.jupiter.api.Test;

class ReferenceTextPaneTest {
    @Test
    void producesReadableColoredCssRowsWithoutChangingSourceText() {
        String css = ".root {\n  -fx-font-size: 12px;\n"
                + "  /* comentario */\n  -fx-base: #f5f6f7;\n}\n";
        var lines = ReferenceTextPane.segment(css, "codetrace.css");
        assertTrue(lines.stream().flatMap(line -> line.spans().stream())
                .anyMatch(span -> span.kind() == LanguageSupport.Kind.SELECTOR));
        assertTrue(lines.stream().flatMap(line -> line.spans().stream())
                .anyMatch(span -> span.kind() == LanguageSupport.Kind.PROPERTY));
        assertTrue(lines.stream().flatMap(line -> line.spans().stream())
                .anyMatch(span -> span.kind() == LanguageSupport.Kind.COMMENT));
        assertTrue(lines.stream().flatMap(line -> line.spans().stream())
                .anyMatch(span -> span.kind() == LanguageSupport.Kind.COLOR));
        assertEquals(".root {", lines.getFirst().source());
        assertEquals(css.replace("\r", ""),
                lines.stream().map(ReferenceTextPane.SourceLine::source)
                        .collect(java.util.stream.Collectors.joining("\n")));
    }

    @Test
    void keepsMultilineCommentsAndStringsInsideTheirOriginalBoundaries() {
        var lines = ReferenceTextPane.segment(
                "/* begin\n continues */\nbody { color: red; }", "theme.css");
        assertTrue(lines.get(0).spans().stream().anyMatch(
                s -> s.kind() == LanguageSupport.Kind.COMMENT));
        assertTrue(lines.get(1).spans().stream().anyMatch(
                s -> s.kind() == LanguageSupport.Kind.COMMENT));
    }
}
