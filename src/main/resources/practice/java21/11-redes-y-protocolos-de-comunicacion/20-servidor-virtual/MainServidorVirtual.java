// Tema:
// Un hilo virtual por conexión aceptada. Los hilos virtuales facilitan E/S bloqueante, pero no eliminan la necesidad de limitar conexiones y recursos. Se admiten solo tres.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainServidorVirtual.java
// Ejecutar: java MainServidorVirtual

import java.util.concurrent.*;

public class MainServidorVirtual {
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
