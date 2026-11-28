// Tema:
// Una convolución con kernel uniforme 3x3 calcula el promedio de vecindad. Solo se procesa el píxel central para no introducir una política de bordes; el resultado no altera la fuente.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 Convolucion.java
// Ejecutar: java Convolucion

import java.awt.image.BufferedImage;

public class Convolucion {
    public static void main(String[] args) throws Exception {
        BufferedImage origen = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        origen.setRGB(1, 1, 0xff5a5a5a);
        int[][] kernel = {{1,1,1}, {1,1,1}, {1,1,1}};
        int suma = 0;
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++) suma += (origen.getRGB(x, y) & 255) * kernel[y][x];
        int gris = suma / 9;
        BufferedImage destino = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        destino.setRGB(0, 0, (gris << 16) | (gris << 8) | gris);
        System.out.println("Centro original: " + (origen.getRGB(1, 1) & 255));
        System.out.println("Filtrado: " + (destino.getRGB(0, 0) & 255));
    }
}

// Salida esperada:
// Centro original: 90
// Filtrado: 10
