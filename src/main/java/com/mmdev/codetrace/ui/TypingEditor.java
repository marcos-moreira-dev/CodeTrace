package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.languages.LanguageRegistry;
import com.mmdev.codetrace.languages.LanguageSupport;
import com.mmdev.codetrace.practice.TypingSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.BooleanSupplier;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private static final Color KEYWORD = Color.web("#164D9F");

    private final TypingSession session;
    private final Color[] syntaxColors;
    private final List<SourceLine> sourceLines = new ArrayList<>();
    private final ListView<SourceLine> lines = new ListView<>();
    private final Runnable onChange;
    private final Consumer<Boolean> onTypedCharacter;
    private double fontSize;
    private double pendingOpacity = 0.70;
    private String fontFamily;
    private long firstKeyNanos;
    private boolean synchronizingLine;
    private boolean turboMode;
    private Consumer<String> onNotice = ignored -> {};
    private BooleanSupplier mayPractice = () -> true;
    private Runnable onPracticeBlocked = () -> {};

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
        // A click on a different line uses the selection listener above.
        // Clicking the CURRENT line must preserve its current character position.
        lines.setOnMouseClicked(event -> lines.requestFocus());
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

    /** Apply only to pending source characters, never to correctly typed tiles. */
    public void setTemplateOpacity(int percent) {
        if (percent < 20 || percent > 100) {
            throw new IllegalArgumentException("Opacity out of range");
        }
        pendingOpacity = percent / 100.0;
        lines.refresh();
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
        if (!permitPractice()) return 0;
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

    public void setTurboMode(boolean enabled) {
        turboMode = enabled;
    }

    public boolean isTurboMode() {
        return turboMode;
    }

    public void setOnNotice(Consumer<String> callback) {
        onNotice = callback == null ? ignored -> {} : callback;
    }

    /**
     * A reference is always readable but cannot be transcribed until its
     * matching file has been explicitly created in the virtual workspace.
     * Navigation remains available while viewing a pending source file.
     */
    public void setPracticePermission(BooleanSupplier allowed, Runnable onBlocked) {
        mayPractice = java.util.Objects.requireNonNull(allowed);
        onPracticeBlocked = java.util.Objects.requireNonNull(onBlocked);
    }

    public boolean isPracticeEnabled() {
        return mayPractice.getAsBoolean();
    }

    private boolean permitPractice() {
        if (mayPractice.getAsBoolean()) return true;
        onPracticeBlocked.run();
        return false;
    }

    private void handlePressed(KeyEvent event) {
        KeyCode key = event.getCode();
        boolean handled = true;
        boolean modified = true;
        switch (key) {
            case BACK_SPACE -> {
                if (permitPractice()) session.backspace();
                else modified = false;
            }
            case DELETE -> {
                if (permitPractice()) session.deleteAtCaret();
                else modified = false;
            }
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
                if (permitPractice()) {
                    beginTyping();
                    typeAndNotify("\t");
                } else {
                    modified = false;
                }
            }
            case V -> {
                if (event.isShortcutDown()) {
                    modified = permitPractice() && pasteSingleSymbol();
                } else {
                    handled = false;
                }
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
        event.consume();
        if (!permitPractice()) return;
        beginTyping();
        typeAndNotify(input);
        refresh(true);
    }

    /**
     * A copied programming symbol can be pasted into the reference position.
     * Pasting a whole source file is deliberately not an alternative to typing.
     */
    private boolean pasteSingleSymbol() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (!clipboard.hasString()) return false;
        String pasted = clipboard.getString();
        if (pasted == null || pasted.isEmpty()) return false;
        if (new TypingSession(pasted).size() != 1) {
            onNotice.accept("Para practicar, pega un solo carácter cada vez.");
            return false;
        }
        int before = session.caret();
        beginTyping();
        typeAndNotify(pasted);
        return session.caret() != before;
    }

    private void typeAndNotify(String committed) {
        // Symbols are bypassed only during an intentional typing action.
        // Navigation and line selection never silently change progress.
        if (turboMode) session.skipTurboSymbols();
        int previous = session.caret();
        session.type(committed);
        int afterTyping = session.caret();
        if (afterTyping > previous) {
            boolean correct = session.state(afterTyping - 1)
                    == TypingSession.State.CORRECT;
            onTypedCharacter.accept(correct);
            if (turboMode) session.skipTurboSymbols();
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

    /**
     * Reference glyphs and their positions never change. The node behind each
     * grapheme changes appearance instead: faint pending, mint correct, pink
     * incorrect. This mirrors the supplied typing reference image.
     *
     * Only the visible ListView rows are built, including for long projects.
     */
    private HBox makeVisibleLine(SourceLine line) {
        HBox row = new HBox(1.0);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("typing-line-glyphs");
        Font font = Font.font(fontFamily, fontSize);

        Text measure = new Text("M");
        measure.setFont(font);
        double columnWidth = Math.max(7.0, measure.getLayoutBounds().getWidth());
        double tileHeight = Math.max(22, Math.ceil(fontSize * 1.45));
        double rowWidth = 0;

        for (int index = line.first(); index < line.last(); index++) {
            TypingSession.Cell cell = session.cell(index);
            String original = cell.expected();
            String visible = original.equals("\t") ? "    " : original;
            Text glyph = new Text(visible);
            glyph.setFont(font);
            glyph.setMouseTransparent(true);
            glyph.setOpacity(cell.state() != TypingSession.State.PENDING ? 1.0
                    : syntaxColors[index].equals(KEYWORD)
                        ? Math.max(0.92, pendingOpacity) : pendingOpacity);
            glyph.setFill(switch (cell.state()) {
                case CORRECT -> CORRECT;
                case INCORRECT -> INCORRECT;
                case SKIPPED -> SKIPPED;
                case PENDING -> syntaxColors[index];
            });

            // Every position gets exactly the same width regardless of its
            // result. Tabs and wider Unicode graphemes preserve their extent.
            double width = original.equals("\t")
                    ? columnWidth * 4 : Math.max(columnWidth,
                            glyph.getLayoutBounds().getWidth());
            StackPane tile = new StackPane(glyph);
            tile.setAlignment(Pos.CENTER);
            tile.setMouseTransparent(true);
            tile.getStyleClass().addAll("trace-character",
                    switch (cell.state()) {
                        case CORRECT -> "trace-correct";
                        case INCORRECT -> "trace-incorrect";
                        case SKIPPED -> "trace-skipped";
                        case PENDING -> "trace-pending";
                    });
            if (index == session.caret()) {
                tile.getStyleClass().add("trace-caret");
            }
            sizeTile(tile, width, tileHeight);
            row.getChildren().add(tile);
            rowWidth += width + 1.0;
        }

        if (session.caret() == line.last()) {
            // A zero-content caret AFTER the last glyph adds no extra source
            // character and never shifts the existing letters.
            StackPane caret = new StackPane();
            caret.setMouseTransparent(true);
            caret.getStyleClass().addAll("trace-character",
                    "trace-pending", "trace-caret", "trace-end-caret");
            sizeTile(caret, columnWidth, tileHeight);
            row.getChildren().add(caret);
            rowWidth += columnWidth + 1.0;
        }

        row.setMinWidth(rowWidth);
        row.setPrefWidth(rowWidth);
        return row;
    }

    private static void sizeTile(StackPane tile, double width, double height) {
        tile.setMinSize(width, height);
        tile.setPrefSize(width, height);
        tile.setMaxSize(width, height);
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
                case KEYWORD, HEADING -> KEYWORD;
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
