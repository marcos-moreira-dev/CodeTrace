package com.mmdev.codetrace.ui;

import java.nio.file.Path;

/** The explanation dialog is for read-only documents, not multimedia. */
public final class ConsultationNoticePolicy {
    private ConsultationNoticePolicy() {}

    public static boolean requiresExplanation(Path file) {
        return ReferenceMediaPane.kindFor(file) == ReferenceMediaPane.Kind.UNSUPPORTED;
    }
}
