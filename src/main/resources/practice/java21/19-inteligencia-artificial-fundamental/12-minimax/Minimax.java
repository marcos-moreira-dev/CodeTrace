// Tema:
// Minimax modela un juego de suma cero con información perfecta: tomar una o dos fichas y gana quien toma la última. Se alternan jugador maximizador y minimizador. El valor es desde la perspectiva del primero, no del jugador actual.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 Minimax.java
// Ejecutar: java Minimax

public class Minimax {
    static int valor(int fichas, boolean maximiza) {
        if (fichas == 0) return maximiza ? -1 : 1;
        int mejor = maximiza ? -2 : 2;
        for (int quitar = 1; quitar <= 2 && quitar <= fichas; quitar++) {
            int resultado = valor(fichas-quitar, !maximiza);
            mejor = maximiza ? Math.max(mejor, resultado) : Math.min(mejor, resultado);
        }
        return mejor;
    }

    public static void main(String[] args) {
        int fichas = 4;
        System.out.println("Quitar 1: " + valor(fichas-1, false));
        System.out.println("Quitar 2: " + valor(fichas-2, false));
        System.out.println("Valor inicial: " + valor(fichas, true));
    }
}

// Salida esperada:
// Quitar 1: 1
// Quitar 2: -1
// Valor inicial: 1
