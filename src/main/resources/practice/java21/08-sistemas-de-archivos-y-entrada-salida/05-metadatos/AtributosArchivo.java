// Tema:
// BasicFileAttributes permite consultar propiedades del sistema de archivos.
// El tamaño de un archivo regular se expresa en bytes.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 AtributosArchivo.java
// Ejecutar: java AtributosArchivo

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class AtributosArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.write(dir.resolve("dato.bin"), new byte[]{1, 2, 3});
            BasicFileAttributes info = Files.readAttributes(archivo, BasicFileAttributes.class);
            System.out.println("Regular: " + info.isRegularFile());
            System.out.println("Bytes: " + info.size());
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
// Regular: true
// Bytes: 3
