// Tema:
// Una función que solo usa parámetros y variables locales no depende de estado mutable compartido. Llamadas simultáneas independientes pueden ejecutarse sin lock.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 FuncionReentrante.java
// Ejecutar: java FuncionReentrante

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class FuncionReentrante {
    static int calcular(int n) { return n * n; }
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
        int[] resultado = new int[2];
        Thread a = iniciar(() -> resultado[0] = calcular(2));
        Thread b = iniciar(() -> resultado[1] = calcular(5));
        esperar(a);
        esperar(b);
        System.out.println(resultado[0] + " " + resultado[1]);
    }
}

// Salida esperada:
// 4 25
