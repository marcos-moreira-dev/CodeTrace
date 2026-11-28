// Tema:
// Los hilos comparten un objeto de la misma JVM. AtomicInteger protege el incremento; join permite observar el resultado final.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 VariablesCompartidas.java
// Ejecutar: java VariablesCompartidas

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class VariablesCompartidas {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger cantidad = new AtomicInteger(10);
        Thread a = new Thread(() -> cantidad.addAndGet(3));
        Thread b = new Thread(() -> cantidad.addAndGet(2));
        a.start();
        b.start();
        esperar(a);
        esperar(b);
        System.out.println(cantidad.get());
    }
}

// Salida esperada:
// 15
