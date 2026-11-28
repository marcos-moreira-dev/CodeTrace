// Tema:
// La frecuencia relativa aproxima una probabilidad al repetir experimentos.
// Una semilla fija permite reproducir esta simulación de un dado justo.
// Modalidad: modelo matemático.
// Compilar: javac --release 21 -encoding UTF-8 ProbabilidadFrecuencia.java
// Ejecutar: java ProbabilidadFrecuencia

import java.util.Random;

public class ProbabilidadFrecuencia {
    public static void main(String[] args) {
        Random azar = new Random(42);
        int exitos = 0, pruebas = 6000;
        for (int i = 0; i < pruebas; i++) {
            if (azar.nextInt(6) == 0) exitos++;
        }
        double frecuencia = (double) exitos / pruebas;
        System.out.println(frecuencia > 0.14 && frecuencia < 0.20);
    }
}

// Salida esperada:
// true
