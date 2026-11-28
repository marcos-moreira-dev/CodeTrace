// Tema:
// ThreadPoolExecutor con un trabajador y una posición de cola. Se mantiene el trabajador ocupado mientras se llena la cola; una tercera tarea se rechaza con AbortPolicy.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 PoolColaAcotada.java
// Ejecutar: java PoolColaAcotada

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class PoolColaAcotada {
    public static void main(String[] args) throws Exception {
        CountDownLatch liberar = new CountDownLatch(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());
        try {
            Future<?> primero = pool.submit(() -> {
                if (!liberar.await(3, TimeUnit.SECONDS)) throw new IllegalStateException("Tiempo");
                return null;
            });
            Future<Integer> segundo = pool.submit(() -> 7);
            try {
                pool.submit(() -> 9);
                throw new AssertionError("Se esperaba rechazo");
            } catch (RejectedExecutionException e) {
                System.out.println("Tarea rechazada");
            }
            liberar.countDown();
            primero.get(3, TimeUnit.SECONDS);
            System.out.println(segundo.get(3, TimeUnit.SECONDS));
        } finally {
            liberar.countDown();
            pool.shutdownNow();
        }
    }
}

// Salida esperada:
// Tarea rechazada
// 7
