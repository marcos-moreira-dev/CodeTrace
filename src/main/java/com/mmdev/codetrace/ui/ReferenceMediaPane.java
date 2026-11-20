package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownRenderer;
import com.mmdev.codetrace.media.MediaTranscoder;
import com.mmdev.codetrace.media.OnDemandHlsVideo;
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
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
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

/** Read-only media preview; neither modifies files nor advances typing progress.  * <p><strong>Engineering rationale.</strong> Media previews are optional read-only views with their own resource lifecycle: JavaFX handles supported formats, while conversion or streaming fallbacks are launched only when required. Close must stop playback, cancel preparation and clean temporary assets; the original reference must never become the conversion destination.</p>
*/
public final class ReferenceMediaPane extends BorderPane {
    public enum Kind { IMAGE, SVG, AUDIO, VIDEO, UNSUPPORTED }
    private MediaPlayer player;
    private AudioSpectrumPane spectrum;
    private Task<Path> conversion;
    private Task<OnDemandHlsVideo> streamPreparation;
    private OnDemandHlsVideo streaming;
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
                message("No se pudo mostrar esta imagen.");
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
        if (kindFor(original) == Kind.VIDEO
                && MediaTranscoder.needsConversion(original)) {
            startStreaming(original);
        } else if (MediaTranscoder.needsConversion(original)) {
            convertAndPlay(original, MediaTranscoder.Output.AUDIO);
        } else {
            playMedia(original, original, false);
        }
    }

    /**
     * Only probe metadata up front. HLS asks for a six-second segment when
     * actually needed, including after a seek to a distant timestamp.
     */
    private void startStreaming(Path original) {
        if (disposed) return;
        if (conversion != null && !conversion.isDone()) conversion.cancel(true);
        if (streamPreparation != null && !streamPreparation.isDone())
            streamPreparation.cancel(true);
        releasePlayer();
        closeStreaming();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
        message("Preparando reproducción por partes…");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(44, 44);
        Label info = new Label("Se preparará únicamente la parte que necesites ver.");
        info.setWrapText(true);
        VBox loading = new VBox(14, spinner, info);
        loading.setAlignment(Pos.CENTER);
        loading.setPadding(new Insets(24));
        setCenter(new StackPane(loading));
        Button cancel = new Button("Cancelar");
        HBox actions = new HBox(10, cancel, openOriginalButton(original));
        actions.getStyleClass().add("media-playback-controls");
        setBottom(actions);

        AtomicReference<OnDemandHlsVideo> createdStream = new AtomicReference<>();
        Task<OnDemandHlsVideo> task = new Task<>() {
            @Override protected OnDemandHlsVideo call() throws IOException {
                OnDemandHlsVideo ready = OnDemandHlsVideo.open(original);
                createdStream.set(ready);
                if (isCancelled() || disposed) {
                    OnDemandHlsVideo stale = createdStream.getAndSet(null);
                    if (stale != null) stale.close();
                    return null;
                }
                return ready;
            }
        };
        streamPreparation = task;
        cancel.setOnAction(event -> {
            task.cancel(true);
            message("Se canceló la apertura del vídeo.");
            showPlaybackOptions(original, true);
        });
        task.setOnSucceeded(event -> {
            OnDemandHlsVideo ready = createdStream.getAndSet(null);
            if (ready == null) return;
            if (disposed || streamPreparation != task) {
                ready.close();
                return;
            }
            streaming = ready;
            playMedia(ready.playlistUrl(), original, false);
        });
        task.setOnCancelled(event -> {
            OnDemandHlsVideo stale = createdStream.getAndSet(null);
            if (stale != null) stale.close();
        });
        task.setOnFailed(event -> {
            OnDemandHlsVideo stale = createdStream.getAndSet(null);
            if (stale != null) stale.close();
            if (disposed || streamPreparation != task) return;
            System.err.println("[CodeTrace/HLS] " + task.getException());
            message("No se pudo iniciar la reproducción por partes.");
            showPlaybackOptions(original, true);
        });
        Thread worker = new Thread(task, "codetrace-hls-start");
        worker.setDaemon(true);
        worker.start();
    }

    private void closeStreaming() {
        if (streaming != null) {
            streaming.close();
            streaming = null;
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
        closeStreaming();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
        conversionMode = output;
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(44, 44);
        String heading = output == MediaTranscoder.Output.AUDIO
                ? "Abriendo audio…" : "Abriendo vídeo…";
        String explanation = output == MediaTranscoder.Output.AUDIO
                ? "Ya casi está listo."
                : "Puedes seguir utilizando CodeTrace. Los vídeos grandes "
                  + "pueden tardar unos minutos.";
        Label title = new Label(heading);
        title.setWrapText(true);
        Label hint = new Label(explanation);
        hint.setWrapText(true);
        VBox loading = new VBox(14, spinner, title, hint);
        loading.setAlignment(Pos.CENTER);
        loading.setPadding(new Insets(24));
        setCenter(new StackPane(loading));

        Button cancel = new Button("Cancelar");
        HBox bar = new HBox(10, cancel, openOriginalButton(original));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("media-playback-controls");
        setBottom(bar);

        AtomicReference<Path> created = new AtomicReference<>();
        AtomicReference<MediaTranscoder.Output> chosen = new AtomicReference<>(output);
        Task<Path> task = new Task<>() {
            @Override protected Path call() throws IOException {
                // Probe the video codec on this worker. If JavaFX would
                // display audio over a blank video track, prepare a playable
                // version directly instead of forcing a failed first attempt.
                MediaTranscoder.Output effective = output
                        == MediaTranscoder.Output.VIDEO_REMUX
                        ? MediaTranscoder.preferredVideoOutput(original) : output;
                chosen.set(effective);
                Path ready = MediaTranscoder.transcode(original, effective, this::isCancelled);
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
            conversionMode = chosen.get();
            task.cancel(true);
            message(output == MediaTranscoder.Output.AUDIO
                    ? "Se canceló la apertura del audio."
                    : "Se canceló la apertura del vídeo.");
            showPlaybackOptions(original,
                    conversionMode == MediaTranscoder.Output.VIDEO_REMUX);
        });
        task.setOnSucceeded(event -> {
            Path prepared = created.getAndSet(null);
            if (prepared == null) return;
            if (disposed || conversion != task) {
                MediaTranscoder.deleteTemporary(prepared);
                return;
            }
            conversionMode = chosen.get();
            temporaryMedia = prepared;
            playMedia(prepared, original, true);
        });
        task.setOnCancelled(event ->
                MediaTranscoder.deleteTemporary(created.getAndSet(null)));
        task.setOnFailed(event -> {
            MediaTranscoder.deleteTemporary(created.getAndSet(null));
            if (!disposed && conversion == task) {
                conversionMode = chosen.get();
                Throwable cause = task.getException();
                if (cause != null) System.err.println("[CodeTrace/media] " + cause);
                String issue = cause != null && cause.getMessage() != null
                        && cause.getMessage().contains("Espacio temporal")
                        ? "No hay suficiente espacio libre para abrir este archivo."
                        : "No se pudo abrir el archivo.";
                message(issue);
                showPlaybackOptions(original,
                        conversionMode == MediaTranscoder.Output.VIDEO_REMUX);
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
        boolean video = kindFor(original) == Kind.VIDEO;
        Button retry = new Button(video ? "Reproducir por partes" : "Reintentar");
        retry.setOnAction(event -> {
            if (video) startStreaming(original);
            else convertAndPlay(original, MediaTranscoder.Output.AUDIO);
        });
        actions.getChildren().add(retry);
        if (allowHDConversion && video) {
            Button compatible = new Button("Convertir todo el vídeo");
            compatible.setTooltip(new Tooltip(
                    "Alternativa lenta: prepara todo el vídeo antes de mostrarlo."));
            compatible.setOnAction(event -> convertAndPlay(
                    original, MediaTranscoder.Output.VIDEO));
            actions.getChildren().add(compatible);
        }
        actions.getChildren().add(openOriginalButton(original));
        setBottom(actions);
    }

    private void playMedia(Path file, Path original, boolean converted) {
        playMedia(file.toUri().toString(), original, converted);
    }

    private void playMedia(String uri, Path original, boolean converted) {
        if (disposed) return;
        try {
            Media media = new Media(uri);
            player = new MediaPlayer(media);
// JavaFX starts playback as soon as the media is READY. This also
            // applies after chunked video preparation or audio conversion;
            // Play/Pause and Stop continue to work normally.
            player.setAutoPlay(true);
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
                Label details = new Label(AudioFileDetails.summary(
                        original, null, media.getMetadata()));
                details.getStyleClass().add("media-audio-subtitle");
                details.setWrapText(true);
                details.setMaxWidth(Double.MAX_VALUE);
                // Duration and tags may arrive asynchronously from the decoder.
                Runnable refreshDetails = () -> {
                    if (!disposed && player == active)
                        details.setText(AudioFileDetails.summary(
                                original, active.getTotalDuration(), media.getMetadata()));
                };
                active.totalDurationProperty().addListener(
                        (obs, previous, duration) -> refreshDetails.run());
                media.getMetadata().addListener(
                        (javafx.collections.MapChangeListener<String, Object>) change ->
                                refreshDetails.run());
                active.setOnReady(refreshDetails);
                spectrum = new AudioSpectrumPane();
                VBox audio = new VBox(10, fileName, details, spectrum);
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
            seek.setAccessibleText("Posición del vídeo o audio");
            seek.setTooltip(new Tooltip(
                    "Haz clic en cualquier punto de la barra para saltar; "
                    + "arrastra el control para ajustar la posición."));
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
            // JavaFX's default track click only moves by blockIncrement;
            // it does not jump straight to the clicked timestamp. Intercept
            // track presses (but not thumb presses) for exact point seeking.
            seek.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if (event.getButton() != MouseButton.PRIMARY) return;
                if (!(event.getTarget() instanceof Node target)) return;
                for (Node node = target; node != null && node != seek;
                     node = node.getParent()) {
                    if (node.getStyleClass().contains("thumb")) return;
                    if (!node.getStyleClass().contains("track")) continue;
                    Bounds track = node.localToScene(node.getBoundsInLocal());
                    double percent = percentAtTrack(
                            event.getSceneX(), track.getMinX(), track.getWidth());
                    seek.setValue(percent);
                    seekPlayer(active, percent);
                    event.consume(); // Prevent a second, incremental track jump.
                    return;
                }
            });
            // Thumb dragging commits on release. Arrow/Home/End keys also seek.
            seek.valueChangingProperty().addListener((obs, before, now) -> {
                if (!now) seekPlayer(active, seek.getValue());
            });
            seek.setOnKeyReleased(event -> {
                KeyCode key = event.getCode();
                if (key == KeyCode.LEFT || key == KeyCode.RIGHT
                        || key == KeyCode.HOME || key == KeyCode.END
                        || key == KeyCode.PAGE_UP || key == KeyCode.PAGE_DOWN) {
                    seekPlayer(active, seek.getValue());
                }
            });
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
            if (kindFor(original) == Kind.VIDEO) {
                Label preview = new Label(streaming != null
                        ? "Vídeo por partes · solo lectura"
                        : (converted ? "Vídeo listo" : "Vídeo de referencia"));
                HBox full = new HBox(12, preview);
                if (streaming != null) {
                    Button fullConversion = new Button("Conversión completa");
                    fullConversion.setTooltip(new Tooltip(
                            "Alternativa si la reproducción por partes no funciona. "
                            + "Prepara todo el vídeo y puede tardar unos minutos."));
                    fullConversion.setOnAction(event -> convertAndPlay(
                            original, MediaTranscoder.Output.VIDEO));
                    full.getChildren().add(fullConversion);
                }
                if (streaming == null && (!converted || conversionMode == MediaTranscoder.Output.VIDEO_REMUX)) {
                    // JavaFX can output audio without surfacing a video-decoding
                    // error. Expose recovery even if its error callback never runs.
                    Button fallback = new Button("¿No se ve la imagen? Mejorar reproducción");
                    fallback.setTooltip(new Tooltip(
                            "Prepara solo los fragmentos que necesitas con FFmpeg."));
                    fallback.setOnAction(event -> startStreaming(original));
                    full.getChildren().add(fallback);
                }
                full.getChildren().add(openOriginalButton(original));
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

    /** Map a click on the *track*, not the whole Slider with its thumb margin. */
    static double percentAtTrack(double sceneX, double trackStart, double trackWidth) {
        if (!Double.isFinite(sceneX) || !Double.isFinite(trackStart)
                || !Double.isFinite(trackWidth) || trackWidth <= 0) return 0;
        return Math.max(0, Math.min(100,
                (sceneX - trackStart) / trackWidth * 100.0));
    }

    private static void seekPlayer(MediaPlayer player, double percent) {
        Duration duration = player.getTotalDuration();
        if (!isUsableDuration(duration) || !Double.isFinite(percent)) return;
        Duration destination = Duration.millis(duration.toMillis()
                * Math.max(0, Math.min(100, percent)) / 100.0);
        // MediaPlayer ignores seek() while STOPPED. Move to PAUSED first so
        // a click after pressing Stop can still change the position.
        if (player.getStatus() == MediaPlayer.Status.STOPPED) {
            player.pause();
            Platform.runLater(() -> {
                if (player.getStatus() != MediaPlayer.Status.DISPOSED
                        && player.getStatus() != MediaPlayer.Status.STOPPED)
                    player.seek(destination);
            });
        } else {
            player.seek(destination);
        }
    }

    private void unsupportedMedia(Path original, boolean converted, Throwable reason) {
        if (disposed) return;
        if (reason != null) {
            System.err.println("[CodeTrace/media] JavaFX: "
                    + reason.getClass().getSimpleName() + ": " + reason.getMessage());
        }
        releasePlayer();
        closeStreaming();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
        message(kindFor(original) == Kind.AUDIO
                ? "No se pudo reproducir este audio."
                : "No se pudo reproducir este vídeo.");
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
        Button button = new Button("Abrir con otra aplicación");
        button.setTooltip(new Tooltip(
                "Abre el archivo en el reproductor predeterminado del equipo."));
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
                            System.err.println("[CodeTrace/media] " + e);
                            message("No se pudo abrir el archivo con otra aplicación.");
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
        if (streamPreparation != null) streamPreparation.cancel(true);
        if (imagePreparation != null) imagePreparation.cancel(true);
        if (webImage != null) {
            webImage.getEngine().loadContent("", "text/html");
            webImage = null;
        }
        releasePlayer();
        closeStreaming();
        releaseViewport();
        MediaTranscoder.deleteTemporary(temporaryMedia);
        temporaryMedia = null;
    }
}
