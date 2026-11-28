// Tema:
// Prioridad de escritores: el primer escritor cierra la entrada y el último la reabre; lectores activos pueden terminar. No garantiza equidad absoluta y puede aplazar lectores.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java Lector.java Escritor.java MainPrioridadEscritores.java
// Ejecutar: java MainPrioridadEscritores

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainPrioridadEscritores {
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
        ejecutar(new Lector(recurso), new Escritor(recurso), new Lector(recurso), new Escritor(recurso));
        System.out.println("Valor final: " + recurso.leer());
    }
}

// Salida esperada:
// Valor final: 2
