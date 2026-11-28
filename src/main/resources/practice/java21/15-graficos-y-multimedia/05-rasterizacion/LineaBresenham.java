// Tema:
// Bresenham rasteriza una línea discreta con acumulador de error entero; permite todas las direcciones y pendientes. Incluye ambos extremos. Se muestran las coordenadas sin renderizar una ventana.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 LineaBresenham.java MainRaster.java
// Ejecutar: java MainRaster

import java.util.ArrayList;
import java.util.List;

public class LineaBresenham {
    public static List<String> puntos(int x, int y, int x2, int y2) {
        List<String> puntos = new ArrayList<>();
        int dx = Math.abs(x2 - x), dy = -Math.abs(y2 - y);
        int sx = x < x2 ? 1 : -1, sy = y < y2 ? 1 : -1;
        int error = dx + dy;
        while (true) {
            puntos.add(x + "," + y);
            if (x == x2 && y == y2) break;
            int doble = 2 * error;
            if (doble >= dy) { error += dy; x += sx; }
            if (doble <= dx) { error += dx; y += sy; }
        }
        return puntos;
    }
}

// Salida esperada:
// [0,0, 1,1, 2,1, 3,2, 4,2]
// [2,2, 2,1, 2,0]
