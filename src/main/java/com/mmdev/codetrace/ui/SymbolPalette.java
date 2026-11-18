package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.symbols.SymbolCatalog;
import com.mmdev.codetrace.symbols.SymbolCatalog.Layout;
import com.mmdev.codetrace.symbols.SymbolCatalog.Symbol;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * A compact Word-like gallery of programming symbols.
 * Selecting an entry copies it; it NEVER enters a character into the
 * training editor or increments typing statistics.
 */
public final class SymbolPalette extends MenuButton {
    private final ComboBox<Layout> layoutSelector = new ComboBox<>();
    private final Label hint = new Label();
    private final Consumer<Layout> onLayoutChanged;

    public SymbolPalette(Layout initialLayout, Consumer<Layout> onLayoutChanged) {
        super("Símbolos");
        this.onLayoutChanged = onLayoutChanged;
        getStyleClass().addAll("symbol-menu-button", "toolbar-action");
        setTooltip(new Tooltip("Copiar símbolos y consultar sus teclas"));

        layoutSelector.getItems().setAll(Layout.values());
        layoutSelector.setValue(initialLayout);
        layoutSelector.setMaxWidth(Double.MAX_VALUE);
        layoutSelector.setCellFactory(view -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Layout value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : value.label());
            }
        });
        layoutSelector.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Layout value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : value.label());
            }
        });
        layoutSelector.valueProperty().addListener((obs, before, selected) -> {
            if (selected != null) {
                hint.setText("");
                this.onLayoutChanged.accept(selected);
            }
        });

        FlowPane grid = new FlowPane(5, 5);
        grid.getStyleClass().add("symbol-grid");
        grid.setPrefWrapLength(6 * 40 + 5 * 5);
        for (Symbol symbol : SymbolCatalog.all()) {
            Button tile = new Button(symbol.glyph());
            tile.getStyleClass().add("symbol-tile");
            tile.setMinSize(38, 38);
            tile.setPrefSize(38, 38);
            tile.setMaxSize(38, 38);
            tile.setAccessibleText(symbol.name());
            tile.setTooltip(new Tooltip(symbol.name()));
            tile.setOnMouseEntered(event -> hint.setText(
                    symbol.name() + ": " + symbol.keys(layoutSelector.getValue())));
            tile.setOnAction(event -> {
                ClipboardContent copied = new ClipboardContent();
                copied.putString(symbol.glyph());
                boolean success = Clipboard.getSystemClipboard().setContent(copied);
                hint.setText((success ? "Copiado: " : "No se pudo copiar: ")
                        + symbol.glyph() + "  ·  "
                        + symbol.keys(layoutSelector.getValue()));
            });
            grid.getChildren().add(tile);
        }

        Label layoutLabel = new Label("Distribución del teclado");
        layoutLabel.getStyleClass().add("palette-caption");
        hint.getStyleClass().add("symbol-hint");
        hint.setWrapText(true);
        hint.setMinHeight(48);
        hint.setMaxWidth(Double.MAX_VALUE);
        VBox body = new VBox(7, grid, layoutLabel, layoutSelector, hint);
        body.getStyleClass().add("symbol-palette");
        body.setAlignment(Pos.TOP_LEFT);
        body.setPadding(new Insets(11));
        CustomMenuItem gallery = new CustomMenuItem(body, false);
        gallery.setHideOnClick(false);
        getItems().add(gallery);
        setOnShowing(event -> hint.setText("Selecciona un símbolo para copiarlo."));
    }

    public void selectLayout(Layout layout) {
        layoutSelector.setValue(layout);
    }
}
