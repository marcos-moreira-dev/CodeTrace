package com.mmdev.codetrace.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Unicode extended grapheme clusters, preserving ZWJ families and flags.  * <p><strong>Engineering rationale.</strong> User-perceived text does not always correspond to one UTF-16 char: combining marks and emoji sequences can span several code units. Centralizing grapheme segmentation prevents the typing engine, metrics and display from applying inconsistent character boundaries.</p>
*/
public final class EmojiUnits {
    private static final Pattern GRAPHEMES = Pattern.compile("\\X");
    private EmojiUnits() {}

    public static List<String> graphemes(String source) {
        Matcher matches = GRAPHEMES.matcher(source);
        List<String> result = new ArrayList<>();
        while (matches.find()) result.add(matches.group());
        return result;
    }

    public static boolean isEmoji(String unit) {
        if (unit == null || unit.isEmpty()) return false;
        for (int i = 0; i < unit.length();) {
            int cp = unit.codePointAt(i);
            if (cp == 0xFE0F || cp == 0x20E3 || cp == 0x200D
                    || (cp >= 0x1F000 && cp <= 0x1FAFF)
                    || (cp >= 0x2600 && cp <= 0x27BF)) return true;
            i += Character.charCount(cp);
        }
        return false;
    }
}
