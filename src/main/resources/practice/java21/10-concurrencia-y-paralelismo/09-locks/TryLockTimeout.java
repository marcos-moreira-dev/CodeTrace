// Tema:
// tryLock con plazo permite abandonar una espera. El hilo principal conserva el lock hasta que el segundo termina su intento.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 TryLockTimeout.java
// Ejecutar: java TryLockTimeout

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class TryLockTimeout {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        FutureTask<Boolean> tarea = new FutureTask<>(() -> {
            boolean obtenido = lock.tryLock(50, TimeUnit.MILLISECONDS);
            if (obtenido) lock.unlock();
            return obtenido;
        });
        lock.lock();
        try {
            iniciar(tarea);
            System.out.println("Obtenido: " + tarea.get(3, TimeUnit.SECONDS));
        } finally { lock.unlock(); }
    }
}

// Salida esperada:
// Obtenido: false
