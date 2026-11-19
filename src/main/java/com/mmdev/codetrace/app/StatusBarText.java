package com.mmdev.codetrace.app;

/**
 * Stable, contextual labels for the footer. Transient action notifications are
 * managed separately by the application and must not outlive the active view.
 */
public final class StatusBarText {
    private StatusBarText() {
    }

    public static String empty(boolean hasProject) {
        return hasProject ? "Proyecto listo · Abre un archivo" : "Listo";
    }

    public static String practice(String path) {
        return "Práctica: " + path;
    }

    public static String consultation(String path) {
        return "Consulta de solo lectura: " + path;
    }

    public static String preview(String path) {
        return "Vista previa · solo lectura: " + path;
    }

    public static String graph(String folder, String selectedNode) {
        return selectedNode == null ? "Mapa · " + folder : selectedNode;
    }
}
