// Tema:
// El timeout de conexión limita connect; SO_TIMEOUT limita una lectura bloqueante. El servidor acepta pero no responde hasta recibir señal de cierre; se provoca un timeout de lectura reproducible sin depender de un servidor externo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorLento.java ClienteTimeout.java MainTimeout.java
// Ejecutar: java MainTimeout

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class ClienteTimeout {
    public static void consultar(int puerto) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(200);
            try {
                int dato = socket.getInputStream().read();
                throw new IOException("Se esperaba timeout, recibido: " + dato);
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout de lectura");
            }
        }
    }
}

// Salida esperada:
// Timeout de lectura
// Conexion cerrada
