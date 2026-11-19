package com.mmdev.codetrace.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

/** Always-on status-bar stopwatch; its time is independent of open files. */
public final class StatusStopwatch extends HBox {
    private final StopwatchClock clock = new StopwatchClock();
    private final Label time = new Label("00:00:00");
    private final Button playPause = new Button();
    private final Button restart = new Button();
    private final Timeline ticker = new Timeline(
            new KeyFrame(Duration.millis(200), event -> refresh()));

    public StatusStopwatch() {
        super(5);
        getStyleClass().add("status-stopwatch");
        setAlignment(Pos.CENTER_LEFT);
        ticker.setCycleCount(Animation.INDEFINITE);
        time.getStyleClass().add("stopwatch-value");
        time.setMinWidth(67);
        time.setAccessibleText("Cronómetro de estudio");

        playPause.getStyleClass().add("stopwatch-action");
        playPause.setOnAction(event -> {
            if (clock.isRunning()) {
                clock.pause();
                ticker.stop();
            } else {
                clock.resume();
                ticker.play();
            }
            refresh();
        });

        restart.getStyleClass().add("stopwatch-action");
        restart.setGraphic(new FontIcon("fth-rotate-ccw"));
        restart.setTooltip(new Tooltip("Iniciar cronómetro desde cero"));
        restart.setAccessibleText("Iniciar desde cero");
        restart.setOnAction(event -> {
            clock.start();
            ticker.play();
            refresh();
        });

        getChildren().addAll(time, playPause, restart);
        refresh();
    }

    private void refresh() {
        time.setText(clock.display());
        boolean running = clock.isRunning();
        String action = running ? "Pausar" : (clock.hasStarted() ? "Reanudar" : "Iniciar");
        playPause.setGraphic(new FontIcon(running ? "fth-pause" : "fth-play"));
        playPause.setTooltip(new Tooltip(action + " cronómetro"));
        playPause.setAccessibleText(action + " cronómetro");
    }

    public void dispose() { ticker.stop(); }
}
