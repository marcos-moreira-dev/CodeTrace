package com.mmdev.codetrace.ui;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/** Side-effect-free operating-system argument construction for native reveal.  * <p><strong>Engineering rationale.</strong> Opening a real file in the operating system's explorer crosses from JavaFX into a platform-dependent facility. Validate the target and handle unsupported desktop integrations without treating failure as a change to the virtual workspace.</p>
*/
public final class ExplorerReveal {
    private ExplorerReveal() {}

    public static List<String> windowsCommand(Path file) {
        return List.of("explorer.exe", "/select,",
                Objects.requireNonNull(file).toAbsolutePath().normalize().toString());
    }
}
