package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.symbols.SymbolCatalog;
import com.mmdev.codetrace.symbols.SymbolCatalog.Layout;
import com.mmdev.codetrace.symbols.SymbolCatalog.Symbol;
import java.util.function.Consumer;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A compact Word-like gallery of programming symbols.
 * Selecting an entry copies it; it NEVER enters a character into the
 * training editor or increments typing statistics.
 */
public final class SymbolPalette extends MenuButton {
    private final ComboBox<Layout> layoutSelector = new ComboBox<>();
    private final Label hint = new Label();
    private final VBox keyboardPreview = new VBox(3);
    private final Consumer<Layout> onLayoutChanged;
    private final Runnable onSymbolCopied;

    public SymbolPalette(Layout initialLayout, Consumer<Layout> onLayoutChanged,
                         Runnable onSymbolCopied) {
        super("Símbolos");
        this.onLayoutChanged = onLayoutChanged;
        this.onSymbolCopied = onSymbolCopied;
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
                updateKeyboardPreview(selected);
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
                if (success) {
                    hide();
                    Platform.runLater(onSymbolCopied);
                }
            });
            grid.getChildren().add(tile);
        }

        Label layoutLabel = new Label("Distribución del teclado");
        layoutLabel.getStyleClass().add("palette-caption");
        hint.getStyleClass().add("symbol-hint");
        hint.setWrapText(true);
        hint.setMinHeight(48);
        hint.setMaxWidth(Double.MAX_VALUE);
        keyboardPreview.getStyleClass().add("mini-keyboard");
        keyboardPreview.setTooltip(new Tooltip(
                "Esquema abreviado del teclado físico; los símbolos varían según el sistema"));
        updateKeyboardPreview(initialLayout);
        Label keyboardNote = new Label("Esquema orientativo de las teclas");
        keyboardNote.getStyleClass().add("palette-caption");
        VBox body = new VBox(7, grid, layoutLabel, layoutSelector,
                keyboardPreview, keyboardNote, hint);
        body.getStyleClass().add("symbol-palette");
        body.setAlignment(Pos.TOP_LEFT);
        body.setPadding(new Insets(11));
        CustomMenuItem gallery = new CustomMenuItem(body, false);
        gallery.setHideOnClick(false);
        getItems().add(gallery);
        setOnShowing(event -> hint.setText("Selecciona un símbolo para copiarlo."));
    }

    private void updateKeyboardPreview(Layout layout) {
        // Compact visual reference, not a promise of an exact OS-level keymap.
        String[][] rows = layout == Layout.LATAM
                ? new String[][] {
                    {"1","2","3","4","5","6","7","8","9","0","'","¿"},
                    {"Q","W","E","R","T","Y","U","I","O","P","´","+"},
                    {"A","S","D","F","G","H","J","K","L","Ñ","[","]"},
                    {"Z","X","C","V","B","N","M",",",".","-"}
                } : new String[][] {
                    {"1","2","3","4","5","6","7","8","9","0","-","="},
                    {"Q","W","E","R","T","Y","U","I","O","P","[","]"},
                    {"A","S","D","F","G","H","J","K","L",";","'"},
                    {"Z","X","C","V","B","N","M",",",".","/"}
                };
        keyboardPreview.getChildren().clear();
        for (String[] keys : rows) {
            HBox row = new HBox(2);
            row.getStyleClass().add("mini-keyboard-row");
            for (String key : keys) {
                Label tile = new Label(key);
                tile.getStyleClass().add("mini-keyboard-key");
                tile.setAlignment(Pos.CENTER);
                row.getChildren().add(tile);
            }
            keyboardPreview.getChildren().add(row);
        }
    }

    public void selectLayout(Layout layout) {
        layoutSelector.setValue(layout);
    }
}
