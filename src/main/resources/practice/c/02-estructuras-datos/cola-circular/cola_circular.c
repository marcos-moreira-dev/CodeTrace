// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\02-estructuras-datos\cola-circular\cola_circular.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stddef.h>

/**
 * Cliente de cola circular de capacidad fija.
 *
 * FIFO atiende elementos en orden de llegada. Cabeza y longitud
 * permiten reutilizar el mismo arreglo mediante aritmética modular.
 * Encolar y extraer cuestan O(1) y no desplazan el arreglo.
 * Las funciones devuelven un indicador de éxito para lleno/vacío.
 */
enum { CAPACIDAD = 4 };
typedef struct { int valores[CAPACIDAD]; size_t cabeza, n; } Cola;

static int encolar(Cola *c, int x) {
    if (c->n == CAPACIDAD) return 0;
    c->valores[(c->cabeza + c->n) % CAPACIDAD] = x;
    c->n++;
    return 1;
}
static int extraer(Cola *c, int *salida) {
    if (!c->n || !salida) return 0;
    *salida = c->valores[c->cabeza];
    c->cabeza = (c->cabeza + 1) % CAPACIDAD;
    c->n--;
    return 1;
}
int main(void) {
    Cola cola = {0};
    int x;
    encolar(&cola, 10);
    encolar(&cola, 20);
    extraer(&cola, &x);
    printf("%d\n", x);
    encolar(&cola, 30);
    while (extraer(&cola, &x)) printf("%d\n", x);
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\cola-circular\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   10
 *   20
 *   30
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
