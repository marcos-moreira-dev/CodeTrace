package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.practice.TypingSession;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;

/**
 * A single visual surface: typed graphemes replace their ghost counterparts.
 * No second entry field or automatic code completion is involved.
 */
public final class TypingEditor extends BorderPane {
    private static final Color PENDING = Color.web("#81909B");
    private static final Color CORRECT = Color.web("#26834B");
    private static final Color INCORRECT = Color.web("#B63E41");
    private static final Color SKIPPED = Color.web("#596D7D");
    private static final Color CARET = Color.web("#245E94");

    private final TypingSession session;
    private final TextFlow surface = new TextFlow();
    private final ScrollPane scroll = new ScrollPane(surface);
    private final Runnable onChange;
    private long firstKeyNanos;
    private double fontSize = 16;

    public TypingEditor(TypingSession session, Runnable onChange) {
        this.session = session;
        this.onChange = onChange;
        getStyleClass().add("typing-editor");
        surface.getStyleClass().add("typing-surface");
        surface.setFocusTraversable(true);
        surface.setPadding(new Insets(18, 24, 30, 24));
        surface.setOnMousePressed(event -> surface.requestFocus());
        scroll.getStyleClass().add("typing-scroll");
        scroll.setFitToWidth(false);
        scroll.setPannable(false);
        setCenter(scroll);
        addEventFilter(KeyEvent.KEY_PRESSED, this::handlePressed);
        addEventFilter(KeyEvent.KEY_TYPED, this::handleTyped);
        render();
        Platform.runLater(surface::requestFocus);
    }

    public TypingSession session() {
        return session;
    }

    public double progress() {
        return session.size() == 0 ? 1.0
                : (double) session.resolvedCount() / session.size();
    }

    public long correctCpm() {
        if (firstKeyNanos == 0) {
            return 0;
        }
        double minutes = (System.nanoTime() - firstKeyNanos) / 60_000_000_000.0;
        return minutes < (3.0 / 60) ? 0 : Math.round(session.correctCount() / minutes);
    }

    /** Skip only pending import lines; never count them as typed characters. */
    public int skipJavaImports() {
        int omittedBefore = session.skippedCount();
        int start = 0;
        StringBuilder line = new StringBuilder();
        for (int index = 0; index <= session.size(); index++) {
            boolean end = index == session.size();
            String unit = end ? "" : session.cell(index).expected();
            if (end || unit.equals("\n")) {
                String trimmed = line.toString().stripLeading();
                if (trimmed.startsWith("import ") || trimmed.startsWith("import\t")) {
                    session.skip(start, Math.min(index + 1, session.size()));
                }
                line.setLength(0);
                start = index + 1;
            } else {
                line.append(unit);
            }
        }
        update();
        return session.skippedCount() - omittedBefore;
    }

    public void focusEditor() {
        surface.requestFocus();
    }

    private void handlePressed(KeyEvent event) {
        KeyCode key = event.getCode();
        boolean handled = true;
        switch (key) {
            case BACK_SPACE -> session.backspace();
            case DELETE -> session.deleteAtCaret();
            case LEFT -> session.moveCaret(Math.max(0, session.caret() - 1));
            case RIGHT -> session.moveCaret(Math.min(session.size(), session.caret() + 1));
            case HOME -> session.moveCaret(event.isControlDown()
                    ? 0 : startOfLine(session.caret()));
            case END -> session.moveCaret(event.isControlDown()
                    ? session.size() : endOfLine(session.caret()));
            case UP -> session.moveCaret(verticalTarget(-1));
            case DOWN -> session.moveCaret(verticalTarget(1));
            case TAB -> {
                beginTyping();
                session.type("\t");
            }
            default -> handled = false;
        }
        if (handled) {
            event.consume();
            update();
        }
    }

