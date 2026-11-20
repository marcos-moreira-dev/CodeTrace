/* CodeTrace | Referencia: C17 / GCC 13+ / Linux o WSL
 * Comprobar en una COPIA: gcc -std=c17 -Wall -Wextra -fPIC -c operaciones.c -o operaciones.o
 * Ejecutar: la función se invoca desde main.c; este .c no tiene main.
 */
#include "operaciones.h"

/* Implementación privada detrás de operaciones.h. No contiene estado global. */
int sumar(int a, int b) { return a + b; }
int multiplicar(int a, int b) { return a * b; }
