// Tema:
// P espera/adquiere un permiso; V lo devuelve. El semáforo puede señalizar un evento entre dos hilos.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 OperacionesPV.java
// Ejecutar: java OperacionesPV

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class OperacionesPV {
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
        Semaphore evento = new Semaphore(0);
        Thread emisor = iniciar(evento::release);
        if (!evento.tryAcquire(3, TimeUnit.SECONDS)) throw new IllegalStateException("Sin evento");
        esperar(emisor);
        System.out.println("Evento recibido");
    }
}

// Salida esperada:
// Evento recibido
