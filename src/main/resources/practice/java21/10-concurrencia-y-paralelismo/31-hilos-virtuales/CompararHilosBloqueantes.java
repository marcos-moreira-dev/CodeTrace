// Tema:
// Java 21 permite un hilo virtual por tarea bloqueante. Ejecutamos el mismo lote pequeño con hilos de plataforma y virtuales y comparamos resultados, no rendimiento. Los hilos virtuales no aceleran automáticamente cálculo intensivo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CompararHilosBloqueantes.java
// Ejecutar: java CompararHilosBloqueantes

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CompararHilosBloqueantes {
    static int lote(ExecutorService pool) throws Exception {
        java.util.ArrayList<Future<Integer>> tareas = new java.util.ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            int dato = i;
            tareas.add(pool.submit(() -> { Thread.sleep(10); return dato; }));
        }
        int total = 0;
        for (Future<Integer> tarea : tareas) total += tarea.get(3, TimeUnit.SECONDS);
        return total;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService plataforma = Executors.newFixedThreadPool(2);
        ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor();
        try {
            System.out.println("Plataforma: " + lote(plataforma));
            System.out.println("Virtual: " + lote(virtual));
        } finally {
            plataforma.shutdownNow();
            virtual.shutdownNow();
        }
    }
}

// Salida esperada:
// Plataforma: 10
// Virtual: 10
