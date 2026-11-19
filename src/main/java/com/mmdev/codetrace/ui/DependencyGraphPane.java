package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Quiet graph workspace. Context lives in the application's statusbar,
 * layout choice in Preferences, and node positions in .ctrace sessions.
 *
 * Critically, ScrollPane native pannable mode and resizing DURING a drag are
 * disabled: either one can reposition the viewport while dragging a node.
 */
public final class DependencyGraphPane extends BorderPane {
    private static final double MAX_WIDTH = 5900;
    private static final double MAX_HEIGHT = 9200;
    private static final double NODE_RADIUS = 10;
    private final Canvas canvas = new Canvas();
    private final Group artwork = new Group(canvas);
    private final ScrollPane scroll = new ScrollPane(artwork);
    private final Map<String, GraphLayout.Point> layout = new LinkedHashMap<>();
    private final ProjectDependencyGraph.Graph model;
    private final Consumer<GraphLayout> onLayoutChanged;
    private final Consumer<String> onNodeSelected;
    private final Consumer<String> openSource;
    private final IntegerProperty zoomPercent = new SimpleIntegerProperty(100);
    private GraphLayoutEngine.Mode layoutMode;
    private String selected;
    private String pressed;
    private GraphLayout.Point initialPosition;
    private double pressX, pressY;
    private boolean panning, moved;
    private double panSceneX, panSceneY, panStartH, panStartV;

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
        this.model = graph;
        this.openSource = openSource;
        this.onLayoutChanged = onLayoutChanged;
        this.onNodeSelected = onNodeSelected;
        getStyleClass().add("dependency-map-pane");

        boolean compatible = restored != null
                && restored.fingerprint().equals(graph.fingerprint());
        layoutMode = compatible ? restored.mode() : initialMode;
        layout.putAll(GraphLayoutEngine.arrange(graph, layoutMode));
        if (compatible && !GraphLayoutEngine.hasOverlaps(restored.positions())) {
            for (var item : restored.positions().entrySet())
                if (layout.containsKey(item.getKey()))
                    layout.put(item.getKey(), item.getValue());
        } else if (compatible && layoutMode == GraphLayoutEngine.Mode.ORGANIZED) {
            // Older organized implementations could save overlapping nodes.
            // Rebuild a legible layout and make that repair persist on Save.
            Platform.runLater(this::notifyLayoutChanged);
        }
        sizeCanvasForLayout();
        redraw();

        scroll.setPannable(false);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(false);
        scroll.getStyleClass().add("dependency-map-scroll");
        setCenter(scroll);

