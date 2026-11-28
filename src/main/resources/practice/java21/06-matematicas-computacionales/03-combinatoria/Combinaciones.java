// Tema:
// C(n,k) cuenta selecciones sin importar su orden. El producto incremental
// calcula el coeficiente binomial sin factoriales completos (para valores pequeños).
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 Combinaciones.java
// Ejecutar: java Combinaciones

public class Combinaciones {
    public static void main(String[] args) {
        int n = 5, k = 3;
        long total = 1;
        for (int i = 1; i <= k; i++) {
            total = total * (n - k + i) / i;
        }
        System.out.println(total);
    }
}

// Salida esperada:
// 10
