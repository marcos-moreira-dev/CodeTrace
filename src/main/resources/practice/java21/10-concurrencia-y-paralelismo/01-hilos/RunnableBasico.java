// Tema:
// Runnable encapsula una tarea sin resultado. Puede ejecutarse en un hilo creado explícitamente.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RunnableBasico.java
// Ejecutar: java RunnableBasico

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class RunnableBasico {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable tarea = () -> System.out.println("Tarea ejecutada");
        Thread hilo = new Thread(tarea);
        hilo.start();
        esperar(hilo);
    }
}

// Salida esperada:
// Tarea ejecutada
