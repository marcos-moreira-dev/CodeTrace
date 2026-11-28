// Tema:
// Si una función continua cambia de signo en un intervalo, la bisección
// reduce ese intervalo a la mitad conservando una raíz. Aquí f(x)=x*x-2.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 Biseccion.java
// Ejecutar: java Biseccion

public class Biseccion {
    public static void main(String[] args) {
        double a = 1, b = 2;
        for (int i = 0; i < 40; i++) {
            double medio = (a + b) / 2;
            if (medio * medio - 2 > 0) b = medio;
            else a = medio;
        }
        System.out.printf(java.util.Locale.ROOT, "%.6f%n", (a + b) / 2);
    }
}

// Salida esperada:
// 1.414214
