// Tema:
// ExecutorService separa la tarea del hilo que la ejecuta. Un pool fijo reutiliza trabajadores y limita el número activo, pero su cola estándar no está acotada.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 PoolFijo.java
// Ejecutar: java PoolFijo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class PoolFijo {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<Integer> a = pool.submit(() -> 4);
            Future<Integer> b = pool.submit(() -> 9);
            System.out.println(a.get(3, TimeUnit.SECONDS) + b.get(3, TimeUnit.SECONDS));
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// 13
