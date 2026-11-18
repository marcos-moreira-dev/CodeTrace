package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownRenderer;
import com.mmdev.codetrace.media.MediaTranscoder;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.util.Duration;

/** Read-only media preview; neither modifies files nor advances typing progress. */
public final class ReferenceMediaPane extends BorderPane {
    public enum Kind { IMAGE, SVG, AUDIO, VIDEO, UNSUPPORTED }
    private MediaPlayer player;
    private Task<Path> conversion;
    private Task<MarkdownRenderer.RenderedPage> imagePreparation;
    private WebView webImage;
    private Path temporaryMedia;
    private volatile boolean disposed;
    private boolean recovering;

    public static Kind kindFor(Path file) {
        if (file == null || file.getFileName() == null) return Kind.UNSUPPORTED;
        String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
        if (name.endsWith(".svg")) return Kind.SVG;
        if (ends(name, ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".webp")) return Kind.IMAGE;
        if (ends(name, ".mp3", ".wav", ".m4a", ".aac", ".flac", ".ogg")) return Kind.AUDIO;
        if (ends(name, ".mp4", ".m4v", ".mov", ".mkv", ".webm", ".avi"))
            return Kind.VIDEO;
        return Kind.UNSUPPORTED;
    }

    /** JavaFX Image cannot decode WebP; the sandboxed JavaFX WebView can. */
    public static boolean needsWebImage(Path file) {
        return file != null && file.getFileName() != null
                && file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".webp");
    }

    private static boolean ends(String name, String... extensions) {
        for (String extension : extensions) if (name.endsWith(extension)) return true;
        return false;
    }

    public ReferenceMediaPane(Path file, Path referenceRoot) {
        getStyleClass().add("reference-media-pane");
        try {
            Path root = referenceRoot.toRealPath();
            Path actual = file.toRealPath();
            if (!actual.startsWith(root) || !Files.isRegularFile(actual)) {
                message("El archivo no pertenece a la referencia seleccionada.");
                return;
            }
            switch (kindFor(actual)) {
                case IMAGE -> {
                    if (needsWebImage(actual)) showWebImage(actual, root);
                    else showImage(actual);
                }
                case SVG -> showWebImage(actual, root);
                case AUDIO, VIDEO -> showMedia(actual);
                default -> message("No hay un visor integrado para este formato.");
            }
        } catch (IOException | SecurityException problem) {
            message("No se pudo abrir el archivo de referencia.");
        }
    }

    private void showImage(Path file) throws IOException {
        if (Files.size(file) > 64L * 1024 * 1024) {
            message("La imagen supera el límite de 64 MB para la vista previa.");
            return;
        }
        Image image = new Image(file.toUri().toString(), true);
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.fitWidthProperty().bind(widthProperty().subtract(40));
        StackPane canvas = new StackPane(view);
        canvas.setPadding(new Insets(20));
        canvas.getStyleClass().add("media-canvas");
        ScrollPane scroll = new ScrollPane(canvas);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        setCenter(scroll);
        image.errorProperty().addListener((obs, old, error) -> {
            if (error && !disposed)
                message("Este formato de imagen no es compatible con JavaFX.");
        });
    }

    /**
     * Decode/base64-encode repository images off the FX thread. Even a valid
     * SVG may be expensive to parse; the tab must stay responsive.
     */
    private void showWebImage(Path file, Path root) {
        String filename = URLEncoder.encode(file.getFileName().toString(),
                StandardCharsets.UTF_8).replace("+", "%20");
        message("Preparando imagen de referencia…");
        Task<MarkdownRenderer.RenderedPage> task = new Task<>() {
            @Override protected MarkdownRenderer.RenderedPage call() {
                return MarkdownRenderer.render("![Imagen](" + filename + ")",
                        file, root, false);
            }
        };
        imagePreparation = task;
        task.setOnSucceeded(event -> {
            if (disposed || imagePreparation != task) return;
            var result = task.getValue();
            if (result.localImages() == 0) {
                message("No se puede mostrar la imagen de forma segura (límite: 8 MB).");
                return;
            }
            WebView web = new WebView();
            web.setContextMenuEnabled(false);
            web.getEngine().setJavaScriptEnabled(false);
            web.getEngine().loadContent(result.html(), "text/html");
            webImage = web;
            setCenter(web);
        });
        task.setOnFailed(event -> {
            if (!disposed && imagePreparation == task)
                message("No fue posible preparar la imagen de referencia.");
        });
        Thread worker = new Thread(task, "codetrace-image-preview");
        worker.setDaemon(true);
        worker.start();
    }

    private void showMedia(Path original) {
        if (MediaTranscoder.needsConversion(original)) convertAndPlay(original);
        else playMedia(original, original, false);
    }

    /**
     * Conversion is off the FX thread. A cancelled JavaFX Task does not invoke
     * onSucceeded, even if the worker finishes after cancellation. Retain the
     * path separately so every race releases the temporary result.
     */
    private void convertAndPlay(Path original) {
        if (disposed || (conversion != null && !conversion.isDone())) return;
        releasePlayer();
        message("Preparando vista multimedia mediante FFmpeg local…");
        Button cancel = new Button("Cancelar conversión");
        HBox bar = new HBox(cancel);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("media-playback-controls");
        setBottom(bar);
        AtomicReference<Path> created = new AtomicReference<>();
        Task<Path> task = new Task<>() {
            @Override protected Path call() throws IOException {
                Path ready = MediaTranscoder.transcode(original,
                        kindFor(original) == Kind.AUDIO
                                ? MediaTranscoder.Output.AUDIO
                                : MediaTranscoder.Output.VIDEO,
                        this::isCancelled);
                created.set(ready);
                if (isCancelled() || disposed) {
                    MediaTranscoder.deleteTemporary(created.getAndSet(null));
                    return null;
                }
                return ready;
            }
        };
        conversion = task;
        cancel.setOnAction(event -> {
            task.cancel(true);
            message("Conversión cancelada.");
        });
        task.setOnSucceeded(event -> {
            Path prepared = created.getAndSet(null);
            if (prepared == null) return;
            if (disposed || conversion != task) {
                MediaTranscoder.deleteTemporary(prepared);
                return;
            }
            temporaryMedia = prepared;
            playMedia(prepared, original, true);
        });
        task.setOnCancelled(event ->
                MediaTranscoder.deleteTemporary(created.getAndSet(null)));
        task.setOnFailed(event -> {
            MediaTranscoder.deleteTemporary(created.getAndSet(null));
            if (!disposed && conversion == task) {
                message("No se pudo preparar este medio: "
                        + task.getException().getMessage());
            }
        });
        Thread thread = new Thread(task, "codetrace-ffmpeg-preview");
        thread.setDaemon(true);
        thread.start();
    }

    private void playMedia(Path file, Path original, boolean converted) {
        if (disposed) return;
        try {
            Media media = new Media(file.toUri().toString());
            player = new MediaPlayer(media);
            player.setAutoPlay(false);
            if (kindFor(original) == Kind.VIDEO) {
                MediaView screen = new MediaView(player);
                screen.setPreserveRatio(true);
                screen.fitWidthProperty().bind(widthProperty().subtract(35));
                screen.fitHeightProperty().bind(heightProperty().subtract(90));
                StackPane canvas = new StackPane(screen);
                canvas.getStyleClass().add("media-canvas");
                setCenter(canvas);
            } else {
                Label fileName = new Label(original.getFileName().toString());
                fileName.getStyleClass().add("media-audio-title");
                StackPane cover = new StackPane(fileName);
                cover.getStyleClass().add("media-canvas");
                setCenter(cover);
            }
            Button play = new Button("Reproducir");
            Button stop = new Button("Detener");
            MediaPlayer active = player;
            play.setOnAction(event -> {
                if (active.getStatus() == MediaPlayer.Status.PLAYING) active.pause();
                else active.play();
            });
            stop.setOnAction(event -> active.stop());
            active.statusProperty().addListener((obs, before, now) ->
                    play.setText(now == MediaPlayer.Status.PLAYING
                            ? "Pausar" : "Reproducir"));
            Slider seek = new Slider(0, 100, 0);
            HBox.setHgrow(seek, Priority.ALWAYS);
            active.currentTimeProperty().addListener((obs, before, now) -> {
                Duration length = active.getTotalDuration();
                if (!seek.isValueChanging() && length != null && !length.isUnknown()
                        && length.toMillis() > 0)
                    seek.setValue(now.toMillis() / length.toMillis() * 100);
            });
            seek.setOnMouseReleased(event -> {
                Duration length = active.getTotalDuration();
                if (length != null && !length.isUnknown() && length.toMillis() > 0)
                    active.seek(Duration.millis(length.toMillis() * seek.getValue() / 100));
            });
            active.setOnEndOfMedia(() -> { active.stop(); seek.setValue(0); });
            active.setOnError(() -> unsupportedMedia(original, converted));
            HBox controls = new HBox(9, play, stop, seek);
            controls.getStyleClass().add("media-playback-controls");
            controls.setAlignment(Pos.CENTER_LEFT);
            setBottom(controls);
        } catch (MediaException | IllegalArgumentException problem) {
            unsupportedMedia(original, converted);
        }
    }

    private void unsupportedMedia(Path original, boolean converted) {
        if (disposed) return;
        if (converted) {
            releasePlayer();
            message("No se puede reproducir este medio con los códecs disponibles.");
            return;
        }
        if (!recovering) {
            recovering = true;
            convertAndPlay(original);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }

    private void message(String text) {
        setBottom(null);
        Label explanation = new Label(text);
        explanation.setWrapText(true);
        explanation.getStyleClass().add("media-error");
        StackPane canvas = new StackPane(explanation);
        canvas.setPadding(new Insets(25));
        setCenter(canvas);
    }

    public void dispose() {
        disposed = true;
        if (conversion != null) conversion.cancel(true);
        if (imagePreparation != null) imagePreparation.cancel(true);
        if (webImage != null) {
            webImage.getEngine().loadContent("", "text/html");
            webImage = null;
        }
        releasePlayer();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
    }
}
