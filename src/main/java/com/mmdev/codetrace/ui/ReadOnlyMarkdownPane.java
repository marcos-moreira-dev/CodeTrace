package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownPreviewEncoding;
import com.mmdev.codetrace.markdown.MarkdownRenderer;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import java.nio.file.Path;

/** Markdown reader for the secondary tree; never creates a typing session.  * <p><strong>Engineering rationale.</strong> A Markdown consultation view renders untrusted local content without creating transcription slots or virtual files. The preview engine handles safe resource resolution; this pane owns only the presentation lifecycle and must release its WebView on disposal.</p>
*/
public final class ReadOnlyMarkdownPane extends BorderPane {
    private final ReferenceTextPane source;
    private final WebView preview = new WebView();
    private final Path markdownFile;
    private final Path folder;
    private final String markdown;
    private final Button toggle = ToolbarIcons.button("Fuente", ToolbarIcons.Icon.SOURCE,
            "Alternar entre texto fuente y Markdown renderizado");
    private Task<MarkdownRenderer.RenderedPage> pending;
    private boolean rendering = true;
    private boolean disposed;

    public ReadOnlyMarkdownPane(String markdown, Path markdownFile, Path folder,
                                String fontFamily, int fontSize) {
        this.markdown = markdown;
        this.markdownFile = markdownFile;
        this.folder = folder;
        source = new ReferenceTextPane(markdown, markdownFile.getFileName().toString());
        source.setFontSettings(fontFamily, fontSize);
        preview.setContextMenuEnabled(false);
        preview.getEngine().setJavaScriptEnabled(false);
        HBox controls = new HBox(toggle);
        controls.setPadding(new Insets(4, 8, 4, 8));
        controls.getStyleClass().add("markdown-controls");
        setTop(controls);
        toggle.setOnAction(event -> {
            rendering = !rendering;
            toggle.setText(rendering ? "Fuente" : "Renderizado");
            toggle.setGraphic(ToolbarIcons.graphic(
                    rendering ? ToolbarIcons.Icon.SOURCE : ToolbarIcons.Icon.PREVIEW));
            if (rendering) render();
            else {
                if (pending != null) pending.cancel();
                setCenter(source);
            }
        });
        render();
    }

    private void render() {
        if (disposed) return;
        setCenter(new Label("Preparando Markdown…"));
        Task<MarkdownRenderer.RenderedPage> next = new Task<>() {
            @Override protected MarkdownRenderer.RenderedPage call() {
                return MarkdownRenderer.render(markdown, markdownFile, folder, false);
            }
        };
        if (pending != null) pending.cancel();
        pending = next;
        next.setOnSucceeded(event -> {
            if (disposed || !rendering || pending != next) return;
            preview.getEngine().loadContent(
                    MarkdownPreviewEncoding.asciiSafeHtml(next.getValue().html()),
                    "text/html");
            setCenter(preview);
        });
        next.setOnFailed(event -> {
            if (!disposed && pending == next) setCenter(new Label(
                    "No se pudo renderizar Markdown. Utiliza la vista Fuente."));
        });
        Thread worker = new Thread(next, "codetrace-readonly-markdown");
        worker.setDaemon(true);
        worker.start();
    }

    public void dispose() {
        disposed = true;
        if (pending != null) pending.cancel(true);
        preview.getEngine().loadContent("", "text/html");
    }
}
