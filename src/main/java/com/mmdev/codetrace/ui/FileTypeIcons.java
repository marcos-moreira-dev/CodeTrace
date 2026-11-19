package com.mmdev.codetrace.ui;

import java.nio.file.Path;
import java.util.Locale;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Icons are a shared UI service: language logos come from the Devicons pack,
 * desktop folders and documents from Feather. Unknown types retain a neutral glyph.
 */
public final class FileTypeIcons {
    private FileTypeIcons() {}

    public static Node forPath(Path path, boolean folder) {
        if (folder) {
            return font("fth-folder", "#B68C38");
        }
        String name = path.getFileName() == null ? "" :
                path.getFileName().toString().toLowerCase(Locale.ROOT);
        if (name.endsWith(".ctrace")) return sessionIcon();
        if (name.endsWith(".java")) return font("di-java", "#C75B38");
        if (name.endsWith(".kt") || name.endsWith(".kts"))
            return badge("Kt", "#8055B5");
        if (name.endsWith(".py") || name.endsWith(".pyw"))
            return font("di-python", "#3678A4");
        if (name.endsWith(".js") || name.endsWith(".jsx"))
            return font("di-javascript", "#C3A22B");
        if (name.endsWith(".ts") || name.endsWith(".tsx"))
            return badge("TS", "#3178C6");
        if (name.endsWith(".c") || name.endsWith(".h")) return badge("C", "#647DA8");
        if (name.endsWith(".cpp") || name.endsWith(".cc")
                || name.endsWith(".hpp") || name.endsWith(".hh"))
            return badge("C+", "#4774A6");
        if (name.endsWith(".md") || name.endsWith(".markdown"))
            return font("di-markdown", "#4A6076");
        if (name.endsWith(".html") || name.endsWith(".htm"))
            return font("di-html5", "#DD6844");
        if (name.endsWith(".css") || name.endsWith(".scss"))
            return font("di-css3", "#3577AF");
        if (name.endsWith(".php")) return font("di-php", "#6F6CA4");
        if (name.endsWith(".rb")) return font("di-ruby", "#A94D53");
        if (name.endsWith(".go")) return font("di-go", "#448DA1");
        if (name.endsWith(".cs") || name.endsWith(".csx"))
            return badge("C#", "#72559A");
        if (name.endsWith(".ps1") || name.endsWith(".psm1")
                || name.endsWith(".psd1")) return badge("PS", "#366EAD");
        if (name.endsWith(".sh") || name.endsWith(".bash")
                || name.endsWith(".zsh")) return font("fth-terminal", "#536A77");
        if (name.endsWith(".bat") || name.endsWith(".cmd"))
            return font("fth-terminal", "#517A66");
        if (name.endsWith(".asm") || name.endsWith(".nasm"))
            return badge("ASM", "#6A5B90");
        if (name.endsWith(".rs")) return font("di-rust", "#5D6266");
        if (name.endsWith(".svg") || name.endsWith(".bmp")
                || name.endsWith(".ico") || name.endsWith(".tiff")
                || name.endsWith(".tif"))
            return font("fth-image", "#6A8B73");
        if (name.endsWith(".mp3") || name.endsWith(".wav")
                || name.endsWith(".aac") || name.endsWith(".m4a")
                || name.endsWith(".flac") || name.endsWith(".ogg"))
            return font("fth-music", "#6686A0");
        if (name.endsWith(".png") || name.endsWith(".jpg")
                || name.endsWith(".jpeg") || name.endsWith(".gif")
                || name.endsWith(".webp")) return font("fth-image", "#6A8B73");
        if (name.endsWith(".mp4") || name.endsWith(".m4v")
                || name.endsWith(".webm") || name.endsWith(".avi")
                || name.endsWith(".mkv") || name.endsWith(".mov"))
            return font("fth-film", "#74849A");
        if (name.equals("dockerfile")) return font("di-docker", "#3C91B7");
        if (name.equals(".gitignore")) return font("di-git", "#C66A45");
        if (name.equals("pom.xml") || name.endsWith(".xml")
                || name.endsWith(".json") || name.endsWith(".yaml")
                || name.endsWith(".yml") || name.endsWith(".properties"))
            return font("fth-settings", "#66798B");
        return font("fth-file-text", "#7A8B98");
    }

    private static Node sessionIcon() {
        java.io.InputStream input = FileTypeIcons.class
                .getResourceAsStream("/icons/codetrace-session.png");
        if (input != null) {
            try (input) {
                javafx.scene.image.Image image = new javafx.scene.image.Image(input);
                javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(image);
                view.setFitWidth(18);
                view.setFitHeight(18);
                view.setPreserveRatio(true);
                view.setSmooth(true);
                return view;
            } catch (java.io.IOException error) {
                // Fonts and generic symbols remain a graceful fallback.
            }
        }
        return font("fth-save", "#42657d");
    }

    private static Node font(String literal, String color) {
        FontIcon icon = new FontIcon(literal);
        icon.setIconColor(Color.web(color));
        icon.setIconSize(17);
        return icon;
    }

    private static Node badge(String text, String color) {
        Label label = new Label(text);
        label.getStyleClass().add("source-file-badge");
        label.setTextFill(Color.web(color));
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setMaxWidth(Region.USE_PREF_SIZE);
        return label;
    }
}
