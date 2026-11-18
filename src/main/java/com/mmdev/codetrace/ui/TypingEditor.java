package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.languages.LanguageRegistry;
import com.mmdev.codetrace.languages.LanguageSupport;
import com.mmdev.codetrace.practice.TypingSession;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * CodeTrace is a tracer, not a text editor: every visible glyph comes from the
 * immutable reference. Typing only changes the color of its existing position.
 * One selected, full-width row defines where the user practices next.
 *
 * ListView virtualizes lines, so refreshing a large document only recreates
 * visible cells instead of allocating a Text node for every character.
 */
public final class TypingEditor extends BorderPane {
    private static final Color PENDING = Color.web("#81909B");
    private static final Color CORRECT = Color.web("#26834B");
    private static final Color INCORRECT = Color.web("#B63E41");
    private static final Color SKIPPED = Color.web("#647689");
    private static final Color CARET = Color.web("#326B9F");

    private final TypingSession session;
    private final Color[] syntaxColors;
    private final List<SourceLine> sourceLines = new ArrayList<>();
    private final ListView<SourceLine> lines = new ListView<>();
    private final Runnable onChange;
    private final Consumer<Boolean> onTypedCharacter;
    private double fontSize;
    private String fontFamily;
    private long firstKeyNanos;
    private boolean synchronizingLine;

    private record SourceLine(int number, int first, int last, String original) {}

