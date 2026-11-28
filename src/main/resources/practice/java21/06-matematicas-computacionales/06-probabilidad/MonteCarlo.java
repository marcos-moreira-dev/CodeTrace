// Tema:
// En un cuadrado unitario, la fracción de puntos dentro de un cuarto de círculo
// aproxima pi/4. La tolerancia expresa error de muestreo, no igualdad exacta.
// Modalidad: modelo matemático.
// Compilar: javac --release 21 -encoding UTF-8 MonteCarlo.java
// Ejecutar: java MonteCarlo

import java.util.Random;

public class MonteCarlo {
    public static void main(String[] args) {
        Random azar = new Random(42);
        int dentro = 0, pruebas = 20000;
        for (int i = 0; i < pruebas; i++) {
            double x = azar.nextDouble(), y = azar.nextDouble();
            if (x * x + y * y <= 1) dentro++;
        }
        double estimacion = 4.0 * dentro / pruebas;
        System.out.println(Math.abs(estimacion - Math.PI) < 0.1);
    }
}

// Salida esperada:
// true
