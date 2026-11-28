// Tema:
// Para multiplicar A(m x n) por B(n x p), se acumulan los productos de
// cada fila de A por cada columna de B.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 ProductoMatrices.java
// Ejecutar: java ProductoMatrices

public class ProductoMatrices {
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};
        int[][] b = {{1, 0}, {0, 1}, {1, 1}};
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
    }
}

// Salida esperada:
// 4 5 
// 10 11 
