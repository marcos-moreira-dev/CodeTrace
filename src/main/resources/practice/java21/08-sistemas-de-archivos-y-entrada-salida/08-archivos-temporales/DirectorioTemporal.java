// Tema:
// Un directorio temporal evita colisiones de nombres y separa datos de prueba.
// Se crea en el directorio de trabajo y se elimina al terminar.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 DirectorioTemporal.java
// Ejecutar: java DirectorioTemporal

import java.nio.file.*;

public class DirectorioTemporal {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.writeString(dir.resolve("nota.txt"), "temporal");
            System.out.println(Files.isDirectory(dir));
            System.out.println(Files.readString(archivo));
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
// temporal
