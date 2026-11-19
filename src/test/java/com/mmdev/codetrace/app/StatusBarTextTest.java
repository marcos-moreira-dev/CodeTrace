package com.mmdev.codetrace.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StatusBarTextTest {
    @Test void emptyWorkspaceNeverKeepsClosedFileName() {
        assertEquals("Listo", StatusBarText.empty(false));
        assertEquals("Proyecto listo · Abre un archivo", StatusBarText.empty(true));
    }

    @Test void selectedDocumentContextIsReconstructedFromCurrentTab() {
        assertEquals("Práctica: src/Persona.java",
                StatusBarText.practice("src/Persona.java"));
        assertEquals("Consulta de solo lectura: pyproject.toml",
                StatusBarText.consultation("pyproject.toml"));
        assertEquals("Vista previa · solo lectura: video.mkv",
                StatusBarText.preview("video.mkv"));
    }

    @Test void graphTracksTheCurrentNodeInsteadOfTheLastSelection() {
        assertEquals("Mapa · src", StatusBarText.graph("src", null));
        assertEquals("src/Persona.java",
                StatusBarText.graph("src", "src/Persona.java"));
    }
}
