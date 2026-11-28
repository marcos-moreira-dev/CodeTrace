// Tema:
// UDP envía datagramas independientes. Cada receive obtiene un datagrama, no un flujo; un buffer demasiado pequeño puede truncarlo. Se limita la espera y se usa loopback.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Receptor.java Emisor.java MainDatagrama.java
// Ejecutar: java MainDatagrama

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Receptor implements AutoCloseable {
    private final DatagramSocket socket = new DatagramSocket(0, InetAddress.getLoopbackAddress());
    public Receptor() throws SocketException { socket.setSoTimeout(3000); }
    public int puerto() { return socket.getLocalPort(); }
    public String recibir() throws IOException {
        byte[] datos = new byte[64];
        DatagramPacket paquete = new DatagramPacket(datos, datos.length);
        socket.receive(paquete);
        return new String(datos, paquete.getOffset(), paquete.getLength(), StandardCharsets.UTF_8);
    }
    public void close() { socket.close(); }
}

// Salida esperada:
// Hola UDP
