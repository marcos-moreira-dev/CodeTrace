// Tema:
// Newton aproxima una raíz con x - f(x)/f'(x). Para x*x-2 se obtiene
// (x + 2/x)/2; se parte de un valor positivo distinto de cero.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 NewtonRaphson.java
// Ejecutar: java NewtonRaphson

public class NewtonRaphson {
    public static void main(String[] args) {
        double x = 2;
        for (int i = 0; i < 8; i++) x = (x + 2 / x) / 2;
        System.out.printf(java.util.Locale.ROOT, "%.6f%n", x);
    }
}

// Salida esperada:
// 1.414214
