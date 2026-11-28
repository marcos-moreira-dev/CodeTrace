// Tema:
// Un hilo de plataforma por conexión aceptada. Se limita a tres clientes; cada conexión tiene sus propios flujos y se cierra después de responder.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainMultiCliente.java
// Ejecutar: java MainMultiCliente

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Cliente {
    public static String solicitar(int puerto, String mensaje) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(3000);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(mensaje);
            salida.flush();
            return new DataInputStream(socket.getInputStream()).readUTF();
        }
    }
}

// Salida esperada:
// OK pan
// OK leche
// OK arroz
