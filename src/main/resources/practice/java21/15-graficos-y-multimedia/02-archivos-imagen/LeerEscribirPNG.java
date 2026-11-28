// Tema:
// PNG conserva píxeles sin pérdida. ImageIO escribe y vuelve a leer una imagen generada en memoria; se comprueba el resultado y se elimina el archivo propio.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 LeerEscribirPNG.java
// Ejecutar: java LeerEscribirPNG

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.file.*;
import java.io.IOException;

public class LeerEscribirPNG {
    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile("imagen-", ".png");
        try {
            BufferedImage original = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
            original.setRGB(1, 1, 0xffaa5500);
            if (!ImageIO.write(original, "png", archivo.toFile())) throw new IOException("Sin escritor PNG");
            BufferedImage leida = ImageIO.read(archivo.toFile());
            if (leida == null) throw new IOException("Sin lector");
            System.out.println("Dimensiones: " + leida.getWidth() + "x" + leida.getHeight());
            System.out.println("Pixel igual: " + (original.getRGB(1, 1) == leida.getRGB(1, 1)));
        } finally { Files.deleteIfExists(archivo); }
    }
}

// Salida esperada:
// Dimensiones: 2x2
// Pixel igual: true
