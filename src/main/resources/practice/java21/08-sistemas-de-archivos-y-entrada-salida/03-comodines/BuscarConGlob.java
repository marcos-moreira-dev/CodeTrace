// Tema:
// Un glob describe nombres de archivos; aquí Java evalúa el patrón, no el shell.
// El asterisco coincide con una secuencia de caracteres.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 BuscarConGlob.java
// Ejecutar: java BuscarConGlob

import java.nio.file.*;

public class BuscarConGlob {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Files.writeString(dir.resolve("a.txt"), "A");
            Files.writeString(dir.resolve("b.csv"), "B");
            Files.writeString(dir.resolve("c.txt"), "C");
            PathMatcher patron = dir.getFileSystem().getPathMatcher("glob:*.txt");
            try (var archivos = Files.list(dir)) {
                for (Path p : archivos.sorted().toList()) {
                    if (patron.matches(p.getFileName())) System.out.println(p.getFileName());
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
// c.txt
