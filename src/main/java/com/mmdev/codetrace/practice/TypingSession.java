package com.mmdev.codetrace.practice;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Fixed-position transcription onto the reference itself, never a free editor.
 * Each reference grapheme has one input slot. Mistakes remain visible but do
 * not prevent the caret from advancing; untyped slots remain translucent.
 */
public final class TypingSession {
    public enum State { PENDING, CORRECT, INCORRECT, SKIPPED }

    public record Cell(String expected, String entered, State state) { }

    private final List<String> reference;
    private final List<String> entered;
    private final BitSet skipped = new BitSet();
    private int caret;

    public TypingSession(String original) {
        reference = List.copyOf(graphemes(normalizeLineEndings(Objects.requireNonNull(original))));
        entered = new ArrayList<>(Collections.nCopies(reference.size(), null));
        preserveEmojiAnnotations();
        advanceEmoji();
    }

    public int size() {
        return reference.size();
    }

    public int caret() {
        return caret;
    }

    /** Source units visible to the typist; structural newlines are not drawn. */
    public int visibleUnitCount() {
        int total = 0;
        for (String expected : reference)
            if (!"\n".equals(expected) && !EmojiUnits.isEmoji(expected)) total++;
        return total;
    }

    /** Correct visible characters; does not count invisible Enter separators. */
    public int correctVisibleCount() {
        int total = 0;
        for (int i = 0; i < size(); i++)
            if (!"\n".equals(reference.get(i))
                    && !EmojiUnits.isEmoji(reference.get(i)) && state(i) == State.CORRECT) total++;
        return total;
    }

    /** For the per-file bar, deliberate skips are resolved but not correct. */
    public int resolvedVisibleCount() {
        int total = 0;
        for (int i = 0; i < size(); i++) {
            if ("\n".equals(reference.get(i)) || EmojiUnits.isEmoji(reference.get(i))) continue;
            State result = state(i);
            if (result == State.CORRECT || result == State.SKIPPED) total++;
        }
        return total;
    }

