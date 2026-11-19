package com.mmdev.codetrace.reference;

/**
 * Location in the immutable source, using human-facing 1-based coordinates.
 * The source viewer decides how to map it to its own internal indices.
 */
public record SourceRange(int line, int column, int length) {
    public SourceRange {
        if (line < 1 || column < 1 || length < 1)
            throw new IllegalArgumentException("Source range must be positive");
    }

    public int lineIndex() { return line - 1; }
    public int columnIndex() { return column - 1; }
}
