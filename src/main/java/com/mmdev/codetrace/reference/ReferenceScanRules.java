package com.mmdev.codetrace.reference;

import java.util.Set;

/**
 * One policy shared by progress indexing, virtual recreation, source search
 * and dependency analysis. Generated output remains visible in the read-only
 * explorer, but is not counted as code to practise or search by default.
  * <p><strong>Engineering rationale.</strong> Centralizing generated-directory exclusions prevents search, progress, graphing and automatic recreation from disagreeing about which paths have educational value. This policy does not hide generated files from the ordinary read-only explorer, where the user may still inspect them.</p>
*/
public final class ReferenceScanRules {
    private static final Set<String> GENERATED = Set.of(
            ".git", ".idea", ".local", "target", "node_modules", "build",
            "dist", "reports", "__pycache__", ".venv");

    private ReferenceScanRules() { }

    public static boolean generatedName(String name) {
        return name != null && GENERATED.contains(name);
    }

    public static boolean generatedPath(String relative) {
        if (relative == null || relative.isEmpty()) return false;
        for (String part : relative.split("/")) {
            if (generatedName(part)) return true;
        }
        return false;
    }
}
