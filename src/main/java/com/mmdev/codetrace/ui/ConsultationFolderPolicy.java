package com.mmdev.codetrace.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/** Canonical folder boundary for supplementary read-only browsing. */
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
