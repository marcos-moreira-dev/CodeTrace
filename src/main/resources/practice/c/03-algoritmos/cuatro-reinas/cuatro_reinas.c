// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\03-algoritmos\cuatro-reinas\cuatro_reinas.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de backtracking: problema de las cuatro reinas.
 *
 * Cada fila prueba columnas sin amenaza por columna o diagonal.
 * Se construye un estado parcial y se descarta una rama tan pronto
 * deja de ser válida. La cantidad de candidatos es combinatoria.
 * La memoria adicional depende de la profundidad de recursión O(n).
 */
enum { N = 4 };
static int columnas[N], soluciones;
static int valido(int fila, int col) {
    for (int f = 0; f < fila; f++) {
        if (columnas[f] == col || abs(columnas[f] - col) == fila - f) return 0;
    }
    return 1;
}
static void resolver(int fila) {
    if (fila == N) {
        soluciones++;
        for (int f = 0; f < N; f++) printf("%d ", columnas[f]);
        putchar('\n');
        return;
    }
    for (int c = 0; c < N; c++) {
        if (valido(fila, c)) {
            columnas[fila] = c;
            resolver(fila + 1);
        }
    }
}
int main(void) {
    resolver(0);
    printf("Soluciones=%d\n", soluciones);
    return 0;
}
