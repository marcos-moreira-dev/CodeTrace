package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownRenderer;
import java.nio.file.Path;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 * A Markdown tab with the original typing surface and a separate, read-only
 * reference preview. The preview resolves media only from the reference root.
 */
public final class MarkdownPracticePane extends BorderPane {
    private final TypingEditor editor;
    private final Path markdownFile;
    private final Path referenceRoot;
    private final WebView preview = new WebView();
    private final Button toggle = ToolbarIcons.button("Renderizado",
            ToolbarIcons.Icon.PREVIEW, "Mostrar Markdown renderizado");
    private Task<String> renderTask;
    private boolean showingPreview;

    public MarkdownPracticePane(TypingEditor editor, Path markdownFile, Path referenceRoot) {
        this.editor = editor;
        this.markdownFile = markdownFile;
        this.referenceRoot = referenceRoot;
        HBox controls = new HBox(toggle);
        controls.setPadding(new Insets(4, 8, 4, 8));
        controls.getStyleClass().add("markdown-controls");
        toggle.getStyleClass().add("markdown-preview-toggle");
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
        if (!showingPreview) {
            if (renderTask != null) renderTask.cancel();
            setCenter(editor);
            setToggleMode(false);
            editor.focusEditor();
            return;
        }
        setToggleMode(true);
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(30, 30);
        spinner.setMaxSize(30, 30);
        VBox loading = new VBox(10, spinner, new Label("Preparando vista Markdown…"));
        loading.setAlignment(Pos.CENTER);
        loading.getStyleClass().add("markdown-preview-loading");
        setCenter(loading);

        // Reading and base64-encoding repository images must never freeze the FX thread.
        Task<String> task = new Task<>() {
            @Override protected String call() {
                return MarkdownRenderer.toHtmlPage(
                        editor.session().expectedText(), markdownFile, referenceRoot);
            }
        };
        renderTask = task;
        task.setOnSucceeded(event -> {
            if (showingPreview && renderTask == task) {
                preview.getEngine().loadContent(task.getValue(), "text/html");
                setCenter(preview);
            }
        });
        task.setOnFailed(event -> {
            if (showingPreview && renderTask == task) {
                Label error = new Label("No se pudo mostrar la vista Markdown.");
                error.getStyleClass().add("markdown-preview-error");
                setCenter(new VBox(error));
            }
        });
        Thread worker = new Thread(task, "codetrace-markdown-render");
        worker.setDaemon(true);
        worker.start();
    }

    private void setToggleMode(boolean previewShown) {
        toggle.setText(previewShown ? "Fuente" : "Renderizado");
        toggle.setGraphic(ToolbarIcons.graphic(
                previewShown ? ToolbarIcons.Icon.SOURCE : ToolbarIcons.Icon.PREVIEW));
        String title = previewShown ? "Volver al texto fuente para practicar"
                : "Mostrar Markdown renderizado";
        toggle.setTooltip(new Tooltip(title));
        toggle.setAccessibleText(title);
    }
}
