package com.mmdev.codetrace.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/** Read-only text reference for configuration and other non-practice files. */
public final class ReferenceTextPane extends BorderPane {
    private final TextArea text = new TextArea();

    public ReferenceTextPane(String source) {
        getStyleClass().add("reference-text-pane");
        text.setEditable(false);
        text.setWrapText(false);
        text.setText(source);
        text.getStyleClass().add("reference-text-content");
        setCenter(text);
    }

    /** Navigate without touching a TypingSession or the original source. */
    public void showSearchMatch(int line, int column, int length) {
        String source = text.getText();
        int start = 0;
        int row = 1;
        while (row < line && start < source.length()) {
            int next = source.indexOf('\n', start);
            if (next < 0) break;
            start = next + 1;
            row++;
        }
        int position = Math.min(source.length(), start + Math.max(0, column - 1));
        text.selectRange(position, Math.min(source.length(), position + length));
        text.requestFocus();
    }
}
