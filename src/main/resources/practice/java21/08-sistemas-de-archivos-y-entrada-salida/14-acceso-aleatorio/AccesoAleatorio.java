// Tema:
// RandomAccessFile permite posicionarse por desplazamiento en bytes.
// Modificar un byte no implica reescribir el archivo completo.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 AccesoAleatorio.java
// Ejecutar: java AccesoAleatorio

import java.nio.file.*;
import java.io.RandomAccessFile;

public class AccesoAleatorio {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.write(dir.resolve("datos.bin"), new byte[]{10, 20, 30});
            try (RandomAccessFile acceso = new RandomAccessFile(archivo.toFile(), "rw")) {
                acceso.seek(1);
                acceso.writeByte(99);
                acceso.seek(0);
                for (int i = 0; i < 3; i++) System.out.println(acceso.readUnsignedByte());
            }
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
// 10
// 99
// 30
