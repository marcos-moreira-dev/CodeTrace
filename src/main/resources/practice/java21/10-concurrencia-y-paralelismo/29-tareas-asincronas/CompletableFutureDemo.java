// Tema:
// CompletableFuture permite componer resultados sin esperar manualmente cada etapa. Usamos un ejecutor explícito y recuperamos un fallo de ejemplo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CompletableFutureDemo.java
// Ejecutar: java CompletableFutureDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 4, pool);
            CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 6, pool);
            System.out.println(a.thenCombine(b, Integer::sum).get(3, TimeUnit.SECONDS));
            CompletableFuture<Integer> fallo = CompletableFuture.failedFuture(new IllegalStateException("Dato"));
            System.out.println(fallo.exceptionally(e -> 0).get(3, TimeUnit.SECONDS));
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// 10
// 0
