// Tema:
// Una escritura volatile publica las escrituras previas a un lector que observa true. El dato es ordinario; la bandera volatile establece la relación happens-before.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 PublicacionSegura.java
// Ejecutar: java PublicacionSegura

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class PublicacionSegura {
    static int dato;
    static volatile boolean listo;
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
        Thread lector = iniciar(() -> {
            long limite = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
            while (!listo && System.nanoTime() < limite) Thread.onSpinWait();
            if (!listo) throw new IllegalStateException("No publicado");
            System.out.println("Dato: " + dato);
        });
        dato = 42;
        listo = true;
        esperar(lector);
    }
}

// Salida esperada:
// Dato: 42
