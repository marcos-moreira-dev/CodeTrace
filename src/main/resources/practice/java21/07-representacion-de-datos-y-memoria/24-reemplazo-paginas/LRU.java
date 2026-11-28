// Tema:
// LRU sustituye la página cuyo último acceso es el más antiguo. El arreglo de tiempos
// registra la última posición de cada referencia.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FIFO.java LRU.java OPT.java MainReemplazo.java
// Ejecutar: java MainReemplazo

class LRU {
    static int fallos(int[] referencias, int capacidad) {
        int[] marcos = new int[capacidad], ultimo = new int[capacidad];
        java.util.Arrays.fill(marcos, -1);
        java.util.Arrays.fill(ultimo, -1);
        int fallos = 0;
        for (int t = 0; t < referencias.length; t++) {
            int indice = -1;
            for (int j = 0; j < capacidad; j++) {
                if (marcos[j] == referencias[t]) indice = j;
            }
            if (indice < 0) {
                fallos++;
                indice = 0;
                for (int j = 1; j < capacidad; j++) {
                    if (ultimo[j] < ultimo[indice]) indice = j;
                }
                marcos[indice] = referencias[t];
            }
            ultimo[indice] = t;
        }
        return fallos;
    }
}

// Salida esperada:
// FIFO: 10
// LRU: 9
// OPT: 7
