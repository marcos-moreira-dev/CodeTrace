package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/** Canonical folder boundary for supplementary read-only browsing.  * <p><strong>Engineering rationale.</strong> Supplementary consultation roots are outside the primary reconstruction workflow. Path validation should ensure a requested file is actually within the allowed folder and prevent a preview operation from silently escaping the user's selected scope.</p>
*/
public final class ConsultationFolderPolicy {
    private ConsultationFolderPolicy() { }

    public static boolean contains(Path folder, Path file) {
        if (folder == null || file == null) return false;
        try {
            return Files.isDirectory(folder, LinkOption.NOFOLLOW_LINKS)
                    && !Files.isSymbolicLink(file)
                    && file.toRealPath().startsWith(folder.toRealPath());
        } catch (IOException | SecurityException error) {
            return false;
        }
    }
}