    public TypingEditor(TypingSession session, Runnable onChange,
                        String filename, String fontFamily, int fontSize,
                        Consumer<Boolean> onTypedCharacter) {
        this.session = session;
        this.onChange = onChange;
        this.onTypedCharacter = onTypedCharacter;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.syntaxColors = syntaxColors(session, filename);
        buildLines();

        getStyleClass().add("typing-editor");
        lines.getStyleClass().add("typing-lines");
        lines.setItems(FXCollections.observableArrayList(sourceLines));
        lines.setFixedCellSize(cellHeight());
        lines.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(SourceLine line, boolean empty) {
                super.updateItem(line, empty);
                setText(null);
                if (empty || line == null) {
                    setGraphic(null);
                } else {
                    setGraphic(makeVisibleLine(line));
                }
            }
        });

        lines.getSelectionModel().selectedItemProperty().addListener(
                (observed, before, chosen) -> {
                    if (chosen != null && !synchronizingLine) {
                        session.moveCaret(chosen.first());
                        lines.refresh();
                    }
                });
        lines.setOnMouseClicked(event -> {
            SourceLine chosen = lines.getSelectionModel().getSelectedItem();
            if (chosen != null) {
                // Clicking any point in a row activates the whole line.
                session.moveCaret(chosen.first());
                lines.refresh();
            }
            lines.requestFocus();
        });
        addEventFilter(KeyEvent.KEY_PRESSED, this::handlePressed);
        addEventFilter(KeyEvent.KEY_TYPED, this::handleTyped);
        setCenter(lines);
        synchronizeLine(false);
        Platform.runLater(lines::requestFocus);
    }

    private void buildLines() {
        int start = 0;
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < session.size(); index++) {
            String expected = session.cell(index).expected();
            if ("\n".equals(expected)) {
                sourceLines.add(new SourceLine(
                        sourceLines.size(), start, index, text.toString()));
                start = index + 1;
                text.setLength(0);
            } else {
                text.append(expected);
            }
        }
        sourceLines.add(new SourceLine(
                sourceLines.size(), start, session.size(), text.toString()));
    }

    public TypingSession session() {
        return session;
    }

    /** Used for GUI regression tests, and guaranteed independent of typed input. */
    public String visibleLineText(int lineNumber) {
        return sourceLines.get(lineNumber).original();
    }

    public int activeLineNumber() {
        SourceLine chosen = lines.getSelectionModel().getSelectedItem();
        return chosen == null ? 0 : chosen.number();
    }

    public void selectLine(int lineNumber) {
        SourceLine target = sourceLines.get(lineNumber);
        session.moveCaret(target.first());
        synchronizeLine(true);
        lines.requestFocus();
    }

    public void setFontSettings(String family, int size) {
        fontFamily = family;
        fontSize = size;
        lines.setFixedCellSize(cellHeight());
        lines.refresh();
    }

    public double progress() {
        return session.size() == 0 ? 1.0
                : (double) session.resolvedCount() / session.size();
    }

    public long correctCpm() {
        if (firstKeyNanos == 0) return 0;
        double minutes = (System.nanoTime() - firstKeyNanos) / 60_000_000_000.0;
        return minutes < (3.0 / 60) ? 0 : Math.round(session.correctCount() / minutes);
    }

    public int skipJavaImports() {
        int omittedBefore = session.skippedCount();
        int start = 0;
        StringBuilder line = new StringBuilder();
        for (int i = 0; i <= session.size(); i++) {
            boolean atEnd = i == session.size();
            String unit = atEnd ? "" : session.cell(i).expected();
            if (atEnd || "\n".equals(unit)) {
                String trimmed = line.toString().stripLeading();
                if (trimmed.startsWith("import ") || trimmed.startsWith("import\t")) {
                    session.skip(start, Math.min(i + 1, session.size()));
                }
                start = i + 1;
                line.setLength(0);
            } else {
                line.append(unit);
            }
        }
        boolean changed = session.skippedCount() != omittedBefore;
        refresh(changed);
        return session.skippedCount() - omittedBefore;
    }

    public void focusEditor() {
        lines.requestFocus();
    }

    private void handlePressed(KeyEvent event) {
        KeyCode key = event.getCode();
        boolean handled = true;
        boolean modified = true;
        switch (key) {
            case BACK_SPACE -> session.backspace();
            case DELETE -> session.deleteAtCaret();
            case LEFT -> {
                session.moveCaret(Math.max(0, session.caret() - 1));
                modified = false;
            }
            case RIGHT -> {
                session.moveCaret(Math.min(session.size(), session.caret() + 1));
                modified = false;
            }
            case HOME -> {
                session.moveCaret(event.isControlDown()
                        ? 0 : startOfLine(session.caret()));
                modified = false;
            }
            case END -> {
                session.moveCaret(event.isControlDown()
                        ? session.size() : endOfLine(session.caret()));
                modified = false;
            }
            case UP -> {
                session.moveCaret(verticalTarget(-1));
                modified = false;
            }
            case DOWN -> {
                session.moveCaret(verticalTarget(1));
                modified = false;
            }
            case TAB -> {
                beginTyping();
                typeAndNotify("\t");
            }
            default -> handled = false;
        }
        if (handled) {
            event.consume();
            refresh(modified);
        }
    }

    private void handleTyped(KeyEvent event) {
        String input = event.getCharacter();
        if (input == null || input.isEmpty() || event.isMetaDown()
                || (event.isControlDown() && !event.isAltDown())) {
            return;
        }
        if (input.equals("\t") || input.equals("\b") || input.equals("\u007F")) {
            return;
        }
        int cp = input.codePointAt(0);
        if (Character.isISOControl(cp) && !input.equals("\n") && !input.equals("\r")) {
            return;
        }
        beginTyping();
        typeAndNotify(input);
        event.consume();
        refresh(true);
    }

    private void typeAndNotify(String committed) {
        int previous = session.caret();
        session.type(committed);
        if (session.caret() > previous) {
            onTypedCharacter.accept(
                    session.state(session.caret() - 1) == TypingSession.State.CORRECT);
        }
    }

    private void beginTyping() {
        if (firstKeyNanos == 0) firstKeyNanos = System.nanoTime();
    }

    private int startOfLine(int position) {
        int i = position;
        while (i > 0 && !"\n".equals(session.cell(i - 1).expected())) i--;
        return i;
    }

    private int endOfLine(int position) {
        int i = position;
        while (i < session.size() && !"\n".equals(session.cell(i).expected())) i++;
        return i;
    }

    private int verticalTarget(int direction) {
        int position = session.caret();
        int column = position - startOfLine(position);
        if (direction < 0) {
            int priorEnd = startOfLine(position) - 1;
            if (priorEnd < 0) return position;
            return Math.min(startOfLine(priorEnd) + column, priorEnd);
        }
        int next = endOfLine(position) + 1;
        if (next > session.size()) return position;
        return Math.min(next + column, endOfLine(next));
    }

    private void refresh(boolean changed) {
        synchronizeLine(true);
        if (changed) onChange.run();
    }

    private void synchronizeLine(boolean scrollToLine) {
        int position = session.caret();
        int selected = sourceLines.size() - 1;
        for (int i = 0; i < sourceLines.size(); i++) {
            SourceLine line = sourceLines.get(i);
            int max = line.last();
            if (position <= max) {
                selected = i;
                break;
            }
        }
        synchronizingLine = true;
        try {
            lines.getSelectionModel().select(selected);
        } finally {
            synchronizingLine = false;
        }
        lines.refresh();
        if (scrollToLine) lines.scrollTo(selected);
    }

    private double cellHeight() {
        return Math.ceil(fontSize * 1.5 + 7);
    }

    private TextFlow makeVisibleLine(SourceLine line) {
        TextFlow flow = new TextFlow();
        flow.getStyleClass().add("typing-line-flow");
        // Fixed reference glyphs make all colored states occupy the same slots.
        StringBuilder run = new StringBuilder();
        Color color = null;
        boolean underline = false;
        for (int i = line.first(); i < line.last(); i++) {
            TypingSession.Cell cell = session.cell(i);
            boolean atCaret = i == session.caret();
            Color next = switch (cell.state()) {
                case PENDING -> syntaxColors[i];
                case CORRECT -> CORRECT;
                case INCORRECT -> INCORRECT;
                case SKIPPED -> SKIPPED;
            };
            boolean nextUnderline = atCaret || cell.state() == TypingSession.State.INCORRECT;
            if (color != next || underline != nextUnderline) {
                flush(run, flow, color, underline);
                color = next;
                underline = nextUnderline;
            }
            // Never display cell.entered(): even an error leaves the source intact.
            run.append(cell.expected());
        }
        flush(run, flow, color, underline);
        if (line.original().isEmpty() || session.caret() == line.last()) {
            // Caret is placed only at end of line; it never shifts source glyphs.
            Text endCaret = styledText(
                    line.number() == activeLineNumber() ? "│" : " ",
                    CARET, false);
            flow.getChildren().add(endCaret);
        }
        Text measurement = styledText(line.original().replace("\t", "    "), PENDING, false);
        flow.setMinWidth(Math.max(14, measurement.getLayoutBounds().getWidth() + 20));
        flow.setPrefWidth(Math.max(14, measurement.getLayoutBounds().getWidth() + 20));
        return flow;
    }

    private void flush(StringBuilder run, TextFlow flow, Color color, boolean underlined) {
        if (run.isEmpty()) return;
        flow.getChildren().add(styledText(run.toString(), color, underlined));
        run.setLength(0);
    }

    private Text styledText(String value, Color color, boolean underline) {
        Text view = new Text(value);
        view.setFont(Font.font(fontFamily, fontSize));
        view.setFill(color);
        view.setUnderline(underline);
        return view;
    }

    private static Color[] syntaxColors(TypingSession session, String filename) {
        String original = session.expectedText();
        Color[] colors = new Color[session.size()];
        Arrays.fill(colors, PENDING);
        int[] units = new int[original.length() + 1];
        int offset = 0;
        for (int i = 0; i < session.size(); i++) {
            String unit = session.cell(i).expected();
            for (int j = 0; j < unit.length(); j++) units[offset + j] = i;
            offset += unit.length();
        }
        units[original.length()] = session.size();
        LanguageSupport support = LanguageRegistry.forFile(filename);
        for (LanguageSupport.Token token : support.highlight(original)) {
            if (token.start() == token.end()) continue;
            int first = units[token.start()];
            int last = units[token.end() - 1] + 1;
            Color tint = switch (token.kind()) {
                case KEYWORD, HEADING -> Color.web("#315F91");
                case COMMENT -> Color.web("#647F75");
                case STRING -> Color.web("#267A65");
                case NUMBER -> Color.web("#9D732F");
                case ANNOTATION -> Color.web("#7B61A5");
            };
            Arrays.fill(colors, first, last, tint);
        }
        return colors;
    }
}
