// Tema:
// Al ordenar n objetos distintos existen n! permutaciones.
// Este cálculo cuenta las posibilidades sin enumerarlas; se usan valores pequeños.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 Permutaciones.java
// Ejecutar: java Permutaciones

public class Permutaciones {
    public static void main(String[] args) {
        int n = 5;
        long total = 1;
        for (int i = 2; i <= n; i++) {
            total *= i;
        }
        System.out.println(total);
    }
}

// Salida esperada:
// 120
