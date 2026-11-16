package com.mmdev.codetrace.app;

import com.mmdev.codetrace.reference.ReferenceFingerprint;
import com.mmdev.codetrace.reference.TextPreview;
import com.mmdev.codetrace.ui.ReferenceTree;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * First real product slice: open and browse a read-only reference.
 * Transcription, virtual files and sessions are subsequent milestones.
 */
public final class CodeTraceApp extends Application {
    private final TabPane tabs = new TabPane();
    private final Map<Path, Tab> opened = new HashMap<>();
    private final Label status = new Label("Sin referencia abierta");
    private final ReferenceTree tree = new ReferenceTree(this::preview);
    private final VBox sidebar = new VBox(new Label("Proyecto"), tree);
    private final SplitPane split = new SplitPane();
    private final Button refresh = new Button("Actualizar");
    private Stage stage;
    private Path reference;

    @Override
    public void start(Stage window) {
        stage = window;
        BorderPane root = new BorderPane();
        MenuItem open = new MenuItem("Abrir referencia...");
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        open.setOnAction(event -> chooseFolder());
        MenuItem quit = new MenuItem("Salir");
        quit.setOnAction(event -> stage.close());
        Menu fileMenu = new Menu("Archivo");
        fileMenu.getItems().addAll(open, quit);

        CheckMenuItem explorer = new CheckMenuItem("Explorador");
        explorer.setSelected(true);
        explorer.selectedProperty().addListener((obs, before, visible) -> {
            if (visible && !split.getItems().contains(sidebar)) {
                split.getItems().add(0, sidebar);
                split.setDividerPositions(0.26);
            } else if (!visible) {
                split.getItems().remove(sidebar);
            }
        });
        Menu viewMenu = new Menu("Ver", null, explorer);
        MenuItem about = new MenuItem("Acerca de CodeTrace");
        about.setOnAction(event -> showAbout());
        Menu helpMenu = new Menu("Ayuda", null, about);

        Button openButton = new Button("Abrir referencia");
        openButton.setOnAction(event -> chooseFolder());
        refresh.setDisable(true);
        refresh.setOnAction(event -> refreshFolder());
        root.setTop(new VBox(new MenuBar(fileMenu, viewMenu, helpMenu),
                new ToolBar(openButton, refresh)));

        sidebar.getStyleClass().add("sidebar");
        sidebar.setMinWidth(155);
        sidebar.getChildren().get(0).getStyleClass().add("sidebar-title");
        VBox.setVgrow(tree, javafx.scene.layout.Priority.ALWAYS);
        split.getItems().addAll(sidebar, tabs);
        split.setDividerPositions(0.26);
        root.setCenter(split);

        HBox footer = new HBox(status);
        footer.setPadding(new Insets(5, 10, 5, 10));
        footer.getStyleClass().add("statusbar");
        root.setBottom(footer);
        Scene scene = new Scene(root, 1100, 720);
        scene.getStylesheets().add(getClass()
                .getResource("/css/codetrace.css").toExternalForm());
        stage.setTitle("CodeTrace");
        stage.setMinWidth(760);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.show();
    }

    private void chooseFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccionar carpeta de referencia");
        if (reference != null && Files.isDirectory(reference)) {
            chooser.setInitialDirectory(reference.toFile());
        }
        java.io.File chosen = chooser.showDialog(stage);
        if (chosen != null) {
            reference = chosen.toPath().toAbsolutePath().normalize();
            refresh.setDisable(false);
            refreshFolder();
            stage.setTitle("CodeTrace — " + reference.getFileName());
        }
    }

    private void refreshFolder() {
        if (reference == null) {
            return;
        }
        if (!Files.isDirectory(reference)) {
            status.setText("Carpeta de referencia no encontrada: " + reference);
            return;
        }
        tree.open(reference);
        tabs.getTabs().clear();
        opened.clear();
        status.setText("Referencia: " + reference);
    }

    private void preview(Path file) {
        Tab previous = opened.get(file);
        if (previous != null) {
            tabs.getSelectionModel().select(previous);
            return;
        }
        try {
            TextPreview.Result data = TextPreview.load(file);
            if (!data.readable()) {
                status.setText(data.reason() + " " + file.getFileName());
                return;
            }
            TextArea area = new TextArea(data.content());
            area.setEditable(false);
            area.setWrapText(false);
            area.getStyleClass().add("source-preview");
            Tab tab = new Tab(file.getFileName().toString(), area);
            tab.setOnClosed(event -> opened.remove(file));
            tabs.getTabs().add(tab);
            opened.put(file, tab);
            tabs.getSelectionModel().select(tab);
            String hash = ReferenceFingerprint.sha256(file);
            status.setText("Solo lectura · " + reference.relativize(file)
                    + " · SHA-256 " + hash.substring(0, 12));
        } catch (IOException | SecurityException error) {
            status.setText("No se pudo abrir " + file.getFileName());
        }
    }

    private void showAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.initOwner(stage);
        about.setTitle("Acerca de CodeTrace");
        about.setHeaderText("CodeTrace · 0.1.0");
        about.setContentText("Herramienta de escritorio para practicar la reconstrucción "
                + "manual de proyectos de software.\n\n"
                + "Java 21 · JavaFX 21\n"
                + "Estado actual: navegador de referencias de solo lectura.");
        about.showAndWait();
    }
}
