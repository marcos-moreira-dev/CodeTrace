// Tema:
// OutputStream escribe bytes, no caracteres. El formato debe acordarse con quien leerá los datos.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EscrituraBytes.java
// Ejecutar: java EscrituraBytes

import java.nio.file.*;

public class EscrituraBytes {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = dir.resolve("datos.bin");
            try (var salida = Files.newOutputStream(archivo)) {
                salida.write(new byte[]{10, 20, 30});
            }
            System.out.println("Bytes: " + Files.size(archivo));
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
// Bytes: 3
