package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * Interactive, read-only dependency map. Node dragging changes only the
 * saved session layout; links remain derived exclusively from the reference.
 */
public final class DependencyGraphPane extends BorderPane {
    private final Canvas canvas;
    private final Group artwork;
    private final Map<String, GraphLayout.Point> layout = new LinkedHashMap<>();
    private final Label info = new Label("Arrastra un nodo para moverlo; doble clic para abrir el archivo.");
    private final ProjectDependencyGraph.Graph model;
    private final Consumer<GraphLayout> onLayoutChanged;
    private final IntegerProperty zoomPercent = new SimpleIntegerProperty(100);
    private String selected;
    private String pressed;
    private GraphLayout.Point initialPosition;
    private double pressX;
    private double pressY;
    private boolean moved;

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource) {
        this(graph, openSource, null, ignored -> {});
    }

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource, GraphLayout restored,
                               Consumer<GraphLayout> onLayoutChanged) {
        model = graph;
        this.onLayoutChanged = onLayoutChanged;
        getStyleClass().add("dependency-map-pane");
        int count = graph.nodes().size();
        int rows = (count + 3) / 4;
        double width = 1710;
        double height = Math.max(690, rows * 148.0 + 125);
        canvas = new Canvas(width, height);
        artwork = new Group(canvas);
        for (int i = 0; i < count; i++) {
            String file = graph.nodes().get(i);
            int column = i % 4, row = i / 4;
            layout.put(file, new GraphLayout.Point(80 + column * 415,
                    85 + row * 148));
        }
        if (restored != null && restored.fingerprint().equals(graph.fingerprint())) {
            for (var entry : restored.positions().entrySet()) {
                if (layout.containsKey(entry.getKey())) {
                    GraphLayout.Point point = entry.getValue();
                    layout.put(entry.getKey(), new GraphLayout.Point(
                            Math.max(15, Math.min(width - 15, point.x())),
                            Math.max(20, Math.min(height - 20, point.y()))));
                }
            }
        }
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
            layout.put(pressed, new GraphLayout.Point(
                    Math.max(15, Math.min(width - 15, initialPosition.x() + dx)),
                    Math.max(20, Math.min(height - 20, initialPosition.y() + dy))));
            redraw();
            event.consume();
        });
        canvas.setOnMouseReleased(event -> {
            if (pressed != null && moved) {
                selected = pressed;
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
            long out = graph.edges().stream().filter(e -> e.source().equals(picked)).count();
            long in = graph.edges().stream().filter(e -> e.target().equals(picked)).count();
            info.setText(picked + " · " + out + " salientes, " + in + " entrantes");
            redraw();
            if (event.getClickCount() == 2) openSource.accept(picked);
            event.consume();
        });
        ScrollPane scroll = new ScrollPane(artwork);
        scroll.setPannable(true);
        scroll.getStyleClass().add("dependency-map-scroll");
        zoomPercent.addListener((obs, old, value) -> {
            double factor = value.doubleValue() / 100.0;
            artwork.setScaleX(factor);
            artwork.setScaleY(factor);
        });
        canvas.setOnScroll(event -> {
            if (!event.isControlDown() && !event.isShortcutDown()) return;
            setZoomPercent(getZoomPercent() + (event.getDeltaY() > 0 ? 10 : -10));
            event.consume();
        });

        Label heading = new Label("Dependencias internas · "
                + count + " nodos · " + graph.edges().size() + " conexiones");
        heading.getStyleClass().add("dependency-map-heading");
        HBox.setHgrow(heading, Priority.ALWAYS);
        Button reset = new Button("Restablecer distribución");
        reset.setOnAction(event -> resetLayout());
        reset.getStyleClass().add("dependency-map-reset");
        reset.setTooltip(new javafx.scene.control.Tooltip(
                "Volver a la distribución automática de esta carpeta"));
        HBox toolbar = new HBox(12, heading, reset);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(10, 14, 10, 14));
        toolbar.getStyleClass().add("dependency-map-toolbar");
        setTop(toolbar);
        setCenter(scroll);
        info.setWrapText(true);
        info.getStyleClass().add("dependency-map-status");
        Label note = new Label(graph.truncated()
                ? "Vista parcial: se alcanzó el límite de análisis."
                : "Solo imports internos verificados. Zoom con Ctrl+rueda o control inferior.");
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

    private void resetLayout() {
        for (int i = 0; i < model.nodes().size(); i++) {
            String file = model.nodes().get(i);
            layout.put(file, new GraphLayout.Point(80 + (i % 4) * 415,
                    85 + (i / 4) * 148));
        }
        notifyLayoutChanged();
        redraw();
    }

    private void notifyLayoutChanged() {
        onLayoutChanged.accept(new GraphLayout(model.fingerprint(), layout));
    }

    private String nearest(double x, double y) {
        String found = null;
        double best = 15 * 15;
        for (var entry : layout.entrySet()) {
            GraphLayout.Point at = entry.getValue();
            double delta = Math.pow(x - at.x(), 2) + Math.pow(y - at.y(), 2);
            if (delta <= best) {
                best = delta;
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
            graphics.setStroke(edge.source().equals(selected) || edge.target().equals(selected)
                    ? Color.web("#43759f") : Color.web("#b7cbd8"));
            graphics.strokeLine(source.x(), source.y(), target.x(), target.y());
            double direction = Math.atan2(target.y() - source.y(), target.x() - source.x());
            double tipX = target.x() - 14 * Math.cos(direction);
            double tipY = target.y() - 14 * Math.sin(direction);
            double arrow = 7;
            graphics.strokeLine(tipX, tipY,
                    tipX - arrow * Math.cos(direction - 0.55),
                    tipY - arrow * Math.sin(direction - 0.55));
            graphics.strokeLine(tipX, tipY,
                    tipX - arrow * Math.cos(direction + 0.55),
                    tipY - arrow * Math.sin(direction + 0.55));
        }
        for (var entry : layout.entrySet()) {
            String path = entry.getKey();
            GraphLayout.Point point = entry.getValue();
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
