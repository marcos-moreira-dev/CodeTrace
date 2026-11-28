// Tema:
// La suma exige matrices de iguales dimensiones. Cada celda se suma
// con la celda correspondiente de la otra matriz.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 SumaMatrices.java
// Ejecutar: java SumaMatrices

public class SumaMatrices {
    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{5, 6}, {7, 8}};
        int[][] c = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                c[i][j] = a[i][j] + b[i][j];
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
    }
}

// Salida esperada:
// 6 8 
// 10 12 
