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
  * <p><strong>Engineering rationale.</strong> A fixed reference-aligned sequence of Unicode grapheme slots models transcription rather than free-form editing. Distinguishing correctly typed, incorrect, skipped and pending units preserves observable learning progress while allowing the user to continue through errors.</p>
*/
public final class TypingSession {
    public enum State { PENDING, CORRECT, INCORRECT, SKIPPED }

    public record Cell(String expected, String entered, State state) { }

    private final List<String> reference;
    private final List<String> entered;
    private final BitSet skipped = new BitSet();
    private int caret;
    // O(1) counters: all user mutations pass through changeSlot().
    private int visibleUnits;
    private int correctVisible;
    private int resolvedVisible;
    private int correctAll;
    private int incorrectAll;

    public TypingSession(String original) {
        reference = List.copyOf(graphemes(normalizeLineEndings(Objects.requireNonNull(original))));
        entered = new ArrayList<>(Collections.nCopies(reference.size(), null));
        preserveEmojiAnnotations();
        advanceEmoji();
        recount();
    }

    public int size() {
        return reference.size();
    }

    public int caret() {
        return caret;
    }

    /** Source units visible to the typist; structural newlines are not drawn. */
    public int visibleUnitCount() {
        return visibleUnits;
    }

    /** Correct visible characters; does not count invisible Enter separators. */
    public int correctVisibleCount() {
        return correctVisible;
    }

    /** For the per-file bar, deliberate skips are resolved but not correct. */
    public int resolvedVisibleCount() {
        return resolvedVisible;
    }

    public int correctCount() {
        return correctAll;
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
        // Only visible source units determine completion; skipped units may
        // resolve an exercise but never count as correctly transcribed.
        return visibleUnits > 0 && resolvedVisible == visibleUnits;
    }

    public int resolvedCount() {
        return correctCount() + skippedCount();
    }

    public int incorrectCount() {
        return incorrectAll;
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

    /**
     * Commits text into existing reference-aligned grapheme slots.
     *
     * <p>This is not an insert operation: it cannot lengthen the reference.
     * Multiple graphemes from a paste are processed consecutively, emoji
     * annotations are ignored, and a trailing combining mark can attach to
     * the previously committed base without consuming another slot.
     *
     * @param committedText one or more committed Unicode characters
     * @throws NullPointerException when input is null
     */
    public void type(String committedText) {
        Objects.requireNonNull(committedText);
        for (String character : graphemes(normalizeLineEndings(committedText))) {
            // Pasted emojis are annotations, never typing targets.
            if (EmojiUnits.isEmoji(character)) continue;
            // A combining mark received after its base must not consume a new slot,
            // including when the base occupies the final reference position.
            int cp = character.codePointAt(0);
            int kind = Character.getType(cp);
            if (caret > 0 && (kind == Character.NON_SPACING_MARK
                    || kind == Character.COMBINING_SPACING_MARK
                    || kind == Character.ENCLOSING_MARK)
                    && entered.get(caret - 1) != null) {
                String previous = entered.get(caret - 1);
                changeSlot(caret - 1, previous + character, skipped.get(caret - 1));
                continue;
            }
            advanceEmoji();
            if (caret >= size()) return;
            changeSlot(caret, character, false);
            caret++;
            advanceEmoji();
        }
    }

    public void backspace() {
        while (caret > 0 && EmojiUnits.isEmoji(reference.get(caret - 1))) caret--;
        if (caret > 0) {
            caret--;
            changeSlot(caret, null, false);
        }
    }

    public void deleteAtCaret() {
        advanceEmoji();
        if (caret < size()) {
            changeSlot(caret, null, false);
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
                changeSlot(index, null, true);
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
            changeSlot(caret, entered.get(caret), true);
            caret++;
        }
        return caret - start;
    }

    public void undoSkip(int fromInclusive, int toExclusive) {
        if (fromInclusive < 0 || fromInclusive > toExclusive || toExclusive > size()) {
            throw new IndexOutOfBoundsException("Invalid range");
        }
        for (int index = fromInclusive; index < toExclusive; index++) {
            if (skipped.get(index)) changeSlot(index, entered.get(index), false);
        }
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
        result.recount();
        return result;
    }

    private boolean isVisible(int index) {
        String unit = reference.get(index);
        return !"\n".equals(unit) && !EmojiUnits.isEmoji(unit);
    }

    /**
     * Update one source position and its aggregate counters in O(1).
     * Direct mutation of entered/skipped during normal input is forbidden:
     * the counters would then diverge from the state of the exercise.
     */
    private void changeSlot(int position, String value, boolean skip) {
        State before = state(position);
        entered.set(position, value);
        skipped.set(position, skip);
        State after = state(position);
        if (before == after) return;
        if (before == State.CORRECT) correctAll--;
        if (after == State.CORRECT) correctAll++;
        if (before == State.INCORRECT) incorrectAll--;
        if (after == State.INCORRECT) incorrectAll++;
        if (isVisible(position)) {
            if (before == State.CORRECT) correctVisible--;
            if (after == State.CORRECT) correctVisible++;
            if (before == State.CORRECT || before == State.SKIPPED) resolvedVisible--;
            if (after == State.CORRECT || after == State.SKIPPED) resolvedVisible++;
        }
    }

    /** One full pass is permitted after constructing or restoring a session. */
    private void recount() {
        visibleUnits = 0;
        correctVisible = 0;
        resolvedVisible = 0;
        correctAll = 0;
        incorrectAll = 0;
        for (int i = 0; i < size(); i++) {
            State value = state(i);
            if (value == State.CORRECT) correctAll++;
            if (value == State.INCORRECT) incorrectAll++;
            if (!isVisible(i)) continue;
            visibleUnits++;
            if (value == State.CORRECT) correctVisible++;
            if (value == State.CORRECT || value == State.SKIPPED) resolvedVisible++;
        }
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
