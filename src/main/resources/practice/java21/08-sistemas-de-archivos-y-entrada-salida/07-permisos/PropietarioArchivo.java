// Tema:
// FileOwnerAttributeView permite consultar el propietario cuando el proveedor lo admite.
// No se cambia la identidad ni se imprimen nombres de usuarios de la máquina.
// Modalidad: M (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 PropietarioArchivo.java
// Ejecutar: java PropietarioArchivo

import java.nio.file.*;
import java.nio.file.attribute.FileOwnerAttributeView;

public class PropietarioArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("dato.txt"), "dato");
            if (Files.getFileAttributeView(archivo, FileOwnerAttributeView.class) == null) {
                System.out.println("Propietario no disponible");
                return;
            }
            System.out.println("Propietario consultado: " + (Files.getOwner(archivo) != null));
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
// Propietario consultado: true
// Alternativa en un sistema sin soporte: Propietario no disponible
