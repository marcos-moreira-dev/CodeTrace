// Tema:
// Sincronizar protege el buffer compartido durante el cálculo. Devolver una copia defensiva evita entregar un alias modificable por otras llamadas.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 FuncionThreadSafe.java
// Ejecutar: java FuncionThreadSafe

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class FuncionThreadSafe {
    static final int[] buffer = new int[1];
    static synchronized int[] calcular(int n) {
        buffer[0] = n * n;
        return buffer.clone();
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
        int[][] resultados = new int[2][];
        Thread a = iniciar(() -> resultados[0] = calcular(2));
        Thread b = iniciar(() -> resultados[1] = calcular(5));
        esperar(a);
        esperar(b);
        System.out.println(resultados[0][0] + " " + resultados[1][0]);
    }
}

// Salida esperada:
// 4 25
