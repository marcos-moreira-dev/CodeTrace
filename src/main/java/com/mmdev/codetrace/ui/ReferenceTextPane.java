package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.languages.LanguageRegistry;
import com.mmdev.codetrace.languages.LanguageSupport;
import com.mmdev.codetrace.practice.EmojiUnits;
import com.mmdev.codetrace.reference.SourceRange;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Read-only colored reference. Virtualized rows avoid creating one node for
 * every character in large CSS/JSON/XML/config files.
 */
public final class ReferenceTextPane extends BorderPane {
    public record Span(String text, LanguageSupport.Kind kind) {}
    public record SourceLine(int number, String source, List<Span> spans) {}

    private final ListView<SourceLine> lines = new ListView<>();
    private final String original;
    private String fontFamily = "Consolas";
    private int fontSize = 12;
    private int selectedMatchLine = -1;
    private int selectedMatchStart = -1;
    private int selectedMatchEnd = -1;

    public ReferenceTextPane(String source) {
        this(source, "reference.txt");
    }

    public ReferenceTextPane(String source, String filename) {
        original = source == null ? "" : source;
        getStyleClass().add("reference-text-pane");
        lines.getStyleClass().add("reference-colored-lines");
        lines.setItems(FXCollections.observableArrayList(segment(original, filename)));
        lines.setCellFactory(view -> new ListCell<>() {
            @Override protected void updateItem(SourceLine line, boolean empty) {
                super.updateItem(line, empty);
                setText(null);
                if (empty || line == null) {
                    setGraphic(null);
                    return;
                }
                Label number = CodeLineGutter.number(
                        line.number(), lines.getItems().size(), fontSize);
                TextFlow flow = new TextFlow();
                flow.getStyleClass().add("reference-text-flow");
                int offset = 0;
                for (Span span : line.spans()) {
                    int end = offset + span.text().length();
                    if (line.number() == selectedMatchLine
                            && selectedMatchStart < end && selectedMatchEnd > offset) {
                        int first = Math.max(0, selectedMatchStart - offset);
                        int last = Math.min(span.text().length(), selectedMatchEnd - offset);
                        addColoredText(flow, span.text().substring(0, first), span.kind(), false);
                        addColoredText(flow, span.text().substring(first, last), span.kind(), true);
                        addColoredText(flow, span.text().substring(last), span.kind(), false);
                    } else {
                        addColoredText(flow, span.text(), span.kind(), false);
                    }
                    offset = end;
                }
                if (line.spans().isEmpty()) addColoredText(flow, " ", null, false);
                HBox row = new HBox(10, number, flow);
                row.setAlignment(Pos.CENTER_LEFT);
                row.getStyleClass().add("reference-text-row");
                setGraphic(row);
            }
        });
        MenuItem copyLine = new MenuItem("Copiar línea");
        copyLine.setOnAction(event -> {
            SourceLine row = lines.getSelectionModel().getSelectedItem();
            if (row != null) copy(row.source());
        });
        MenuItem copyAll = new MenuItem("Copiar texto completo");
        copyAll.setOnAction(event -> copy(original));
        lines.setContextMenu(new ContextMenu(copyLine, copyAll));
        setCenter(lines);
    }

    /**
     * Uses the same font preference as TypingEditor, but never changes text.
     * Reuses the virtualized ListView rather than reparsing the reference.
     */
    public void setFontSettings(String family, int points) {
        String nextFamily = family == null || family.isBlank() ? "Consolas" : family;
        int nextSize = Math.max(10, Math.min(36, points));
        if (fontFamily.equals(nextFamily) && fontSize == nextSize) return;
        fontFamily = nextFamily;
        fontSize = nextSize;
        lines.refresh();
    }

    public int fontSize() { return fontSize; }
    public String fontFamily() { return fontFamily; }

    private static void copy(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard.getSystemClipboard().setContent(content);
    }

