// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\01-fundamentos\qsort_callbacks.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de qsort y punteros a función: callback de comparación.
 *
 * La biblioteca no conoce la forma de ordenar los elementos. Recibe
 * una función cuyo resultado es negativo, cero o positivo según
 * el orden de dos objetos. Comparar con una resta directa puede
 * desbordar, por eso se utilizan comparaciones relacionales.
 */
static int comparar(const void *a, const void *b) {
    int x = *(const int *)a;
    int y = *(const int *)b;
    return (x > y) - (x < y);
}
int main(void) {
    int datos[] = {9, 1, 5, 3, 7};
    size_t n = sizeof datos / sizeof datos[0];
    qsort(datos, n, sizeof datos[0], comparar);
    for (size_t i = 0; i < n; i++) printf("%d ", datos[i]);
    putchar('\n');
    return 0;
}
