package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

/**
 * Run separately under xvfb-run: validates actual JavaFX initialization,
 * stylesheet loading and showing the main window (not just javac).
 */
@EnabledIfSystemProperty(named = "codetrace.guiSmoke", matches = "true")
class CodeTraceAppSmokeTest {
    @Test
    void launchesAndShowsTheMainWindow() throws Exception {
        CountDownLatch finished = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                new CodeTraceApp().start(stage);
                assertTrue(stage.isShowing(), "The main window was not shown");
                assertNotNull(stage.getScene());
                stage.getScene().getRoot().applyCss();
                stage.getScene().getRoot().layout();
            } catch (Throwable problem) {
                failure.set(problem);
            } finally {
                stage.close();
                Platform.exit();
                finished.countDown();
            }
        });
        assertTrue(finished.await(25, TimeUnit.SECONDS),
                "JavaFX startup did not finish");
        if (failure.get() != null) {
            fail("JavaFX startup failed", failure.get());
        }
    }
}
