// Tema:
// Un hilo de plataforma por conexión aceptada. Se limita a tres clientes; cada conexión tiene sus propios flujos y se cierra después de responder.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainMultiCliente.java
// Ejecutar: java MainMultiCliente

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements AutoCloseable {
    private static final int MAX_CLIENTES = 3;
    private final ServerSocket escucha = new ServerSocket(0, MAX_CLIENTES, InetAddress.getLoopbackAddress());
    
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
        List<Thread> hilos = new ArrayList<>();
        try {
            // El número máximo de conexiones aceptadas acota los recursos de este ejemplo.
            for (int i = 0; i < MAX_CLIENTES; i++) {
                Socket socket = escucha.accept();
                try {
                    FutureTask<Void> tarea = new FutureTask<>(() -> { responder(socket); return null; });
                    Thread hilo = new Thread(tarea, "cliente-" + i);
                    hilos.add(hilo);
                    tareas.add(tarea);
                    hilo.start();
                } catch (RuntimeException | Error e) {
                    socket.close();
                    throw e;
                }
            }
            for (Future<?> tarea : tareas) tarea.get(5, TimeUnit.SECONDS);
        } finally {
            for (Thread hilo : hilos) {
                hilo.join(4000);
                if (hilo.isAlive()) throw new IllegalStateException("Hilo no terminado");
            }
        }
    }
    public void close() throws IOException {
        
        escucha.close();
    }
}

// Salida esperada:
// OK pan
// OK leche
// OK arroz
