/* CodeTrace | Referencia: C17 / GCC 13+ / Linux o WSL
 * Comprobar en una COPIA: gcc -std=c17 -Wall -Wextra -fPIC -c operaciones.c -o operaciones.o
 * Ejecutar: la función se invoca desde main.c; este .c no tiene main.
 */
#include "operaciones.h"

/* Implementación privada detrás de operaciones.h. No contiene estado global. */
int sumar(int a, int b) { return a + b; }
int multiplicar(int a, int b) { return a * b; }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: operaciones.c es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   Suma=7, producto=12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
