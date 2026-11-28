// Tema:
// Cada llamada tiene sus variables locales independientes; los resultados se depositan en posiciones separadas.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 EstadoLocal.java
// Ejecutar: java EstadoLocal

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class EstadoLocal {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }
    static int calcular(int n) { int cuadrado = n * n; return cuadrado; }

    public static void main(String[] args) throws Exception {
        int[] resultados = new int[2];
        Thread a = new Thread(() -> resultados[0] = calcular(2));
        Thread b = new Thread(() -> resultados[1] = calcular(4));
        a.start();
        b.start();
        esperar(a);
        esperar(b);
        System.out.println(resultados[0] + " " + resultados[1]);
    }
}

// Salida esperada:
// 4 16
