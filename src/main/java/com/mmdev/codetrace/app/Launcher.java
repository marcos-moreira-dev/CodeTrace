package com.mmdev.codetrace.app;

import javafx.application.Application;

/** Non-modular JavaFX entry point. */
public final class Launcher {
    private Launcher() {
    }

    public static void main(String[] args) {
        Application.launch(CodeTraceApp.class, args);
    }
}
