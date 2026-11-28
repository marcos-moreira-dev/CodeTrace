// Tema:
// La transposición intercambia filas y columnas: B[j][i] = A[i][j].
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 Transpuesta.java
// Ejecutar: java Transpuesta

public class Transpuesta {
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};
        int[][] b = new int[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) b[j][i] = a[i][j];
        }
        for (int[] fila : b) {
            System.out.println(fila[0] + " " + fila[1]);
        }
    }
}

// Salida esperada:
// 1 4
// 2 5
// 3 6
