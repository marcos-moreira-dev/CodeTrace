// Tema:
// La distancia euclidiana mide la norma de la diferencia entre dos puntos.
// Los puntos deben tener igual dimensión.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 DistanciaEuclidiana.java
// Ejecutar: java DistanciaEuclidiana

public class DistanciaEuclidiana {
    public static void main(String[] args) {
        double[] a = {1, 2}, b = {4, 6};
        double cuadrados = 0;
        for (int i = 0; i < a.length; i++) {
            double diferencia = a[i] - b[i];
            cuadrados += diferencia * diferencia;
        }
        System.out.println(Math.sqrt(cuadrados));
    }
}

// Salida esperada:
// 5.0
