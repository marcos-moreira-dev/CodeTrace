package com.mmdev.codetrace.ui;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/** Side-effect-free operating-system argument construction for native reveal. */
public final class ExplorerReveal {
    private ExplorerReveal() {}

    public static List<String> windowsCommand(Path file) {
        return List.of("explorer.exe", "/select,",
                Objects.requireNonNull(file).toAbsolutePath().normalize().toString());
    }
}
