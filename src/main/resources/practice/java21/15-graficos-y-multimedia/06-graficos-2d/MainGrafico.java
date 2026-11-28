// Tema:
// Graphics2D dibuja sobre BufferedImage sin interfaz gráfica ni servidor de pantalla. Se libera el contexto con dispose; se verifican píxeles interiores, sin depender de suavizado ni fuentes instaladas.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 DibujarFormas.java MainGrafico.java
// Ejecutar: java MainGrafico

import java.awt.image.BufferedImage;

public class MainGrafico {
    public static void main(String[] args) throws Exception {
        BufferedImage imagen = DibujarFormas.crear();
        System.out.printf("Fondo: %06x%n", imagen.getRGB(0, 0) & 0xffffff);
        System.out.printf("Centro: %06x%n", imagen.getRGB(3, 3) & 0xffffff);
    }
}

// Salida esperada:
// Fondo: ffffff
// Centro: ff0000
