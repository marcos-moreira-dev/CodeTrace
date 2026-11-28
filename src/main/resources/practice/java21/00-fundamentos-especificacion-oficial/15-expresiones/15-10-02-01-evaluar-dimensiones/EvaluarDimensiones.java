// Tema:
// Las expresiones de dimensiones de un arreglo se evalúan de izquierda a derecha antes de
// crear el arreglo. Un cambio de variable posterior no altera una dimensión ya evaluada.
// Compilar: javac --release 21 -encoding UTF-8 -d . EvaluarDimensiones.java
// Ejecutar: java EvaluarDimensiones
// Comandos desde la carpeta de la unidad.

public class EvaluarDimensiones {
    public static void main(String[] args) {
        int n = 5;
        int[][] matriz = new int[n][n = 2];
        System.out.println(matriz.length + " " + matriz[0].length);
        System.out.println(n);
    }
}

// Salida esperada:
// 5 2
// 2
