package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.practice.ReconstructionStatus.State;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * One semantic status icon per virtual reference item.
 * These are vector glyphs from the existing Ikonli Feather pack,
 * never Unicode characters rendered by a Label.
  * <p><strong>Engineering rationale.</strong> Pending, created and completed have domain meanings that must be communicated consistently across the explorer. Using stable vector shapes and accessible descriptions avoids relying on color alone or squeezing textual badges into narrow rows.</p>
*/
public final class ReconstructionStateIcons {
    private static final String AMBER = "#AA7918";
    private static final String GREEN = "#29834B";
    private static final String GREY = "#91A2AF";

    private ReconstructionStateIcons() {}

    /** Compatibility with existing call sites: created is not completed. */
    public static StackPane forCreated(boolean created) {
        return forState(created ? State.CREATED : State.PENDING);
    }

    public static StackPane forState(State state) {
        String glyph = state == State.PENDING || state == State.NOT_APPLICABLE
                ? "fth-circle" : "fth-check-circle";
        String hex = switch (state) {
            case PENDING, NOT_APPLICABLE -> GREY;
            case CREATED -> AMBER;
            case COMPLETED -> GREEN;
        };
        FontIcon symbol = new FontIcon(glyph);
        symbol.setIconSize(15);
        symbol.setIconColor(Color.web(hex));
        symbol.setMouseTransparent(true);
        StackPane badge = new StackPane(symbol);
        badge.setAlignment(Pos.CENTER);
        badge.setMinSize(18, 18);
        badge.setPrefSize(18, 18);
        badge.setMaxSize(18, 18);
        badge.getStyleClass().addAll("reconstruction-state-icon",
                switch (state) {
                    case NOT_APPLICABLE -> "state-not-applicable";
                    case PENDING -> "state-pending";
                    case CREATED -> "state-created";
                    case COMPLETED -> "state-completed";
                });
        String meaning = switch (state) {
            case NOT_APPLICABLE -> "No requiere práctica";
            case PENDING -> "Pendiente de recrear";
            case CREATED -> "Creado · mecanografía pendiente o incompleta";
            case COMPLETED -> "Transcripción completa · todos los caracteres correctos";
        };
        badge.setAccessibleText(meaning);
        Tooltip.install(badge, new Tooltip(meaning));
        return badge;
    }

}
