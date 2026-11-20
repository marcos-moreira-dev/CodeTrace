package com.mmdev.codetrace.ui;

import java.util.function.IntConsumer;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Compact, reusable font-size control for the status bar.
 * Only interaction with this control changes the global editor font size.
  * <p><strong>Engineering rationale.</strong> The status bar exposes one compact font-size control for text-oriented views. Keep zoom state a presentation preference rather than modifying the reference or transcription; when a media or graph tab is selected, the application switches to the appropriate visual zoom semantics.</p>
*/
public final class EditorZoomControl extends HBox {
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 36;

    private final Slider slider = new Slider(MIN_SIZE, MAX_SIZE, 16);
    private final Label value = new Label();
    private final IntConsumer onChanged;
    private boolean updating;

    public EditorZoomControl(int initialSize, IntConsumer onChanged) {
        this.onChanged = onChanged;
        getStyleClass().add("status-zoom-control");
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(5);

        Button decrease = new Button("−");
        Button increase = new Button("+");
        decrease.getStyleClass().add("zoom-step");
        increase.getStyleClass().add("zoom-step");
        decrease.setTooltip(new Tooltip("Reducir tamaño de letra"));
        increase.setTooltip(new Tooltip("Aumentar tamaño de letra"));
        decrease.setOnAction(event -> setFromUser((int) Math.round(slider.getValue()) - 1));
        increase.setOnAction(event -> setFromUser((int) Math.round(slider.getValue()) + 1));

        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setMinWidth(92);
        slider.setPrefWidth(112);
        slider.setMaxWidth(112);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(1);
        slider.setTooltip(new Tooltip("Tamaño de fuente del editor"));
        slider.valueProperty().addListener((obs, oldValue, newValue) -> {
            int size = (int) Math.round(newValue.doubleValue());
            value.setText(size + " pt");
            if (!updating && (int) Math.round(oldValue.doubleValue()) != size) {
                onChanged.accept(size);
            }
        });
        slider.setOnScroll(event -> {
            setFromUser((int) Math.round(slider.getValue())
                    + (event.getDeltaY() > 0 ? 1 : -1));
            event.consume();
        });
        value.getStyleClass().add("zoom-value");
        value.setMinWidth(40);
        value.setAlignment(Pos.CENTER_RIGHT);
        getChildren().addAll(decrease, slider, increase, value);
        setValue(initialSize);
    }

    /** Synchronization from Preferences must not invoke onChanged again. */
    public void setValue(int pointSize) {
        updating = true;
        try {
            int clamped = Math.max(MIN_SIZE, Math.min(MAX_SIZE, pointSize));
            slider.setValue(clamped);
            value.setText(clamped + " pt");
        } finally {
            updating = false;
        }
    }

    private void setFromUser(int pointSize) {
        int size = Math.max(MIN_SIZE, Math.min(MAX_SIZE, pointSize));
        if (value().equals(size + " pt")) {
            return;
        }
        slider.setValue(size);
    }

    public String value() {
        return value.getText();
    }
}
