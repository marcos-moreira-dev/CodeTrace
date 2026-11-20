// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\03-algoritmos\merge_sort.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de Merge Sort en C: divide y combina arreglos.
 *
 * Se divide el intervalo semiabierto [inicio, fin) hasta un elemento.
 * Combinar requiere O(n) por nivel y hay log n niveles: O(n log n)
 * tiempo y O(n) memoria temporal. El auxiliar se reserva una sola vez
 * y su propiedad pertenece al cliente.
 */
static void ordenar(int *a, int *temp, size_t lo, size_t hi) {
    if (hi - lo < 2) return;
    size_t mid = lo + (hi - lo) / 2;
    ordenar(a, temp, lo, mid);
    ordenar(a, temp, mid, hi);
    size_t i = lo, j = mid;
    for (size_t k = lo; k < hi; k++)
        temp[k] = (j == hi || (i < mid && a[i] <= a[j])) ? a[i++] : a[j++];
    for (size_t k = lo; k < hi; k++) a[k] = temp[k];
}
int main(void) {
    int valores[] = {9, 5, 3, 7, 1, 8};
    size_t n = sizeof valores / sizeof valores[0];
    int *auxiliar = malloc(n * sizeof *auxiliar);
    if (!auxiliar) return EXIT_FAILURE;
    ordenar(valores, auxiliar, 0, n);
    for (size_t i = 0; i < n; i++) printf("%d ", valores[i]);
    putchar('\n');
    free(auxiliar);
    return 0;
}
