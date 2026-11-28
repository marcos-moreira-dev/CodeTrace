// Tema:
// Se simula la fase de marcado recorriendo un grafo desde una raíz. Los nodos no marcados
// no son alcanzables desde ella, incluso si se referencian entre sí.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Alcanzabilidad.java MainMarcarBarrer.java
// Ejecutar: java MainMarcarBarrer

class Alcanzabilidad {
    static void marcar(int nodo, int[][] referencias, boolean[] marcado) {
        if (marcado[nodo]) return;
        marcado[nodo] = true;
        for (int destino : referencias[nodo]) {
            marcar(destino, referencias, marcado);
        }
    }
}

// Salida esperada:
// 0 vivo
// 1 vivo
// 2 vivo
// 3 recuperable
