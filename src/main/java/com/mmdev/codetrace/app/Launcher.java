package com.mmdev.codetrace.app;

import javafx.application.Application;

/** Non-modular JavaFX entry point.  * <p><strong>Engineering rationale.</strong> The tiny launcher is the process boundary used by Maven and jpackage to enter JavaFX correctly. Keeping bootstrapping outside the main window avoids coupling OS packaging rules to application navigation, session restoration or project-domain logic.</p>
*/
public final class Launcher {
    private Launcher() {
    }

    public static void main(String[] args) {
        Application.launch(CodeTraceApp.class, args);
    }
}
