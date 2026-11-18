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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    private final Button externalImages = new Button("Cargar imágenes externas");
    private final Label imagesInfo = new Label();
    private Task<MarkdownRenderer.RenderedPage> renderTask;
    private boolean showingPreview;
    private boolean allowExternalImages;
    private boolean disposed;

    public MarkdownPracticePane(TypingEditor editor, Path markdownFile, Path referenceRoot) {
        this.editor = editor;
        this.markdownFile = markdownFile;
        this.referenceRoot = referenceRoot;
        Region controlsGap = new Region();
        HBox.setHgrow(controlsGap, Priority.ALWAYS);
        externalImages.getStyleClass().add("markdown-external-images");
        externalImages.setVisible(false);
        externalImages.managedProperty().bind(externalImages.visibleProperty());
        externalImages.setTooltip(new Tooltip(
                "Solo imágenes HTTPS; el sitio de origen puede conocer la solicitud"));
        externalImages.setOnAction(event -> {
            allowExternalImages = !allowExternalImages;
            renderPreview();
        });
        imagesInfo.getStyleClass().add("markdown-images-status");
        HBox controls = new HBox(8, toggle, controlsGap, imagesInfo, externalImages);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(4, 8, 4, 8));
        controls.getStyleClass().add("markdown-controls");
        toggle.getStyleClass().add("markdown-preview-toggle");
        setTop(controls);
        setCenter(editor);
        preview.setContextMenuEnabled(false);
        preview.getEngine().setJavaScriptEnabled(false);
        toggle.setOnAction(event -> toggle());
    }

    /** Search navigation must reveal the source row instead of the HTML preview. */
    public void showSource() {
        if (showingPreview) toggle();
    }

    public TypingEditor editor() {
        return editor;
    }

    private void toggle() {
        showingPreview = !showingPreview;
        if (!showingPreview) {
            if (renderTask != null) renderTask.cancel();
            // Stop outstanding external requests when the preview is hidden.
            preview.getEngine().loadContent("", "text/html");
            setCenter(editor);
            externalImages.setVisible(false);
            imagesInfo.setVisible(false);
            imagesInfo.setManaged(false);
            setToggleMode(false);
            editor.focusEditor();
            return;
        }
        setToggleMode(true);
        renderPreview();
    }

    private void renderPreview() {
        if (disposed) return;
        if (renderTask != null) renderTask.cancel();
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(30, 30);
        spinner.setMaxSize(30, 30);
        VBox loading = new VBox(10, spinner, new Label("Preparando vista Markdown…"));
        loading.setAlignment(Pos.CENTER);
        loading.getStyleClass().add("markdown-preview-loading");
        setCenter(loading);

        // Reading and base64-encoding repository images must never freeze the FX thread.
        Task<MarkdownRenderer.RenderedPage> task = new Task<>() {
            @Override protected MarkdownRenderer.RenderedPage call() {
                return MarkdownRenderer.render(editor.session().expectedText(),
                        markdownFile, referenceRoot, allowExternalImages);
            }
        };
        renderTask = task;
        task.setOnSucceeded(event -> {
            if (!disposed && showingPreview && renderTask == task) {
                MarkdownRenderer.RenderedPage result = task.getValue();
                imagesInfo.setText(result.localImages() + " locales · "
                        + result.externalImages() + " externas · "
                        + result.unavailableImages() + " no disponibles");
                imagesInfo.setVisible(result.localImages() + result.externalImages()
                        + result.unavailableImages() > 0);
                imagesInfo.setManaged(imagesInfo.isVisible());
                externalImages.setVisible(result.externalImages() > 0);
                externalImages.setText(allowExternalImages
                        ? "Bloquear imágenes externas" : "Cargar imágenes externas");
                preview.getEngine().loadContent(result.html(), "text/html");
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

    /** Release pending rendering and the WebView when its tab closes. */
    public void dispose() {
        disposed = true;
        showingPreview = false;
        if (renderTask != null) renderTask.cancel(true);
        preview.getEngine().loadContent("", "text/html");
    }
}
