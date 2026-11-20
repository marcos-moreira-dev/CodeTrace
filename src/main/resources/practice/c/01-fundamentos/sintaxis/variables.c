// CodeTrace | Referencia: C17 (ISO C17); GCC 13+.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: gcc -std=c17 -Wall -Wextra '.\01-sintaxis\variables.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>

int main(void) {
    int unidades = 4;
    double precio = 12.5;
    double total = unidades * precio;
    printf("Total: %.2f\n", total);
    return 0;
}
