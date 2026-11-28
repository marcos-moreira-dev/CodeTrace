// Tema:
// Un enlace duro es otra entrada que apunta al mismo archivo. No es una copia.
// La creación depende del sistema de archivos y sus permisos.
// Modalidad: P (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EnlaceDuro.java
// Ejecutar: java EnlaceDuro

import java.nio.file.*;

public class EnlaceDuro {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path original = Files.writeString(dir.resolve("original.txt"), "uno");
            Path enlace = dir.resolve("enlace.txt");
            try {
                Files.createLink(enlace, original);
            } catch (UnsupportedOperationException | java.io.IOException e) {
                System.out.println("Enlace no disponible");
                return;
            }
            Files.writeString(enlace, "dos");
            System.out.println(Files.isSameFile(original, enlace));
            System.out.println(Files.readString(original));
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
// true
// dos
// Alternativa en un sistema sin soporte: Enlace no disponible
