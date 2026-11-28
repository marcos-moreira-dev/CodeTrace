// Tema:
// Un Future con plazo permite detectar una tarea que no termina. Se usa un latch para provocar espera real, y después se libera y cancela para no dejar trabajadores activos.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 PruebaTimeout.java
// Ejecutar: java PruebaTimeout

import java.util.concurrent.*;

public class PruebaTimeout {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        CountDownLatch bloqueo = new CountDownLatch(1);
        Future<?> tarea = pool.submit(() -> { bloqueo.await(); return null; });
        try {
            try { tarea.get(100, TimeUnit.MILLISECONDS); throw new AssertionError("Se esperaba timeout"); }
            catch (TimeoutException e) { System.out.println("Timeout detectado"); }
            if (!tarea.cancel(true)) throw new AssertionError("Cancelacion");
        } finally {
            bloqueo.countDown();
            pool.shutdownNow();
            if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new AssertionError("Pool activo");
        }
        System.out.println("Pool terminado");
    }
}

// Salida esperada:
// Timeout detectado
// Pool terminado
