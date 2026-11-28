// Tema:
// Files.walk recorre de manera recursiva. El resultado incluye directorios y archivos.
// No sigue enlaces simbólicos por defecto.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 RecorrerArbol.java
// Ejecutar: java RecorrerArbol

import java.nio.file.*;

public class RecorrerArbol {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path sub = Files.createDirectories(dir.resolve("ventas").resolve("enero"));
            Files.writeString(sub.resolve("a.txt"), "A");
            try (var rutas = Files.walk(dir)) {
                for (Path p : rutas.filter(Files::isRegularFile).toList()) {
                    System.out.println(p.getFileName());
                    System.out.println("Profundidad: " + dir.relativize(p).getNameCount());
                }
            }
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
// a.txt
// Profundidad: 3
