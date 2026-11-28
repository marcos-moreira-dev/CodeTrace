// Tema:
// Escalar multiplica las coordenadas por factores independientes. Se escala respecto al origen; no se conserva necesariamente la forma si los factores difieren.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Escalado.java
// Ejecutar: java Escalado

public class Escalado {
    public static void main(String[] args) throws Exception {
        int[][] matriz = {{2,0}, {0,3}};
        int[] punto = {4, 2};
        int[] resultado = new int[2];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) resultado[i] += matriz[i][j] * punto[j];
        System.out.println(resultado[0] + " " + resultado[1]);
    }
}

// Salida esperada:
// 8 6
