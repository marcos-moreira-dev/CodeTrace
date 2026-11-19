package com.mmdev.codetrace.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** One source of truth: version injected by Maven from pom.xml at packaging. */
public final class AppVersion {
    private AppVersion() {}

    public static String value() {
        try (InputStream stream = AppVersion.class.getResourceAsStream(
                "/codetrace-version.properties")) {
            if (stream == null) return "no disponible";
            Properties config = new Properties();
            config.load(stream);
            String result = config.getProperty("version", "").trim();
            if (result.isBlank() || result.contains("${"))
                return "no disponible";
            return result;
        } catch (IOException ex) {
            return "no disponible";
        }
    }

    public static String display() {
        String version = value();
        return version.endsWith("-SNAPSHOT")
                ? "Versión " + version.substring(0, version.length() - 9)
                        + " · desarrollo"
                : "Versión " + version;
    }
}
