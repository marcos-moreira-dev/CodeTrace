package com.mmdev.codetrace.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/** Consistent vector glyphs used by the desktop toolbar. */
public final class ToolbarIcons {
    public enum Icon {
        OPEN("M 3 6 H 10 L 12 9 H 21 V 19 H 3 Z M 3 9 V 5 H 10"),
        SAVE("M 4 3 H 18 L 21 6 V 21 H 3 V 3 Z M 7 3 V 10 H 17 V 3 M 7 21 V 14 H 17 V 21"),
        REFRESH("M 21 12 A 9 9 0 1 1 18 5 M 21 3 V 9 H 15"),
        SKIP("M 4 5 V 19 M 8 5 L 16 12 L 8 19 Z M 19 5 V 19"),
        PREVIEW("M 2 12 C 6 4 18 4 22 12 C 18 20 6 20 2 12 Z M 12 9 A 3 3 0 1 1 11.99 9"),
        SOURCE("M 9 7 L 4 12 L 9 17 M 15 7 L 20 12 L 15 17");
        final String path;
        Icon(String path) { this.path = path; }
    }

    private ToolbarIcons() {}

    public static Button button(String title, Icon icon, String tooltip) {
        Button button = new Button(title, graphic(icon));
        button.setTooltip(new Tooltip(tooltip));
        button.setAccessibleText(tooltip);
        button.getStyleClass().add("toolbar-action");
        return button;
    }

    /** Reusable vector graphic, including when a button changes state. */
    public static Node graphic(Icon icon) {
        SVGPath graphic = new SVGPath();
        graphic.setContent(icon.path);
        graphic.setFill(Color.TRANSPARENT);
        graphic.setStroke(Color.web(icon == Icon.SKIP ? "#26834B" : "#4A6076"));
        graphic.setStrokeWidth(1.8);
        graphic.setStrokeLineCap(StrokeLineCap.ROUND);
        graphic.setStrokeLineJoin(StrokeLineJoin.ROUND);
        graphic.setScaleX(0.78);
        graphic.setScaleY(0.78);
        StackPane box = new StackPane(graphic);
        box.setAlignment(Pos.CENTER);
        box.setMinSize(20, 20);
        box.setPrefSize(20, 20);
        box.setMaxSize(20, 20);
        return box;
    }
}
