// Tema:
// Un intento con tiempo límite evita mantener indefinidamente el primer recurso mientras se espera el segundo. El ejemplo fuerza el fallo y verifica que se devuelve el primer lock.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecuperacionTryLock.java
// Ejecutar: java RecuperacionTryLock

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class RecuperacionTryLock {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock primero = new ReentrantLock(), segundo = new ReentrantLock();
        FutureTask<Boolean> trabajo = new FutureTask<>(() -> {
            primero.lock();
            try {
                if (!segundo.tryLock(50, TimeUnit.MILLISECONDS)) return false;
                try { return true; }
                finally { segundo.unlock(); }
            } finally { primero.unlock(); }
        });
        segundo.lock();
        try {
            iniciar(trabajo);
            System.out.println("Operacion: " + trabajo.get(3, TimeUnit.SECONDS));
            boolean libre = primero.tryLock();
            try { System.out.println("Primer recurso libre: " + libre); }
            finally { if (libre) primero.unlock(); }
        } finally { segundo.unlock(); }
    }
}

// Salida esperada:
// Operacion: false
// Primer recurso libre: true
