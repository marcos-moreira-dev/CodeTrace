// Tema:
// Un Selector multiplica canales no bloqueantes sobre un hilo. Una clave cambia de OP_READ a OP_WRITE cuando se han acumulado cuatro bytes; el buffer conserva la posición en operaciones parciales. Se limita a una conexión y a un plazo fijo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorSelector.java Cliente.java MainSelector.java
// Ejecutar: java MainSelector

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Cliente {
    public static String solicitar(int puerto) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(3000);
            socket.getOutputStream().write(new byte[]{'H', 'O'});
            socket.getOutputStream().write(new byte[]{'L', 'A'});
            byte[] datos = new DataInputStream(socket.getInputStream()).readNBytes(4);
            if (datos.length != 4) throw new EOFException("Respuesta truncada");
            return new String(datos, StandardCharsets.US_ASCII);
        }
    }
}

// Salida esperada:
// HOLA
