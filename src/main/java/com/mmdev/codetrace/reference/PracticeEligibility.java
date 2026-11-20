package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.languages.LanguageRegistry;
import java.nio.file.Path;

/** Only supported source languages and Markdown are text transcription targets.  * <p><strong>Engineering rationale.</strong> Whether a file can be practiced is a language/type policy, not an indication that the source compiles. The reference remains consultable even when it does not map to an editable TypingSession, and eligibility should remain stable across different views.</p>
*/
public final class PracticeEligibility {
    private PracticeEligibility() {}

    public static boolean supports(String filename) {
        if (filename == null || filename.isBlank()) return false;
        return switch (LanguageRegistry.forFile(filename).name()) {
            case "Texto", "CSS", "JSON", "XML", "YAML", "Properties" -> false;
            default -> true;
        };
    }

    public static boolean supports(Path file) {
        return file != null && file.getFileName() != null
                && supports(file.getFileName().toString());
    }
}
