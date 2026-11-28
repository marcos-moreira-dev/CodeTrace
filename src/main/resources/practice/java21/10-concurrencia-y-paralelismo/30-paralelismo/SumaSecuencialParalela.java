// Tema:
// Dividir un arreglo en rangos independientes permite sumar parciales y combinar resultados. Se compara exactitud, no tiempos ni supuesta aceleración.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 SumaSecuencialParalela.java
// Ejecutar: java SumaSecuencialParalela

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class SumaSecuencialParalela {
    static int sumar(int[] datos, int desde, int hasta) {
        int suma = 0;
        for (int i = desde; i < hasta; i++) suma += datos[i];
        return suma;
    }

    public static void main(String[] args) throws Exception {
        int[] datos = {1, 2, 3, 4, 5, 6};
        int secuencial = sumar(datos, 0, datos.length);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            int mitad = datos.length / 2;
            Future<Integer> a = pool.submit(() -> sumar(datos, 0, mitad));
            Future<Integer> b = pool.submit(() -> sumar(datos, mitad, datos.length));
            int paralelo = a.get(3, TimeUnit.SECONDS) + b.get(3, TimeUnit.SECONDS);
            System.out.println("Secuencial: " + secuencial);
            System.out.println("Paralelo: " + paralelo);
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// Secuencial: 21
// Paralelo: 21
