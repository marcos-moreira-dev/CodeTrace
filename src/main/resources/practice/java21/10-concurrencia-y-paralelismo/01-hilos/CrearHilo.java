// Tema:
// start inicia un hilo distinto; run por sí solo sería una llamada normal. join establece visibilidad de los resultados al terminar.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CrearHilo.java
// Ejecutar: java CrearHilo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CrearHilo {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread hilo = new Thread(() -> System.out.println("Trabajo del hilo"));
        hilo.start();
        esperar(hilo);
        System.out.println("Main termina");
    }
}

// Salida esperada:
// Trabajo del hilo
// Main termina
