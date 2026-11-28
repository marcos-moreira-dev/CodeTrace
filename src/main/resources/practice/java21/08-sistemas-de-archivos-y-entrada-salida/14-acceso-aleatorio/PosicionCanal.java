// Tema:
// FileChannel tiene una posición de lectura y escritura; position permite cambiarla.
// El buffer recibe bytes hasta su límite o hasta el fin de archivo.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 PosicionCanal.java
// Ejecutar: java PosicionCanal

import java.nio.file.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class PosicionCanal {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = Files.write(dir.resolve("datos.bin"), new byte[]{10, 20, 30});
            try (FileChannel canal = FileChannel.open(archivo, StandardOpenOption.READ)) {
                canal.position(1);
                ByteBuffer buffer = ByteBuffer.allocate(1);
                while (buffer.hasRemaining() && canal.read(buffer) != -1) { }
                buffer.flip();
                System.out.println(buffer.get());
                System.out.println("Posicion: " + canal.position());
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
// 20
// Posicion: 2
