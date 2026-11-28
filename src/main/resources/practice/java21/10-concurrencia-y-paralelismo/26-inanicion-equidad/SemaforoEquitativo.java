// Tema:
// Un semáforo fair favorece el orden de espera en las adquisiciones bloqueantes. No garantiza justicia del planificador y tryAcquire sin tiempo puede adelantarse. Comprobamos la política configurada y la finalización de tareas.
// Modalidad: modelo y ejecución controlada.
// Compilar: javac --release 21 -encoding UTF-8 SemaforoEquitativo.java
// Ejecutar: java SemaforoEquitativo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class SemaforoEquitativo {
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
        Semaphore permiso = new Semaphore(1, true);
        AtomicInteger hechos = new AtomicInteger();
        Runnable tarea = () -> {
            permiso.acquireUninterruptibly();
            try { hechos.incrementAndGet(); }
            finally { permiso.release(); }
        };
        ejecutar(tarea, tarea, tarea);
        System.out.println("Equitativo: " + permiso.isFair());
        System.out.println("Atendidos: " + hechos.get());
    }
}

// Salida esperada:
// Equitativo: true
// Atendidos: 3
