// Tema:
// Cola concurrente no bloqueante: offer agrega y poll extrae o devuelve null. Se agregan elementos desde varios hilos; se vacía después de que terminen, sin presuponer el orden entre productores.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ConcurrentLinkedQueueDemo.java
// Ejecutar: java ConcurrentLinkedQueueDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ConcurrentLinkedQueueDemo {
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
        ConcurrentLinkedQueue<Integer> cola = new ConcurrentLinkedQueue<>();
        ejecutar(() -> { cola.offer(1); cola.offer(2); }, () -> { cola.offer(3); cola.offer(4); });
        int total = 0;
        Integer dato;
        while ((dato = cola.poll()) != null) total += dato;
        System.out.println("Suma: " + total);
        System.out.println("Vacia: " + cola.isEmpty());
    }
}

// Salida esperada:
// Suma: 10
// Vacia: true
