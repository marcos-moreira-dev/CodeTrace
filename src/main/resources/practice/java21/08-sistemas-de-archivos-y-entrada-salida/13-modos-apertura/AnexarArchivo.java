// Tema:
// APPEND añade bytes al final sin truncar el contenido existente.
// No garantiza atomicidad de un registro completo entre procesos concurrentes.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 AnexarArchivo.java
// Ejecutar: java AnexarArchivo

import java.nio.file.*;

public class AnexarArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("ventas.txt"), "pan\n");
            Files.writeString(archivo, "leche\n", StandardOpenOption.APPEND);
            System.out.print(Files.readString(archivo));
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
