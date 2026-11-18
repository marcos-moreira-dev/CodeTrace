package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.markdown.MarkdownRenderer;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
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
                case IMAGE -> showImage(actual);
                case SVG -> showSvg(actual, root);
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
            if (error) message("Este formato de imagen no es compatible con JavaFX.");
        });
    }

    private void showSvg(Path file, Path root) {
        String filename = URLEncoder.encode(file.getFileName().toString(),
                StandardCharsets.UTF_8).replace("+", "%20");
        var result = MarkdownRenderer.render("![SVG](" + filename + ")", file, root, false);
        if (result.localImages() == 0) {
            message("No se puede mostrar este SVG de forma segura.");
            return;
        }
        WebView web = new WebView();
        web.setContextMenuEnabled(false);
        web.getEngine().setJavaScriptEnabled(false);
        web.getEngine().loadContent(result.html(), "text/html");
        setCenter(web);
    }

    private void showMedia(Path file) {
        try {
            Media media = new Media(file.toUri().toString());
            player = new MediaPlayer(media);
            player.setAutoPlay(false);
            if (kindFor(file) == Kind.VIDEO) {
                MediaView screen = new MediaView(player);
                screen.setPreserveRatio(true);
                screen.fitWidthProperty().bind(widthProperty().subtract(35));
                screen.fitHeightProperty().bind(heightProperty().subtract(90));
                StackPane canvas = new StackPane(screen);
                canvas.getStyleClass().add("media-canvas");
                setCenter(canvas);
            } else {
                Label fileName = new Label(file.getFileName().toString());
                fileName.getStyleClass().add("media-audio-title");
                StackPane cover = new StackPane(fileName);
                cover.getStyleClass().add("media-canvas");
                setCenter(cover);
            }
            Button play = new Button("Reproducir");
            Button stop = new Button("Detener");
            play.setOnAction(event -> {
                if (player.getStatus() == MediaPlayer.Status.PLAYING) player.pause();
                else player.play();
            });
            stop.setOnAction(event -> player.stop());
            player.statusProperty().addListener((obs, before, now) ->
                    play.setText(now == MediaPlayer.Status.PLAYING
                            ? "Pausar" : "Reproducir"));
            Slider seek = new Slider(0, 100, 0);
            HBox.setHgrow(seek, Priority.ALWAYS);
            player.currentTimeProperty().addListener((obs, before, now) -> {
                Duration length = player.getTotalDuration();
                if (!seek.isValueChanging() && length != null && !length.isUnknown()
                        && length.toMillis() > 0)
                    seek.setValue(now.toMillis() / length.toMillis() * 100);
            });
            seek.setOnMouseReleased(event -> {
                Duration length = player.getTotalDuration();
                if (length != null && !length.isUnknown() && length.toMillis() > 0)
                    player.seek(Duration.millis(length.toMillis() * seek.getValue() / 100));
            });
            player.setOnEndOfMedia(() -> { player.stop(); seek.setValue(0); });
            player.setOnError(() -> message(
                    "No se puede reproducir este medio con los códecs disponibles."));
            HBox controls = new HBox(9, play, stop, seek);
            controls.getStyleClass().add("media-playback-controls");
            controls.setAlignment(Pos.CENTER_LEFT);
            setBottom(controls);
        } catch (MediaException | IllegalArgumentException problem) {
            message("Este medio requiere otro códec o contenedor. "
                    + "Puedes abrirlo con «Mostrar en el Explorador».");
        }
    }

    private void message(String text) {
        Label explanation = new Label(text);
        explanation.setWrapText(true);
        explanation.getStyleClass().add("media-error");
        StackPane canvas = new StackPane(explanation);
        canvas.setPadding(new Insets(25));
        setCenter(canvas);
    }

    public void dispose() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }
}
