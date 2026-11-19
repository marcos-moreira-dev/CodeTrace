package com.mmdev.codetrace.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/** Continuous, full-row line-number gutter in both virtualized code viewers. */
public final class CodeLineGutter {
    private static final double BASE_WIDTH = 44;

    private CodeLineGutter() { }

    /** Same height as the actual character tiles plus two pixels of breathing room. */
    public static double rowHeight(double fontSize) {
        if (fontSize <= 0) throw new IllegalArgumentException("Font size must be positive");
        return Math.ceil(Math.max(22, fontSize * 1.45) + 2);
    }

    /** Reserve enough room for all digits before the document is scrolled. */
    public static double widthFor(int lineCount, double fontSize) {
        if (lineCount < 1 || fontSize <= 0)
            throw new IllegalArgumentException("Invalid gutter dimensions");
        int digits = Integer.toString(lineCount).length();
        return Math.max(BASE_WIDTH,
                Math.ceil(18 + digits * Math.max(11, fontSize - 2) * 0.65));
    }

    /** Kept for existing standalone control usage and GUI smoke tests. */
    public static Label number(int oneBasedLine) {
        return number(oneBasedLine, oneBasedLine, 13);
    }

    /** Both views display 1-based numbers; private caret indices remain unchanged. */
    public static Label number(int oneBasedLine, int lineCount, double fontSize) {
        if (oneBasedLine < 1 || lineCount < oneBasedLine)
            throw new IllegalArgumentException("Invalid line number");
        double width = widthFor(lineCount, fontSize);
        double height = rowHeight(fontSize);
        Label label = new Label(Integer.toString(oneBasedLine));
        label.getStyleClass().add("code-line-number");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMinSize(width, height);
        label.setPrefSize(width, height);
        label.setMaxSize(width, height);
        label.setStyle("-fx-font-size: " + Math.max(11, fontSize - 2) + "px;");
        label.setAccessibleText("Línea " + oneBasedLine);
        label.setMouseTransparent(true);
        return label;
    }
}
