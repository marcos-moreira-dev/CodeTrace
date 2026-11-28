// Tema:
// BufferedImage almacena píxeles en memoria. Coordenadas x horizontal e y vertical, origen superior izquierdo; no se requiere una ventana.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 ImagenPixeles.java
// Ejecutar: java ImagenPixeles

import java.awt.image.BufferedImage;

public class ImagenPixeles {
    public static void main(String[] args) throws Exception {
        BufferedImage imagen = new BufferedImage(3, 2, BufferedImage.TYPE_INT_ARGB);
        imagen.setRGB(2, 1, 0xff123456);
        System.out.println("Dimensiones: " + imagen.getWidth() + "x" + imagen.getHeight());
        System.out.printf("Pixel: %08x%n", imagen.getRGB(2, 1));
    }
}

// Salida esperada:
// Dimensiones: 3x2
// Pixel: ff123456
