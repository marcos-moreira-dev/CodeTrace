package com.mmdev.codetrace.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * Shared, accessible rectangular single-choice control, reusable in settings
 * and workspaces. It uses real RadioButtons/ToggleGroup rather than checkboxes.
 */
public final class ExclusiveChoiceBar<T> extends HBox {
    public record Choice<T>(T value, String text, String tooltip) {}

    private final ToggleGroup group = new ToggleGroup();
    private final Map<T, RadioButton> buttons = new LinkedHashMap<>();

    public ExclusiveChoiceBar(List<Choice<T>> options, T initial,
                              Consumer<T> changed) {
        super(0);
        getStyleClass().add("exclusive-choice-bar");
        setAlignment(Pos.CENTER_LEFT);
        for (Choice<T> option : options) {
            RadioButton button = new RadioButton(option.text());
            button.setToggleGroup(group);
            button.setUserData(option.value());
            button.getStyleClass().add("exclusive-choice");
            if (option.tooltip() != null) {
                button.setTooltip(new javafx.scene.control.Tooltip(option.tooltip()));
            }
            buttons.put(option.value(), button);
            getChildren().add(button);
        }
        RadioButton selected = buttons.get(initial);
        if (selected != null) selected.setSelected(true);
        group.selectedToggleProperty().addListener((obs, old, toggle) -> {
            if (toggle == null) {
                if (old != null) group.selectToggle(old);
                return;
            }
            @SuppressWarnings("unchecked") T value = (T) toggle.getUserData();
            changed.accept(value);
        });
    }

    public T selected() {
        if (group.getSelectedToggle() == null) return null;
        @SuppressWarnings("unchecked")
        T result = (T) group.getSelectedToggle().getUserData();
        return result;
    }

    public void select(T value) {
        RadioButton button = Objects.requireNonNull(buttons.get(value));
        button.setSelected(true);
    }
}
