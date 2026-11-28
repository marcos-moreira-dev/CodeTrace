// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 ConcurrenciaVirtual.java
// Ejecutar: java ConcurrenciaVirtual

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Cliente de tareas y virtual threads en Java 21.
 *
 * <p>Un ExecutorService recibe tareas; Future permite conocer el resultado
 * y observar excepciones. Los hilos virtuales facilitan concurrencia con
 * operaciones bloqueantes, pero no hacen que el cálculo sea más rápido
 * automáticamente ni garantizan paralelismo de CPU.
 *
 * <p>try-with-resources cierra el executor y espera a sus tareas.
 * La demostración usa operaciones deterministas, sin Internet.
 */
public class ConcurrenciaVirtual {
    public static void main(String[] args) throws Exception {
        try (ExecutorService tareas = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> primero = tareas.submit(() -> 20 + 22);
            Future<String> segundo = tareas.submit(() -> "tarea finalizada");
            System.out.println(primero.get());
            System.out.println(segundo.get());
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ConcurrenciaVirtual.java
 *   java ConcurrenciaVirtual
 * Salida esperada:
 *   42
 *   tarea finalizada
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
