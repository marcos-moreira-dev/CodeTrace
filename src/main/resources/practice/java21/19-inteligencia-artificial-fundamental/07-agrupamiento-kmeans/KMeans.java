// Tema:
// K-means alterna asignación al centroide más próximo y recalcula medias. Utiliza dos centros elegidos con semilla fija de dos mitades de muestras ordenadas, conserva un centro vacío y limita iteraciones. No garantiza óptimo global.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 KMeans.java
// Ejecutar: java KMeans

import java.util.Random;
import java.util.Arrays;

public class KMeans {
    public static void main(String[] args) {
        double[] puntos = {1,2,3,9,10,11};
        Random azar = new Random(21);
        double[] centros = {puntos[azar.nextInt(3)], puntos[3 + azar.nextInt(3)]};
        for (int iteracion = 0; iteracion < 20; iteracion++) {
            double[] suma = new double[2];
            int[] cuenta = new int[2];
            for (double p : puntos) {
                int grupo = Math.abs(p-centros[0]) <= Math.abs(p-centros[1]) ? 0 : 1;
                suma[grupo] += p;
                cuenta[grupo]++;
            }
            boolean cambio = false;
            for (int i = 0; i < 2; i++) {
                if (cuenta[i] == 0) continue;
                double nuevo = suma[i] / cuenta[i];
                if (nuevo != centros[i]) cambio = true;
                centros[i] = nuevo;
            }
            if (!cambio) break;
        }
        System.out.println("Centros: " + Arrays.toString(centros));
    }
}

// Salida esperada:
// Centros: [2.0, 10.0]
