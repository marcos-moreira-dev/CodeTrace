// Tema:
// KNN clasifica por mayoría de k vecinos más cercanos. Aquí las características tienen igual escala, se usa Manhattan y se resuelven empates de distancia por orden de muestra y de voto por etiqueta menor.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ClasificadorKNN.java
// Ejecutar: java ClasificadorKNN

public class ClasificadorKNN {
    static int clasificar(int[][] muestras, int[] clases, int[] punto, int k) {
        if (k < 1 || k > muestras.length || clases.length != muestras.length)
            throw new IllegalArgumentException("Datos");
        boolean[] usados = new boolean[muestras.length];
        int[] votos = new int[2];
        for (int vecino = 0; vecino < k; vecino++) {
            int mejor = -1;
            long menor = Long.MAX_VALUE;
            for (int i = 0; i < muestras.length; i++) {
                if (usados[i]) continue;
                long d = 0;
                for (int j = 0; j < punto.length; j++)
                    d += Math.abs((long) punto[j] - muestras[i][j]);
                if (d < menor) { menor = d; mejor = i; }
            }
            usados[mejor] = true;
            votos[clases[mejor]]++;
        }
        return votos[1] > votos[0] ? 1 : 0;
    }

    public static void main(String[] args) {
        int[][] muestras = {{1,1},{2,1},{1,2},{8,8},{9,8},{8,9}};
        int[] clases = {0,0,0,1,1,1};
        System.out.println("Cerca de origen: " + clasificar(muestras, clases, new int[]{2,2}, 3));
        System.out.println("Lejos de origen: " + clasificar(muestras, clases, new int[]{9,9}, 3));
    }
}

// Salida esperada:
// Cerca de origen: 0
// Lejos de origen: 1
