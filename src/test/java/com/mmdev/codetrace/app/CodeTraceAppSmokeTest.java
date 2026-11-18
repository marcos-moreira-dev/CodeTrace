package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.Event;
import com.mmdev.codetrace.ui.TypingEditor;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import com.mmdev.codetrace.ui.SymbolPalette;
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
                CodeTraceApp application = new CodeTraceApp();
                application.start(stage);
                assertTrue(stage.isShowing(), "The main window was not shown");
                assertNotNull(stage.getScene());
                stage.getScene().getRoot().applyCss();
                stage.getScene().getRoot().layout();
                // Verify the compact gallery is present and copies without typing.
                SymbolPalette palette = (SymbolPalette) stage.getScene().getRoot()
                        .lookup(".symbol-menu-button");
                assertNotNull(palette);
                VBox gallery = (VBox) ((CustomMenuItem) palette.getItems().getFirst())
                        .getContent();
                Button firstSymbol = (Button) ((FlowPane) gallery.getChildren().getFirst())
                        .getChildren().getFirst();
                firstSymbol.fire();
                assertEquals("{", Clipboard.getSystemClipboard().getString());

                // Regression: double-clicked reference files must open even
                // before their virtual path has been manually created.
                Path root = Files.createTempDirectory("codetrace-smoke-");
                Path src = Files.createDirectory(root.resolve("src"));
                Path file = src.resolve("Main.java");
                Files.writeString(file, "class Main {}");
                Field referenceField = CodeTraceApp.class.getDeclaredField("reference");
                referenceField.setAccessible(true);
                referenceField.set(application, root);
                Method open = CodeTraceApp.class.getDeclaredMethod(
                        "openForPractice", Path.class);
                open.setAccessible(true);
                open.invoke(application, file);
                Field tabsField = CodeTraceApp.class.getDeclaredField("tabs");
                tabsField.setAccessible(true);
                TabPane tabPane = (TabPane) tabsField.get(application);
                assertEquals(1, tabPane.getTabs().size());
                TypingEditor editor = (TypingEditor) tabPane.getTabs().getFirst().getContent();
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_TYPED,
                        "c", "c", KeyCode.UNDEFINED, false, false, false, false));
                Field workspaceField = CodeTraceApp.class.getDeclaredField("workspace");
                workspaceField.setAccessible(true);
                VirtualWorkspace virtual = (VirtualWorkspace) workspaceField.get(application);
                assertTrue(virtual.exists("src/Main.java"));
                assertEquals("c", virtual.text("src/Main.java"));
                Files.delete(file);
                Files.delete(src);
                Files.delete(root);
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
