// Tema:
// TCP proporciona un flujo fiable de bytes, no mensajes. El ejemplo usa EOF mediante shutdownOutput para delimitar una única petición; el servidor devuelve los bytes y cierra la conexión. Solo loopback y puerto efímero.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainEcho.java
// Ejecutar: java MainEcho

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Servidor implements AutoCloseable {
    private final ServerSocket escucha = new ServerSocket(0, 4, InetAddress.getLoopbackAddress());
    public Servidor() throws IOException { escucha.setSoTimeout(3000); }
    public int puerto() { return escucha.getLocalPort(); }
    public void atender() throws IOException {
        try (Socket cliente = escucha.accept()) {
            cliente.setSoTimeout(3000);
            byte[] datos = cliente.getInputStream().readNBytes(64);
            cliente.getOutputStream().write(datos);
        }
    }
    public void close() throws IOException { escucha.close(); }
}

// Salida esperada:
// Hola TCP
