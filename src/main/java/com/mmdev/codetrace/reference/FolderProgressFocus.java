package com.mmdev.codetrace.reference;

/**
 * Pure visual classification for the active "Solo carpeta" progress filter.
 * It never changes creation state, exclusions, navigation or typing permission.
  * <p><strong>Engineering rationale.</strong> The Solo selection changes how progress is calculated and visually emphasized; it must not forbid browsing outside the chosen branch. Segment-aware relative-path checks let child folders inherit a focus without accidentally matching similarly named siblings.</p>
*/
public final class FolderProgressFocus {
    private FolderProgressFocus() {}

    public static boolean outside(String activeFolder, String path) {
        if (activeFolder == null || activeFolder.isEmpty()
                || path == null || path.isEmpty()) return false;
        // Keep ancestors visible so users can reach and select another folder.
        return !path.equals(activeFolder)
                && !path.startsWith(activeFolder + "/")
                && !activeFolder.startsWith(path + "/");
    }
}
