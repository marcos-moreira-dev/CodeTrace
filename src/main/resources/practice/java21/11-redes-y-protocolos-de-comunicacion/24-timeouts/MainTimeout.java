// Tema:
// El timeout de conexión limita connect; SO_TIMEOUT limita una lectura bloqueante. El servidor acepta pero no responde hasta recibir señal de cierre; se provoca un timeout de lectura reproducible sin depender de un servidor externo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorLento.java ClienteTimeout.java MainTimeout.java
// Ejecutar: java MainTimeout

import java.util.concurrent.*;

public class MainTimeout {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try (ServidorLento servidor = new ServidorLento()) {
            Future<?> trabajo = pool.submit(() -> { servidor.atender(); return null; });
            ClienteTimeout.consultar(servidor.puerto());
            servidor.close();
            trabajo.get(6, TimeUnit.SECONDS);
            System.out.println("Conexion cerrada");
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// Timeout de lectura
// Conexion cerrada
