// Tema:
// Callable puede devolver un resultado o lanzar una excepción. Future representa ese cálculo y get permite obtener el resultado o la causa del fallo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CallableFuture.java
// Ejecutar: java CallableFuture

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CallableFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Future<Integer> bien = pool.submit(() -> 6 * 7);
            Future<Integer> mal = pool.submit(() -> { throw new IllegalArgumentException("Dato"); });
            System.out.println(bien.get(3, TimeUnit.SECONDS));
            try { mal.get(3, TimeUnit.SECONDS); }
            catch (ExecutionException e) { System.out.println(e.getCause().getClass().getSimpleName()); }
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// 42
// IllegalArgumentException
