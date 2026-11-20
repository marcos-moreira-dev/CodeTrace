// CodeTrace | Referencia: C17, GCC 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: gcc -std=c17 -Wall -Wextra '.\04-patrones-diseno\Observer.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>
typedef void (*Observador)(int);
void informar(int nuevoValor, Observador callback) {
    if (callback != NULL) callback(nuevoValor);
}
void consola(int valor) { printf("Cambio: %d\n", valor); }
int main(void) {
    informar(42, consola);
    return 0;
}
