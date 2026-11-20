/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra 03-algoritmos/busqueda-binaria/busqueda_binaria.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: & "$env:TEMP\ct-ejemplo.exe"
 */

#include <stdio.h>
#include <stddef.h>

/**
 * Cliente de búsqueda binaria en arreglo previamente ordenado.
 *
 * La invariante mantiene el posible resultado dentro de [inicio, fin).
 * En cada iteración se descarta al menos media región activa, por eso
 * la búsqueda requiere O(log n) tiempo y O(1) memoria auxiliar.
 * No se debe aplicar sobre datos desordenados.
 */
static int buscar(const int *a, size_t n, int objetivo) {
    size_t inicio = 0, fin = n;
    while (inicio < fin) {
        size_t mitad = inicio + (fin - inicio) / 2;
        if (a[mitad] == objetivo) return (int)mitad;
        if (a[mitad] < objetivo) inicio = mitad + 1;
        else fin = mitad;
    }
    return -1;
}
int main(void) {
    const int datos[] = {2, 5, 8, 12, 21, 34};
    printf("Índice=%d\n", buscar(datos, sizeof datos / sizeof datos[0], 12));
    return 0;
}
