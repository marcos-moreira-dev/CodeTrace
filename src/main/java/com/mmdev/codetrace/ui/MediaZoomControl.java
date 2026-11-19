package com.mmdev.codetrace.ui;

import java.util.function.IntConsumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

/** Contextual statusbar control; replaces font zoom when a media tab is active. */
public final class MediaZoomControl extends HBox {
    private final Slider slider = new Slider(25, 400, 100);
    private final Label value = new Label("100 %");
    private final IntConsumer changed;
    private boolean updating;

    public MediaZoomControl(IntConsumer changed) {
        this.changed = changed;
        getStyleClass().addAll("status-zoom-control", "media-zoom-control");
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(5);
        Button decrease = new Button("−");
        Button increase = new Button("+");
        decrease.getStyleClass().add("zoom-step");
        increase.getStyleClass().add("zoom-step");
        decrease.setTooltip(new Tooltip("Alejar"));
        increase.setTooltip(new Tooltip("Acercar"));
        decrease.setOnAction(event -> setFromUser((int)slider.getValue() - 10));
        increase.setOnAction(event -> setFromUser((int)slider.getValue() + 10));
        slider.setPrefWidth(112);
        slider.setMinWidth(92);
        slider.setMaxWidth(112);
        slider.setTooltip(new Tooltip("Zoom de imagen, vídeo o mapa; Ctrl+rueda"));
        slider.valueProperty().addListener((obs, previous, current) -> {
            int percent = (int) Math.round(current.doubleValue());
            value.setText(percent + " %");
            if (!updating && (int)Math.round(previous.doubleValue()) != percent)
                changed.accept(percent);
        });
        slider.setOnScroll(event -> {
            setFromUser((int)slider.getValue() + (event.getDeltaY() > 0 ? 10 : -10));
            event.consume();
        });
        value.getStyleClass().add("zoom-value");
        value.setMinWidth(46);
        value.setAlignment(Pos.CENTER_RIGHT);
        FontIcon graphic = new FontIcon("fth-image");
        graphic.setIconSize(15);
        getChildren().addAll(graphic, decrease, slider, increase, value);
    }

    /** Reuse the same status-bar position for media and dependency graphs. */
    public void setGraphMode(boolean graph) {
        getStyleClass().removeAll("graph-zoom-control", "media-image-zoom-control");
        getStyleClass().add(graph ? "graph-zoom-control" : "media-image-zoom-control");
        FontIcon graphic = new FontIcon(graph ? "fth-share-2" : "fth-image");
        graphic.setIconSize(15);
        getChildren().set(0, graphic);
        setAccessibleText(graph ? "Zoom del mapa de dependencias, porcentaje"
                : "Zoom multimedia, porcentaje");
    }

    public void setValue(int zoomPercent) {
        updating = true;
        try {
            slider.setValue(Math.max(25, Math.min(400, zoomPercent)));
            value.setText((int)Math.round(slider.getValue()) + " %");
        } finally {
            updating = false;
        }
    }

    private void setFromUser(int percent) {
        slider.setValue(Math.max(25, Math.min(400, percent)));
    }
}
