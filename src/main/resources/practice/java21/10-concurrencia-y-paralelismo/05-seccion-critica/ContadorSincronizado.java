// Tema:
// synchronized protege la sección crítica con el monitor de la clase. Los incrementos se excluyen mutuamente.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ContadorSincronizado.java
// Ejecutar: java ContadorSincronizado

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ContadorSincronizado {
    static int contador;
    static synchronized void sumarUno() { contador++; }
    static void incrementar() { for (int i = 0; i < 1000; i++) sumarUno(); }
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
        Thread a = iniciar(ContadorSincronizado::incrementar);
        Thread b = iniciar(ContadorSincronizado::incrementar);
        esperar(a);
        esperar(b);
        System.out.println("Contador: " + contador);
    }
}

// Salida esperada:
// Contador: 2000
