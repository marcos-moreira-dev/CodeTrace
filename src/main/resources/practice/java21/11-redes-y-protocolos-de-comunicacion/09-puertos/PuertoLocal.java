// Tema:
// El puerto cero al enlazar solicita un puerto efímero al sistema operativo. Se restringe el socket a loopback y se cierra al terminar.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 PuertoLocal.java
// Ejecutar: java PuertoLocal

import java.net.*;

public class PuertoLocal {
    public static void main(String[] args) throws Exception {
        try (ServerSocket servidor = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            System.out.println("Asignado: " + (servidor.getLocalPort() > 0));
            System.out.println("Loopback: " + servidor.getInetAddress().isLoopbackAddress());
        }
    }
}

// Salida esperada:
// Asignado: true
// Loopback: true
