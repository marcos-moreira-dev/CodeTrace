// Tema:
// Exactitud mide aciertos globales, precisión la proporción de positivos predichos correctos y exhaustividad la proporción de positivos reales detectados. Ante denominador cero la métrica se marca indefinida; no se convierte arbitrariamente en cero.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 MetricasClasificacion.java
// Ejecutar: java MetricasClasificacion

public class MetricasClasificacion {
    static void mostrar(String nombre, int numerador, int denominador) {
        if (denominador == 0) System.out.println(nombre + ": indefinida");
        else System.out.printf(java.util.Locale.ROOT, "%s: %.2f%n", nombre, (double) numerador / denominador);
    }

    public static void main(String[] args) {
        int vp = 3, vn = 4, fp = 1, fn = 2;
        mostrar("Exactitud", vp+vn, vp+vn+fp+fn);
        mostrar("Precision", vp, vp+fp);
        mostrar("Exhaustividad", vp, vp+fn);
        mostrar("Sin positivos predichos", 0, 0);
    }
}

// Salida esperada:
// Exactitud: 0.70
// Precision: 0.75
// Exhaustividad: 0.60
// Sin positivos predichos: indefinida
