// Tema:
// Un semáforo de un permiso puede proteger la sección crítica. Se libera únicamente tras adquirir; a diferencia de un cerrojo (lock), no tiene propiedad de hilo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 SemaforoBinario.java
// Ejecutar: java SemaforoBinario

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class SemaforoBinario {
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
        Semaphore sem = new Semaphore(1);
        int[] contador = {0};
        Runnable tarea = () -> {
            for (int i = 0; i < 500; i++) {
                sem.acquireUninterruptibly();
                try { contador[0]++; }
                finally { sem.release(); }
            }
        };
        Thread a = iniciar(tarea);
        Thread b = iniciar(tarea);
        esperar(a);
        esperar(b);
        System.out.println(contador[0]);
    }
}

// Salida esperada:
// 1000
