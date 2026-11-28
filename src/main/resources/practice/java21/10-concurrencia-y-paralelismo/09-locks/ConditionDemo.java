// Tema:
// Condition.await libera temporalmente el lock y lo recupera antes de retornar. Se comprueba el predicado en un bucle para tolerar despertares espurios.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ConditionDemo.java
// Ejecutar: java ConditionDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ConditionDemo {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition cambio = lock.newCondition();
        boolean[] listo = {false};
        FutureTask<Boolean> tarea = new FutureTask<>(() -> {
            lock.lock();
            try {
                long restante = TimeUnit.SECONDS.toNanos(3);
                while (!listo[0] && restante > 0) restante = cambio.awaitNanos(restante);
                return listo[0];
            } finally { lock.unlock(); }
        });
        iniciar(tarea);
        lock.lock();
        try {
            listo[0] = true;
            cambio.signalAll();
        } finally { lock.unlock(); }
        System.out.println("Listo: " + tarea.get(4, TimeUnit.SECONDS));
    }
}

// Salida esperada:
// Listo: true
