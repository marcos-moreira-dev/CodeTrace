// Tema:
// La norma euclidiana es la raíz cuadrada de la suma de cuadrados.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 NormaVector.java
// Ejecutar: java NormaVector

public class NormaVector {
    public static void main(String[] args) {
        double[] vector = {3, 4};
        double cuadrados = 0;
        for (double componente : vector) cuadrados += componente * componente;
        System.out.println(Math.sqrt(cuadrados));
    }
}

// Salida esperada:
// 5.0
