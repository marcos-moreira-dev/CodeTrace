package com.mmdev.codetrace.reference;

import java.util.Locale;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * Select which text categories contribute to project-wide progress.
 * Visibility and the ability to practice files are not affected.
 */
public record ProgressScope(boolean source, boolean configuration,
                            boolean documentation, boolean otherText) {
    public enum Category { SOURCE, CONFIGURATION, DOCUMENTATION, OTHER_TEXT }

    private static final Preferences STORE =
            Preferences.userNodeForPackage(ProgressScope.class);

    private static final Set<String> CODE_EXTENSIONS = Set.of(
            "java", "kt", "scala", "groovy", "py", "pyw", "js", "jsx",
            "ts", "tsx", "c", "h", "cc", "cpp", "cxx", "hpp", "hh",
            "cs", "csx", "go", "rs", "rb", "rake", "php", "swift", "dart", "sql",
            "asm", "nasm",
            "html", "htm", "css", "scss", "sass", "sh", "bash", "zsh",
            "ps1", "psm1", "psd1", "bat", "cmd");
    private static final Set<String> CONFIG_EXTENSIONS = Set.of(
            "xml", "json", "jsonc", "yaml", "yml", "toml",
            "properties", "ini", "conf", "gradle", "env", "lock");
    private static final Set<String> DOC_EXTENSIONS = Set.of(
            "md", "markdown", "rst", "adoc", "txt", "tex");

    public static ProgressScope defaults() {
        return new ProgressScope(true, false, false, false);
    }

    public static ProgressScope load() {
        return new ProgressScope(
                STORE.getBoolean("include.source", true),
                STORE.getBoolean("include.configuration", false),
                STORE.getBoolean("include.documentation", false),
                STORE.getBoolean("include.other", false));
    }

    public void save() {
        STORE.putBoolean("include.source", source);
        STORE.putBoolean("include.configuration", configuration);
        STORE.putBoolean("include.documentation", documentation);
        STORE.putBoolean("include.other", otherText);
    }

    public boolean includes(Category category) {
        return switch (category) {
            case SOURCE -> source;
            case CONFIGURATION -> configuration;
            case DOCUMENTATION -> documentation;
            case OTHER_TEXT -> otherText;
        };
    }

    public ProgressScope with(Category category, boolean included) {
        return switch (category) {
            case SOURCE -> new ProgressScope(included, configuration, documentation, otherText);
            case CONFIGURATION -> new ProgressScope(source, included, documentation, otherText);
            case DOCUMENTATION -> new ProgressScope(source, configuration, included, otherText);
            case OTHER_TEXT -> new ProgressScope(source, configuration, documentation, included);
        };
    }

    public static Category classify(String relativePath) {
        String name = relativePath.substring(relativePath.lastIndexOf('/') + 1)
                .toLowerCase(Locale.ROOT);
        if (name.equals("dockerfile") || name.equals("makefile")
                || name.equals("cmakelists.txt") || name.equals("pom.xml")
                || name.equals("build.gradle") || name.equals("settings.gradle")
                || name.equals("package.json") || name.equals("tsconfig.json")
                || name.equals("pyproject.toml") || name.equals("requirements.txt")
                || name.startsWith(".env") || name.equals(".gitignore")
                || name.equals(".editorconfig")) {
            return Category.CONFIGURATION;
        }
        int dot = name.lastIndexOf('.');
        String ext = dot >= 0 ? name.substring(dot + 1) : "";
        if (CODE_EXTENSIONS.contains(ext)) return Category.SOURCE;
        if (CONFIG_EXTENSIONS.contains(ext)) return Category.CONFIGURATION;
        if (DOC_EXTENSIONS.contains(ext)) return Category.DOCUMENTATION;
        return Category.OTHER_TEXT;
    }
}
