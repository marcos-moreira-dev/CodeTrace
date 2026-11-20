package com.mmdev.codetrace.practice;

/** Symbols that CodeTrace can silently bypass in optional Turbo mode.  * <p><strong>Engineering rationale.</strong> Skipping punctuation is an intentional accessibility and speed aid, not an edit to the original source. The decision belongs in a deterministic practice rule so keyboard layout and rendering do not silently change the units recorded as skipped.</p>
*/
public final class TurboSymbols {
    // Explicit ASCII punctuation only: whitespace, digits and accented
    // characters always stay part of the exercise.
    private static final String AUTO = "{}[]()<>@#|/;:,.!?'+-_*=^%&$";

    private TurboSymbols() {}

    public static boolean isAutomatic(String grapheme) {
        if (grapheme == null || grapheme.length() != 1) return false;
        char value = grapheme.charAt(0);
        return AUTO.indexOf(value) >= 0 || value == 92 || value == 34
                || value == 96 || value == 126;
    }
}
