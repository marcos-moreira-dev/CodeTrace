// Tema:
// La regla del trapecio aproxima el área bajo f(x)=x*x en [0,1].
// Las muestras de los extremos pesan la mitad que las interiores.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 IntegracionTrapecios.java
// Ejecutar: java IntegracionTrapecios

public class IntegracionTrapecios {
    public static void main(String[] args) {
        int n = 1000;
        double h = 1.0 / n;
        double suma = 0.5; // (f(0) + f(1)) / 2
        for (int i = 1; i < n; i++) {
            double x = i * h;
            suma += x * x;
        }
        System.out.printf(java.util.Locale.ROOT, "%.6f%n", h * suma);
    }
}

// Salida esperada:
// 0.333334
