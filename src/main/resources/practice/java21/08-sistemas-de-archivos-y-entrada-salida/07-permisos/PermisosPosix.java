// Tema:
// La vista POSIX permite consultar y cambiar los permisos rwx de un archivo propio.
// No equivale al modelo de ACL de Windows; se verifica soporte antes de operar.
// Modalidad: M (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 PermisosPosix.java
// Ejecutar: java PermisosPosix

import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;

public class PermisosPosix {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("dato.txt"), "dato");
            if (!Files.getFileStore(archivo).supportsFileAttributeView("posix")) {
                System.out.println("POSIX no disponible");
                return;
            }
            Files.setPosixFilePermissions(archivo, PosixFilePermissions.fromString("rw-------"));
            System.out.println(PosixFilePermissions.toString(Files.getPosixFilePermissions(archivo)));
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
// rw-------
// Alternativa en un sistema sin soporte: POSIX no disponible
