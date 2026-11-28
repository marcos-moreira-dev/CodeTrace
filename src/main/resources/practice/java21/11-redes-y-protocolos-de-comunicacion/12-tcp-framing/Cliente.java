// Tema:
// Framing: un entero de cuatro bytes anuncia la longitud de la carga UTF-8. readFully reconstruye el mensaje aunque TCP entregue fragmentos; la longitud se valida antes de reservar memoria.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CodificadorTrama.java Servidor.java Cliente.java MainFraming.java
// Ejecutar: java MainFraming

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Cliente {
    public static String solicitar(int puerto) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), puerto), 3000);
            socket.setSoTimeout(3000);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            CodificadorTrama.escribir(salida, "pan");
            CodificadorTrama.escribir(salida, "leche");
            return CodificadorTrama.leer(entrada) + "\n" + CodificadorTrama.leer(entrada);
        }
    }
}

// Salida esperada:
// OK pan
// OK leche
