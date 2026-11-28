// Tema:
// Prethreading: crear trabajadores antes de recibir las tareas. prestartAllCoreThreads inicia los hilos núcleo; luego se asignan solicitudes simuladas a ese conjunto reutilizable.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 MainPrethreading.java
// Ejecutar: java MainPrethreading

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainPrethreading {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4));
        try {
            System.out.println("Precreados: " + pool.prestartAllCoreThreads());
            Future<String> a = pool.submit(() -> "Solicitud A");
            Future<String> b = pool.submit(() -> "Solicitud B");
            System.out.println(a.get(3, TimeUnit.SECONDS));
            System.out.println(b.get(3, TimeUnit.SECONDS));
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// Precreados: 2
// Solicitud A
// Solicitud B
