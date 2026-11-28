// Tema:
// FileChannel.map permite acceder a una región del archivo mediante un buffer mapeado.
// Cerrar el canal no desmapea inmediatamente el buffer. En Windows el archivo puede seguir bloqueado hasta que termine la JVM.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 ArchivoMapeado.java
// Ejecutar: java ArchivoMapeado

import java.nio.file.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ArchivoMapeado {
    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile(Path.of("."), "mapa-", ".bin");
        archivo.toFile().deleteOnExit();
        Files.write(archivo, new byte[]{10, 20, 30});
        try (FileChannel canal = FileChannel.open(archivo, StandardOpenOption.READ)) {
            MappedByteBuffer mapa = canal.map(FileChannel.MapMode.READ_ONLY, 0, 3);
            System.out.println(mapa.get(1));
        }
        try {
            Files.deleteIfExists(archivo);
        } catch (java.io.IOException e) {
            // En Windows el mapeo todavía puede existir; la JVM libera el recurso al salir.
            // Ejecutar desde un directorio temporal; puede quedar un archivo para limpiar.
        }
    }
}

// Salida esperada:
// 20
