// Tema:
// Files.move cambia la ubicación o el nombre. No se presupone que todo movimiento sea atómico.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 MoverArchivo.java
// Ejecutar: java MoverArchivo

import java.nio.file.*;

public class MoverArchivo {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path origen = Files.writeString(dir.resolve("borrador.txt"), "listo");
            Path destino = Files.move(origen, dir.resolve("final.txt"));
            System.out.println("Origen existe: " + Files.exists(origen));
            System.out.println("Destino: " + Files.readString(destino));
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
// Origen existe: false
// Destino: listo
