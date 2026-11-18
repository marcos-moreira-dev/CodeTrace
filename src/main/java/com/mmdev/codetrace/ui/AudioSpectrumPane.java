package com.mmdev.codetrace.ui;

import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Lightweight real-time spectrum for JavaFX MediaPlayer audio.
 * A spectrum is NOT a precomputed waveform: bars represent frequency bands
 * and move only when the player reports new audio samples.
 */
public final class AudioSpectrumPane extends Region {
    private static final int BANDS = 40;
    private final Canvas canvas = new Canvas();
    private final double[] levels = new double[BANDS];
    private volatile boolean disposed;

    public AudioSpectrumPane() {
        getStyleClass().add("audio-spectrum-pane");
        setMinSize(0, 120);
        setPrefHeight(174);
        getChildren().add(canvas);
        widthProperty().addListener((obs, before, after) -> redraw());
        heightProperty().addListener((obs, before, after) -> redraw());
        redraw();
    }

    /** Accept magnitude values in dB from JavaFX AudioSpectrumListener. */
    public void accept(float[] magnitudes) {
        if (disposed || magnitudes == null) return;
        // Copy the buffer: JavaFX may reuse it after the callback returns.
        float[] copy = Arrays.copyOf(magnitudes, Math.min(BANDS, magnitudes.length));
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> accept(copy));
            return;
        }
        for (int i = 0; i < BANDS; i++) {
            double decibels = i < copy.length ? copy[i] : -60.0;
            double normalized = Math.max(0, Math.min(1, (decibels + 60.0) / 60.0));
            levels[i] = Math.max(normalized, levels[i] * 0.68);
        }
        redraw();
    }

    public void clear() {
        if (disposed) return;
        Arrays.fill(levels, 0);
        redraw();
    }

    public void dispose() {
        disposed = true;
        Arrays.fill(levels, 0);
    }

    @Override protected void layoutChildren() {
        super.layoutChildren();
        canvas.setWidth(Math.max(0, getWidth()));
        canvas.setHeight(Math.max(0, getHeight()));
        redraw();
    }

    private void redraw() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        if (w <= 0 || h <= 0 || disposed) return;
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#f5f8fa"));
        g.fillRect(0, 0, w, h);
        g.setFill(Color.web("#61788a"));
        g.fillText("ESPECTRO DE AUDIO · EN TIEMPO REAL", 18, 22);
        g.setStroke(Color.web("#d9e3ea"));
        double bottom = h - 18;
        double plotTop = 38;
        for (int k = 1; k <= 3; k++) {
            double y = plotTop + (bottom - plotTop) * k / 4;
            g.strokeLine(16, y, w - 16, y);
        }
        double usable = Math.max(0, w - 36);
        double step = usable / BANDS;
        for (int i = 0; i < BANDS; i++) {
            double height = Math.max(3, (bottom - plotTop) * levels[i]);
            g.setFill(i < BANDS * 0.63 ? Color.web("#4a7899")
                    : Color.web("#579480"));
            g.fillRect(18 + step * i, bottom - height,
                    Math.max(1.0, step * 0.64), height);
        }
    }
}
