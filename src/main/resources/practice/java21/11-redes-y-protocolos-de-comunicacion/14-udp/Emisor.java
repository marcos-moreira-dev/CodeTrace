// Tema:
// UDP envía datagramas independientes. Cada receive obtiene un datagrama, no un flujo; un buffer demasiado pequeño puede truncarlo. Se limita la espera y se usa loopback.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Receptor.java Emisor.java MainDatagrama.java
// Ejecutar: java MainDatagrama

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Emisor {
    public static void enviar(int puerto, String mensaje) throws IOException {
        byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);
        try (DatagramSocket socket = new DatagramSocket(0, InetAddress.getLoopbackAddress())) {
            socket.send(new DatagramPacket(datos, datos.length, InetAddress.getLoopbackAddress(), puerto));
        }
    }
}

// Salida esperada:
// Hola UDP
