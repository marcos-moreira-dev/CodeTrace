package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Read-only dependency graph on a viewport-sized Canvas with a logical camera.
 * Unlike a giant scrollable bitmap, panning has no fixed edges, the graph is
 * centered on opening, and large drawings do not allocate huge Canvas buffers.
  * <p><strong>Engineering rationale.</strong> Keep the complete graph in model/world coordinates while drawing only a viewport-sized Canvas. A camera maps those coordinates to screen space for panning and zooming; the layout algorithm and persisted node positions remain independent of presentation scale. This is why very large diagrams do not need a correspondingly enormous bitmap.</p>
*/
public final class DependencyGraphPane extends BorderPane {
    private static final double NODE_RADIUS = 10;
    private static final double MAX_NODE_COORDINATE = 100_000;
    private final Pane viewport = new Pane();
    private final Canvas canvas = new Canvas();
    private final StackPane graphSurface = new StackPane();
    private final VBox inspector = new VBox(8);
    private final Label inspectorTitle = new Label();
    private final Label inspectorPath = new Label();
    private final ListView<String> inspectorFiles = new ListView<>();
    private final ListView<String> inspectorFolders = new ListView<>();
    private final GraphCamera camera = new GraphCamera();
    private final Map<String, GraphLayout.Point> layout = new LinkedHashMap<>();
    /** Recompute edge curves on layout changes, not zoom, pan or selection. */
    private Map<ProjectDependencyGraph.Edge, GraphEdgeRouting.Route> edgeRoutes;
    private final ProjectDependencyGraph.Graph model;
    private final Consumer<GraphLayout> onLayoutChanged;
    private final Consumer<String> onNodeSelected;
    private final Consumer<String> openSource;
    private final IntegerProperty zoomPercent = new SimpleIntegerProperty(100);
    private GraphLayoutEngine.Mode layoutMode;
    private String selected;
    private Set<String> selectedNeighbors = Set.of();
    private String pressed;
    private GraphLayout.Point initialPosition;
    private double pressX, pressY;
    private boolean panning, moved;
    private double panSceneX, panSceneY, panStartX, panStartY;
    private boolean firstFitPerformed;

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource) {
        this(graph, openSource, null, ignored -> {}, ignored -> {},
                GraphLayoutEngine.Mode.ORGANIZED);
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged) {
        this(graph, openSource, restored, onLayoutChanged, ignored -> {},
                GraphLayoutEngine.Mode.ORGANIZED);
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged,
                               Consumer<String> onNodeSelected) {
        this(graph, openSource, restored, onLayoutChanged, onNodeSelected,
                GraphLayoutEngine.Mode.ORGANIZED);
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged,
                               Consumer<String> onNodeSelected,
                               GraphLayoutEngine.Mode initialMode) {
        model = graph;
        this.openSource = openSource;
        this.onLayoutChanged = onLayoutChanged;
        this.onNodeSelected = onNodeSelected;
        layoutMode = initialMode == null
                ? GraphLayoutEngine.Mode.ORGANIZED : initialMode;
        getStyleClass().add("dependency-map-pane");

        layout.putAll(GraphLayoutEngine.arrange(graph, layoutMode));
        boolean compatible = restored != null
                && restored.fingerprint().equals(graph.fingerprint());
        boolean sameMode = compatible && restored.mode() == layoutMode;
        if (sameMode && !GraphLayoutEngine.hasOverlaps(restored.positions())) {
            for (var entry : restored.positions().entrySet()) {
                if (layout.containsKey(entry.getKey()))
                    layout.put(entry.getKey(), entry.getValue());
            }
        } else if (compatible) {
            // Old GRID sessions must not silently override a user's current
            // Organized preference; repair invalid overlap layouts as well.
            Platform.runLater(this::notifyLayoutChanged);
        }
        centerCameraOnGraph();

        viewport.getStyleClass().add("dependency-map-viewport");
        viewport.setMinSize(0, 0);
        viewport.getChildren().add(canvas);
        canvas.setManaged(false);
        canvas.widthProperty().bind(viewport.widthProperty());
        canvas.heightProperty().bind(viewport.heightProperty());
        canvas.widthProperty().addListener((obs, old, value) -> onViewportResized());
        canvas.heightProperty().addListener((obs, old, value) -> onViewportResized());
        graphSurface.getChildren().add(viewport);
        VBox legend = new VBox(5);
        legend.getStyleClass().add("dependency-map-legend");
        legend.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
        Label legendTitle = new Label("Leyenda");
        legendTitle.getStyleClass().add("dependency-map-legend-title");
        Label dependency = new Label("━━▶  Origen depende del destino");
        dependency.getStyleClass().add("dependency-map-legend-dependency");
        Label containment = new Label("┄┄┄   Contiene subcarpeta");
        legend.getChildren().addAll(legendTitle, dependency, containment);
        legend.setMouseTransparent(true);
        graphSurface.getChildren().add(legend);
        StackPane.setAlignment(legend, Pos.BOTTOM_LEFT);
        StackPane.setMargin(legend, new Insets(12));
        setCenter(graphSurface);
        initializeInspector();
        canvas.setCursor(Cursor.OPEN_HAND);
        canvas.setFocusTraversable(true);

        MenuItem reset = new MenuItem("Restablecer distribución");
        reset.setOnAction(event -> resetLayout());
        MenuItem fit = new MenuItem("Centrar y ajustar a ventana");
        fit.setOnAction(event -> fitToViewport());
        ContextMenu graphMenu = new ContextMenu(fit, reset);
        viewport.setOnContextMenuRequested(event -> graphMenu.show(
                viewport, event.getScreenX(), event.getScreenY()));

        canvas.setOnMouseMoved(event -> {
            if (!panning && pressed == null)
                canvas.setCursor(pickNode(event.getX(), event.getY()) == null
                        ? Cursor.OPEN_HAND : Cursor.HAND);
        });
        canvas.setOnMouseExited(event -> {
            if (!panning && pressed == null) canvas.setCursor(Cursor.DEFAULT);
        });
        canvas.setOnMousePressed(event -> {
            pressed = null;
            panning = false;
            moved = false;
            if (event.getButton() != MouseButton.PRIMARY
                    && event.getButton() != MouseButton.MIDDLE) return;
            canvas.requestFocus();
            String hit = event.getButton() == MouseButton.PRIMARY
                    ? pickNode(event.getX(), event.getY()) : null;
            if (hit != null) {
                pressed = hit;
                initialPosition = layout.get(hit);
                pressX = event.getX();
                pressY = event.getY();
            } else {
                panning = true;
                panSceneX = event.getSceneX();
                panSceneY = event.getSceneY();
                panStartX = camera.centerX();
                panStartY = camera.centerY();
            }
            canvas.setCursor(Cursor.CLOSED_HAND);
            event.consume();
        });
        canvas.setOnMouseDragged(event -> {
            if (pressed != null && initialPosition != null) {
                if (!event.isPrimaryButtonDown()) return;
                double dx = event.getX() - pressX;
                double dy = event.getY() - pressY;
                if (!moved && Math.abs(dx) + Math.abs(dy) < 3) return;
                moved = true;
                double x = clamp(initialPosition.x() + dx / camera.scale(),
                        -MAX_NODE_COORDINATE, MAX_NODE_COORDINATE);
                double y = clamp(initialPosition.y() + dy / camera.scale(),
                        -MAX_NODE_COORDINATE, MAX_NODE_COORDINATE);
                layout.put(pressed, new GraphLayout.Point(x, y));
                edgeRoutes = null;
                redraw();
                event.consume();
            } else if (panning) {
                camera.center(panStartX - (event.getSceneX() - panSceneX) / camera.scale(),
                        panStartY - (event.getSceneY() - panSceneY) / camera.scale());
                moved = true;
                redraw();
                event.consume();
            }
        });
        canvas.setOnMouseReleased(event -> {
            if (pressed != null && moved) {
                selectNode(pressed);
                notifyLayoutChanged();
                event.consume();
            }
            pressed = null;
            initialPosition = null;
            panning = false;
            canvas.setCursor(pickNode(event.getX(), event.getY()) == null
                    ? Cursor.OPEN_HAND : Cursor.HAND);
        });
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY || moved) {
                moved = false;
                return;
            }
            String hit = pickNode(event.getX(), event.getY());
            selectNode(hit);
            // Directory nodes are explored through the inspector, not opened as files.
            event.consume();
        });
        viewport.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown() && !event.isShortcutDown()) return;
            Point2D local = viewport.sceneToLocal(event.getSceneX(), event.getSceneY());
            zoomAt(getZoomPercent() + (event.getDeltaY() > 0 ? 10 : -10),
                    local.getX(), local.getY());
            event.consume();
        });
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearSelection();
            } else if (event.getCode() == KeyCode.HOME) {
                fitToViewport();
            } else if (event.getCode() == KeyCode.LEFT) {
                panByPixels(65, 0);
            } else if (event.getCode() == KeyCode.RIGHT) {
                panByPixels(-65, 0);
            } else if (event.getCode() == KeyCode.UP) {
                panByPixels(0, 65);
            } else if (event.getCode() == KeyCode.DOWN) {
                panByPixels(0, -65);
            } else return;
            event.consume();
        });
        redraw();
    }

    private void initializeInspector() {
        inspector.getStyleClass().add("dependency-map-inspector");
        inspector.setPrefWidth(265);
        inspector.setMinWidth(220);
        inspectorTitle.getStyleClass().add("dependency-map-inspector-title");
        inspectorTitle.setWrapText(true);
        inspectorPath.getStyleClass().add("dependency-map-inspector-path");
        inspectorPath.setWrapText(true);
        inspectorFiles.setPlaceholder(new Label("Sin archivos directos"));
        inspectorFolders.setPlaceholder(new Label("Sin subcarpetas visibles"));
        inspectorFiles.setCellFactory(list -> pathCell());
        inspectorFolders.setCellFactory(list -> pathCell());
        inspectorFiles.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                String file = inspectorFiles.getSelectionModel().getSelectedItem();
                if (file != null) openSource.accept(file);
            }
        });
        inspectorFolders.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                String folder = inspectorFolders.getSelectionModel().getSelectedItem();
                if (folder != null) {
                    selectNode(folder);
                    GraphLayout.Point at = layout.get(folder);
                    if (at != null) camera.center(at.x() + 120, at.y());
                    redraw();
                }
            }
        });
        inspectorFolders.setPrefHeight(115);
        inspectorFolders.setMaxHeight(165);
        VBox.setVgrow(inspectorFiles, Priority.ALWAYS);
        inspector.getChildren().addAll(inspectorTitle, inspectorPath,
                new Label("Archivos"), inspectorFiles,
                new Label("Subcarpetas"), inspectorFolders);
    }

    private static ListCell<String> pathCell() {
        return new ListCell<>() {
            @Override protected void updateItem(String path, boolean empty) {
                super.updateItem(path, empty);
                setText(empty || path == null ? null : shortName(path));
                setTooltip(empty || path == null ? null : new Tooltip(path));
            }
        };
    }

    private static String shortName(String path) {
        if (path.equals(".")) return "Raíz";
        return path.substring(path.lastIndexOf('/') + 1);
    }

    private void showInspector(String directory) {
        if (directory == null) {
            setRight(null);
            return;
        }
        inspectorTitle.setText(shortName(directory.equals(".") && !model.folder().isEmpty()
                ? model.folder() : directory));
        inspectorPath.setText(directory.equals(".") ? "Carpeta del proyecto" : directory);
        inspectorFiles.getItems().setAll(model.files(directory));
        inspectorFolders.getItems().setAll(model.subfolders(directory));
        setRight(inspector);
    }

    private void onViewportResized() {
        if (!firstFitPerformed && canvas.getWidth() >= 320
                && canvas.getHeight() >= 240) {
            firstFitPerformed = true;
            fitToViewport();
        } else {
            redraw();
        }
    }

    /**
     * Center on all nodes and choose an overview zoom when the workspace first
     * becomes visible. Users may then zoom in without losing the camera.
     */
    public void fitToViewport() {
        double w = canvas.getWidth(), h = canvas.getHeight();
        centerCameraOnGraph();
        if (w >= 120 && h >= 120 && !layout.isEmpty()) {
            double minX = layout.values().stream()
                    .mapToDouble(GraphLayout.Point::x).min().orElse(0);
            double maxX = layout.values().stream()
                    .mapToDouble(GraphLayout.Point::x).max().orElse(0);
            double minY = layout.values().stream()
                    .mapToDouble(GraphLayout.Point::y).min().orElse(0);
            double maxY = layout.values().stream()
                    .mapToDouble(GraphLayout.Point::y).max().orElse(0);
            double graphWidth = Math.max(250, maxX - minX + 270);
            double graphHeight = Math.max(110, maxY - minY + 90);
            double factor = Math.min((w - 90) / graphWidth,
                    (h - 90) / graphHeight);
            int percent = (int) clamp(Math.floor(factor * 100), 25, 100);
            camera.zoomAt(percent / 100.0, w / 2, h / 2, w, h);
            zoomPercent.set(percent);
        }
        redraw();
    }

    private void centerCameraOnGraph() {
        double minX = layout.values().stream().mapToDouble(GraphLayout.Point::x)
                .min().orElse(0);
        double maxX = layout.values().stream().mapToDouble(GraphLayout.Point::x)
                .max().orElse(0);
        double minY = layout.values().stream().mapToDouble(GraphLayout.Point::y)
                .min().orElse(0);
        double maxY = layout.values().stream().mapToDouble(GraphLayout.Point::y)
                .max().orElse(0);
        // Include filename labels to the right of each node.
        camera.center((minX + maxX + 235) / 2, (minY + maxY) / 2);
    }

    public int getZoomPercent() { return zoomPercent.get(); }
    public IntegerProperty zoomPercentProperty() { return zoomPercent; }

    /** The shared statusbar slider zooms around the visible viewport center. */
    public void setZoomPercent(int percent) {
        zoomAt(percent, canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    /** Ctrl+wheel keeps the map location below the cursor fixed on screen. */
    public void setZoomPercentAround(int percent, double sceneX, double sceneY) {
        Point2D local = viewport.getScene() == null
                ? new Point2D(canvas.getWidth() / 2, canvas.getHeight() / 2)
                : viewport.sceneToLocal(sceneX, sceneY);
        zoomAt(percent, local.getX(), local.getY());
    }

    private void zoomAt(int percent, double pixelX, double pixelY) {
        int value = (int) clamp(percent, 25, 400);
        if (zoomPercent.get() == value) return;
        camera.zoomAt(value / 100.0, pixelX, pixelY,
                canvas.getWidth(), canvas.getHeight());
        zoomPercent.set(value);
        redraw();
    }

    public GraphLayoutEngine.Mode getLayoutMode() { return layoutMode; }
    public String getSelectedNode() { return selected; }
    public Set<String> getSelectedNeighbors() { return selectedNeighbors; }
    public double getCanvasWidth() { return canvas.getWidth(); }
    public double getCanvasHeight() { return canvas.getHeight(); }
    public double getCameraX() { return camera.centerX(); }
    public double getCameraY() { return camera.centerY(); }
    public boolean hasUnboundedPan() { return true; }
    public boolean isPartial() { return model.truncated(); }
    public int nodeCount() { return model.nodes().size(); }
    public int connectionCount() { return model.edges().size(); }
    public String folder() { return model.folder(); }

    /** Useful for keyboard movement and repeatable viewport tests. */
    public void panByPixels(double dx, double dy) {
        camera.panPixels(dx, dy);
        redraw();
    }

    public String selectedNodeSummary() {
        if (selected == null) return nodeCount() + " carpetas · "
                + connectionCount() + " dependencias · "
                + model.hierarchy().size() + " subcarpetas";
        long outgoing = model.edges().stream()
                .filter(e -> e.source().equals(selected)).count();
        long incoming = model.edges().stream()
                .filter(e -> e.target().equals(selected)).count();
        return outgoing + " dependencias salientes · " + incoming
                + " entrantes · " + model.subfolders(selected).size() + " subcarpetas";
    }

    public void clearSelection() { selectNode(null); }

    private void selectNode(String path) {
        if (path != null && !layout.containsKey(path)) return;
        selected = path;
        if (path == null) {
            selectedNeighbors = Set.of();
        } else {
            Set<String> neighbors = new LinkedHashSet<>();
            for (var link : model.hierarchy()) {
                if (link.parent().equals(path)) neighbors.add(link.child());
                if (link.child().equals(path)) neighbors.add(link.parent());
            }
            for (var edge : model.edges()) {
                if (edge.source().equals(path) && !edge.target().equals(path))
                    neighbors.add(edge.target());
                if (edge.target().equals(path) && !edge.source().equals(path))
                    neighbors.add(edge.source());
            }
            selectedNeighbors = Set.copyOf(neighbors);
        }
        showInspector(path);
        onNodeSelected.accept(path);
        redraw();
    }

    public void setLayoutMode(GraphLayoutEngine.Mode mode) {
        if (mode == null || mode == layoutMode) return;
        layoutMode = mode;
        resetLayout();
    }

    public void resetLayout() {
        layout.clear();
        layout.putAll(GraphLayoutEngine.arrange(model, layoutMode));
        edgeRoutes = null;
        fitToViewport();
        notifyLayoutChanged();
    }

    private void notifyLayoutChanged() {
        onLayoutChanged.accept(new GraphLayout(model.fingerprint(), layout, layoutMode));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private String pickNode(double screenX, double screenY) {
        double x = camera.worldX(screenX, canvas.getWidth());
        double y = camera.worldY(screenY, canvas.getHeight());
        for (String path : model.nodes()) {
            GraphLayout.Point point = layout.get(path);
            if (point == null) continue;
            if (x >= point.x() - NODE_RADIUS - 2 && x <= point.x() + 258
                    && Math.abs(y - point.y()) <= 16)
                return path;
        }
        return null;
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth(), h = canvas.getHeight();
        g.setTransform(1, 0, 0, 1, 0, 0);
        g.setFill(Color.web("#f8fafb"));
        g.fillRect(0, 0, w, h);
        double zoom = camera.scale();
        g.setTransform(zoom, 0, 0, zoom,
                w / 2 - camera.centerX() * zoom,
                h / 2 - camera.centerY() * zoom);
        g.setLineWidth(1.15);
        // Dashed containment links are independent from directed code dependencies.
        g.setLineDashes(7, 6);
        for (var link : model.hierarchy()) {
            GraphLayout.Point a = layout.get(link.parent());
            GraphLayout.Point b = layout.get(link.child());
            if (a == null || b == null) continue;
            boolean related = selected != null && (selected.equals(link.parent())
                    || selected.equals(link.child()));
            g.setStroke(Color.web(related ? "#738796" : "#b0bdc7"));
            g.setLineWidth(related ? 1.8 : 1.25);
            g.strokeLine(a.x(), a.y(), b.x(), b.y());
        }
        g.setLineDashes();
        if (edgeRoutes == null) edgeRoutes = GraphEdgeRouting.arrange(model.edges(), layout);
        for (var edge : model.edges()) {
            GraphLayout.Point a = layout.get(edge.source());
            GraphLayout.Point b = layout.get(edge.target());
            if (a == null || b == null) continue;
            boolean related = selected != null && (edge.source().equals(selected)
                    || edge.target().equals(selected));
            g.setStroke(Color.web(related ? "#168a97" : "#c1d2dd"));
            g.setLineWidth(related ? 2.05 : 1.1);
            GraphEdgeRouting.Route route = edgeRoutes.get(edge);
            double tangentX = b.x() - a.x();
            double tangentY = b.y() - a.y();
            if (route != null && route.curved()) {
                // Deterministic subtle Bezier lanes separate overlapping
                // arrows, without noise in the user's saved node positions.
                g.beginPath();
                g.moveTo(a.x(), a.y());
                g.quadraticCurveTo(route.controlX(), route.controlY(), b.x(), b.y());
                g.stroke();
                tangentX = b.x() - route.controlX();
                tangentY = b.y() - route.controlY();
            } else {
                g.strokeLine(a.x(), a.y(), b.x(), b.y());
            }
            double angle = Math.atan2(tangentY, tangentX);
            double tipX = b.x() - NODE_RADIUS * Math.cos(angle);
            double tipY = b.y() - NODE_RADIUS * Math.sin(angle);
            g.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(angle - 0.5),
                    tipY - 7 * Math.sin(angle - 0.5));
            g.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(angle + 0.5),
                    tipY - 7 * Math.sin(angle + 0.5));
        }
        for (String path : model.nodes()) {
            GraphLayout.Point p = layout.get(path);
            if (p == null) continue;
            boolean active = path.equals(selected);
            boolean neighbor = selectedNeighbors.contains(path);
            String label = path.equals(".") && !model.folder().isEmpty()
                    ? shortName(model.folder()) : shortName(path);
            if (label.length() > 35) label = label.substring(0, 32) + "...";
            double labelWidth = Math.min(241, Math.max(70, label.length() * 6.5 + 12));
            g.setFill(Color.web(active ? "#ede9fb" : neighbor ? "#dff6f5" : "#f8fafb"));
            g.fillRect(p.x() + 14, p.y() - 13, labelWidth, 26);
            g.setFill(Color.web(active ? "#7254b5" : neighbor ? "#07919a" : "#4b7593"));
            g.fillOval(p.x() - NODE_RADIUS, p.y() - NODE_RADIUS,
                    NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.setStroke(Color.WHITE);
            g.strokeOval(p.x() - NODE_RADIUS, p.y() - NODE_RADIUS,
                    NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.setFill(Color.web(active ? "#423174"
                    : neighbor ? "#135e68" : "#263f51"));
            g.setFont(Font.font("Segoe UI", active ? 13 : 12));
            g.fillText(label, p.x() + 20, p.y() + 4, 226);
        }
        g.setTransform(1, 0, 0, 1, 0, 0);
    }
}
