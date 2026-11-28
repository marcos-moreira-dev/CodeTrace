// Tema:
// Un filtro de umbral convierte niveles de gris en negro o blanco. Se usa una imagen en memoria y un umbral inclusivo de 128.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 Umbral.java
// Ejecutar: java Umbral

import java.awt.image.BufferedImage;

public class Umbral {
    public static void main(String[] args) throws Exception {
        BufferedImage imagen = new BufferedImage(3, 1, BufferedImage.TYPE_INT_RGB);
        int[] grises = {100, 128, 200};
        for (int x = 0; x < grises.length; x++) {
            int g = grises[x];
            imagen.setRGB(x, 0, (g << 16) | (g << 8) | g);
        }
        for (int x = 0; x < grises.length; x++) {
            int g = imagen.getRGB(x, 0) & 255;
            int binario = g >= 128 ? 255 : 0;
            imagen.setRGB(x, 0, (binario << 16) | (binario << 8) | binario);
            System.out.println(imagen.getRGB(x, 0) & 255);
        }
    }
}

// Salida esperada:
// 0
// 255
// 255