        canvas.setOnMousePressed(event -> {
            pressed = null;
            panning = false;
            moved = false;
            if (event.getButton() != MouseButton.PRIMARY
                    && event.getButton() != MouseButton.MIDDLE) return;
            String hit = event.getButton() == MouseButton.PRIMARY
                    ? pickNode(event.getX(), event.getY()) : null;
            if (hit != null) {
                pressed = hit;
                initialPosition = layout.get(hit);
                pressX = event.getX();
                pressY = event.getY();
            } else {
                // Pan deliberately on empty background (left or middle button).
                panning = true;
                panSceneX = event.getSceneX();
                panSceneY = event.getSceneY();
                panStartH = scroll.getHvalue();
                panStartV = scroll.getVvalue();
            }
            event.consume();
        });
        canvas.setOnMouseDragged(event -> {
            if (pressed != null && initialPosition != null) {
                if (!event.isPrimaryButtonDown()) return;
                double dx = event.getX() - pressX, dy = event.getY() - pressY;
                if (!moved && Math.abs(dx) + Math.abs(dy) < 3) return;
                moved = true;
                // Never resize the Canvas here: doing so reanchors ScrollPane
                // and makes a dragged node appear to jump to the upper-left.
                double newX = clamp(initialPosition.x() + dx, 35, MAX_WIDTH - 290);
                double newY = clamp(initialPosition.y() + dy, 35, MAX_HEIGHT - 110);
                layout.put(pressed, new GraphLayout.Point(newX, newY));
                redraw();
                event.consume();
            } else if (panning) {
                double zoom = zoomPercent.get() / 100.0;
                double horizontal = Math.max(1,
                        canvas.getWidth() * zoom - scroll.getViewportBounds().getWidth());
                double vertical = Math.max(1,
                        canvas.getHeight() * zoom - scroll.getViewportBounds().getHeight());
                scroll.setHvalue(clamp(panStartH
                        - (event.getSceneX() - panSceneX) / horizontal, 0, 1));
                scroll.setVvalue(clamp(panStartV
                        - (event.getSceneY() - panSceneY) / vertical, 0, 1));
                moved = true;
                event.consume();
            }
        });
        canvas.setOnMouseReleased(event -> {
            if (pressed != null && moved) {
                selected = pressed;
                onNodeSelected.accept(selected);
                notifyLayoutChanged();
                // Growth happens AFTER release, retaining absolute scroll
                // offsets; it never interferes with the drag gesture.
                GraphLayout.Point at = layout.get(selected);
                extendCanvasKeepingViewport(at.x() + 510, at.y() + 360);
                redraw();
                event.consume();
            }
            pressed = null;
            initialPosition = null;
            panning = false;
        });
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY || moved) {
                moved = false;
                return;
            }
            String node = pickNode(event.getX(), event.getY());
            if (node == null) return;
            selected = node;
            onNodeSelected.accept(node);
            redraw();
            if (event.getClickCount() == 2) openSource.accept(node);
            event.consume();
        });
        scroll.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown() && !event.isShortcutDown()) return;
            setZoomPercent(getZoomPercent() + (event.getDeltaY() > 0 ? 10 : -10));
            event.consume();
        });
        zoomPercent.addListener((obs, old, value) -> {
            double factor = value.doubleValue() / 100.0;
            artwork.setScaleX(factor);
            artwork.setScaleY(factor);
        });
    }

    public int getZoomPercent() { return zoomPercent.get(); }
    public IntegerProperty zoomPercentProperty() { return zoomPercent; }
    public void setZoomPercent(int percent) {
        zoomPercent.set(Math.max(25, Math.min(400, percent)));
    }
    public GraphLayoutEngine.Mode getLayoutMode() { return layoutMode; }
    public String getSelectedNode() { return selected; }
    public double getCanvasWidth() { return canvas.getWidth(); }
    public double getCanvasHeight() { return canvas.getHeight(); }
    public boolean isPannableByScrollPane() { return scroll.isPannable(); }
    public int nodeCount() { return model.nodes().size(); }
    public int connectionCount() { return model.edges().size(); }
    public String folder() { return model.folder(); }

    public String selectedNodeSummary() {
        if (selected == null) return nodeCount() + " nodos · "
                + connectionCount() + " conexiones";
        long outgoing = model.edges().stream()
                .filter(e -> e.source().equals(selected)).count();
        long incoming = model.edges().stream()
                .filter(e -> e.target().equals(selected)).count();
        return outgoing + " salientes · " + incoming + " entrantes";
    }

    public void setLayoutMode(GraphLayoutEngine.Mode mode) {
        if (mode == null || mode == layoutMode) return;
        layoutMode = mode;
        resetLayout();
    }

    public void resetLayout() {
        layout.clear();
        layout.putAll(GraphLayoutEngine.arrange(model, layoutMode));
        sizeCanvasForLayout();
        redraw();
        notifyLayoutChanged();
    }

    private void notifyLayoutChanged() {
        onLayoutChanged.accept(new GraphLayout(model.fingerprint(), layout, layoutMode));
    }

    private void sizeCanvasForLayout() {
        double right = layout.values().stream()
                .mapToDouble(GraphLayout.Point::x).max().orElse(900);
        double bottom = layout.values().stream()
                .mapToDouble(GraphLayout.Point::y).max().orElse(550);
        canvas.setWidth(Math.min(MAX_WIDTH, Math.max(2700, right + 550)));
        canvas.setHeight(Math.min(MAX_HEIGHT, Math.max(1500, bottom + 440)));
    }

    private void extendCanvasKeepingViewport(double right, double bottom) {
        double oldWidth = canvas.getWidth(), oldHeight = canvas.getHeight();
        double nextWidth = right > oldWidth - 160
                ? Math.min(MAX_WIDTH, Math.max(oldWidth + 460, right + 200)) : oldWidth;
        double nextHeight = bottom > oldHeight - 130
                ? Math.min(MAX_HEIGHT, Math.max(oldHeight + 380, bottom + 170)) : oldHeight;
        if (nextWidth == oldWidth && nextHeight == oldHeight) return;
        double zoom = zoomPercent.get() / 100.0;
        double viewportWidth = scroll.getViewportBounds().getWidth();
        double viewportHeight = scroll.getViewportBounds().getHeight();
        double pixelLeft = scroll.getHvalue() * Math.max(0, oldWidth * zoom - viewportWidth);
        double pixelTop = scroll.getVvalue() * Math.max(0, oldHeight * zoom - viewportHeight);
        canvas.setWidth(nextWidth);
        canvas.setHeight(nextHeight);
        Platform.runLater(() -> {
            double newHorizontal = Math.max(1, nextWidth * zoom
                    - scroll.getViewportBounds().getWidth());
            double newVertical = Math.max(1, nextHeight * zoom
                    - scroll.getViewportBounds().getHeight());
            scroll.setHvalue(clamp(pixelLeft / newHorizontal, 0, 1));
            scroll.setVvalue(clamp(pixelTop / newVertical, 0, 1));
        });
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private String pickNode(double x, double y) {
        for (String path : model.nodes()) {
            GraphLayout.Point point = layout.get(path);
            if (point == null) continue;
            // Hit targets include the filename, not only the tiny circle.
            if (x >= point.x() - NODE_RADIUS - 2 && x <= point.x() + 258
                    && Math.abs(y - point.y()) <= 16)
                return path;
        }
        return null;
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#f8fafb"));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setLineWidth(1.15);
        for (var edge : model.edges()) {
            GraphLayout.Point a = layout.get(edge.source());
            GraphLayout.Point b = layout.get(edge.target());
            if (a == null || b == null) continue;
            boolean related = edge.source().equals(selected) || edge.target().equals(selected);
            g.setStroke(Color.web(related ? "#477c9f" : "#c1d2dd"));
            g.strokeLine(a.x(), a.y(), b.x(), b.y());
            double angle = Math.atan2(b.y() - a.y(), b.x() - a.x());
            double tipX = b.x() - NODE_RADIUS * Math.cos(angle);
            double tipY = b.y() - NODE_RADIUS * Math.sin(angle);
            g.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(angle - 0.50),
                    tipY - 7 * Math.sin(angle - 0.50));
            g.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(angle + 0.50),
                    tipY - 7 * Math.sin(angle + 0.50));
        }
        // Draw labels last on opaque backing so crisscrossing edges never
        // obscure their text, even in graphs with high-degree hubs.
        for (String path : model.nodes()) {
            GraphLayout.Point p = layout.get(path);
            if (p == null) continue;
            boolean active = path.equals(selected);
            String label = path.substring(path.lastIndexOf('/') + 1);
            if (label.length() > 35) label = label.substring(0, 32) + "...";
            double labelWidth = Math.min(241, Math.max(70, label.length() * 6.5 + 12));
            g.setFill(Color.web(active ? "#e8f0f6" : "#f8fafb"));
            g.fillRect(p.x() + 14, p.y() - 13, labelWidth, 26);
            g.setFill(Color.web(active ? "#c36c31" : "#4b7593"));
            g.fillOval(p.x() - NODE_RADIUS, p.y() - NODE_RADIUS, NODE_RADIUS * 2,
                    NODE_RADIUS * 2);
            g.setStroke(Color.WHITE);
            g.strokeOval(p.x() - NODE_RADIUS, p.y() - NODE_RADIUS,
                    NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.setFill(Color.web("#263f51"));
            g.setFont(Font.font("Segoe UI", active ? 13 : 12));
            g.fillText(label, p.x() + 20, p.y() + 4);
        }
    }
}
