// Tema:
// El timeout de conexión limita connect; SO_TIMEOUT limita una lectura bloqueante. El servidor acepta pero no responde hasta recibir señal de cierre; se provoca un timeout de lectura reproducible sin depender de un servidor externo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorLento.java ClienteTimeout.java MainTimeout.java
// Ejecutar: java MainTimeout

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class ServidorLento implements AutoCloseable {
    private final ServerSocket escucha = new ServerSocket(0, 1, InetAddress.getLoopbackAddress());
    private final CountDownLatch cerrar = new CountDownLatch(1);
    public ServidorLento() throws IOException { escucha.setSoTimeout(3000); }
    public int puerto() { return escucha.getLocalPort(); }
    public void atender() throws Exception {
        try (Socket cliente = escucha.accept()) {
            if (!cerrar.await(5, TimeUnit.SECONDS)) throw new IOException("Plazo del servidor");
        }
    }
    public void close() throws IOException {
        cerrar.countDown();
        escucha.close();
    }
}

// Salida esperada:
// Timeout de lectura
// Conexion cerrada
