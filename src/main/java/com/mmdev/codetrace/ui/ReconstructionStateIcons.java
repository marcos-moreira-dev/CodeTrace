package com.mmdev.codetrace.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * One semantic status icon per virtual reference item.
 * These are vector glyphs from the existing Ikonli Feather pack,
 * never Unicode characters rendered by a Label.
 */
public final class ReconstructionStateIcons {
    private static final String CREATED = "#29834B";
    private static final String PENDING = "#91A2AF";

    private ReconstructionStateIcons() {}

    public static StackPane forCreated(boolean created) {
        FontIcon symbol = new FontIcon(
                created ? "fth-check-circle" : "fth-circle");
        symbol.setIconSize(15);
        symbol.setIconColor(Color.web(created ? CREATED : PENDING));
        symbol.setMouseTransparent(true);

        StackPane badge = new StackPane(symbol);
        badge.setAlignment(Pos.CENTER);
        badge.setMinSize(18, 18);
        badge.setPrefSize(18, 18);
        badge.setMaxSize(18, 18);
        badge.getStyleClass().addAll("reconstruction-state-icon",
                created ? "state-created" : "state-pending");
        String meaning = created ? "Creado en el proyecto virtual"
                : "Pendiente de recrear";
        badge.setAccessibleText(meaning);
        Tooltip.install(badge, new Tooltip(meaning));
        return badge;
    }
}
