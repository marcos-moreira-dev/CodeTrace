// Tema:
// ReentrantReadWriteLock permite varios lectores o un escritor exclusivo. La opción fair ofrece una política aproximadamente por llegada, no un orden FIFO absoluto.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java MainLectoresEscritoresEquitativo.java
// Ejecutar: java MainLectoresEscritoresEquitativo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainLectoresEscritoresEquitativo {
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
        RecursoCompartido recurso = new RecursoCompartido();
        ejecutar(() -> recurso.leer(), recurso::escribir, recurso::escribir, () -> recurso.leer());
        System.out.println("Valor final: " + recurso.leer());
    }
}

// Salida esperada:
// Valor final: 2
