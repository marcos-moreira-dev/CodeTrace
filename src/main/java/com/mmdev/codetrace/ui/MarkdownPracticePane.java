package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownRenderer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

/** One document tab: source transcription or read-only rendered reference. */
public final class MarkdownPracticePane extends BorderPane {
    private final TypingEditor editor;
    private final WebView preview = new WebView();
    private final Button toggle = new Button("Renderizado");
    private boolean showingPreview;

    public MarkdownPracticePane(TypingEditor editor) {
        this.editor = editor;
        HBox controls = new HBox(toggle);
        controls.setPadding(new Insets(4, 8, 4, 8));
        controls.getStyleClass().add("markdown-controls");
        setTop(controls);
        setCenter(editor);
        preview.setContextMenuEnabled(false);
        preview.getEngine().setJavaScriptEnabled(false);
        toggle.setOnAction(event -> toggle());
    }

    public TypingEditor editor() {
        return editor;
    }

    private void toggle() {
        showingPreview = !showingPreview;
        if (showingPreview) {
            preview.getEngine().loadContent(
                    MarkdownRenderer.toHtmlPage(editor.session().expectedText()),
                    "text/html");
            setCenter(preview);
            toggle.setText("Fuente");
        } else {
            setCenter(editor);
            toggle.setText("Renderizado");
            editor.focusEditor();
        }
    }
}
