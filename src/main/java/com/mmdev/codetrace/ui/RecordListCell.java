package com.mmdev.codetrace.ui;

import java.util.function.Function;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Shared compact desktop list item for commands, search results and other
 * structured records. Presentation only; the owning ListView retains actions.
 */
public final class RecordListCell<T> extends ListCell<T> {
    public record Content(String title, String subtitle, String description,
                          String iconLiteral) {}

    private final Function<T, Content> presentation;

    public RecordListCell(Function<T, Content> presentation) {
        this.presentation = presentation;
        getStyleClass().add("record-list-cell");
    }

    @Override protected void updateItem(T value, boolean empty) {
        super.updateItem(value, empty);
        setText(null);
        if (empty || value == null) {
            setGraphic(null);
            return;
        }
        Content record = presentation.apply(value);
        Label name = new Label(record.title());
        name.getStyleClass().add("record-list-title");
        name.setMaxWidth(Double.MAX_VALUE);
        name.setTextOverrun(javafx.scene.control.OverrunStyle.CENTER_ELLIPSIS);
        Label description = new Label(record.description() == null
                ? "" : record.description());
        description.getStyleClass().add("record-list-description");
        description.setMaxWidth(Double.MAX_VALUE);
        description.setTextOverrun(javafx.scene.control.OverrunStyle.ELLIPSIS);
        Label subtitle = new Label(record.subtitle() == null
                ? "" : record.subtitle());
        subtitle.getStyleClass().add("record-list-subtitle");
        VBox text = new VBox(2, name, subtitle);
        if (!description.getText().isBlank()) text.getChildren().add(description);
        HBox.setHgrow(text, Priority.ALWAYS);
        String literal = record.iconLiteral() == null
                ? "fth-file-text" : record.iconLiteral();
        FontIcon icon = new FontIcon(literal);
        icon.setIconSize(16);
        HBox row = new HBox(9, icon, text);
        row.getStyleClass().add("record-list-row");
        row.setAlignment(Pos.CENTER_LEFT);
        setGraphic(row);
    }
}