    public int correctCount() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (state(i) == State.CORRECT) {
                count++;
            }
        }
        return count;
    }

    public int skippedCount() {
        return skipped.cardinality();
    }

    /**
     * Complete when all VISIBLE source graphemes are correctly transcribed
     * (or deliberately skipped). Newline separators are not rendered as
     * glyphs by TypingEditor; they must never invisibly block completion.
     * Enter can still be practiced and counted when typed correctly.
     */
    public boolean finishedExercise() {
        boolean containsVisibleExercise = false;
        for (int i = 0; i < size(); i++) {
            if ("\n".equals(reference.get(i)) || EmojiUnits.isEmoji(reference.get(i))) continue;
            containsVisibleExercise = true;
            State result = state(i);
            if (result == State.PENDING || result == State.INCORRECT) return false;
        }
        // Empty files and files made entirely of line breaks are not exercises.
        // Deliberate Turbo/import skips still count as resolved for completion,
        // but never inflate correctCount().
        return containsVisibleExercise;
    }

    public int resolvedCount() {
        return correctCount() + skippedCount();
    }

    public int incorrectCount() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (state(i) == State.INCORRECT) {
                count++;
            }
        }
        return count;
    }

    public State state(int position) {
        Objects.checkIndex(position, size());
        if (skipped.get(position)) {
            return State.SKIPPED;
        }
        String actual = entered.get(position);
        if (actual == null) {
            return State.PENDING;
        }
        return equalsNormalized(reference.get(position), actual)
                ? State.CORRECT : State.INCORRECT;
    }

    public Cell cell(int position) {
        return new Cell(reference.get(position), entered.get(position), state(position));
    }

    public void type(String committedText) {
        Objects.requireNonNull(committedText);
        for (String character : graphemes(normalizeLineEndings(committedText))) {
            // A combining mark received after its base must not consume a new slot,
            // including when the base occupies the final reference position.
            int cp = character.codePointAt(0);
            int kind = Character.getType(cp);
            if (caret > 0 && (kind == Character.NON_SPACING_MARK
                    || kind == Character.COMBINING_SPACING_MARK
                    || kind == Character.ENCLOSING_MARK)
                    && entered.get(caret - 1) != null) {
                String previous = entered.get(caret - 1);
                entered.set(caret - 1, previous + character);
                continue;
            }
            advanceEmoji();
            if (caret >= size()) return;
            entered.set(caret, character);
            skipped.clear(caret);
            caret++;
            advanceEmoji();
        }
    }

    public void backspace() {
        while (caret > 0 && EmojiUnits.isEmoji(reference.get(caret - 1))) caret--;
        if (caret > 0) {
            caret--;
            entered.set(caret, null);
            skipped.clear(caret);
        }
    }

    public void deleteAtCaret() {
        advanceEmoji();
        if (caret < size()) {
            entered.set(caret, null);
            skipped.clear(caret);
        }
    }

    public void moveCaret(int position) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException("Caret: " + position);
        }
        caret = position;
        advanceEmoji();
    }

    public void skip(int fromInclusive, int toExclusive) {
        if (fromInclusive < 0 || fromInclusive > toExclusive || toExclusive > size()) {
            throw new IndexOutOfBoundsException("Invalid skip range");
        }
        for (int index = fromInclusive; index < toExclusive; index++) {
            if (entered.get(index) == null) {
                skipped.set(index);
            }
        }
        if (caret >= fromInclusive && caret < toExclusive) {
            caret = toExclusive;
        }
    }

    /**
     * Turbo advances past pending punctuation, never letters, numbers, spaces,
     * tabs or line endings. Omitted slots are preserved in .ctrace and do
     * not contribute to the correctly-typed progress metric.
     */
    public int skipTurboSymbols() {
        int start = caret;
        while (caret < size() && TurboSymbols.isAutomatic(reference.get(caret))
                && (entered.get(caret) == null || skipped.get(caret))) {
            skipped.set(caret);
            caret++;
        }
        return caret - start;
    }

    public void undoSkip(int fromInclusive, int toExclusive) {
        if (fromInclusive < 0 || fromInclusive > toExclusive || toExclusive > size()) {
            throw new IndexOutOfBoundsException("Invalid range");
        }
        skipped.clear(fromInclusive, toExclusive);
        preserveEmojiAnnotations();
        advanceEmoji();
    }

    public String expectedText() {
        return String.join("", reference);
    }

    /** Only user-entered characters are returned; skipped text never becomes typed. */
    public String enteredText() {
        StringBuilder out = new StringBuilder();
        for (String unit : entered) {
            if (unit != null) {
                out.append(unit);
            }
        }
        return out.toString();
    }

    /** Preserve the position of pending slots, unlike enteredText(). */
    public List<String> enteredSlots() {
        return Collections.unmodifiableList(entered);
    }

    public List<Integer> skippedSlots() {
        return skipped.stream().boxed().toList();
    }

    public static TypingSession restore(String original, List<String> slots,
                                        List<Integer> omitted, int position) {
        TypingSession result = new TypingSession(original);
        if (slots.size() != result.size()) {
            throw new IllegalArgumentException("Reference differs from saved session");
        }
        result.entered.clear();
        result.entered.addAll(slots);
        for (int index : omitted) {
            Objects.checkIndex(index, result.size());
            if (result.entered.get(index) != null) {
                throw new IllegalArgumentException("Written and skipped at the same position");
            }
            result.skipped.set(index);
        }
        result.preserveEmojiAnnotations();
        result.moveCaret(position);
        return result;
    }

    private static boolean equalsNormalized(String left, String right) {
        return Normalizer.normalize(left, Normalizer.Form.NFC)
                .equals(Normalizer.normalize(right, Normalizer.Form.NFC));
    }

    private static String normalizeLineEndings(String text) {
        return text.replace("\r\n", "\n").replace("\r", "\n");
    }

    private void preserveEmojiAnnotations() {
        for (int i = 0; i < size(); i++) {
            if (EmojiUnits.isEmoji(reference.get(i))) {
                entered.set(i, null);
                skipped.set(i);
            }
        }
    }

    private void advanceEmoji() {
        while (caret < size() && EmojiUnits.isEmoji(reference.get(caret))) {
            skipped.set(caret++);
        }
    }

    private static List<String> graphemes(String text) {
        return EmojiUnits.graphemes(text);
    }
}
