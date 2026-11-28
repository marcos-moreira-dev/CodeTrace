// Tema:
// TCP proporciona un flujo fiable de bytes, no mensajes. El ejemplo usa EOF mediante shutdownOutput para delimitar una única petición; el servidor devuelve los bytes y cierra la conexión. Solo loopback y puerto efímero.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainEcho.java
// Ejecutar: java MainEcho

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Cliente {
    public static String solicitar(int puerto) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(3000);
            socket.getOutputStream().write("Hola TCP".getBytes(StandardCharsets.UTF_8));
            socket.shutdownOutput();
            return new String(socket.getInputStream().readNBytes(64), StandardCharsets.UTF_8);
        }
    }
}

// Salida esperada:
// Hola TCP
