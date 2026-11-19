package com.mmdev.codetrace.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Unicode extended grapheme clusters, preserving ZWJ families and flags. */
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
        for (int cp : unit.codePoints().toArray()) {
            if (cp == 0xFE0F || cp == 0x20E3 || cp == 0x200D
                    || (cp >= 0x1F000 && cp <= 0x1FAFF)
                    || (cp >= 0x2600 && cp <= 0x27BF)) return true;
        }
        return false;
    }
}
