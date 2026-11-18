package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.languages.LanguageSupport;
import javafx.scene.paint.Color;

/** Shared syntax palette for read-only previews and immutable typing glyphs. */
public final class SyntaxPalette {
    private SyntaxPalette() {}

    public static Color color(LanguageSupport.Kind kind) {
        return switch (kind) {
            case KEYWORD -> Color.web("#245AA4");
            case TYPE -> Color.web("#1E708B");
            case CALL -> Color.web("#6D57A2");
            case MEMBER -> Color.web("#3B6D91");
            case PROPERTY -> Color.web("#356F9A");
            case SELECTOR -> Color.web("#8C58A4");
            case TAG -> Color.web("#A04F60");
            case COLOR -> Color.web("#A65E33");
            case COMMENT -> Color.web("#418052");
            case STRING -> Color.web("#997122");
            case NUMBER -> Color.web("#946029");
            case ANNOTATION -> Color.web("#875A9A");
            case HEADING -> Color.web("#316383");
        };
    }
}
