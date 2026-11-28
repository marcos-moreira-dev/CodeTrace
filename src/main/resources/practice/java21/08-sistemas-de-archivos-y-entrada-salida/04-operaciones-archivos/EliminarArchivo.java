// Tema:
// deleteIfExists elimina una entrada si existe y devuelve un booleano.
// La operación se restringe a un archivo propio dentro de un directorio temporal.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EliminarArchivo.java
// Ejecutar: java EliminarArchivo

import java.nio.file.*;

public class EliminarArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("prueba.txt"), "dato");
            System.out.println(Files.deleteIfExists(archivo));
            System.out.println(Files.deleteIfExists(archivo));
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
// true
// false
