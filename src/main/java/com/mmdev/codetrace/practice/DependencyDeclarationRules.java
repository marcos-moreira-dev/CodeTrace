package com.mmdev.codetrace.practice;

import java.util.Locale;
import java.util.Set;

/** Conservative dependency declarations by language, never arbitrary calls.  * <p><strong>Engineering rationale.</strong> Automatically omitting import or dependency declarations is an explicit training aid, not a change to the source file. Central rules make the feature predictable across language adapters and keep the underlying expected text available for inspection.</p>
*/
public final class DependencyDeclarationRules {
    private static final Set<String> SUPPORTED = Set.of(
            "java", "kt", "kts", "py", "pyw", "js", "jsx", "ts", "tsx",
            "c", "h", "cc", "cpp", "hpp", "cs", "csx",
            "go", "rb", "rake", "sh", "bash", "zsh", "ps1", "psm1", "asm", "nasm");
    private DependencyDeclarationRules() {}

    public static boolean supports(String file) { return SUPPORTED.contains(extension(file)); }

    private static String extension(String file) {
        if (file == null) return "";
        String lower = file.toLowerCase(Locale.ROOT);
        int dot = lower.lastIndexOf('.');
        return dot < 0 ? "" : lower.substring(dot + 1);
    }

    public static boolean startsGoImportBlock(String filename, String line) {
        return extension(filename).equals("go")
                && line.stripLeading().matches("import\\s*\\(\\s*(?://.*)?");
    }

    public static boolean isDeclaration(String file, String line) {
        String text = line.stripLeading();
        return switch (extension(file)) {
            case "java", "kt", "kts", "ts", "tsx", "js", "jsx" ->
                    text.matches("(?:import\\b.*|export\\s+.*\\s+from\\b.*)");
            case "py", "pyw" -> text.matches("(?:import\\b.*|from\\s+\\S+\\s+import\\b.*)");
            case "c", "h", "cc", "cpp", "hpp" ->
                    text.matches("#\\s*(?:include|import)\\b.*");
            case "cs", "csx" ->
                    text.matches("(?:global\\s+)?using\\s+(?!\\().*;\\s*")
                    || text.matches("extern\\s+alias\\s+\\w+\\s*;\\s*");
            case "go" -> text.matches("import\\b.*");
            case "rb", "rake" -> text.matches("(?:require|require_relative)\\b.*");
            case "sh", "bash", "zsh" ->
                    text.matches("(?:source\\s+.*|\\.\\s+\\S+.*)");
            case "ps1", "psm1" -> text.matches("(?i)Import-Module\\b.*");
            case "asm", "nasm" -> text.matches("(?i)%include\\b.*");
            default -> false;
        };
    }
}
