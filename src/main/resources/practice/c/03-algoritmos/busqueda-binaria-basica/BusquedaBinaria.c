// CodeTrace | Referencia: C17, GCC 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: gcc -std=c17 -Wall -Wextra '.\03-algoritmos\busqueda-binaria-basica\BusquedaBinaria.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>
int buscar(const int datos[], int n, int objetivo) {
    int izquierda = 0, derecha = n - 1;
    while (izquierda <= derecha) {
        int medio = izquierda + (derecha - izquierda) / 2;
        if (datos[medio] == objetivo) return medio;
        if (datos[medio] < objetivo) izquierda = medio + 1;
        else derecha = medio - 1;
    }
    return -1;
}
int main(void) {
    const int datos[] = { 2, 4, 8, 12, 16 };
    printf("Índice: %d\n", buscar(datos, 5, 12));
    return 0;
}
