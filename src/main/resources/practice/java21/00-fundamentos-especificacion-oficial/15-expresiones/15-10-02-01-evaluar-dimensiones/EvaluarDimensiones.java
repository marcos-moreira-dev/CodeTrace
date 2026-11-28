// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.10.2; ejemplo 15.10.2-1
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
