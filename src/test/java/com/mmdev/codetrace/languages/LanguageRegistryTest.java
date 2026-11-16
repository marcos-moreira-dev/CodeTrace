package com.mmdev.codetrace.languages;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LanguageRegistryTest {
    @Test
    void classifiesBuiltInLanguagesAndFallsBackToPlainText() {
        assertEquals("Java", LanguageRegistry.forFile("Main.java").name());
        assertEquals("Python", LanguageRegistry.forFile("script.PY").name());
        assertEquals("TypeScript", LanguageRegistry.forFile("service.ts").name());
        assertEquals("C/C++", LanguageRegistry.forFile("main.cpp").name());
        assertEquals("Markdown", LanguageRegistry.forFile("README.md").name());
        assertEquals("Texto", LanguageRegistry.forFile("notes.unknown").name());
    }

    @Test
    void doesNotColorKeywordsInsideCommentsOrStrings() {
        var java = LanguageRegistry.forFile("Main.java");
        String source = "// public\nString x = \"class\";\npublic class Main {}";
        var matches = java.highlight(source);
        assertTrue(matches.stream().anyMatch(t ->
                t.kind() == LanguageSupport.Kind.COMMENT));
        assertTrue(matches.stream().anyMatch(t ->
                t.kind() == LanguageSupport.Kind.STRING));
        assertEquals(0, matches.stream().filter(t -> t.kind()
                == LanguageSupport.Kind.KEYWORD && t.start() < 9).count());
        assertTrue(matches.stream().anyMatch(t ->
                t.kind() == LanguageSupport.Kind.KEYWORD
                && source.substring(t.start(), t.end()).equals("public")));
    }

    @Test
    void recognizesMarkdownHeadings() {
        var markdown = LanguageRegistry.forFile("README.md");
        assertTrue(markdown.highlight("# Title\ntext").stream()
                .anyMatch(t -> t.kind() == LanguageSupport.Kind.HEADING));
    }
}
