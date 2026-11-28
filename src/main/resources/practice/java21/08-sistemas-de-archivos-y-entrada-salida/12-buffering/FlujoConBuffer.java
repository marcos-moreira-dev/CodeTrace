// Tema:
// Un flujo con buffer acumula datos para reducir llamadas al flujo subyacente.
// El tamaño del buffer no modifica el formato de los datos.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 FlujoConBuffer.java
// Ejecutar: java FlujoConBuffer

import java.nio.file.*;
import java.io.*;

public class FlujoConBuffer {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = dir.resolve("datos.bin");
            try (var salida = new BufferedOutputStream(Files.newOutputStream(archivo), 8)) {
                salida.write(new byte[]{1, 2, 3});
            }
            try (var entrada = new BufferedInputStream(Files.newInputStream(archivo), 8)) {
                System.out.println(entrada.read());
                System.out.println(entrada.read());
                System.out.println(entrada.read());
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
// 1
// 2
// 3
