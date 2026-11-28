// Tema:
// Future.cancel(true) solicita interrupción si el trabajo está ejecutándose. La cancelación del futuro no implica que una tarea que ignore la interrupción haya terminado.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CancelarTarea.java
// Ejecutar: java CancelarTarea

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CancelarTarea {
    public static void main(String[] args) throws Exception {
        CountDownLatch iniciada = new CountDownLatch(1), finalizada = new CountDownLatch(1);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Future<?> tarea = pool.submit(() -> {
                iniciada.countDown();
                try { Thread.sleep(10000); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                finally { finalizada.countDown(); }
            });
            if (!iniciada.await(3, TimeUnit.SECONDS)) throw new IllegalStateException("No inicio");
            System.out.println("Cancelada: " + tarea.cancel(true));
            System.out.println("Termino: " + finalizada.await(3, TimeUnit.SECONDS));
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// Cancelada: true
// Termino: true