    private void handleTyped(KeyEvent event) {
        String input = event.getCharacter();
        if (input == null || input.isEmpty()
                || (event.isControlDown() && !event.isAltDown())
                || event.isMetaDown()) {
            return;
        }
        if (input.equals("\t") || input.equals("\b") || input.equals("\u007F")) {
            return; // Tab and deletion have dedicated pressed-key handlers.
        }
        int codePoint = input.codePointAt(0);
        if (Character.isISOControl(codePoint) && !input.equals("\r")
                && !input.equals("\n")) {
            return;
        }
        beginTyping();
        session.type(input);
        event.consume();
        update();
    }

    private void beginTyping() {
        if (firstKeyNanos == 0) {
            firstKeyNanos = System.nanoTime();
        }
    }

    private int startOfLine(int current) {
        int result = current;
        while (result > 0 && !session.cell(result - 1).expected().equals("\n")) {
            result--;
        }
        return result;
    }

    private int endOfLine(int current) {
        int result = current;
        while (result < session.size() && !session.cell(result).expected().equals("\n")) {
            result++;
        }
        return result;
    }

    private int verticalTarget(int direction) {
        int current = session.caret();
        int column = current - startOfLine(current);
        if (direction < 0) {
            int previousEnd = startOfLine(current) - 1;
            if (previousEnd < 0) {
                return current;
            }
            int previousStart = startOfLine(previousEnd);
            return Math.min(previousStart + column, previousEnd);
        }
        int nextStart = endOfLine(current) + 1;
        if (nextStart > session.size()) {
            return current;
        }
        return Math.min(nextStart + column, endOfLine(nextStart));
    }

    private void update() {
        render();
        onChange.run();
    }

    private void render() {
        List<Text> output = new ArrayList<>();
        StringBuilder segment = new StringBuilder();
        TypingSession.State style = null;
        int segmentStart = 0;
        for (int index = 0; index <= session.size(); index++) {
            if (index == session.caret()) {
                flush(output, segment, style, segmentStart);
                style = null;
                Text cursor = styledText("│", CARET);
                cursor.setMouseTransparent(true);
                output.add(cursor);
            }
            if (index == session.size()) {
                break;
            }
            TypingSession.Cell cell = session.cell(index);
            if (style != cell.state()) {
                flush(output, segment, style, segmentStart);
                style = cell.state();
                segmentStart = index;
            }
            // Keep reference line breaks even when a wrong character is typed there.
            String shown = cell.expected().equals("\n") ? "\n"
                    : cell.entered() == null ? cell.expected() : cell.entered();
            segment.append(shown);
        }
        flush(output, segment, style, segmentStart);
        surface.getChildren().setAll(output);
    }

    private void flush(List<Text> output, StringBuilder chars,
                       TypingSession.State state, int start) {
        if (chars.isEmpty()) {
            return;
        }
        Color color = switch (state) {
            case CORRECT -> CORRECT;
            case INCORRECT -> INCORRECT;
            case SKIPPED -> SKIPPED;
            case PENDING -> PENDING;
        };
        String displayed = chars.toString();
        Text view = styledText(displayed, color);
        view.setOnMousePressed(event -> {
            int charIndex = view.hitTest(new Point2D(event.getX(), event.getY()))
                    .getCharIndex();
            int target = start + clusterCount(displayed.substring(
                    0, Math.max(0, Math.min(charIndex, displayed.length()))));
            session.moveCaret(Math.max(0, Math.min(session.size(), target)));
            update();
            surface.requestFocus();
            event.consume();
        });
        output.add(view);
        chars.setLength(0);
    }

    private Text styledText(String value, Color color) {
        Text text = new Text(value);
        text.setFill(color);
        text.setFont(Font.font("Consolas", fontSize));
        return text;
    }

    private static int clusterCount(String text) {
        BreakIterator iterator = BreakIterator.getCharacterInstance(Locale.ROOT);
        iterator.setText(text);
        int result = 0;
        for (int boundary = iterator.first(); iterator.next() != BreakIterator.DONE;
             boundary = iterator.current()) {
            result++;
        }
        return result;
    }
}
