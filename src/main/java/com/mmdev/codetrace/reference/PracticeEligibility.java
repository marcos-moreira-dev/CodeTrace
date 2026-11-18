package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.languages.LanguageRegistry;
import java.nio.file.Path;

/** Only supported source languages and Markdown are text transcription targets. */
public final class PracticeEligibility {
    private PracticeEligibility() {}

    public static boolean supports(String filename) {
        return filename != null && !filename.isBlank()
                && !LanguageRegistry.forFile(filename).name().equals("Texto");
    }

    public static boolean supports(Path file) {
        return file != null && file.getFileName() != null
                && supports(file.getFileName().toString());
    }
}
