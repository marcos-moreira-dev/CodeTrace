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
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
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
    private AudioSpectrumPane spectrum;
    private Task<Path> conversion;
    private Task<MarkdownRenderer.RenderedPage> imagePreparation;
    private WebView webImage;
    private Path temporaryMedia;
    private volatile boolean disposed;
    private MediaTranscoder.Output conversionMode;
    private ZoomableMediaViewport viewport;
    private final IntegerProperty zoomPercent = new SimpleIntegerProperty(100);
    private final Kind previewKind;

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
        previewKind = kindFor(file);
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
        view.fitHeightProperty().bind(heightProperty().subtract(40));
        installViewport(view);
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
            web.prefWidthProperty().bind(widthProperty());
            web.prefHeightProperty().bind(heightProperty());
            installViewport(web);
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
        if (MediaTranscoder.needsConversion(original)) {
            convertAndPlay(original, kindFor(original) == Kind.VIDEO
                    ? MediaTranscoder.Output.VIDEO_REMUX
                    : MediaTranscoder.Output.AUDIO);
        } else {
            playMedia(original, original, false);
        }
    }

    /**
     * FFmpeg runs off the JavaFX application thread, and a cancellation never
     * publishes a partially written MP4. Fast mode copies every video frame
     * without rescaling or changing its frame rate. Full HD re-encoding is an
     * explicit recovery option if JavaFX cannot decode the original codec.
     */
    private void convertAndPlay(Path original, MediaTranscoder.Output output) {
        if (disposed || (conversion != null && !conversion.isDone())) return;
        releasePlayer();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
        conversionMode = output;
        message("Preparando medio completo con FFmpeg…");
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(44, 44);
        String heading = switch (output) {
            case AUDIO -> "Preparando audio completo con FFmpeg…";
            case VIDEO_REMUX -> "Preparando vídeo completo sin recomprimir los fotogramas…";
            case VIDEO -> "Convirtiendo vídeo completo a H.264 HD (hasta 1080p)…";
        };
        String explanation = output == MediaTranscoder.Output.VIDEO
                ? "Se conservará la duración y la cadencia del original. "
                  + "Esta conversión puede tardar según el tamaño del archivo."
                : "Sin límite de 30 segundos ni reducción de resolución o FPS.";
        Label title = new Label(heading);
        title.setWrapText(true);
        Label hint = new Label(explanation);
        hint.setWrapText(true);
        VBox loading = new VBox(14, spinner, title, hint);
        loading.setAlignment(Pos.CENTER);
        loading.setPadding(new Insets(24));
        setCenter(new StackPane(loading));

        Button cancel = new Button("Cancelar preparación");
        HBox bar = new HBox(10, cancel, openOriginalButton(original));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("media-playback-controls");
        setBottom(bar);

        AtomicReference<Path> created = new AtomicReference<>();
        Task<Path> task = new Task<>() {
            @Override protected Path call() throws IOException {
                Path ready = MediaTranscoder.transcode(original, output, this::isCancelled);
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
            message("Preparación cancelada. El archivo original permanece intacto.");
            showPlaybackOptions(original, output == MediaTranscoder.Output.VIDEO_REMUX);
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
                Throwable cause = task.getException();
                message("No se pudo preparar el vídeo completo: "
                        + (cause == null ? "error desconocido" : abbreviate(
                        cause.getMessage() == null ? "error desconocido"
                                : cause.getMessage(), 220)));
                showPlaybackOptions(original, output == MediaTranscoder.Output.VIDEO_REMUX);
            }
        });
        Thread worker = new Thread(task, "codetrace-ffmpeg-fullmedia");
        worker.setDaemon(true);
        worker.start();
    }

    /** An HD conversion is a conscious choice, not a surprise multi-hour job. */
    private void showPlaybackOptions(Path original, boolean allowHDConversion) {
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.getStyleClass().add("media-playback-controls");
        if (allowHDConversion && kindFor(original) == Kind.VIDEO) {
            Button encode = new Button("Preparar vídeo completo en HD");
            encode.setTooltip(new Tooltip(
                    "Convierte todos los fotogramas a H.264, hasta 1080p, "
                    + "sin reducir los FPS. Puede tardar bastante."));
            encode.setOnAction(event -> convertAndPlay(
                    original, MediaTranscoder.Output.VIDEO));
            actions.getChildren().add(encode);
        }
        actions.getChildren().add(openOriginalButton(original));
        setBottom(actions);
    }

    private void playMedia(Path file, Path original, boolean converted) {
        if (disposed) return;
        try {
            Media media = new Media(file.toUri().toString());
            player = new MediaPlayer(media);
            player.setAutoPlay(false);
            MediaPlayer active = player;
            if (kindFor(original) == Kind.VIDEO) {
                MediaView screen = new MediaView(active);
                screen.setPreserveRatio(true);
                screen.fitWidthProperty().bind(widthProperty().subtract(35));
                screen.fitHeightProperty().bind(heightProperty().subtract(90));
                installViewport(screen);
            } else {
                releaseViewport();
                Label fileName = new Label(original.getFileName().toString());
                fileName.getStyleClass().add("media-audio-title");
                Label kind = new Label("Audio de referencia · solo lectura");
                kind.getStyleClass().add("media-audio-subtitle");
                spectrum = new AudioSpectrumPane();
                VBox audio = new VBox(10, fileName, kind, spectrum);
                audio.getStyleClass().add("media-audio-panel");
                audio.setAlignment(Pos.CENTER);
                spectrum.prefWidthProperty().bind(audio.widthProperty().subtract(40));
                StackPane cover = new StackPane(audio);
                cover.getStyleClass().add("media-canvas");
                cover.setPadding(new Insets(24));
                setCenter(cover);
                active.setAudioSpectrumNumBands(40);
                active.setAudioSpectrumInterval(0.1);
                active.setAudioSpectrumThreshold(-60);
                AudioSpectrumPane currentSpectrum = spectrum;
                active.setAudioSpectrumListener((time, duration, mags, phases) -> {
                    if (!disposed && player == active) currentSpectrum.accept(mags);
                });
            }

            Button playPause = new Button();
            playPause.getStyleClass().addAll("media-icon-button", "media-play-toggle");
            Button stop = new Button();
            stop.getStyleClass().add("media-icon-button");
            playPause.setGraphic(new FontIcon("fth-play"));
            stop.setGraphic(new FontIcon("fth-square"));
            playPause.setAccessibleText("Reproducir o pausar");
            stop.setAccessibleText("Detener reproducción");
            Tooltip playTip = new Tooltip("Reproducir");
            playPause.setTooltip(playTip);
            stop.setTooltip(new Tooltip("Detener y regresar al inicio"));
            playPause.setOnAction(event -> {
                if (active.getStatus() == MediaPlayer.Status.PLAYING) active.pause();
                else active.play();
            });
            stop.setOnAction(event -> {
                active.stop();
                if (spectrum != null) spectrum.clear();
            });
            active.statusProperty().addListener((obs, before, now) -> {
                boolean playing = now == MediaPlayer.Status.PLAYING;
                playPause.setGraphic(new FontIcon(playing ? "fth-pause" : "fth-play"));
                playTip.setText(playing ? "Pausar" : "Reproducir");
            });
            Slider seek = new Slider(0, 100, 0);
            seek.getStyleClass().add("media-timeline-slider");
            HBox.setHgrow(seek, Priority.ALWAYS);
            Label elapsed = new Label("0:00 / 0:00");
            elapsed.getStyleClass().add("media-time-label");
            active.currentTimeProperty().addListener((obs, before, now) -> {
                Duration total = active.getTotalDuration();
                if (!seek.isValueChanging() && isUsableDuration(total))
                    seek.setValue(now.toMillis() / total.toMillis() * 100);
                elapsed.setText(formatTime(now) + " / " + formatTime(total));
            });
            active.totalDurationProperty().addListener((obs, before, now) ->
                    elapsed.setText(formatTime(active.getCurrentTime())
                            + " / " + formatTime(now)));
            seek.valueChangingProperty().addListener((obs, before, now) -> {
                if (!now) seekPlayer(active, seek.getValue());
            });
            seek.setOnMouseReleased(event -> seekPlayer(active, seek.getValue()));
            active.setOnEndOfMedia(() -> {
                active.stop();
                seek.setValue(0);
                if (spectrum != null) spectrum.clear();
            });

            Label volumeIcon = new Label();
            volumeIcon.setGraphic(new FontIcon("fth-volume-2"));
            volumeIcon.setTooltip(new Tooltip("Volumen del reproductor"));
            Slider volume = new Slider(0, 100, 80);
            volume.setPrefWidth(75);
            volume.setMaxWidth(100);
            volume.setTooltip(new Tooltip("Volumen"));
            active.setVolume(0.8);
            volume.valueProperty().addListener((obs, before, now) ->
                    active.setVolume(now.doubleValue() / 100.0));
            active.setOnError(() -> unsupportedMedia(
                    original, converted, active.getError()));
            HBox controls = new HBox(10, playPause, stop, seek, elapsed,
                    volumeIcon, volume);
            controls.getStyleClass().add("media-playback-controls");
            controls.setAlignment(Pos.CENTER_LEFT);
            if (converted && kindFor(original) == Kind.VIDEO) {
                Label preview = new Label(conversionMode == MediaTranscoder.Output.VIDEO_REMUX
                        ? "Vídeo completo · resolución, fotogramas y duración originales"
                        : "Vídeo completo · H.264, hasta 1080p y FPS originales");
                HBox full = new HBox(12, preview, openOriginalButton(original));
                full.setAlignment(Pos.CENTER_LEFT);
                full.setPadding(new Insets(3, 9, 5, 9));
                setBottom(new VBox(4, controls, full));
            } else {
                setBottom(controls);
            }
        } catch (MediaException | IllegalArgumentException problem) {
            unsupportedMedia(original, converted, problem);
        }
    }

    private static boolean isUsableDuration(Duration duration) {
        return duration != null && !duration.isUnknown()
                && !duration.isIndefinite() && duration.toMillis() > 0;
    }

    private static String formatTime(Duration duration) {
        if (!isUsableDuration(duration)) return "0:00";
        long seconds = Math.max(0L, (long)(duration.toMillis() / 1000));
        return String.format(Locale.ROOT, "%d:%02d", seconds / 60, seconds % 60);
    }

    private static void seekPlayer(MediaPlayer player, double percent) {
        Duration duration = player.getTotalDuration();
        if (isUsableDuration(duration))
            player.seek(Duration.millis(duration.toMillis() * percent / 100.0));
    }

    private void unsupportedMedia(Path original, boolean converted, Throwable reason) {
        if (disposed) return;
        if (reason != null) {
            System.err.println("[CodeTrace/media] JavaFX: "
                    + reason.getClass().getSimpleName() + ": " + reason.getMessage());
        }
        releasePlayer();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
        String detail = reason == null || reason.getMessage() == null ? ""
                : " Detalle: " + abbreviate(reason.getMessage(), 180);
        message("JavaFX/Windows no puede reproducir este códec." + detail
                + " Puedes preparar el archivo completo en H.264 HD, "
                + "o abrir el original con el reproductor del sistema.");
        showPlaybackOptions(original, !converted
                || conversionMode == MediaTranscoder.Output.VIDEO_REMUX);
    }

    private static String abbreviate(String text, int maximum) {
        String compact = text.replaceAll("[\\r\\n\\t]+", " ").trim();
        return compact.length() > maximum
                ? compact.substring(0, maximum) + "…" : compact;
    }

    private void showOriginalAction(Path original) {
        showPlaybackOptions(original, false);
    }

    /**
     * Never launch another application automatically. This action is only
     * performed after the user clicks the button.
     */
    private Button openOriginalButton(Path original) {
        Button button = new Button("Abrir archivo completo en reproductor externo");
        button.setTooltip(new Tooltip(
                "Abre el archivo original con la aplicación predeterminada de Windows."));
        button.setOnAction(event -> {
            button.setDisable(true);
            Thread open = new Thread(() -> {
                try {
                    if (!java.awt.Desktop.isDesktopSupported()
                            || !java.awt.Desktop.getDesktop()
                                    .isSupported(java.awt.Desktop.Action.OPEN)) {
                        throw new IOException("El sistema no admite esta acción.");
                    }
                    java.awt.Desktop.getDesktop().open(original.toFile());
                } catch (IOException | UnsupportedOperationException | SecurityException e) {
                    Platform.runLater(() -> {
                        if (!disposed) {
                            message("No se pudo abrir el reproductor externo: "
                                    + abbreviate(e.getMessage() == null
                                            ? "operación no disponible" : e.getMessage(), 180));
                            showOriginalAction(original);
                        }
                    });
                } finally {
                    Platform.runLater(() -> {
                        if (!disposed) button.setDisable(false);
                    });
                }
            }, "codetrace-external-media-open");
            open.setDaemon(true);
            open.start();
        });
        return button;
    }

    private void releasePlayer() {
        if (player != null) {
            player.setAudioSpectrumListener(null);
            player.stop();
            player.dispose();
            player = null;
        }
        if (spectrum != null) {
            spectrum.dispose();
            spectrum = null;
        }
    }

    private void message(String text) {
        setBottom(null);
        releaseViewport();
        Label explanation = new Label(text);
        explanation.setWrapText(true);
        explanation.getStyleClass().add("media-error");
        StackPane canvas = new StackPane(explanation);
        canvas.setPadding(new Insets(25));
        setCenter(canvas);
    }

    private void installViewport(javafx.scene.Node visual) {
        releaseViewport();
        ZoomableMediaViewport next = new ZoomableMediaViewport(visual);
        next.zoomPercentProperty().bindBidirectional(zoomPercent);
        viewport = next;
        setCenter(next);
    }

    private void releaseViewport() {
        if (viewport == null) return;
        viewport.zoomPercentProperty().unbindBidirectional(zoomPercent);
        viewport = null;
    }

    public boolean supportsZoom() {
        return !disposed && (previewKind == Kind.IMAGE
                || previewKind == Kind.SVG || previewKind == Kind.VIDEO);
    }

    public int getZoomPercent() { return zoomPercent.get(); }
    public IntegerProperty zoomPercentProperty() { return zoomPercent; }
    public void setZoomPercent(int value) {
        zoomPercent.set(Math.max(25, Math.min(400, value)));
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
        releaseViewport();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
    }
}
