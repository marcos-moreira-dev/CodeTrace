// Tema:
// ArrayBlockingQueue implementa el buffer acotado y bloquea put/take según capacidad. Un centinela cierra el ejemplo con un consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Productor.java Consumidor.java MainBlockingQueue.java
// Ejecutar: java MainBlockingQueue

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainBlockingQueue {
    static void ejecutar(Runnable... tareas) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(tareas.length)) {
            java.util.ArrayList<Future<?>> resultados = new java.util.ArrayList<>();
            try {
                for (Runnable tarea : tareas) resultados.add(pool.submit(tarea));
                for (Future<?> resultado : resultados) resultado.get(5, TimeUnit.SECONDS);
            } finally { pool.shutdownNow(); }
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(2);
        ejecutar(new Productor(buffer), new Consumidor(buffer));
    }
}

// Salida esperada:
// Suma: 10
