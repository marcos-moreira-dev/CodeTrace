// Tema:
// join espera la terminación de otro hilo. Los trabajadores escriben en posiciones distintas y main lee después de ambos joins.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 JoinHilos.java
// Ejecutar: java JoinHilos

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class JoinHilos {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        int[] resultados = new int[2];
        Thread a = new Thread(() -> resultados[0] = 4);
        Thread b = new Thread(() -> resultados[1] = 9);
        a.start();
        b.start();
        esperar(a);
        esperar(b);
        System.out.println(resultados[0] + resultados[1]);
    }
}

// Salida esperada:
// 13
