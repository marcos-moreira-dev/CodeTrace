package com.mmdev.codetrace.ui;

import com.mmdev.codetrace.reference.ProjectDependencyGraph;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * One node per internal file (or imported Go package folder); edges are
 * confirmed imports, not guessed references. A double-click opens a file.
 */
public final class DependencyGraphPane extends BorderPane {
    private record Position(double x, double y) {}
    private final Canvas canvas;
    private final Group artwork;
    private final Map<String, Position> layout = new HashMap<>();
    private final Label info = new Label("Selecciona un círculo; doble clic para abrir el archivo.");
    private final ProjectDependencyGraph.Graph model;
    private String selected;
    private double previousX;
    private double previousY;

    public DependencyGraphPane(ProjectDependencyGraph.Graph graph,
                               Consumer<String> openSource) {
        model = graph;
        getStyleClass().add("dependency-map-pane");
        int count = graph.nodes().size();
        int rows = (count + 2) / 3;
        double width = 1450;
        double height = Math.max(640, rows * 94.0 + 90);
        canvas = new Canvas(width, height);
        artwork = new Group(canvas);
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) return;
            String picked = nearest(event.getX(), event.getY());
            if (picked == null) return;
            selected = picked;
            long out = graph.edges().stream().filter(e -> e.source().equals(picked)).count();
            long in = graph.edges().stream().filter(e -> e.target().equals(picked)).count();
            info.setText(picked + " · " + out + " salientes, " + in + " entrantes");
            redraw();
            if (event.getClickCount() == 2 && !picked.endsWith("/")) {
                openSource.accept(picked);
            }
        });
        for (int i = 0; i < count; i++) {
            String file = graph.nodes().get(i);
            int column = i % 3, row = i / 3;
            layout.put(file, new Position(65 + column * 465, 62 + row * 94));
        }
        redraw();
        ScrollPane scroll = new ScrollPane(artwork);
        scroll.setPannable(true);
        scroll.setFitToHeight(false);
        scroll.setFitToWidth(false);
        scroll.getStyleClass().add("dependency-map-scroll");
        Slider zoom = new Slider(50, 160, 100);
        zoom.setPrefWidth(142);
        Label percentage = new Label("100 %");
        zoom.valueProperty().addListener((obs, old, value) -> {
            double factor = value.doubleValue() / 100.0;
            artwork.setScaleX(factor);
            artwork.setScaleY(factor);
            percentage.setText(Math.round(factor * 100) + " %");
        });
        Button reset = new Button("100 %");
        reset.setOnAction(event -> zoom.setValue(100));
        Label heading = new Label("Dependencias internas · "
                + count + " nodos · " + graph.edges().size() + " conexiones");
        heading.getStyleClass().add("dependency-map-heading");
        HBox.setHgrow(heading, Priority.ALWAYS);
        HBox tools = new HBox(10, heading, new Label("Zoom"), zoom, percentage, reset);
        tools.setPadding(new Insets(10, 14, 10, 14));
        tools.setAlignment(Pos.CENTER_LEFT);
        tools.getStyleClass().add("dependency-map-toolbar");
        setTop(tools);
        setCenter(scroll);
        info.setWrapText(true);
        info.getStyleClass().add("dependency-map-status");
        Label note = new Label(graph.truncated()
                ? "Vista parcial: se alcanzó el límite de análisis."
                : "Se muestran solo imports resueltos dentro de la carpeta analizada.");
        note.getStyleClass().add("dependency-map-note");
        HBox bottom = new HBox(16, info, note);
        HBox.setHgrow(info, Priority.ALWAYS);
        bottom.setPadding(new Insets(8, 12, 8, 12));
        bottom.getStyleClass().add("dependency-map-footer");
        setBottom(bottom);
    }

    private String nearest(double x, double y) {
        String found = null;
        double best = 15 * 15;
        for (var entry : layout.entrySet()) {
            Position at = entry.getValue();
            double delta = Math.pow(x - at.x(), 2) + Math.pow(y - at.y(), 2);
            if (delta <= best) { best = delta; found = entry.getKey(); }
        }
        return found;
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#f8fafb"));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setStroke(Color.web("#cbd9e2"));
        g.setLineWidth(1.2);
        for (ProjectDependencyGraph.Edge edge : model.edges()) {
            Position src = layout.get(edge.source()), dst = layout.get(edge.target());
            if (src == null || dst == null) continue;
            g.setStroke(edge.source().equals(selected) || edge.target().equals(selected)
                    ? Color.web("#43759f") : Color.web("#bccdd9"));
            g.strokeLine(src.x(), src.y(), dst.x(), dst.y());
            double direction = Math.atan2(dst.y() - src.y(), dst.x() - src.x());
            double tipX = dst.x() - 14 * Math.cos(direction);
            double tipY = dst.y() - 14 * Math.sin(direction);
            double arrow = 7;
            g.strokeLine(tipX, tipY,
                    tipX - arrow * Math.cos(direction - 0.55),
                    tipY - arrow * Math.sin(direction - 0.55));
            g.strokeLine(tipX, tipY,
                    tipX - arrow * Math.cos(direction + 0.55),
                    tipY - arrow * Math.sin(direction + 0.55));
        }
        for (var entry : layout.entrySet()) {
            String path = entry.getKey();
            Position at = entry.getValue();
            boolean active = path.equals(selected);
            g.setFill(Color.web(active ? "#c36c31" : "#4b7593"));
            g.fillOval(at.x() - 9, at.y() - 9, 18, 18);
            g.setStroke(Color.WHITE);
            g.strokeOval(at.x() - 9, at.y() - 9, 18, 18);
            g.setFill(Color.web("#273f50"));
            g.setFont(Font.font("Segoe UI", active ? 13 : 12));
            String label = path.substring(path.lastIndexOf('/') + 1);
            if (label.length() > 32) label = label.substring(0, 29) + "...";
            g.fillText(label, at.x() + 14, at.y() + 4);
        }
    }
}
