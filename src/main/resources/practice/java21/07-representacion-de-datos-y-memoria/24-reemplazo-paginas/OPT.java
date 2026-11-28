// Tema:
// OPT reemplaza la página cuyo próximo uso está más lejos en el futuro. Es una referencia
// teórica: un sistema real no conoce toda la secuencia futura.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FIFO.java LRU.java OPT.java MainReemplazo.java
// Ejecutar: java MainReemplazo

class OPT {
    static int fallos(int[] referencias, int capacidad) {
        int[] marcos = new int[capacidad];
        java.util.Arrays.fill(marcos, -1);
        int fallos = 0;
        for (int t = 0; t < referencias.length; t++) {
            boolean presente = false;
            for (int marco : marcos) if (marco == referencias[t]) presente = true;
            if (presente) continue;
            fallos++;
            int elegido = 0, masLejano = -1;
            for (int j = 0; j < capacidad; j++) {
                if (marcos[j] == -1) {
                    elegido = j;
                    break;
                }
                int futuro = t + 1;
                while (futuro < referencias.length && referencias[futuro] != marcos[j]) {
                    futuro++;
                }
                if (futuro > masLejano) {
                    masLejano = futuro;
                    elegido = j;
                }
            }
            marcos[elegido] = referencias[t];
        }
        return fallos;
    }
}

// Salida esperada:
// FIFO: 10
// LRU: 9
// OPT: 7
