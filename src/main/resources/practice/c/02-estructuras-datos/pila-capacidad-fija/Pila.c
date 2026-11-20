// CodeTrace | Referencia: C17, GCC 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: gcc -std=c17 -Wall -Wextra '.\02-estructuras-datos\pila-capacidad-fija\Pila.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>
#define CAPACIDAD 8
typedef struct { int datos[CAPACIDAD]; int cantidad; } Pila;
int apilar(Pila *p, int valor) {
    if (p->cantidad == CAPACIDAD) return 0;
    p->datos[p->cantidad++] = valor; return 1;
}
int desapilar(Pila *p, int *resultado) {
    if (p->cantidad == 0) return 0;
    *resultado = p->datos[--p->cantidad]; return 1;
}
int main(void) {
    Pila p = { {0}, 0 };
    int valor = 0;
    apilar(&p, 12); apilar(&p, 8);
    if (desapilar(&p, &valor)) printf("%d\n", valor);
    return 0;
}

/*
 * Salida esperada:
 * 8
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\pila-capacidad-fija\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   8
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
