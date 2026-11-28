// Tema:
// Una barrera cíclica espera a todos los participantes de una fase y puede reutilizarse. La acción de barrera se ejecuta una vez por fase.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CyclicBarrierDemo.java
// Ejecutar: java CyclicBarrierDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CyclicBarrierDemo {
    static void ejecutar(Runnable... tareas) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(tareas.length);
        try {
            java.util.ArrayList<Future<?>> resultados = new java.util.ArrayList<>();
            for (Runnable tarea : tareas) resultados.add(pool.submit(tarea));
            for (Future<?> resultado : resultados) resultado.get(5, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
            if (!pool.awaitTermination(3, TimeUnit.SECONDS))
                throw new IllegalStateException("Tareas no terminadas");
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger fases = new AtomicInteger();
        CyclicBarrier barrera = new CyclicBarrier(2, fases::incrementAndGet);
        Runnable tarea = () -> {
            try {
                for (int i = 0; i < 2; i++) barrera.await(3, TimeUnit.SECONDS);
            } catch (Exception e) { throw new IllegalStateException(e); }
        };
        ejecutar(tarea, tarea);
        System.out.println("Fases: " + fases.get());
    }
}

// Salida esperada:
// Fases: 2
