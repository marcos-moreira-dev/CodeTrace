// Tema:
// NEW precede a start; un hilo esperando un latch puede entrar en WAITING. Se coordina la observación sin asumir un orden temporal arbitrario.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 EstadosHilo.java
// Ejecutar: java EstadosHilo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class EstadosHilo {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch salida = new CountDownLatch(1);
        Thread hilo = new Thread(() -> {
            try { salida.await(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        hilo.setDaemon(true);
        System.out.println(hilo.getState());
        hilo.start();
        try {
            long limite = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
            while (hilo.getState() != Thread.State.WAITING && System.nanoTime() < limite)
                Thread.sleep(1);
            if (hilo.getState() != Thread.State.WAITING) throw new IllegalStateException("Estado no observado");
            System.out.println(hilo.getState());
        } finally {
            salida.countDown();
        }
        esperar(hilo);
        System.out.println(hilo.getState());
    }
}

// Salida esperada:
// NEW
// WAITING
// TERMINATED
