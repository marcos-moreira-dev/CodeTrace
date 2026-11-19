package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.GraphLayoutEngine;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Read-only internal dependency graph. Both automatic layout modes retain
 * free dragging, source navigation and session-only persistence.
 */
public final class DependencyGraphPane extends BorderPane {
    private static final double MAX_WIDTH = 6500;
    private static final double MAX_HEIGHT = 14000;
    private final Canvas canvas = new Canvas();
    private final Group artwork = new Group(canvas);
    private final Map<String, GraphLayout.Point> layout = new LinkedHashMap<>();
    private final Label info = new Label(
            "Clic: seleccionar · Arrastrar: mover nodo · Doble clic: abrir archivo");
    private final ProjectDependencyGraph.Graph model;
    private final Consumer<GraphLayout> onLayoutChanged;
    private final Consumer<String> onNodeSelected;
    private final IntegerProperty zoomPercent = new SimpleIntegerProperty(100);
    private GraphLayoutEngine.Mode layoutMode;
    private String selected;
    private String pressed;
    private GraphLayout.Point initialPosition;
    private double pressX;
    private double pressY;
    private boolean moved;

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource) {
        this(graph, openSource, null, ignored -> {}, ignored -> {});
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged) {
        this(graph, openSource, restored, onLayoutChanged, ignored -> {});
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged,
                               Consumer<String> onNodeSelected) {
        model = graph;
        this.onLayoutChanged = onLayoutChanged;
        this.onNodeSelected = onNodeSelected;
        getStyleClass().add("dependency-map-pane");

        boolean compatible = restored != null
                && restored.fingerprint().equals(graph.fingerprint());
        layoutMode = compatible ? restored.mode() : GraphLayoutEngine.Mode.GRID;
        layout.putAll(GraphLayoutEngine.arrange(graph, layoutMode));
        if (compatible) {
            for (var entry : restored.positions().entrySet()) {
                if (layout.containsKey(entry.getKey())) layout.put(entry.getKey(), entry.getValue());
            }
        }
        resizeCanvasToContent();
        redraw();

        canvas.setOnMousePressed(event -> {
            if (event.getButton() != MouseButton.PRIMARY) return;
            pressX = event.getX();
            pressY = event.getY();
            pressed = nearest(pressX, pressY);
            initialPosition = pressed == null ? null : layout.get(pressed);
            moved = false;
            if (pressed != null) event.consume();
        });
        canvas.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown() || pressed == null
                    || initialPosition == null) return;
            double dx = event.getX() - pressX;
            double dy = event.getY() - pressY;
            if (Math.abs(dx) + Math.abs(dy) <= 3 && !moved) return;
            moved = true;
            double x = Math.max(35, Math.min(MAX_WIDTH - 275,
                    initialPosition.x() + dx));
            double y = Math.max(35, Math.min(MAX_HEIGHT - 90,
                    initialPosition.y() + dy));
            // Extend the scrollable workspace as the user approaches an edge.
            extendCanvas(x + 340, y + 240);
            layout.put(pressed, new GraphLayout.Point(x, y));
            redraw();
            event.consume();
        });
        canvas.setOnMouseReleased(event -> {
            if (pressed != null && moved) {
                selected = pressed;
                info.setText(connectionCounts(selected));
                onNodeSelected.accept(selected);
                notifyLayoutChanged();
                redraw();
                event.consume();
            }
            pressed = null;
            initialPosition = null;
        });
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY || moved) {
                moved = false;
                return;
            }
            String picked = nearest(event.getX(), event.getY());
            if (picked == null) return;
            selected = picked;
            info.setText(connectionCounts(picked));
            onNodeSelected.accept(picked);
            redraw();
            if (event.getClickCount() == 2) openSource.accept(picked);
            event.consume();
        });

        ScrollPane scroll = new ScrollPane(artwork);
        scroll.setPannable(true);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(false);
        scroll.getStyleClass().add("dependency-map-scroll");
        zoomPercent.addListener((obs, old, value) -> {
            double factor = value.doubleValue() / 100.0;
            artwork.setScaleX(factor);
            artwork.setScaleY(factor);
        });
        scroll.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (!event.isControlDown() && !event.isShortcutDown()) return;
            setZoomPercent(getZoomPercent() + (event.getDeltaY() > 0 ? 10 : -10));
            event.consume();
        });

        Label heading = new Label("Dependencias internas · "
                + graph.nodes().size() + " nodos · "
                + graph.edges().size() + " conexiones");
        heading.getStyleClass().add("dependency-map-heading");
        HBox.setHgrow(heading, Priority.ALWAYS);
        Label layoutLabel = new Label("Distribución");
        layoutLabel.getStyleClass().add("dependency-map-mode-label");
        ExclusiveChoiceBar<GraphLayoutEngine.Mode> choices = new ExclusiveChoiceBar<>(
                List.of(
                        new ExclusiveChoiceBar.Choice<>(GraphLayoutEngine.Mode.GRID,
                                "Cuadrícula", "Distribución clásica, por filas y columnas"),
                        new ExclusiveChoiceBar.Choice<>(GraphLayoutEngine.Mode.ORGANIZED,
                                "Organizado",
                                "Agrupa conexiones, reduce cruces y evita superposición de nodos")),
                layoutMode, this::setLayoutMode);
        choices.getStyleClass().add("graph-layout-modes");
        Button reset = new Button("Restablecer");
        reset.setOnAction(event -> resetLayout());
        reset.getStyleClass().add("dependency-map-reset");
        reset.setTooltip(new javafx.scene.control.Tooltip(
                "Recuperar la distribución automática del modo seleccionado"));
        HBox toolbar = new HBox(10, heading, layoutLabel, choices, reset);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(10, 14, 10, 14));
        toolbar.getStyleClass().add("dependency-map-toolbar");
        setTop(toolbar);
        setCenter(scroll);

        info.setWrapText(false);
        info.getStyleClass().add("dependency-map-status");
        Label note = new Label(graph.truncated()
                ? "Vista parcial · se alcanzó el límite de análisis."
                : "Solo imports internos · Ctrl+rueda o control inferior para acercar.");
        note.getStyleClass().add("dependency-map-note");
        HBox footer = new HBox(12, info, note);
        HBox.setHgrow(info, Priority.ALWAYS);
        footer.setPadding(new Insets(8, 12, 8, 12));
        footer.getStyleClass().add("dependency-map-footer");
        setBottom(footer);
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

    public void setLayoutMode(GraphLayoutEngine.Mode mode) {
        if (mode == null || mode == layoutMode) return;
        layoutMode = mode;
        resetLayout();
    }

    private void resetLayout() {
        layout.clear();
        layout.putAll(GraphLayoutEngine.arrange(model, layoutMode));
        resizeCanvasToContent();
        notifyLayoutChanged();
        redraw();
    }

    private void notifyLayoutChanged() {
        onLayoutChanged.accept(new GraphLayout(model.fingerprint(), layout, layoutMode));
    }

    /** Fresh layouts and restored coordinates get enough free area to pan. */
    private void resizeCanvasToContent() {
        double right = layout.values().stream()
                .mapToDouble(GraphLayout.Point::x).max().orElse(1000);
        double bottom = layout.values().stream()
                .mapToDouble(GraphLayout.Point::y).max().orElse(500);
        double width = Math.max(2550, Math.min(MAX_WIDTH, right + 550));
        double height = Math.max(1050, Math.min(MAX_HEIGHT, bottom + 420));
        canvas.setWidth(width);
        canvas.setHeight(height);
    }

    private void extendCanvas(double right, double bottom) {
        if (right > canvas.getWidth() - 120 && canvas.getWidth() < MAX_WIDTH)
            canvas.setWidth(Math.min(MAX_WIDTH,
                    Math.max(canvas.getWidth() + 480, right + 180)));
        if (bottom > canvas.getHeight() - 100 && canvas.getHeight() < MAX_HEIGHT)
            canvas.setHeight(Math.min(MAX_HEIGHT,
                    Math.max(canvas.getHeight() + 400, bottom + 120)));
    }

    private String connectionCounts(String path) {
        long outgoing = model.edges().stream()
                .filter(e -> e.source().equals(path)).count();
        long incoming = model.edges().stream()
                .filter(e -> e.target().equals(path)).count();
        return outgoing + " salientes · " + incoming
                + " entrantes · Doble clic para abrir";
    }

    private String nearest(double x, double y) {
        String found = null;
        double best = 16 * 16;
        for (var entry : layout.entrySet()) {
            GraphLayout.Point at = entry.getValue();
            double dx = x - at.x(), dy = y - at.y();
            double distance = dx * dx + dy * dy;
            if (distance <= best) {
                best = distance;
                found = entry.getKey();
            }
        }
        return found;
    }

    private void redraw() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.web("#f8fafb"));
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphics.setLineWidth(1.2);
        for (ProjectDependencyGraph.Edge edge : model.edges()) {
            GraphLayout.Point source = layout.get(edge.source());
            GraphLayout.Point target = layout.get(edge.target());
            if (source == null || target == null) continue;
            boolean highlighted = edge.source().equals(selected)
                    || edge.target().equals(selected);
            graphics.setStroke(Color.web(highlighted ? "#43759f" : "#b7cbd8"));
            graphics.strokeLine(source.x(), source.y(), target.x(), target.y());
            double direction = Math.atan2(target.y() - source.y(), target.x() - source.x());
            double tipX = target.x() - 14 * Math.cos(direction);
            double tipY = target.y() - 14 * Math.sin(direction);
            graphics.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(direction - 0.55),
                    tipY - 7 * Math.sin(direction - 0.55));
            graphics.strokeLine(tipX, tipY,
                    tipX - 7 * Math.cos(direction + 0.55),
                    tipY - 7 * Math.sin(direction + 0.55));
        }
        for (String path : model.nodes()) {
            GraphLayout.Point point = layout.get(path);
            if (point == null) continue;
            boolean active = path.equals(selected);
            graphics.setFill(Color.web(active ? "#c36c31" : "#4b7593"));
            graphics.fillOval(point.x() - 10, point.y() - 10, 20, 20);
            graphics.setStroke(Color.WHITE);
            graphics.strokeOval(point.x() - 10, point.y() - 10, 20, 20);
            graphics.setFill(Color.web("#273f50"));
            graphics.setFont(Font.font("Segoe UI", active ? 13 : 12));
            String label = path.substring(path.lastIndexOf('/') + 1);
            if (label.length() > 36) label = label.substring(0, 33) + "...";
            graphics.fillText(label, point.x() + 17, point.y() + 4);
        }
    }
}
