// Tema:
// La distancia de Hamming entre dos imágenes binarias cuenta píxeles distintos. Las matrices son 5x5 y sus posiciones se corresponden; no tolera desplazamiento o escala automáticamente.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 CompararMatrices.java
// Ejecutar: java CompararMatrices

public class CompararMatrices {
    static int distancia(int[][] a, int[][] b) {
        int total = 0;
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++)
                if (a[y][x] != b[y][x]) total++;
        return total;
    }

    public static void main(String[] args) {
        int[][] a = {
            {0,1,1,1,0}, {0,1,0,1,0}, {0,1,0,1,0}, {0,1,0,1,0}, {0,1,1,1,0}
        };
        int[][] b = new int[5][5];
        for (int i = 0; i < 5; i++) b[i] = a[i].clone();
        b[0][0] = 1;
        b[4][2] = 0;
        System.out.println("Identicas: " + distancia(a,a));
        System.out.println("Diferencias: " + distancia(a,b));
    }
}

// Salida esperada:
// Identicas: 0
// Diferencias: 2
