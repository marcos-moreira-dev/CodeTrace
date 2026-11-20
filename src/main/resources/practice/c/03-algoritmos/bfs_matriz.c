// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\03-algoritmos\bfs_matriz.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>

/**
 * Cliente de BFS sobre matriz de adyacencia.
 *
 * Los vértices son índices 0..4. Una cola FIFO descubre cada vértice
 * una sola vez. Con una MATRIZ se revisan V posibles vecinos por cada
 * nodo: O(V²) tiempo y O(V) memoria, no O(V+E) como con listas.
 * Esta diferencia ilustra cómo la estructura condiciona el algoritmo.
 */
enum { V = 5 };
static void bfs(const int g[V][V], int origen) {
    int cola[V], visto[V] = {0};
    int inicio = 0, fin = 0;
    visto[origen] = 1;
    cola[fin++] = origen;
    while (inicio < fin) {
        int actual = cola[inicio++];
        printf("%d ", actual);
        for (int i = 0; i < V; i++) {
            if (g[actual][i] && !visto[i]) {
                visto[i] = 1;
                cola[fin++] = i;
            }
        }
    }
}
int main(void) {
    const int grafo[V][V] = {
        {0,1,1,0,0}, {0,0,0,1,0}, {0,0,0,0,1},
        {0,0,0,0,0}, {0,0,0,0,0}
    };
    bfs(grafo, 0);
    putchar('\n');
    return 0;
}
