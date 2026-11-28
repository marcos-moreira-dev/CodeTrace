// Tema:
// Promedio RGB como filtro didáctico (no luminancia perceptual). Se conserva alfa y se sustituyen los tres canales por el promedio.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 EscalaGrises.java
// Ejecutar: java EscalaGrises

import java.awt.image.BufferedImage;

public class EscalaGrises {
    public static void main(String[] args) throws Exception {
        BufferedImage imagen = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        imagen.setRGB(0, 0, 0xff1e3c5a);
        for (int y = 0; y < imagen.getHeight(); y++) {
            for (int x = 0; x < imagen.getWidth(); x++) {
                int pixel = imagen.getRGB(x, y);
                int gris = (((pixel >>> 16) & 255) + ((pixel >>> 8) & 255) + (pixel & 255)) / 3;
                imagen.setRGB(x, y, (pixel & 0xff000000) | (gris << 16) | (gris << 8) | gris);
            }
        }
        System.out.printf("%08x%n", imagen.getRGB(0, 0));
    }
}

// Salida esperada:
// ff3c3c3c
