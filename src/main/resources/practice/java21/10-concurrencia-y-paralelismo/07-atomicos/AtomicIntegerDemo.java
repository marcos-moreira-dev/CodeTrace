// Tema:
// AtomicInteger permite incrementar un contador compartido sin separar lectura y escritura.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 AtomicIntegerDemo.java
// Ejecutar: java AtomicIntegerDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class AtomicIntegerDemo {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger contador = new AtomicInteger();
        Runnable tarea = () -> {
            for (int i = 0; i < 1000; i++) contador.incrementAndGet();
        };
        Thread a = iniciar(tarea);
        Thread b = iniciar(tarea);
        esperar(a);
        esperar(b);
        System.out.println(contador.get());
    }
}

// Salida esperada:
// 2000
