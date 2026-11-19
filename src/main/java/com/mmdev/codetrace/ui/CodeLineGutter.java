package com.mmdev.codetrace.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * A single visual line-number contract for practice and read-only text views.
 * Used inside ListView cells: virtualized with the source line, never overlaid.
 */
public final class CodeLineGutter {
    private static final double WIDTH = 52;

    private CodeLineGutter() { }

    /** Numbering shown to people is always 1-based. */
    public static Label number(int oneBasedLine) {
        if (oneBasedLine < 1) throw new IllegalArgumentException("Line must be positive");
        Label label = new Label(Integer.toString(oneBasedLine));
        label.getStyleClass().add("code-line-number");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMinWidth(WIDTH);
        label.setPrefWidth(WIDTH);
        label.setMaxWidth(WIDTH);
        label.setAccessibleText("Línea " + oneBasedLine);
        return label;
    }
}