    private void addColoredText(TextFlow flow, String content,
                                       LanguageSupport.Kind kind, boolean selected) {
        if (content.isEmpty()) return;
        // Emit one Text node per ordinary run, not per character; only emoji
        // clusters receive a font fallback. This preserves virtualized speed.
        StringBuilder ordinary = new StringBuilder();
        for (String cluster : EmojiUnits.graphemes(content)) {
            if (EmojiUnits.isEmoji(cluster)) {
                if (!ordinary.isEmpty()) {
                    appendTextRun(flow, ordinary.toString(), kind, selected, false);
                    ordinary.setLength(0);
                }
                appendTextRun(flow, cluster, kind, selected, true);
            } else {
                ordinary.append(cluster);
            }
        }
        if (!ordinary.isEmpty())
            appendTextRun(flow, ordinary.toString(), kind, selected, false);
    }

    private void appendTextRun(TextFlow flow, String content,
                               LanguageSupport.Kind kind,
                               boolean selected, boolean emoji) {
        Text text = new Text(content);
        text.setFont(Font.font(emoji ? "Segoe UI Emoji" : fontFamily, fontSize));
        text.setFill(selected ? Color.web("#BE6423")
                : kind == null ? Color.web("#273947") : SyntaxPalette.color(kind));
        if (selected) {
            text.setUnderline(true);
            text.setStyle("-fx-font-weight: bold;");
        }
        flow.getChildren().add(text);
    }

    /**
     * Pure line segmentation from UTF-16 token offsets. Multi-line comments
     * remain colored as they cross line boundaries.
     */
    public static List<SourceLine> segment(String source, String filename) {
        String text = source == null ? "" : source;
        List<LanguageSupport.Token> tokens = LanguageRegistry.forFile(filename)
                .highlight(text);
        List<SourceLine> rows = new ArrayList<>();
        int cursor = 0;
        int tokenCursor = 0;
        int number = 1;
        while (cursor <= text.length()) {
            int newLine = text.indexOf('\n', cursor);
            int rawEnd = newLine < 0 ? text.length() : newLine;
            int end = rawEnd > cursor && text.charAt(rawEnd - 1) == '\r'
                    ? rawEnd - 1 : rawEnd;
            List<Span> spans = new ArrayList<>();
            int pos = cursor;
            while (tokenCursor < tokens.size() && tokens.get(tokenCursor).end() <= cursor)
                tokenCursor++;
            int candidate = tokenCursor;
            while (candidate < tokens.size() && tokens.get(candidate).start() < end) {
                LanguageSupport.Token token = tokens.get(candidate);
                int start = Math.max(pos, token.start());
                int finish = Math.min(end, token.end());
                if (start > pos) spans.add(new Span(text.substring(pos, start), null));
                if (finish > start)
                    spans.add(new Span(text.substring(start, finish), token.kind()));
                pos = Math.max(pos, finish);
                candidate++;
            }
            if (pos < end) spans.add(new Span(text.substring(pos, end), null));
            rows.add(new SourceLine(number++, text.substring(cursor, end),
                    List.copyOf(spans)));
            if (newLine < 0) break;
            cursor = newLine + 1;
        }
        return List.copyOf(rows);
    }

    /** Search navigation selects a colored line without editing its contents. */
    /** Canonical 1-based navigation, shared with practice view. */
    public void showSearchMatch(SourceRange location) {
        showSearchMatch(location.line(), location.column(), location.length());
    }

    public void showSearchMatch(int line, int column, int length) {
        if (line < 1 || line > lines.getItems().size()) return;
        selectedMatchLine = line;
        selectedMatchStart = Math.max(0, column - 1);
        selectedMatchEnd = selectedMatchStart + Math.max(1, length);
        lines.getSelectionModel().select(line - 1);
        lines.refresh();
        lines.scrollTo(Math.max(0, line - 3));
        lines.requestFocus();
    }
}
