// Tema:
// read no garantiza completar el buffer. Se acumulan bytes hasta completar cuatro octetos; EOF prematuro es truncamiento. El cliente manda tres bytes y cierra la escritura para provocar el caso controlado.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainLecturaParcial.java
// Ejecutar: java MainLecturaParcial

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
            InputStream entrada = cliente.getInputStream();
            byte[] registro = new byte[4];
            int acumulado = 0;
            while (acumulado < registro.length) {
                int n = entrada.read(registro, acumulado, registro.length - acumulado);
                if (n == -1) break;
                acumulado += n;
            }
            String resultado = acumulado == registro.length ? "Completo" : "Truncado: " + acumulado;
            cliente.getOutputStream().write(resultado.getBytes(StandardCharsets.UTF_8));
        }
    }
    public void close() throws IOException { escucha.close(); }
}

// Salida esperada:
// Truncado: 3
