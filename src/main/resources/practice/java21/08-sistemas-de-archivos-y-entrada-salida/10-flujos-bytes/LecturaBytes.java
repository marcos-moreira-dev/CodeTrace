// Tema:
// InputStream.read devuelve un byte sin signo (0 a 255) o -1 al finalizar.
// El cierre automático libera el recurso incluso si ocurre una excepción.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 LecturaBytes.java
// Ejecutar: java LecturaBytes

import java.nio.file.*;

public class LecturaBytes {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.write(dir.resolve("datos.bin"), new byte[]{1, (byte) 255});
            try (var entrada = Files.newInputStream(archivo)) {
                int dato;
                while ((dato = entrada.read()) != -1) System.out.println(dato);
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
// 255
