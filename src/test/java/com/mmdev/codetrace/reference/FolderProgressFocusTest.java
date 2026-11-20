package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FolderProgressFocusTest {
    @Test
    void outsideOnlyDimsSiblingsButNotAncestorsOrDescendants() {
        String focus = "src/main/java";
        assertFalse(FolderProgressFocus.outside(focus, ""));
        assertFalse(FolderProgressFocus.outside(focus, "src"));
        assertFalse(FolderProgressFocus.outside(focus, "src/main"));
        assertFalse(FolderProgressFocus.outside(focus, focus));
        assertFalse(FolderProgressFocus.outside(focus, "src/main/java/app/App.java"));
        assertTrue(FolderProgressFocus.outside(focus, "src/main/resources"));
        assertTrue(FolderProgressFocus.outside(focus, "src/test"));
        assertTrue(FolderProgressFocus.outside(focus, "README.md"));
    }

    @Test
    void wholeProjectDisablesDimmingAndChecksPathBoundaries() {
        assertFalse(FolderProgressFocus.outside("", "src/test"));
        assertFalse(FolderProgressFocus.outside(null, "src/test"));
        assertFalse(FolderProgressFocus.outside("src", ""));
        assertTrue(FolderProgressFocus.outside("src", "src-old"));
        assertTrue(FolderProgressFocus.outside("src/main", "src/main2"));
        assertFalse(FolderProgressFocus.outside("src/test", "src/test/Main.java"));
    }

    @Test
    void switchingFolderReclassifiesFormerlyFocusedSubtree() {
        String file = "src/main/java/App.java";
        assertFalse(FolderProgressFocus.outside("src/main", file));
        assertTrue(FolderProgressFocus.outside("docs", file));
        assertFalse(FolderProgressFocus.outside("docs", "docs/README.md"));
    }
}
