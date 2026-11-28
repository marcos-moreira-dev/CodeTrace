// Tema:
// Un perceptrón binario aprende una separación lineal mediante pesos y sesgo. Se entrenan etiquetas AND y OR con los mismos datos de entrada; el algoritmo no puede representar XOR con una sola frontera. Los datos son lógicos, no circuitos.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 Perceptron.java
// Ejecutar: java Perceptron

public class Perceptron {
    private final int[] pesos = new int[2];
    private int sesgo;
    int predecir(int[] x) {
        int suma = sesgo;
        for (int i = 0; i < pesos.length; i++) suma += pesos[i] * x[i];
        return suma >= 0 ? 1 : 0;
    }
    void entrenar(int[][] x, int[] y) {
        for (int epoca = 0; epoca < 30; epoca++) {
            int errores = 0;
            for (int n = 0; n < x.length; n++) {
                int error = y[n] - predecir(x[n]);
                if (error == 0) continue;
                errores++;
                for (int i = 0; i < pesos.length; i++) pesos[i] += error * x[n][i];
                sesgo += error;
            }
            if (errores == 0) return;
        }
    }

    public static void main(String[] args) {
        int[][] entradas = {{0,0},{0,1},{1,0},{1,1}};
        for (int[] etiquetas : new int[][]{{0,0,0,1},{0,1,1,1}}) {
            Perceptron modelo = new Perceptron();
            modelo.entrenar(entradas, etiquetas);
            for (int i = 0; i < entradas.length; i++) {
                int prediccion = modelo.predecir(entradas[i]);
                if (prediccion != etiquetas[i]) throw new AssertionError("No convergio");
                System.out.print(prediccion);
            }
            System.out.println();
        }
    }
}

// Salida esperada:
// 0001
// 0111
