// Tema:
// Orden global de adquisición: aunque dos operaciones soliciten recursos en orden lógico distinto, ambas toman primero el lock de menor identificador. Se rompe la espera circular.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 OrdenGlobalLocks.java
// Ejecutar: java OrdenGlobalLocks

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class OrdenGlobalLocks {
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
    static void usar(ReentrantLock[] locks, int a, int b, AtomicInteger hechos) {
        int primero = Math.min(a, b), segundo = Math.max(a, b);
        locks[primero].lock();
        try {
            locks[segundo].lock();
            try { hechos.incrementAndGet(); }
            finally { locks[segundo].unlock(); }
        } finally { locks[primero].unlock(); }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock[] locks = {new ReentrantLock(), new ReentrantLock()};
        AtomicInteger hechos = new AtomicInteger();
        ejecutar(() -> usar(locks, 0, 1, hechos), () -> usar(locks, 1, 0, hechos));
        System.out.println("Completados: " + hechos.get());
    }
}

// Salida esperada:
// Completados: 2
