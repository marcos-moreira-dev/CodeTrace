// CodeTrace | Referencia: C17, GCC 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: gcc -std=c17 -Wall -Wextra '.\03-algoritmos\euclides\Euclides.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>
unsigned mcd(unsigned a, unsigned b) {
    while (b != 0) {
        unsigned resto = a % b;
        a = b; b = resto;
    }
    return a;
}
int main(void) {
    printf("MCD: %u\n", mcd(48, 18));
    return 0;
}
