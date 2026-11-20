package com.mmdev.codetrace.ui;

import java.nio.file.Path;

/** The explanation dialog is for read-only documents, not multimedia.  * <p><strong>Engineering rationale.</strong> Informational notices should explain why a file cannot be transcribed without interrupting ordinary media use repeatedly. The policy remains independent of the confirmation required before real commands, which has higher safety implications.</p>
*/
public final class ConsultationNoticePolicy {
    private ConsultationNoticePolicy() {}

    public static boolean requiresExplanation(Path file) {
        return ReferenceMediaPane.kindFor(file) == ReferenceMediaPane.Kind.UNSUPPORTED;
    }
}
