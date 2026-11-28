// Tema:
// ++ no es una operación indivisible. Se separan sus pasos para demostrar una pérdida de actualización, sin depender de la suerte del planificador.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ContadorInseguro.java
// Ejecutar: java ContadorInseguro

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ContadorInseguro {
    static int contador;
    static final CyclicBarrier barrera = new CyclicBarrier(2);

    static void incrementar() {
        int lectura = contador;
        try {
            // Ambos leen antes de que cualquiera escriba.
            barrera.await(3, TimeUnit.SECONDS);
        } catch (Exception e) { throw new IllegalStateException(e); }
        contador = lectura + 1;
    }
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
        Thread a = iniciar(ContadorInseguro::incrementar);
        Thread b = iniciar(ContadorInseguro::incrementar);
        esperar(a);
        esperar(b);
        System.out.println("Contador: " + contador);
    }
}

// Salida esperada:
// Contador: 1
