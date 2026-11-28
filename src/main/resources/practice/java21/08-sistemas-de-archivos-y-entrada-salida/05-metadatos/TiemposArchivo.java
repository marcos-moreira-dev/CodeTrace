// Tema:
// La fecha de modificación es metadato; no es necesariamente la de creación.
// Se elige un instante fijo y se consulta la fecha almacenada.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 TiemposArchivo.java
// Ejecutar: java TiemposArchivo

import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class TiemposArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("dato.txt"), "dato");
            FileTime instante = FileTime.from(Instant.parse("2020-01-01T00:00:00Z"));
            Files.setLastModifiedTime(archivo, instante);
            System.out.println(Files.getLastModifiedTime(archivo).toInstant());
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
// 2020-01-01T00:00:00Z
