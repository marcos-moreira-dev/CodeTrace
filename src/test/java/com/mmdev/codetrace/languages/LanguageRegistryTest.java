package com.mmdev.codetrace.languages;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LanguageRegistryTest {
    @Test
    void classifiesBuiltInLanguagesAndFallsBackToPlainText() {
        assertEquals("Java", LanguageRegistry.forFile("Main.java").name());
        assertEquals("Python", LanguageRegistry.forFile("script.PY").name());
        assertEquals("TypeScript", LanguageRegistry.forFile("service.ts").name());
        assertEquals("TypeScript", LanguageRegistry.forFile("Component.tsx").name());
        assertEquals("JavaScript", LanguageRegistry.forFile("index.js").name());
        assertEquals("JavaScript", LanguageRegistry.forFile("Component.jsx").name());
        assertEquals("C/C++", LanguageRegistry.forFile("main.cpp").name());
        assertEquals("Markdown", LanguageRegistry.forFile("README.md").name());
        assertEquals("Go", LanguageRegistry.forFile("main.go").name());
        assertEquals("Ruby", LanguageRegistry.forFile("app.rb").name());
        assertEquals("C#", LanguageRegistry.forFile("Program.cs").name());
        assertEquals("PowerShell", LanguageRegistry.forFile("build.ps1").name());
        assertEquals("Shell", LanguageRegistry.forFile("script.sh").name());
        assertEquals("Windows CMD", LanguageRegistry.forFile("build.cmd").name());
        assertEquals("Windows CMD", LanguageRegistry.forFile("run.bat").name());
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
    void highlightsTypeScriptTypeSyntaxAndNarrowing() {
        String source = "interface Producto { precio: number }\n"
                + "type Resultado = string | undefined;\n"
                + "const esProducto = (valor: unknown): valor is Producto => true;";
        var tokens = LanguageRegistry.forFile("modelo.ts").highlight(source);
        for (String word : java.util.List.of("interface", "number", "type",
                "string", "undefined", "unknown", "is")) {
            assertTrue(tokens.stream().anyMatch(token ->
                    token.kind() == LanguageSupport.Kind.KEYWORD &&
                    source.substring(token.start(), token.end()).equals(word)), word);
        }
    }

    @Test
    void recognizesMarkdownHeadings() {
        var markdown = LanguageRegistry.forFile("README.md");
        assertTrue(markdown.highlight("# Title\ntext").stream()
                .anyMatch(t -> t.kind() == LanguageSupport.Kind.HEADING));
    }
}
