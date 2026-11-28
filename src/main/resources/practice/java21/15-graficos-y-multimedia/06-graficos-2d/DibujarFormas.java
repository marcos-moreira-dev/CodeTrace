// Tema:
// Graphics2D dibuja sobre BufferedImage sin interfaz gráfica ni servidor de pantalla. Se libera el contexto con dispose; se verifican píxeles interiores, sin depender de suavizado ni fuentes instaladas.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 DibujarFormas.java MainGrafico.java
// Ejecutar: java MainGrafico

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DibujarFormas {
    public static BufferedImage crear() {
        BufferedImage imagen = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();
        try {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 8, 8);
            g.setColor(Color.RED);
            g.fillRect(2, 2, 4, 4);
        } finally { g.dispose(); }
        return imagen;
    }
}

// Salida esperada:
// Fondo: ffffff
// Centro: ff0000
