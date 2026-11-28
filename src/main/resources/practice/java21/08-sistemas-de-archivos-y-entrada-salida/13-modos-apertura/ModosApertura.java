// Tema:
// CREATE_NEW falla si el archivo ya existe. TRUNCATE_EXISTING vacía un archivo abierto para escritura.
// La elección del modo es parte del contrato de escritura.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 ModosApertura.java
// Ejecutar: java ModosApertura

import java.nio.file.*;

public class ModosApertura {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = dir.resolve("dato.txt");
            Files.writeString(archivo, "anterior", StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            try {
                Files.writeString(archivo, "otro", StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            } catch (FileAlreadyExistsException e) {
                System.out.println("Ya existe");
            }
            Files.writeString(archivo, "nuevo", StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
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
// Ya existe
// nuevo
