// Tema:
// Algoritmo genético elemental: aptitud es número de unos en ocho bits. Selección por torneo, cruce en un punto, mutación de un bit y elitismo. Semilla fija, población y generaciones acotadas; el elitismo garantiza no empeorar el mejor, no alcanzar el óptimo.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 SeleccionMutacion.java
// Ejecutar: java SeleccionMutacion

import java.util.Random;

public class SeleccionMutacion {
    static int aptitud(int cromosoma) { return Integer.bitCount(cromosoma); }
    static int mejor(int[] poblacion) {
        int mejor = poblacion[0];
        for (int x : poblacion) if (aptitud(x) > aptitud(mejor)) mejor = x;
        return mejor;
    }
    static int torneo(int[] poblacion, Random azar) {
        int a = poblacion[azar.nextInt(poblacion.length)];
        int b = poblacion[azar.nextInt(poblacion.length)];
        return aptitud(a) >= aptitud(b) ? a : b;
    }

    public static void main(String[] args) {
        Random azar = new Random(21);
        int[] poblacion = new int[12];
        for (int i = 0; i < poblacion.length; i++) poblacion[i] = azar.nextInt(256);
        int inicial = aptitud(mejor(poblacion));
        for (int generacion = 0; generacion < 40; generacion++) {
            int[] nueva = new int[poblacion.length];
            nueva[0] = mejor(poblacion);
            for (int i = 1; i < nueva.length; i++) {
                int a = torneo(poblacion,azar), b = torneo(poblacion,azar);
                int punto = 1 + azar.nextInt(7), mascara = (1 << punto) - 1;
                int hijo = (a & mascara) | (b & (255 ^ mascara));
                if (azar.nextDouble() < 0.3) hijo ^= 1 << azar.nextInt(8);
                nueva[i] = hijo;
            }
            poblacion = nueva;
        }
        int fin = aptitud(mejor(poblacion));
        System.out.println("No empeora: " + (fin >= inicial));
        System.out.println("Aptitud valida: " + (fin >= 0 && fin <= 8));
    }
}

// Salida esperada:
// No empeora: true
// Aptitud valida: true
