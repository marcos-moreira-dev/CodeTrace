// Tema:
// Dos regiones libres adyacentes pueden fusionarse si el final exclusivo de la primera
// coincide con el inicio de la segunda.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Coalescencia.java
// Ejecutar: java Coalescencia

public class Coalescencia {
    public static void main(String[] args) {
        int inicioA = 2, tamanoA = 3;
        int inicioB = 5, tamanoB = 4;
        if (inicioA + tamanoA == inicioB) {
            System.out.println(inicioA + " " + (tamanoA + tamanoB));
        }
    }
}

// Salida esperada:
// 2 7
