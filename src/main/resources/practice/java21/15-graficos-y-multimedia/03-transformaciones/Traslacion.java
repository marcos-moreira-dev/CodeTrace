// Tema:
// Una traslación suma un desplazamiento a las coordenadas. Se utiliza una matriz homogénea 3x3 para representar una transformación afín.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Traslacion.java
// Ejecutar: java Traslacion

public class Traslacion {
    public static void main(String[] args) throws Exception {
        int[][] matriz = {{1,0,3}, {0,1,-2}, {0,0,1}};
        int[] punto = {2, 4, 1};
        int[] resultado = new int[3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) resultado[i] += matriz[i][j] * punto[j];
        System.out.println(resultado[0] + " " + resultado[1]);
    }
}

// Salida esperada:
// 5 2
