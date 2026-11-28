// Tema:
// FileLock representa un bloqueo de región del archivo. Su comportamiento entre procesos depende del sistema.
// tryLock no espera; una superposición dentro de la misma JVM provoca OverlappingFileLockException.
// Modalidad: P.
// Compilar: javac --release 21 -encoding UTF-8 BloqueoArchivo.java ClienteBloqueo.java
// Ejecutar: java ClienteBloqueo

import java.io.IOException;
import java.nio.channels.*;

public class BloqueoArchivo {
    public static FileLock intentar(FileChannel canal) throws IOException {
        return canal.tryLock();
    }
}

// Salida esperada:
// Bloqueo: true
// Superposicion detectada
// Alternativa: Bloqueo no disponible
