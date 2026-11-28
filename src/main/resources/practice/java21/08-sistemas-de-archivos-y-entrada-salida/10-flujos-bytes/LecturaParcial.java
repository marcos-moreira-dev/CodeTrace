// Tema:
// read(buffer) devuelve cuántos bytes se leyeron, no necesariamente la capacidad del arreglo.
// Solo se procesan los primeros n bytes de cada lectura.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 LecturaParcial.java
// Ejecutar: java LecturaParcial

import java.nio.file.*;

public class LecturaParcial {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.write(dir.resolve("datos.bin"), new byte[]{1, 2, 3, 4, 5});
            byte[] buffer = new byte[2];
            int total = 0;
            try (var entrada = Files.newInputStream(archivo)) {
                int n;
                while ((n = entrada.read(buffer)) != -1) {
                    for (int i = 0; i < n; i++) total += buffer[i];
                }
            }
            System.out.println("Suma: " + total);
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
// Suma: 15
