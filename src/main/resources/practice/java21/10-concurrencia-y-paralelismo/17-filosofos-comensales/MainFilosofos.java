// Tema:
// Cinco filósofos comparten cinco tenedores. Cada uno toma sus dos tenedores en orden creciente de identificador, rompiendo la espera circular. El resultado final no depende del orden de comida.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Mesa.java Filosofo.java MainFilosofos.java
// Ejecutar: java MainFilosofos

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainFilosofos {
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
        Mesa mesa = new Mesa(5);
        Runnable[] tareas = new Runnable[5];
        for (int i = 0; i < tareas.length; i++) tareas[i] = new Filosofo(mesa, i);
        ejecutar(tareas);
        System.out.println("Comidas: " + mesa.comidas());
    }
}

// Salida esperada:
// Comidas: 5
