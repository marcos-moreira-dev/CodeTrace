// Tema:
// createDirectories crea los padres faltantes. Files.list enumera un nivel.
// El flujo debe cerrarse; ordenar hace que la salida sea reproducible.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 CrearListarDirectorio.java
// Ejecutar: java CrearListarDirectorio

import java.nio.file.*;

public class CrearListarDirectorio {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path datos = Files.createDirectories(dir.resolve("datos"));
            Files.writeString(datos.resolve("b.txt"), "B");
            Files.writeString(datos.resolve("a.txt"), "A");
            try (var archivos = Files.list(datos)) {
                for (Path p : archivos.sorted().toList()) {
                    System.out.println(p.getFileName());
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
// b.txt
