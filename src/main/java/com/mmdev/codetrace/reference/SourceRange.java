package com.mmdev.codetrace.reference;

/**
 * Location in the immutable source, using human-facing 1-based coordinates.
 * The source viewer decides how to map it to its own internal indices.
  * <p><strong>Engineering rationale.</strong> Line and column coordinates crossing a service boundary require a single convention. This value object validates 1-based source locations so the UI can translate to its 0-based internal indexes explicitly instead of scattering off-by-one adjustments.</p>
*/
public record SourceRange(int line, int column, int length) {
    public SourceRange {
        if (line < 1 || column < 1 || length < 1)
            throw new IllegalArgumentException("Source range must be positive");
    }

    public int lineIndex() { return line - 1; }
    public int columnIndex() { return column - 1; }
}
