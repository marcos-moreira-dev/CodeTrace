// Tema:
// Operaciones individuales son seguras entre hilos, pero get seguido de put no es una actualización atómica. merge realiza la actualización por clave de forma atómica.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ConcurrentHashMapDemo.java
// Ejecutar: java ConcurrentHashMapDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ConcurrentHashMapDemo {
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
        ConcurrentHashMap<String, Integer> ventas = new ConcurrentHashMap<>();
        Runnable tarea = () -> {
            for (int i = 0; i < 500; i++) ventas.merge("pan", 1, Integer::sum);
        };
        ejecutar(tarea, tarea);
        System.out.println(ventas.get("pan"));
    }
}

// Salida esperada:
// 1000
