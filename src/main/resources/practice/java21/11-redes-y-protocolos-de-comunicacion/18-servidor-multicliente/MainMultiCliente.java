// Tema:
// Un hilo de plataforma por conexión aceptada. Se limita a tres clientes; cada conexión tiene sus propios flujos y se cierra después de responder.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainMultiCliente.java
// Ejecutar: java MainMultiCliente

import java.util.concurrent.*;

public class MainMultiCliente {
    public static void main(String[] args) throws Exception {
        ExecutorService tareas = Executors.newFixedThreadPool(4);
        try (Servidor servidor = new Servidor()) {
            Future<?> servicio = tareas.submit(() -> { servidor.atender(); return null; });
            Future<String> a = tareas.submit(() -> Cliente.solicitar(servidor.puerto(), "pan"));
            Future<String> b = tareas.submit(() -> Cliente.solicitar(servidor.puerto(), "leche"));
            Future<String> c = tareas.submit(() -> Cliente.solicitar(servidor.puerto(), "arroz"));
            System.out.println(a.get(6, TimeUnit.SECONDS));
            System.out.println(b.get(6, TimeUnit.SECONDS));
            System.out.println(c.get(6, TimeUnit.SECONDS));
            servicio.get(6, TimeUnit.SECONDS);
        } finally { tareas.shutdownNow(); }
    }
}

// Salida esperada:
// OK pan
// OK leche
// OK arroz
