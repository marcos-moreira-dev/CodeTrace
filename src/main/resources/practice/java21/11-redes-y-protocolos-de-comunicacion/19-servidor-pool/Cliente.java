// Tema:
// Prethreading: dos trabajadores se crean antes de aceptar conexiones. Una cola acotada guarda trabajos pendientes; el ejemplo limita admisión a tres clientes y no es un servidor de producción.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainServidorPool.java
// Ejecutar: java MainServidorPool

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
