package com.mmdev.codetrace.reference;

import com.mmdev.codetrace.workspace.VirtualChildrenIndex;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.util.Objects;

/**
 * Domain eligibility for the explorer's contextual actions. JavaFX owns the
 * actual menu controls; this policy only answers what the reference and
 * virtual workspace permit, without modifying either.
  * <p><strong>Engineering rationale.</strong> The available actions depend on both reference existence and virtual creation state. Compute permissions as a pure policy before constructing JavaFX MenuItems so the same operations can be tested without opening a window and no impossible action is advertised.</p>
*/
public final class ExplorerMenuPolicy {
    private ExplorerMenuPolicy() { }

    public record Actions(
            boolean recursiveExpansion, boolean createThisFolder,
            boolean createSubfolder, boolean createFile,
            boolean createThisFile, boolean progressScope) { }

    public static Actions forPath(String relative, boolean folder,
                                  ReferenceTreeIndex index,
                                  VirtualChildrenIndex virtualChildren,
                                  VirtualWorkspace workspace) {
        Objects.requireNonNull(relative);
        Objects.requireNonNull(virtualChildren);
        Objects.requireNonNull(workspace);
        boolean created = workspace.exists(relative);
        if (!folder) {
            return new Actions(false, false, false, false,
                    !created && index != null && index.isWorkable(relative),
                    false);
        }
        if (index == null) {
            return new Actions(false, false, false, false, false, false);
        }

        var children = index.children(relative);
        boolean hasNestedFolders = virtualChildren.hasDirectoryChild(relative)
                || children.stream().anyMatch(index::hasDirectory);
        boolean hasWorkable = index.hasWorkableDescendant(relative);
        boolean hasPendingFolders = children.stream().anyMatch(child ->
                index.hasDirectory(child) && index.hasWorkableDescendant(child)
                        && !workspace.exists(child));
        boolean hasPendingFiles = children.stream().anyMatch(child ->
                index.isWorkable(child) && !workspace.exists(child));

        return new Actions(hasNestedFolders,
                !created && !relative.isEmpty() && hasWorkable,
                hasPendingFolders, hasPendingFiles,
                false, hasWorkable);
    }
}
