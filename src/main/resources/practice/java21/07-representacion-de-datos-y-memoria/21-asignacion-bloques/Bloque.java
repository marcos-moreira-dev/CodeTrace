// Tema:
// Un bloque libre del modelo tiene dirección inicial y tamaño. No representa una dirección
// de memoria real.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Bloque.java FirstFit.java BestFit.java WorstFit.java MainAsignacion.java
// Ejecutar: java MainAsignacion

class Bloque {
    final int inicio;
    final int tamano;

    Bloque(int inicio, int tamano) {
        this.inicio = inicio;
        this.tamano = tamano;
    }
}

// Salida esperada:
// First Fit: 0
// Best Fit: 3
// Worst Fit: 6
