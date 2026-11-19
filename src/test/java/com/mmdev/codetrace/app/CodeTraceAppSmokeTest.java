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
import com.mmdev.codetrace.ui.FileTypeIcons;
import com.mmdev.codetrace.ui.AudioSpectrumPane;
import com.mmdev.codetrace.ui.ProjectSearchWindow;
import com.mmdev.codetrace.ui.ReferenceMediaPane;
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
                javafx.scene.control.ToolBar toolbar = (javafx.scene.control.ToolBar)
                        stage.getScene().getRoot().lookup(".tool-bar");
                assertNotNull(toolbar);
                assertFalse(toolbar.getItems().stream()
                        .filter(javafx.scene.control.Button.class::isInstance)
                        .map(javafx.scene.control.Button.class::cast)
                        .anyMatch(button -> "Abrir archivo".equals(button.getText())),
                        "The redundant single-file action must not return");
                assertTrue(toolbar.getItems().stream()
                        .filter(javafx.scene.control.Button.class::isInstance)
                        .map(javafx.scene.control.Button.class::cast)
                        .anyMatch(button -> "Abrir proyecto".equals(button.getText())));
                stage.getScene().getRoot().applyCss();
                stage.getScene().getRoot().layout();
                // Verify the compact gallery is present and copies without typing.
                SymbolPalette palette = (SymbolPalette) stage.getScene().getRoot()
                        .lookup(".symbol-menu-button");
                assertNotNull(palette);
                VBox gallery = (VBox) ((CustomMenuItem) palette.getItems().getFirst())
                        .getContent();
                assertNotNull(gallery.lookup(".mini-keyboard"),
                        "Symbols should include a compact keyboard-layout reference");
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
                Path imageFile = src.resolve("image.png");
                Files.write(imageFile, java.util.Base64.getDecoder().decode(
                        "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQIHWP4"
                        + "z8DwHwAFgAI/ScLttAAAAABJRU5ErkJggg=="));
                open.invoke(application, imageFile);
                assertEquals(2, tabPane.getTabs().size());
                assertTrue(tabPane.getSelectionModel().getSelectedItem().getContent()
                        instanceof ReferenceMediaPane);
                ReferenceMediaPane selectedMedia = (ReferenceMediaPane)
                        tabPane.getSelectionModel().getSelectedItem().getContent();
                selectedMedia.setZoomPercent(150);
                assertEquals(150, selectedMedia.getZoomPercent());
                assertNotNull(selectedMedia.lookup(".media-zoom-viewport"));
                assertNotNull(stage.getScene().getRoot().lookup(".media-zoom-control"));
                assertTrue(tabPane.getSelectionModel().getSelectedItem().isClosable());
                // Spectrum rendering does not require an audio device.
                AudioSpectrumPane spectrum = new AudioSpectrumPane();
                spectrum.resize(520, 170);
                spectrum.layout();
                spectrum.accept(new float[] {-38f, -21f, -44f});
                spectrum.clear();
                spectrum.dispose();
                ProjectSearchWindow find = new ProjectSearchWindow(stage,
                        () -> root, () -> "src/Main.java", hit -> {});
                find.show(ProjectSearchWindow.Scope.FILE);
                Field searchStageField = ProjectSearchWindow.class
                        .getDeclaredField("stage");
                searchStageField.setAccessible(true);
                Stage searchStage = (Stage) searchStageField.get(find);
                assertTrue(searchStage.isShowing());
                assertFalse(searchStage.getIcons().isEmpty(),
                        "Find should show the CodeTrace titlebar icon");
                var filterRow = (FlowPane) searchStage.getScene().getRoot()
                        .lookup(".search-filter-options");
                assertNotNull(filterRow);
                assertEquals(4, filterRow.getChildren().size(),
                        "The scope and checkboxes share a responsive row");
                searchStage.close();
                tabPane.getSelectionModel().selectFirst();
                var zoom = stage.getScene().getRoot().lookup(".status-zoom-control");
                assertNotNull(zoom, "The zoom belongs to the status bar");
                assertSame(stage.getScene().getRoot().lookup(".statusbar"),
                        zoom.getParent());
                // Every read-only text format must follow the global font slider.
                com.mmdev.codetrace.ui.ReferenceTextPane xml =
                        new com.mmdev.codetrace.ui.ReferenceTextPane(
                                "<project><version>21</version></project>", "pom.xml");
                xml.setFontSettings("Consolas", 23);
                assertEquals(23, xml.fontSize());
                xml.setFontSettings("Consolas", 13);
                assertEquals(13, xml.fontSize());
                var gutter = com.mmdev.codetrace.ui.CodeLineGutter.number(42, 300, 13);
                assertEquals("42", gutter.getText());
                assertTrue(gutter.getStyleClass().contains("code-line-number"));
                assertEquals(com.mmdev.codetrace.ui.CodeLineGutter.rowHeight(13),
                        gutter.getPrefHeight());
                assertEquals(com.mmdev.codetrace.ui.CodeLineGutter.widthFor(300, 13),
                        gutter.getPrefWidth());
                var disposableTabs = new com.mmdev.codetrace.ui.DocumentTabs(
                        new TabPane(), () -> {});
                javafx.scene.control.Tab sampleTab = new javafx.scene.control.Tab("A");
                disposableTabs.open("src/A.java", sampleTab);
                assertSame(sampleTab, disposableTabs.find("src/A.java"));
                disposableTabs.close(sampleTab);
                assertNull(disposableTabs.find("src/A.java"));
                com.mmdev.codetrace.ui.ReferenceTextPane plain =
                        new com.mmdev.codetrace.ui.ReferenceTextPane("Hello", "notes.txt");
                plain.setFontSettings("Consolas", 24);
                assertEquals(24, plain.fontSize());

                // Graph mode swaps the same zoom control, not an extra slider.
                com.mmdev.codetrace.ui.MediaZoomControl graphZoom =
                        new com.mmdev.codetrace.ui.MediaZoomControl(value -> {});
                graphZoom.setGraphMode(true);
                assertTrue(graphZoom.getStyleClass().contains("graph-zoom-control"));
                graphZoom.setValue(175);
                assertEquals(1, graphZoom.lookupAll(".slider").size());
                var graphModel = new com.mmdev.codetrace.reference.ProjectDependencyGraph.Graph(
                        "src", java.util.List.of("src/Main.java", "src/Util.java"),
                        java.util.List.of(new com.mmdev.codetrace.reference
                                .ProjectDependencyGraph.Edge("src/Main.java",
                                        "src/Util.java", "import")),
                        2, false, "a".repeat(64));
                AtomicReference<com.mmdev.codetrace.reference.GraphLayout> graphChanges =
                        new AtomicReference<>();
                AtomicReference<String> selectedGraphPath = new AtomicReference<>();
                var graphPane = new com.mmdev.codetrace.ui.DependencyGraphPane(
                        graphModel, ignored -> {}, null, graphChanges::set,
                        selectedGraphPath::set);
                assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.ORGANIZED,
                        graphPane.getLayoutMode());
                assertTrue(graphPane.hasUnboundedPan(),
                        "Graph navigation should use a camera, not finite ScrollPane edges");
                assertNull(graphPane.getTop());
                assertNull(graphPane.getBottom());
                graphPane.setLayoutMode(
                        com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID);
                assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.GRID,
                        graphChanges.get().mode());
                graphPane.setLayoutMode(
                        com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.ORGANIZED);
                assertEquals(com.mmdev.codetrace.reference.GraphLayoutEngine.Mode.ORGANIZED,
                        graphChanges.get().mode());
                var previousTab = tabPane.getSelectionModel().getSelectedItem();
                var mapTab = new javafx.scene.control.Tab("Mapa prueba", graphPane);
                tabPane.getTabs().add(mapTab);
                tabPane.getSelectionModel().select(mapTab);
                stage.getScene().getRoot().applyCss();
                stage.getScene().getRoot().layout();
                javafx.scene.layout.Pane graphViewport =
                        (javafx.scene.layout.Pane) graphPane.getCenter();
                javafx.scene.canvas.Canvas graphSurface =
                        (javafx.scene.canvas.Canvas) graphViewport.getChildren().getFirst();
                assertTrue(graphSurface.getWidth() > 300);
                assertEquals(graphViewport.getWidth(), graphSurface.getWidth(), 0.01,
                        "Graph Canvas should only allocate the viewport size");
                graphPane.setZoomPercent(55);
                double oldCameraX = graphPane.getCameraX();
                double oldCameraY = graphPane.getCameraY();
                graphPane.panByPixels(50_000, -30_000);
                assertTrue(Math.abs(graphPane.getCameraX() - oldCameraX) > 80_000,
                        "Pan is not constrained by finite canvas dimensions");
                graphPane.panByPixels(-50_000, 30_000);
                assertEquals(oldCameraX, graphPane.getCameraX(), 0.0001);
                assertEquals(oldCameraY, graphPane.getCameraY(), 0.0001);

                var oldNode = graphChanges.get().positions().get("src/Main.java");
                double x = (oldNode.x() - graphPane.getCameraX()) * 0.55
                        + graphSurface.getWidth() / 2;
                double y = (oldNode.y() - graphPane.getCameraY()) * 0.55
                        + graphSurface.getHeight() / 2;
                graphSurface.getOnMousePressed().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                                x, y, 0, 0,
                                javafx.scene.input.MouseButton.PRIMARY, 1,
                                false, false, false, false, true, false, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, x, y)));
                graphSurface.getOnMouseDragged().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_DRAGGED,
                                x + 45, y + 30, 0, 0,
                                javafx.scene.input.MouseButton.PRIMARY, 1,
                                false, false, false, false, true, false, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, x + 45, y + 30)));
                assertEquals(oldCameraX, graphPane.getCameraX(), 0.0001,
                        "Dragging a node must not move the graph camera");
                graphSurface.getOnMouseReleased().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_RELEASED,
                                x + 45, y + 30, 0, 0,
                                javafx.scene.input.MouseButton.PRIMARY, 1,
                                false, false, false, false, false, false, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, x + 45, y + 30)));
                assertTrue(graphChanges.get().positions().get("src/Main.java").x()
                        > oldNode.x(), "The node moves independently from the viewport");
                assertEquals("src/Main.java", graphPane.getSelectedNode());
                assertTrue(graphPane.getSelectedNeighbors().contains("src/Util.java"));
                assertEquals("src/Main.java", selectedGraphPath.get());

                // Middle button pans without affecting the selected node.
                double bgX = graphSurface.getWidth() - 20;
                double bgY = graphSurface.getHeight() - 20;
                graphSurface.getOnMousePressed().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                                bgX, bgY, 100, 100,
                                javafx.scene.input.MouseButton.MIDDLE, 1,
                                false, false, false, false, false, true, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, bgX, bgY)));
                assertEquals(javafx.scene.Cursor.CLOSED_HAND, graphSurface.getCursor());
                graphSurface.getOnMouseDragged().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_DRAGGED,
                                bgX + 90, bgY + 30, 190, 130,
                                javafx.scene.input.MouseButton.MIDDLE, 1,
                                false, false, false, false, false, true, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, bgX, bgY)));
                assertTrue(graphPane.getCameraX() < oldCameraX);
                graphSurface.getOnMouseReleased().handle(
                        new javafx.scene.input.MouseEvent(
                                javafx.scene.input.MouseEvent.MOUSE_RELEASED,
                                bgX + 90, bgY + 30, 190, 130,
                                javafx.scene.input.MouseButton.MIDDLE, 1,
                                false, false, false, false, false, false, false,
                                false, false, false,
                                new javafx.scene.input.PickResult(graphSurface, bgX, bgY)));
                assertEquals(javafx.scene.Cursor.OPEN_HAND, graphSurface.getCursor());
                graphPane.clearSelection();
                assertNull(graphPane.getSelectedNode());
                assertTrue(graphPane.getSelectedNeighbors().isEmpty());
                assertNull(selectedGraphPath.get());
                tabPane.getTabs().remove(mapTab);
                tabPane.getSelectionModel().select(previousTab);
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
                @SuppressWarnings("unchecked")
                ListView<String> preferencesNavigation = (ListView<String>)
                        preferences.getScene().getRoot().lookup(".prefs-navigation");
                assertTrue(preferencesNavigation.getItems().contains("Grafo"));
                preferencesNavigation.getSelectionModel().select("Grafo");
                preferences.getScene().getRoot().applyCss();
                preferences.getScene().getRoot().layout();
                javafx.scene.Node choices = preferences.getScene().getRoot()
                        .lookup(".prefs-graph-layout");
                assertNotNull(choices, "Graph distribution belongs in Preferences");
                assertTrue(choices instanceof javafx.scene.layout.VBox,
                        "Radio choices must be stacked vertically");
                assertEquals(2, ((javafx.scene.layout.VBox) choices)
                        .getChildren().size());
                preferencesNavigation.getSelectionModel().select("Avisos");
                preferences.getScene().getRoot().applyCss();
                preferences.getScene().getRoot().layout();
                assertNotNull(preferences.getScene().getRoot()
                        .lookup(".read-only-consultation-notice"),
                        "The notice can be reenabled in Preferences > Avisos");
                preferences.close();
                var consultationNotice = ProductDialogs.createConsultationNotice(stage);
                consultationNotice.show();
                CheckBox optOut = (CheckBox) consultationNotice
                        .getDialogPane().lookup(".consultation-notice-optout");
                assertNotNull(optOut, "Read-only warning includes a one-click opt-out");
                assertFalse(optOut.isSelected(),
                        "Do not preselect 'do not show again'");
                consultationNotice.close();
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
                javafx.scene.image.ImageView authorPortrait =
                        (javafx.scene.image.ImageView)
                                about.getDialogPane().lookup(".about-author-portrait");
                assertNotNull(authorPortrait, "The About author portrait must be packaged locally");
                assertEquals(112.0, authorPortrait.getImage().getWidth());
                javafx.scene.control.Label authorName =
                        (javafx.scene.control.Label)
                                about.getDialogPane().lookup(".about-author-name");
                assertNotNull(authorName);
                assertEquals("Marcos Moreira", authorName.getText());
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
                Field autoCreate = CodeTraceApp.class.getDeclaredField("autoCreateCheck");
                autoCreate.setAccessible(true);
                CheckBox auto = (CheckBox) autoCreate.get(application);
                assertNotNull(auto, "Auto creation belongs to practice rules");
                Method selectMode = CodeTraceApp.class.getDeclaredMethod(
                        "selectPracticeMode", PracticeMode.class);
                selectMode.setAccessible(true);
                selectMode.invoke(application, PracticeMode.DIRECT);
                assertTrue(auto.isSelected());
                selectMode.invoke(application, PracticeMode.RECONSTRUCTION);
                assertFalse(auto.isSelected());
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
                assertTrue(editor.lookupAll(".code-line-number").stream()
                        .anyMatch(node -> node instanceof javafx.scene.control.Label label
                                && "1".equals(label.getText())),
                        "Practice lines must have a native 1-based gutter");
                assertTrue(editor.getChildren().stream()
                        .anyMatch(node -> node.getStyleClass().contains("typing-lines")));

                TypingEditor second = new TypingEditor(
                        new TypingSession("alpha\nbeta"), () -> {},
                        "Example.java", "Consolas", 16, matched -> {});
                second.selectLine(1);
                assertEquals(6, second.session().caret());
                assertEquals("beta", second.visibleLineText(1));
                second.showSearchMatch(0, 1, 2);
                assertEquals(6, second.session().caret(),
                        "Find must never change the typing cursor");

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
                assertNotNull(stage.getScene().getRoot().lookup(".tree-name-search"));
                assertNotNull(stage.getScene().getRoot().lookup(".tree-exclude-checkbox"));
                assertNotNull(stage.getScene().getRoot().lookup(".tabs-actions"));
                // Regression: the virtual status must be a real vector icon,
                // not a Label whose text can collapse into an ellipsis.
                StackPane createdBadge = ReconstructionStateIcons.forCreated(true);
                StackPane pendingBadge = ReconstructionStateIcons.forCreated(false);
                assertTrue(FileTypeIcons.forPath(Path.of("install.bat"), false)
                        instanceof FontIcon, "Batch files require real vector icons");
                assertTrue(FileTypeIcons.forPath(Path.of("install.cmd"), false)
                        instanceof FontIcon, "CMD files require real vector icons");
                assertTrue(FileTypeIcons.forPath(Path.of("logo.svg"), false)
                        instanceof FontIcon, "SVG files require an image icon");
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
                Path deep = Files.createDirectories(nested.resolve("deep/layer"));
                Files.writeString(deep.resolve("Example.java"), "class Example {}");
                fileTree.open(root);
                Field treeIndexField = ReferenceTree.class.getDeclaredField("index");
                treeIndexField.setAccessible(true);
                treeIndexField.set(fileTree, ReferenceTreeIndex.scan(root));
                Method menuFor = ReferenceTree.class.getDeclaredMethod(
                        "contextFor", Path.class);
                menuFor.setAccessible(true);
                ContextMenu rootMenu = (ContextMenu) menuFor.invoke(fileTree, root);
                assertFalse(rootMenu.getItems().stream().anyMatch(
                        item -> "Crear subcarpeta...".equals(item.getText())),
                        "No new-folder action when all immediate folders already exist");
                assertFalse(rootMenu.getItems().stream().anyMatch(
                        item -> "Crear archivo...".equals(item.getText())));
                ContextMenu srcMenu = (ContextMenu) menuFor.invoke(fileTree, src);
                assertTrue(srcMenu.getItems().stream().anyMatch(item ->
                        "Colapsar todas las subcarpetas".equals(item.getText())));
                assertTrue(srcMenu.getItems().stream().anyMatch(item ->
                        "Mapa de dependencias...".equals(item.getText())));
                ContextMenu folderMenu = (ContextMenu) menuFor.invoke(fileTree, nested);
                assertTrue(folderMenu.getItems().stream().anyMatch(item ->
                        "Colapsar todas las subcarpetas".equals(item.getText())),
                        "The nested folder now contains a deeper subfolder");
                assertFalse(((ContextMenu) menuFor.invoke(fileTree, deep))
                        .getItems().stream().anyMatch(item ->
                        "Colapsar todas las subcarpetas".equals(item.getText())));
                assertTrue(folderMenu.getItems().stream().anyMatch(item ->
                        "Mapa de dependencias...".equals(item.getText())));
                assertTrue(folderMenu.getItems().stream().anyMatch(
                        item -> "Crear esta carpeta".equals(item.getText())));
                assertTrue(folderMenu.getItems().stream().anyMatch(
                        item -> "Crear subcarpeta...".equals(item.getText())),
                        "The deep fixture includes an uncreated subfolder");
                folderMenu.getItems().stream()
                        .filter(item -> "Crear esta carpeta".equals(item.getText()))
                        .findFirst().orElseThrow().fire();
                assertTrue(virtual.exists("src/ejemplos"));
                // Recursive collapse keeps the clicked parent expanded,
                // while its already expanded child folders become collapsed.
                fileTree.refreshTree();
                var srcTree = fileTree.getRoot().getChildren().stream()
                        .filter(item -> src.equals(item.getValue()))
                        .findFirst().orElseThrow();
                srcTree.setExpanded(true);
                var nestedTree = srcTree.getChildren().stream()
                        .filter(item -> nested.equals(item.getValue()))
                        .findFirst().orElseThrow();
                nestedTree.setExpanded(true);
                var deepTree = nestedTree.getChildren().stream()
                        .filter(item -> nested.resolve("deep").equals(item.getValue()))
                        .findFirst().orElseThrow();
                deepTree.setExpanded(true);
                var layerTree = deepTree.getChildren().stream()
                        .filter(item -> deep.equals(item.getValue()))
                        .findFirst().orElseThrow();
                layerTree.setExpanded(true);
                assertTrue(layerTree.isExpanded());
                srcMenu.getItems().stream()
                        .filter(item -> "Colapsar todas las subcarpetas".equals(item.getText()))
                        .findFirst().orElseThrow().fire();
                var collapsedSrc = fileTree.getRoot().getChildren().stream()
                        .filter(item -> src.equals(item.getValue()))
                        .findFirst().orElseThrow();
                var collapsedNested = collapsedSrc.getChildren().stream()
                        .filter(item -> nested.equals(item.getValue()))
                        .findFirst().orElseThrow();
                assertFalse(collapsedSrc.isExpanded(),
                        "Colapsar todas closes the selected branch itself");
                // Opening the branch again must not restore deep expansion.
                collapsedSrc.setExpanded(true);
                assertFalse(collapsedNested.isExpanded(),
                        "Nested directories should be recursively collapsed");
                assertTrue(collapsedSrc.getChildren().contains(collapsedNested),
                        "Immediate subfolders must remain visible");
                collapsedNested.setExpanded(true);
                var collapsedDeep = collapsedNested.getChildren().stream()
                        .filter(item -> nested.resolve("deep").equals(item.getValue()))
                        .findFirst().orElseThrow();
                assertFalse(collapsedDeep.isExpanded(),
                        "Reopening the first descendant must not restore deeper expansion");
                collapsedDeep.setExpanded(true);
                var collapsedLayer = collapsedDeep.getChildren().stream()
                        .filter(item -> deep.equals(item.getValue()))
                        .findFirst().orElseThrow();
                assertFalse(collapsedLayer.isExpanded(),
                        "All nested levels must stay collapsed, not just the first one");
                // User request: show ALL nested directories in one action.
                var expandedMenu = (ContextMenu) menuFor.invoke(fileTree, src);
                assertTrue(expandedMenu.getItems().stream().anyMatch(item ->
                        "Expandir todas las subcarpetas".equals(item.getText())));
                expandedMenu.getItems().stream()
                        .filter(item -> "Expandir todas las subcarpetas".equals(item.getText()))
                        .findFirst().orElseThrow().fire();
                assertTrue(collapsedSrc.isExpanded(), "Chosen folder stays visible");
                assertTrue(collapsedNested.isExpanded(), "First nested directory expanded");
                assertTrue(collapsedDeep.isExpanded(), "Grandchild directory expanded");
                assertTrue(collapsedLayer.isExpanded(), "Deep descendant expanded recursively");
                // Collapse a second time to check both commands are repeatable.
                fileTree.collapseRecursively(src);
                var againSrc = fileTree.getRoot().getChildren().stream()
                        .filter(item -> src.equals(item.getValue()))
                        .findFirst().orElseThrow();
                assertFalse(againSrc.isExpanded(),
                        "Second recursive collapse must close the branch itself");
                againSrc.setExpanded(true);
                assertFalse(againSrc.getChildren().stream()
                        .filter(item -> nested.equals(item.getValue()))
                        .findFirst().orElseThrow().isExpanded());
                assertTrue(Files.exists(nestedFile));
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
                assertTrue(createdFolderMenu.getItems().stream().anyMatch(item ->
                        "Excluir carpeta del progreso".equals(item.getText())));
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
                MenuButton filter = (MenuButton) stage.getScene().getRoot()
                        .lookup(".practice-rules-button");
                assertNotNull(filter);
                CustomMenuItem filterPanel = (CustomMenuItem) filter.getItems().getFirst();
                assertFalse(filterPanel.isHideOnClick(),
                        "Checking files must keep the menu open");
                VBox filterChoices = (VBox) filterPanel.getContent();
                assertTrue(filterChoices.getChildren().size() >= 8);
                assertTrue(filterChoices.getChildren().stream().anyMatch(n ->
                        n.getStyleClass().contains("turbo-toggle")));
                CheckBox all = (CheckBox) filterChoices.getChildren().get(1);
                all.fire();
                for (var child : filterChoices.getChildren().subList(2, 4)) {
                    assertTrue(((CheckBox) child).isSelected());
                }
                CheckBox markdown = (CheckBox) filterChoices.getChildren().get(3);
                markdown.fire();
                assertFalse(all.isSelected());
                all.fire();
                for (var child : filterChoices.getChildren().subList(2, 4)) {
                    assertTrue(((CheckBox) child).isSelected());
                }
                assertNotNull(stage.getScene().getRoot().lookup(".tree-activity"));
                assertNotNull(stage.getScene().getRoot().lookup(".tree-scope-checkbox"));
                Method focusFolder = CodeTraceApp.class.getDeclaredMethod(
                        "setProgressFolder", String.class);
                focusFolder.setAccessible(true);
                focusFolder.invoke(application, "src");
                assertTrue(auto != null);
                CheckBox only = (CheckBox) stage.getScene().getRoot()
                        .lookup(".tree-scope-checkbox");
                assertTrue(only.isSelected());
                assertEquals("src", fileTree.progressFocusFolder());
                assertFalse(fileTree.isDisabled(), "Outside folders remain navigable");
                var srcNode = fileTree.getRoot().getChildren().stream()
                        .filter(n -> n.getValue().equals(src)).findFirst().orElseThrow();
                var nestedNode = srcNode.getChildren().stream()
                        .filter(n -> n.getValue().equals(nested)).findFirst().orElseThrow();
                fileTree.getSelectionModel().select(nestedNode);
                assertEquals("src/ejemplos", fileTree.progressFocusFolder(),
                        "Selecting a different folder while Solo is on switches scope");
                fileTree.getSelectionModel().select(fileTree.getRoot());
                assertEquals("", fileTree.progressFocusFolder(),
                        "Selecting project root returns to whole-project progress");
                assertFalse(only.isSelected());

                Files.delete(imageFile);
                Files.delete(directFile);
                Files.delete(nestedFile);
                Files.delete(deep.resolve("Example.java"));
                Files.delete(deep);
                Files.delete(nested.resolve("deep"));
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
