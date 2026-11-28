// Tema:
// Una matriz de confusión compara etiquetas reales con predicciones de un conjunto de evaluación separado. Las filas corresponden a realidad y las columnas a predicción, ambas en orden 0,1.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 MatrizConfusion.java
// Ejecutar: java MatrizConfusion

public class MatrizConfusion {
    public static void main(String[] args) {
        int[] real = {0,0,0,1,1,1};
        int[] predicho = {0,1,0,1,0,1};
        int[][] matriz = new int[2][2];
        for (int i = 0; i < real.length; i++) matriz[real[i]][predicho[i]]++;
        System.out.println("VN: " + matriz[0][0]);
        System.out.println("FP: " + matriz[0][1]);
        System.out.println("FN: " + matriz[1][0]);
        System.out.println("VP: " + matriz[1][1]);
    }
}

// Salida esperada:
// VN: 2
// FP: 1
// FN: 1
// VP: 2
