// Tema:
// Un hilo virtual por conexión aceptada. Los hilos virtuales facilitan E/S bloqueante, pero no eliminan la necesidad de limitar conexiones y recursos. Se admiten solo tres.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainServidorVirtual.java
// Ejecutar: java MainServidorVirtual

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements AutoCloseable {
    private static final int MAX_CLIENTES = 3;
    private final ServerSocket escucha = new ServerSocket(0, MAX_CLIENTES, InetAddress.getLoopbackAddress());
    private final ExecutorService trabajadores = Executors.newVirtualThreadPerTaskExecutor();
    public Servidor() throws IOException {
        escucha.setSoTimeout(4000);
        
    }
    public int puerto() { return escucha.getLocalPort(); }

    private static void responder(Socket socket) throws IOException {
        try (socket) {
            socket.setSoTimeout(3000);
            String mensaje = new DataInputStream(socket.getInputStream()).readUTF();
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF("OK " + mensaje);
            salida.flush();
        }
    }

    public void atender() throws Exception {
        List<Future<?>> tareas = new ArrayList<>();
        
        try {
            // El número máximo de conexiones aceptadas acota los recursos de este ejemplo.
            for (int i = 0; i < MAX_CLIENTES; i++) {
                Socket socket = escucha.accept();
                try {
                    tareas.add(trabajadores.submit(() -> { responder(socket); return null; }));
                } catch (RuntimeException | Error e) {
                    socket.close();
                    throw e;
                }
            }
            for (Future<?> tarea : tareas) tarea.get(5, TimeUnit.SECONDS);
        } finally {
            trabajadores.shutdownNow();
            if (!trabajadores.awaitTermination(4, TimeUnit.SECONDS))
                throw new IllegalStateException("Trabajadores no terminados");
        }
    }
    public void close() throws IOException {
        trabajadores.shutdownNow();
        escucha.close();
    }
}

// Salida esperada:
// OK pan
// OK leche
// OK arroz
