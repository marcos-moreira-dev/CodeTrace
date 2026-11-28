// Tema:
// read no garantiza completar el buffer. Se acumulan bytes hasta completar cuatro octetos; EOF prematuro es truncamiento. El cliente manda tres bytes y cierra la escritura para provocar el caso controlado.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainLecturaParcial.java
// Ejecutar: java MainLecturaParcial

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Cliente {
    public static String solicitar(int puerto) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(3000);
            socket.getOutputStream().write(new byte[]{1, 2});
            socket.getOutputStream().write(new byte[]{3});
            socket.shutdownOutput();
            return new String(socket.getInputStream().readNBytes(64), StandardCharsets.UTF_8);
        }
    }
}

// Salida esperada:
// Truncado: 3
