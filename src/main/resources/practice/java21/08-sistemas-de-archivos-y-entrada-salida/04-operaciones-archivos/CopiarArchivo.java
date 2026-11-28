// Tema:
// Files.copy crea un segundo archivo con los mismos bytes. Cambiar la copia no cambia el original.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 CopiarArchivo.java
// Ejecutar: java CopiarArchivo

import java.nio.file.*;

public class CopiarArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path original = Files.writeString(dir.resolve("original.txt"), "venta");
            Path copia = Files.copy(original, dir.resolve("copia.txt"));
            Files.writeString(copia, "otra");
            System.out.println(Files.readString(original));
            System.out.println(Files.readString(copia));
        } finally {
            // Se elimina únicamente el árbol temporal creado por este programa.
            try (var recorrido = Files.walk(dir)) {
                for (Path p : recorrido.sorted(java.util.Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(p);
                }
            }
        }
    }
}

// Salida esperada:
// venta
// otra
