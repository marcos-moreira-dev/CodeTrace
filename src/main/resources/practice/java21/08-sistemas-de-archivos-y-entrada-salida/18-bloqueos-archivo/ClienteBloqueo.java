// Tema:
// FileLock representa un bloqueo de región del archivo. Su comportamiento entre procesos depende del sistema.
// tryLock no espera; una superposición dentro de la misma JVM provoca OverlappingFileLockException.
// Modalidad: P.
// Compilar: javac --release 21 -encoding UTF-8 BloqueoArchivo.java ClienteBloqueo.java
// Ejecutar: java ClienteBloqueo

import java.nio.file.*;
import java.nio.channels.*;

public class ClienteBloqueo {
    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile(Path.of("."), "bloqueo-", ".bin");
        try (FileChannel canal = FileChannel.open(archivo, StandardOpenOption.WRITE)) {
            FileLock lock;
            try {
                lock = BloqueoArchivo.intentar(canal);
            } catch (UnsupportedOperationException | java.io.IOException e) {
                System.out.println("Bloqueo no disponible");
                return;
            }
            if (lock == null) {
                System.out.println("Bloqueo no disponible");
                return;
            }
            try (lock) {
                System.out.println("Bloqueo: " + lock.isValid());
                try {
                    BloqueoArchivo.intentar(canal);
                    throw new AssertionError("Se esperaba superposicion");
                } catch (OverlappingFileLockException e) {
                    System.out.println("Superposicion detectada");
                }
            }
        } finally {
            Files.deleteIfExists(archivo);
        }
    }
}

// Salida esperada:
// Bloqueo: true
// Superposicion detectada
// Alternativa: Bloqueo no disponible
