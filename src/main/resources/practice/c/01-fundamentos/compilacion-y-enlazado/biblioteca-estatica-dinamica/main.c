/* CodeTrace | Referencia: C17 / GCC 13+ / Linux o WSL
 * Construir y ejecutar (en COPIA): sh compilar.sh
 */
#include "operaciones.h"
#include <stdio.h>

/**
 * Cliente de biblioteca ESTÁTICA y DINÁMICA en C17.
 *
 * 1. El preprocesador lee declaraciones de operaciones.h.
 * 2. gcc -c compila operaciones.c a un objeto .o: todavía NO ejecutable.
 * 3. ar rcs agrupa el .o en una biblioteca estática .a; su código
 *    se incorpora al ejecutable durante el enlazado.
 * 4. gcc -shared -fPIC crea una biblioteca compartida .so. El
 *    ejecutable enlaza referencias que resolverá el cargador dinámico.
 * 5. La ABI y la ruta de carga importan; -Wl,-rpath,'$ORIGIN'
 *    permite encontrar la .so junto al ejecutable en Linux.
 *
 * Es el MISMO cliente, contrato y dominio con dos estrategias de
 * enlazado. No es el patrón Factory: se estudia el toolchain.
 *
 * El script escribe SOLO en un directorio temporal de trabajo,
 * compara el resultado de ambas variantes y lo elimina al finalizar.
 * Para Windows nativo, las variantes típicas son .lib/.dll o .a/.dll,
 * y requieren adaptar los flags al compilador/ABI (MSVC vs MinGW).
 */
int main(void) {
    int s = sumar(3, 4), p = multiplicar(3, 4);
    printf("Suma=%d, producto=%d\n", s, p);
    return (s == 7 && p == 12) ? 0 : 1;
}
