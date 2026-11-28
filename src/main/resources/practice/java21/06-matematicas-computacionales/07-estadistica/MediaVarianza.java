// Tema:
// La media resume el centro; la varianza poblacional promedia las distancias
// cuadráticas respecto de esa media. Se supone un conjunto no vacío.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 MediaVarianza.java
// Ejecutar: java MediaVarianza

public class MediaVarianza {
    public static void main(String[] args) {
        double[] datos = {2, 4, 6, 8};
        double suma = 0;
        for (double x : datos) suma += x;
        double media = suma / datos.length;
        double cuadrados = 0;
        for (double x : datos) cuadrados += (x - media) * (x - media);
        System.out.println("Media: " + media);
        System.out.println("Varianza: " + cuadrados / datos.length);
    }
}

// Salida esperada:
// Media: 5.0
// Varianza: 5.0
