// Tema:
// BufferedReader traduce bytes a caracteres con un charset explícito.
// readLine elimina el terminador de línea y devuelve null al final.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 LecturaTexto.java
// Ejecutar: java LecturaTexto

import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class LecturaTexto {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("ventas.txt"), "pan\nleche\n", StandardCharsets.UTF_8);
            try (var lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
                String linea;
                while ((linea = lector.readLine()) != null) System.out.println(linea);
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
// pan
// leche
