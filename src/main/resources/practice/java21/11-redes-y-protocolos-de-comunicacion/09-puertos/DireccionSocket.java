// Tema:
// InetSocketAddress combina dirección y puerto. Construir una dirección no abre una conexión.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 DireccionSocket.java
// Ejecutar: java DireccionSocket

import java.net.*;

public class DireccionSocket {
    public static void main(String[] args) throws Exception {
        InetSocketAddress destino = new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080);
        System.out.println("Puerto: " + destino.getPort());
        System.out.println("Loopback: " + destino.getAddress().isLoopbackAddress());
    }
}

// Salida esperada:
// Puerto: 8080
// Loopback: true
