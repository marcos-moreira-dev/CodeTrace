/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra ARCHIVO.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: & "$env:TEMP\ct-biblioteca-c.exe"
 */

#include "operaciones.h"
#include <stdio.h>

/**
 * Cliente de compilación separada y enlazado en C17.
 *
 * operaciones.h es el contrato y operaciones.c la implementación. El mismo
 * código fuente puede convertirse en biblioteca estática (.a) o compartida
 * (.so/.dll). Una biblioteca estática aporta código al ejecutable al enlazar;
 * una compartida se carga por el sistema conforme a su ABI y búsqueda.
 *
 * En Linux/WSL desde esta carpeta:
 * gcc -std=c17 -fPIC -c operaciones.c -o operaciones.o
 * ar rcs liboperaciones.a operaciones.o
 * gcc -std=c17 main.c -L. -loperaciones -o app_estatica
 * gcc -std=c17 -fPIC -shared operaciones.c -o liboperaciones.so
 * gcc -std=c17 main.c -L. -loperaciones -Wl,-rpath,'$ORIGIN' -o app_dinamica
 * (Los nombres de artefactos y flags de DLL varían en Windows/MinGW.)
 */
int main(void) {
    printf("Suma=%d, producto=%d\n", sumar(3, 4), multiplicar(3, 4));
    return 0;
}
