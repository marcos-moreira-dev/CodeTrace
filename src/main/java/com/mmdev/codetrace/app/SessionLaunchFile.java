package com.mmdev.codetrace.app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Selects the single session argument supplied by Windows file association. */
public final class SessionLaunchFile {
    private SessionLaunchFile() {}

    public static Optional<Path> fromArguments(List<String> arguments) {
        if (arguments == null || arguments.size() != 1) return Optional.empty();
        try {
            Path path = Path.of(arguments.getFirst()).toAbsolutePath().normalize();
            String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
            return name.endsWith(".ctrace") && Files.isRegularFile(path)
                    ? Optional.of(path) : Optional.empty();
        } catch (IllegalArgumentException | SecurityException invalid) {
            return Optional.empty();
        }
    }
}
