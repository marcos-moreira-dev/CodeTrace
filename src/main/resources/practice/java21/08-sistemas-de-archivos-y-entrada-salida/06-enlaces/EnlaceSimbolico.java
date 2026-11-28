// Tema:
// Un enlace simbólico guarda una ruta objetivo; puede quedar colgante.
// Windows puede exigir permisos adicionales para crearlo.
// Modalidad: P (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EnlaceSimbolico.java
// Ejecutar: java EnlaceSimbolico

import java.nio.file.*;

public class EnlaceSimbolico {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path original = Files.writeString(dir.resolve("original.txt"), "dato");
            Path enlace = dir.resolve("enlace.txt");
            try {
                Files.createSymbolicLink(enlace, original.getFileName());
            } catch (UnsupportedOperationException | java.io.IOException e) {
                System.out.println("Enlace no disponible");
                return;
            }
            System.out.println(Files.isSymbolicLink(enlace));
            Files.delete(original);
            System.out.println("Destino existe: " + Files.exists(enlace));
            System.out.println("Enlace existe: " + Files.exists(enlace, LinkOption.NOFOLLOW_LINKS));
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
// Destino existe: false
// Enlace existe: true
// Alternativa en un sistema sin soporte: Enlace no disponible
