/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra ARCHIVO.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: Consulte main.c
 */

#include "pila.h"
#include <stdint.h>
#include <stdlib.h>

struct Pila {
    int *datos;
    size_t cantidad;
    size_t capacidad;
};

Pila *pila_crear(size_t capacidad) {
    if (capacidad == 0 || capacidad > SIZE_MAX / sizeof(int)) return NULL;
    Pila *pila = malloc(sizeof *pila);
    if (!pila) return NULL;
    pila->datos = malloc(capacidad * sizeof(int));
    if (!pila->datos) { free(pila); return NULL; }
    pila->cantidad = 0;
    pila->capacidad = capacidad;
    return pila;
}
int pila_insertar(Pila *pila, int valor) {
    if (!pila || pila->cantidad == pila->capacidad) return 0;
    pila->datos[pila->cantidad++] = valor;
    return 1;
}
int pila_extraer(Pila *pila, int *valor) {
    if (!pila || !valor || !pila->cantidad) return 0;
    *valor = pila->datos[--pila->cantidad];
    return 1;
}
void pila_destruir(Pila *pila) {
    if (!pila) return;
    free(pila->datos);
    free(pila);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\pila\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: pila.c es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   20
 *   10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
