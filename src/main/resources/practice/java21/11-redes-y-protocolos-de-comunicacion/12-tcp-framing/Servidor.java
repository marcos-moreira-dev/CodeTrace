// Tema:
// Framing: un entero de cuatro bytes anuncia la longitud de la carga UTF-8. readFully reconstruye el mensaje aunque TCP entregue fragmentos; la longitud se valida antes de reservar memoria.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CodificadorTrama.java Servidor.java Cliente.java MainFraming.java
// Ejecutar: java MainFraming

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Servidor implements AutoCloseable {
    private final ServerSocket escucha = new ServerSocket(0, 4, InetAddress.getLoopbackAddress());
    public Servidor() throws IOException { escucha.setSoTimeout(3000); }
    public int puerto() { return escucha.getLocalPort(); }
    public void atender() throws IOException {
        try (Socket cliente = escucha.accept()) {
            cliente.setSoTimeout(3000);
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            for (int i = 0; i < 2; i++) {
                String mensaje = CodificadorTrama.leer(entrada);
                CodificadorTrama.escribir(salida, "OK " + mensaje);
            }
        }
    }
    public void close() throws IOException { escucha.close(); }
}

// Salida esperada:
// OK pan
// OK leche
