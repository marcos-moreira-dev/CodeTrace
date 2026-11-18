package com.mmdev.codetrace.ui;

import java.util.function.IntConsumer;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/** Reusable, narrow desktop zoom rail with mouse-wheel and drag support. */
public final class EditorZoomControl extends VBox {
    private final Slider slider = new Slider(10, 36, 16);
    private final IntConsumer onChanged;

    public EditorZoomControl(int initialSize, IntConsumer onChanged) {
        this.onChanged = onChanged;
        getStyleClass().add("editor-zoom-control");
        setAlignment(Pos.CENTER);
        setSpacing(9);
        setMinWidth(39);
        setPrefWidth(39);
        Button increase = new Button("A+");
        Button decrease = new Button("A−");
        increase.getStyleClass().add("zoom-step");
        decrease.getStyleClass().add("zoom-step");
        increase.setTooltip(new Tooltip("Aumentar tamaño de letra"));
        decrease.setTooltip(new Tooltip("Reducir tamaño de letra"));
        increase.setOnAction(event -> setValue((int) Math.round(slider.getValue()) + 1));
        decrease.setOnAction(event -> setValue((int) Math.round(slider.getValue()) - 1));
        slider.setOrientation(Orientation.VERTICAL);
        slider.setMinHeight(155);
        slider.setPrefHeight(210);
        slider.setMaxHeight(Double.MAX_VALUE);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(2);
        slider.setBlockIncrement(1);
        slider.setTooltip(new Tooltip("Tamaño de letra"));
        slider.valueProperty().addListener((observed, previous, current) ->
                onChanged.accept((int) Math.round(current.doubleValue())));
        slider.setOnScroll(event -> {
            setValue((int) Math.round(slider.getValue()) + (event.getDeltaY() > 0 ? 1 : -1));
            event.consume();
        });
        getChildren().addAll(increase, slider, decrease);
        setValue(initialSize);
    }

    public void setValue(int pointSize) {
        slider.setValue(Math.max(10, Math.min(36, pointSize)));
    }

    public int value() {
        return (int) Math.round(slider.getValue());
    }
}
