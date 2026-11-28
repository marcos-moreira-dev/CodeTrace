// Tema:
// ReentrantLock delimita una sección crítica. finally garantiza liberar el bloqueo incluso si el trabajo falla.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ReentrantLockDemo.java
// Ejecutar: java ReentrantLockDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ReentrantLockDemo {
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
        ReentrantLock lock = new ReentrantLock();
        int[] saldo = {0};
        Runnable tarea = () -> {
            for (int i = 0; i < 500; i++) {
                lock.lock();
                try { saldo[0]++; }
                finally { lock.unlock(); }
            }
        };
        Thread a = iniciar(tarea);
        Thread b = iniciar(tarea);
        esperar(a);
        esperar(b);
        System.out.println(saldo[0]);
    }
}

// Salida esperada:
// 1000
