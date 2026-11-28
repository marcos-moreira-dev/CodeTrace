// Tema:
// La prueba coordina el inicio de dos tareas con un latch y propaga errores mediante Future.get. Se comprueba un resultado determinista, no el orden de ejecución.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 PruebaContador.java
// Ejecutar: java PruebaContador

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PruebaContador {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CountDownLatch inicio = new CountDownLatch(1);
        AtomicInteger contador = new AtomicInteger();
        try {
            Callable<Void> tarea = () -> {
                if (!inicio.await(3, TimeUnit.SECONDS)) throw new AssertionError("Inicio");
                for (int i = 0; i < 1000; i++) contador.incrementAndGet();
                return null;
            };
            Future<Void> a = pool.submit(tarea), b = pool.submit(tarea);
            inicio.countDown();
            a.get(3, TimeUnit.SECONDS);
            b.get(3, TimeUnit.SECONDS);
            if (contador.get() != 2000) throw new AssertionError("Contador");
            System.out.println("Contador: 2000 OK");
        } finally { inicio.countDown(); pool.shutdownNow(); }
    }
}

// Salida esperada:
// Contador: 2000 OK
