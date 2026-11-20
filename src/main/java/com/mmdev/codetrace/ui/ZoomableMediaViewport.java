package com.mmdev.codetrace.ui;

import java.util.function.IntConsumer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Reusable, read-only media canvas: Ctrl+wheel zoom, left-drag pan and
 * double-click reset. It never moves or modifies the underlying file.
  * <p><strong>Engineering rationale.</strong> Images are displayed in a viewport with pan and zoom transforms, not rewritten or resampled on disk whenever the user navigates. Keeping that camera-like interaction in one component avoids duplicating mouse-gesture logic across media formats.</p>
*/
public final class ZoomableMediaViewport extends StackPane {
    public static final int MIN_ZOOM = 25;
    public static final int MAX_ZOOM = 400;
    private final Node content;
    private final IntegerProperty zoom = new SimpleIntegerProperty(100);
    private double pressX;
    private double pressY;
    private double initialX;
    private double initialY;
    private boolean dragged;

    public ZoomableMediaViewport(Node content) {
        this.content = content;
        getChildren().add(content);
        setAlignment(Pos.CENTER);
        getStyleClass().add("media-zoom-viewport");
        Rectangle bounds = new Rectangle();
        bounds.widthProperty().bind(widthProperty());
        bounds.heightProperty().bind(heightProperty());
        setClip(bounds);
        setMinSize(0, 0);
        zoom.addListener((obs, before, now) -> {
            double factor = now.doubleValue() / 100d;
            content.setScaleX(factor);
            content.setScaleY(factor);
            if (now.intValue() == 100) {
                content.setTranslateX(0);
                content.setTranslateY(0);
            } else {
                clampTranslation();
            }
        });

        addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown() && !event.isShortcutDown()) return;
            int delta = event.getDeltaY() > 0 ? 10 : -10;
            setZoomPercent(getZoomPercent() + delta);
            event.consume();
        });
        addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() != MouseButton.PRIMARY) return;
            pressX = event.getSceneX();
            pressY = event.getSceneY();
            initialX = content.getTranslateX();
            initialY = content.getTranslateY();
            dragged = false;
        });
        addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, event -> {
            if (!event.isPrimaryButtonDown() || getZoomPercent() == 100) return;
            if (Math.abs(event.getSceneX() - pressX) > 3
                    || Math.abs(event.getSceneY() - pressY) > 3) dragged = true;
            content.setTranslateX(initialX + event.getSceneX() - pressX);
            content.setTranslateY(initialY + event.getSceneY() - pressY);
            clampTranslation();
            event.consume();
        });
        addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2 && !dragged) {
                setZoomPercent(100);
                event.consume();
            }
        });
        widthProperty().addListener((obs, a, b) -> clampTranslation());
        heightProperty().addListener((obs, a, b) -> clampTranslation());
    }

    private void clampTranslation() {
        double scale = getZoomPercent() / 100d;
        double x = Math.max(0, (content.getLayoutBounds().getWidth() * scale - getWidth()) / 2d);
        double y = Math.max(0, (content.getLayoutBounds().getHeight() * scale - getHeight()) / 2d);
        content.setTranslateX(Math.min(x, Math.max(-x, content.getTranslateX())));
        content.setTranslateY(Math.min(y, Math.max(-y, content.getTranslateY())));
    }

    public int getZoomPercent() { return zoom.get(); }
    public IntegerProperty zoomPercentProperty() { return zoom; }
    public void setZoomPercent(int percent) {
        zoom.set(Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, percent)));
    }
}
