package com.mmdev.codetrace.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

/** Shared compact desktop checkbox: consistent hit area, keyboard focus and CSS.  * <p><strong>Engineering rationale.</strong> Small shared UI controls encode repeated styling, accessibility and sizing conventions, keeping window implementations consistent. The abstraction is intentionally shallow: it should remove visual duplication without becoming a framework for unrelated business rules.</p>
*/
public final class AppControls {
    private AppControls() {}

    public static CheckBox checkBox(String caption) {
        CheckBox result = new CheckBox(caption);
        result.getStyleClass().add("app-checkbox");
        result.setMinHeight(27);
        return result;
    }

    public static CheckBox checkBox(String caption, String hint) {
        CheckBox result = checkBox(caption);
        if (hint != null && !hint.isBlank()) result.setTooltip(new Tooltip(hint));
        return result;
    }
}
