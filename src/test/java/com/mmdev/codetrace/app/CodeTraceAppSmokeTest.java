package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.Slider;
import javafx.geometry.Orientation;
import javafx.stage.Window;
import com.mmdev.codetrace.ui.PreferencesWindow;
import com.mmdev.codetrace.ui.ProductDialogs;
import com.mmdev.codetrace.reference.ReferenceTreeIndex;
import com.mmdev.codetrace.commands.ProjectCommandStore;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.Event;
import com.mmdev.codetrace.ui.TypingEditor;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.ui.EditorZoomControl;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SplitPane;
import javafx.scene.control.CheckBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.Spinner;
import com.mmdev.codetrace.ui.ReferenceTree;
import com.mmdev.codetrace.ui.ReconstructionStateIcons;
import com.mmdev.codetrace.practice.ReconstructionStatus;
import com.mmdev.codetrace.practice.PracticeMode;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
                var zoom = stage.getScene().getRoot().lookup(".status-zoom-control");
                assertNotNull(zoom, "The zoom belongs to the status bar");
                assertSame(stage.getScene().getRoot().lookup(".statusbar"),
                        zoom.getParent());
                Slider fontSlider = (Slider) zoom.lookup(".slider");
                assertNotNull(fontSlider);
                assertEquals(Orientation.HORIZONTAL, fontSlider.getOrientation());
                assertNull(stage.getScene().getRoot().lookup(".sound-toggle"),
                        "Sound control is in Preferences, not the main toolbar");

                Stage preferences = PreferencesWindow.create(stage, AppSettings.load(),
                        next -> {}, correct -> true);
                preferences.show();
                preferences.getScene().getRoot().applyCss();
                preferences.getScene().getRoot().layout();
                assertNotNull(preferences.getScene().getRoot().lookup(".prefs-navigation"));
                assertFalse(preferences.getIcons().isEmpty(),
                        "Preferences should use the CodeTrace window icon");
                assertNotNull(preferences.getScene().getRoot().lookup(".prefs-content"));
                assertNotNull(preferences.getScene().getRoot().lookup(".prefs-actions"));
                Slider opacitySlider = (Slider) preferences.getScene().getRoot()
                        .lookup(".prefs-opacity-slider");
                assertNotNull(opacitySlider, "Opacity must be a horizontal Slider");
                assertEquals(Orientation.HORIZONTAL, opacitySlider.getOrientation());
                assertEquals(20.0, opacitySlider.getMin());
                assertEquals(100.0, opacitySlider.getMax());
                assertNull(preferences.getScene().getRoot().lookup(".prefs-opacity-spinner"));
                assertNotNull(preferences.getScene().getRoot().lookup(".prefs-font-choice"));
                preferences.close();
                var about = ProductDialogs.createAbout(stage);
                about.show();
                about.getDialogPane().getScene().getRoot().applyCss();
                about.getDialogPane().getScene().getRoot().layout();
                javafx.scene.control.Label aboutCopy = (javafx.scene.control.Label)
                        about.getDialogPane().lookup(".about-description");
                assertNotNull(aboutCopy);
                assertTrue(aboutCopy.getText().contains("inteligencia artificial"));
                javafx.scene.image.ImageView aboutImage =
                        (javafx.scene.image.ImageView)
                                about.getDialogPane().lookup(".about-logo-large");
                assertNotNull(aboutImage);
                assertTrue(aboutImage.getImage().getWidth() >= 256);
                about.close();
                Stage projectPrefs = PreferencesWindow.create(
                        stage, AppSettings.load(), next -> {}, sample -> true,
                        root, new ProjectCommandStore(root.resolve("config-cache")), () -> {});
                projectPrefs.show();
                var projectNavigation = (ListView<String>) projectPrefs
                        .getScene().getRoot().lookup(".prefs-navigation");
                projectNavigation.getSelectionModel().select("Comandos");
                projectPrefs.getScene().getRoot().applyCss();
                projectPrefs.getScene().getRoot().layout();
                assertNotNull(projectPrefs.getScene().getRoot()
                        .lookup(".command-settings-pane"));
                projectPrefs.close();
                TypingEditor editor = (TypingEditor) tabPane.getTabs().getFirst().getContent();
                Field workspaceField = CodeTraceApp.class.getDeclaredField("workspace");
                workspaceField.setAccessible(true);
                VirtualWorkspace virtual = (VirtualWorkspace) workspaceField.get(application);

                // Opening the original is read-only; all attempted writes are blocked
                // until the file and each parent are explicitly created in memory.
                assertFalse(editor.isPracticeEnabled());
                AtomicInteger blocked = new AtomicInteger();
                editor.setPracticePermission(() ->
                        virtual.entry("src/Main.java") != null
                                && virtual.entry("src/Main.java").kind()
                                        == VirtualWorkspace.Kind.FILE,
                        blocked::incrementAndGet);
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_TYPED,
                        "c", "c", KeyCode.UNDEFINED, false, false, false, false));
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_PRESSED,
                        "", "", KeyCode.BACK_SPACE, false, false, false, false));
                assertEquals(2, blocked.get());
                assertEquals(1, virtual.entries().size(),
                        "Opening the reference must not create virtual paths");
                Field modeField = CodeTraceApp.class.getDeclaredField("practiceMode");
                modeField.setAccessible(true);
                assertEquals(PracticeMode.RECONSTRUCTION, modeField.get(application));
                assertEquals(0, editor.session().correctCount());
                assertEquals(0, editor.session().caret());

                virtual.createDirectory("src");
                virtual.createFile("src/Main.java");
                assertTrue(editor.isPracticeEnabled());
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_TYPED,
                        "c", "c", KeyCode.UNDEFINED, false, false, false, false));
                assertEquals("c", virtual.text("src/Main.java"));
                String untouched = editor.visibleLineText(0);
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_TYPED,
                        "x", "x", KeyCode.UNDEFINED, false, false, false, false));
                assertEquals(untouched, editor.visibleLineText(0),
                        "Wrong input must never replace the visible source");
                assertEquals(TypingSession.State.INCORRECT, editor.session().state(1));

                // Rendered glyphs remain from the reference; only backgrounds
                // communicate correct/incorrect. Nothing typed replaces a glyph.
                stage.getScene().getRoot().applyCss();
                stage.getScene().getRoot().layout();
                assertTrue(editor.lookupAll(".trace-correct").size() >= 1,
                        "Expected a green-background correct-character tile");
                assertTrue(editor.lookupAll(".trace-incorrect").size() >= 1,
                        "Expected a red-background incorrect-character tile");
                editor.selectLine(0);
                assertEquals(0, editor.session().caret());
                assertEquals(0, editor.activeLineNumber());
                assertTrue(editor.getChildren().stream()
                        .anyMatch(node -> node.getStyleClass().contains("typing-lines")));

                TypingEditor second = new TypingEditor(
                        new TypingSession("alpha\nbeta"), () -> {},
                        "Example.java", "Consolas", 16, matched -> {});
                second.selectLine(1);
                assertEquals(6, second.session().caret());
                assertEquals("beta", second.visibleLineText(1));

                // Ctrl+V of ONE symbol writes into the existing reference slot
                // without shifting the source glyph or changing lines.
                int last = editor.session().size() - 1;
                editor.session().moveCaret(last);
                ClipboardContent copiedSymbol = new ClipboardContent();
                copiedSymbol.putString("}");
                Clipboard.getSystemClipboard().setContent(copiedSymbol);
                Event.fireEvent(editor, new KeyEvent(KeyEvent.KEY_PRESSED,
                        "", "", KeyCode.V, false, true, false, false));
                assertEquals(TypingSession.State.CORRECT, editor.session().state(last));
                assertEquals(editor.session().size(), editor.session().caret());
                assertEquals(untouched, editor.visibleLineText(0));

                // The tree has bounded expansion/collapse and explicit virtual
                // created/uncreated markers.
                ReferenceTree fileTree = (ReferenceTree) stage.getScene()
                        .getRoot().lookup(".tree-view");
                assertNotNull(fileTree);
                // Regression: the virtual status must be a real vector icon,
                // not a Label whose text can collapse into an ellipsis.
                StackPane createdBadge = ReconstructionStateIcons.forCreated(true);
                StackPane pendingBadge = ReconstructionStateIcons.forCreated(false);
                assertTrue(createdBadge.getChildren().getFirst() instanceof FontIcon);
                assertTrue(pendingBadge.getChildren().getFirst() instanceof FontIcon);
                assertEquals(18.0, createdBadge.getMinWidth());
                assertEquals(18.0, pendingBadge.getMinWidth());
                assertTrue(createdBadge.getStyleClass().contains("state-created"));
                assertTrue(pendingBadge.getStyleClass().contains("state-pending"));
                StackPane completedBadge = ReconstructionStateIcons.forState(
                        ReconstructionStatus.State.COMPLETED);
                assertTrue(completedBadge.getChildren().getFirst() instanceof FontIcon);
                assertTrue(completedBadge.getStyleClass().contains("state-completed"));
                assertTrue(createdBadge.getStyleClass().contains("state-created"));
                // Menus must permit constructing a real reference directory
                // before the virtual file, with no implicit disk creation.
                Path nested = Files.createDirectory(src.resolve("ejemplos"));
                Path nestedFile = nested.resolve("Demo.java");
                Files.writeString(nestedFile, "class Demo {}");
                fileTree.open(root);
                Field treeIndexField = ReferenceTree.class.getDeclaredField("index");
                treeIndexField.setAccessible(true);
                treeIndexField.set(fileTree, ReferenceTreeIndex.scan(root));
                Method menuFor = ReferenceTree.class.getDeclaredMethod(
                        "contextFor", Path.class);
                menuFor.setAccessible(true);
                ContextMenu rootMenu = (ContextMenu) menuFor.invoke(fileTree, root);
                assertTrue(rootMenu.getItems().stream().anyMatch(
                        item -> "Crear subcarpeta...".equals(item.getText())));
                assertFalse(rootMenu.getItems().stream().anyMatch(
                        item -> "Crear archivo...".equals(item.getText())));
                ContextMenu folderMenu = (ContextMenu) menuFor.invoke(fileTree, nested);
                assertTrue(folderMenu.getItems().stream().anyMatch(
                        item -> "Crear esta carpeta".equals(item.getText())));
                assertFalse(folderMenu.getItems().stream().anyMatch(
                        item -> "Crear subcarpeta...".equals(item.getText())));
                folderMenu.getItems().stream()
                        .filter(item -> "Crear esta carpeta".equals(item.getText()))
                        .findFirst().orElseThrow().fire();
                assertTrue(virtual.exists("src/ejemplos"));
                ContextMenu fileMenu = (ContextMenu) menuFor.invoke(fileTree, nestedFile);
                assertTrue(fileMenu.getItems().stream().anyMatch(
                        item -> "Crear este archivo".equals(item.getText())));
                assertTrue(fileMenu.getItems().stream().anyMatch(
                        item -> "Mostrar en el Explorador".equals(item.getText())));
                fileMenu.getItems().stream()
                        .filter(item -> "Crear este archivo".equals(item.getText()))
                        .findFirst().orElseThrow().fire();
                assertTrue(virtual.exists("src/ejemplos/Demo.java"));
                ContextMenu createdFileMenu = (ContextMenu) menuFor.invoke(
                        fileTree, nestedFile);
                assertTrue(createdFileMenu.getItems().stream().anyMatch(
                        item -> "Mostrar en el Explorador".equals(item.getText())));
                assertFalse(createdFileMenu.getItems().stream().anyMatch(item ->
                        item.getText() != null && java.util.Set.of("Cambiar nombre...",
                                "Copiar", "Cortar", "Eliminar").contains(item.getText())));
                ContextMenu createdFolderMenu = (ContextMenu) menuFor.invoke(
                        fileTree, nested);
                assertFalse(createdFolderMenu.getItems().stream().anyMatch(
                        item -> "Crear archivo...".equals(item.getText())),
                        "No file-creation menu once all eligible files are created");
                assertFalse(createdFolderMenu.getItems().stream().anyMatch(item ->
                        item.getText() != null && java.util.Set.of("Cambiar nombre...",
                                "Copiar", "Cortar", "Eliminar").contains(item.getText())));
                assertFalse(Files.size(nestedFile) == 0,
                        "The real source must never be modified by virtual creation");
                // Direct mode bypasses manual file/folder creation only at
                // the moment of typing, never while merely opening the preview.
                Path directFile = src.resolve("Direct.java");
                Files.writeString(directFile, "A");
                open.invoke(application, directFile);
                assertFalse(virtual.exists("src/Direct.java"));
                Method changeMode = CodeTraceApp.class.getDeclaredMethod(
                        "setPracticeMode", PracticeMode.class);
                changeMode.setAccessible(true);
                changeMode.invoke(application, PracticeMode.DIRECT);
                assertEquals(PracticeMode.DIRECT, modeField.get(application));
                TypingEditor directEditor = (TypingEditor) tabPane
                        .getSelectionModel().getSelectedItem().getContent();
                Event.fireEvent(directEditor, new KeyEvent(KeyEvent.KEY_TYPED,
                        "A", "A", KeyCode.UNDEFINED, false, false, false, false));
                assertTrue(virtual.exists("src/Direct.java"),
                        "Direct mode virtualizes the file on the first typed character");
                assertEquals("A", virtual.text("src/Direct.java"));
                changeMode.invoke(application, PracticeMode.RECONSTRUCTION);
                assertTrue(virtual.exists("src/Direct.java"),
                        "Switching modes must not discard created files");
                fileTree.collapseAll();
                fileTree.expandAll();
                assertTrue(fileTree.getRoot().isExpanded());
                Method toggleConsole = CodeTraceApp.class.getDeclaredMethod(
                        "setConsoleVisible", boolean.class);
                toggleConsole.setAccessible(true);
                toggleConsole.invoke(application, true);
                var console = (com.mmdev.codetrace.ui.CommandConsolePane)
                        stage.getScene().getRoot().lookup(".command-console-pane");
                assertNotNull(console);
                console.setProject(root);
                console.setPresets(java.util.List.of());
                SplitPane workspaceLayout = (SplitPane) stage.getScene()
                        .getRoot().lookup(".workspace-split");
                assertNotNull(workspaceLayout);
                assertEquals(Orientation.VERTICAL, workspaceLayout.getOrientation());
                assertEquals(2, workspaceLayout.getItems().size(),
                        "Console must occupy a resizable SplitPane, not a fixed VBox");
                assertTrue(workspaceLayout.getItems().contains(console));
                assertTrue(stage.getScene().getRoot()
                        .lookup(".console-empty-state").isVisible());
                assertTrue(stage.getScene().getRoot()
                        .lookup(".console-configure").isVisible());
                toggleConsole.invoke(application, false);
                assertEquals(1, workspaceLayout.getItems().size());
                assertNotNull(stage.getScene().getRoot().lookup(".turbo-toggle"));
                MenuButton filter = (MenuButton) stage.getScene().getRoot()
                        .lookup(".status-progress-filter");
                assertNotNull(filter);
                CustomMenuItem filterPanel = (CustomMenuItem) filter.getItems().getFirst();
                assertFalse(filterPanel.isHideOnClick(),
                        "Checking files must keep the menu open");
                VBox filterChoices = (VBox) filterPanel.getContent();
                assertEquals(3, filterChoices.getChildren().size());
                CheckBox all = (CheckBox) filterChoices.getChildren().getFirst();
                all.fire();
                for (var child : filterChoices.getChildren().subList(1, 3)) {
                    assertTrue(((CheckBox) child).isSelected());
                }
                CheckBox markdown = (CheckBox) filterChoices.getChildren().get(2);
                markdown.fire();
                assertFalse(all.isSelected());
                all.fire();
                for (var child : filterChoices.getChildren().subList(1, 3)) {
                    assertTrue(((CheckBox) child).isSelected());
                }
                assertNotNull(stage.getScene().getRoot().lookup(".tree-activity"));
                assertNotNull(stage.getScene().getRoot().lookup(".tree-scope-checkbox"));
                Files.delete(directFile);
                Files.delete(nestedFile);
                Files.delete(nested);
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
