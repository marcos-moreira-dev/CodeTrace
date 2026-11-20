/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra ARCHIVO.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: Consulte main.c
 */

#ifndef CODETRACE_PILA_H
#define CODETRACE_PILA_H

#include <stddef.h>

/* Pila de enteros con capacidad explícita y almacenamiento propio. */
typedef struct Pila Pila;
Pila *pila_crear(size_t capacidad);
int pila_insertar(Pila *pila, int valor);
int pila_extraer(Pila *pila, int *valor);
void pila_destruir(Pila *pila);

#endif

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\pila\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: pila.h es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   20
 *   10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
