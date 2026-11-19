package com.mmdev.codetrace.reference;

import java.util.Arrays;

/**
 * Index of immutable, inclusive line-end offsets in a reference document.
 * Finds the first source row ending at or after the caret in O(log lines).
 * Newline positions belong to the preceding row; the next grapheme belongs
 * to the following row. Empty source documents still contain one row.
 */
public final class SourceLineIndex {
    private final int[] ends;

    public SourceLineIndex(int[] inclusiveEnds) {
        if (inclusiveEnds == null || inclusiveEnds.length == 0)
            throw new IllegalArgumentException("At least one source line is required");
        ends = Arrays.copyOf(inclusiveEnds, inclusiveEnds.length);
        int previous = -1;
        for (int end : ends) {
            if (end < 0 || end < previous)
                throw new IllegalArgumentException("Unsorted line bounds");
            previous = end;
        }
    }

    /** Zero-based line index. Positions past EOF select the last line. */
    public int findLine(int caretPosition) {
        if (caretPosition < 0)
            throw new IllegalArgumentException("Negative source position");
        int low = 0;
        int high = ends.length;
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (ends[middle] < caretPosition) low = middle + 1;
            else high = middle;
        }
        return Math.min(low, ends.length - 1);
    }

    public int lineCount() {
        return ends.length;
    }
}
