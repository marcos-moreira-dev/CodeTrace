/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra 01-fundamentos/punteros-y-memoria/punteros_y_memoria.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: & "$env:TEMP\ct-ejemplo.exe"
 */

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente: dirección, objeto apuntado y vida de memoria en C.
 *
 * Un puntero guarda una dirección, no una copia del dato. La memoria
 * dinámica debe liberarse exactamente una vez y no utilizarse después.
 * El ejemplo comprueba malloc, muestra indireccion y conserva el puntero
 * exclusivamente mientras el objeto sigue vivo.
 */
int main(void) {
    int numero = 7;
    int *referencia = &numero;
    printf("Original=%d, por puntero=%d\n", numero, *referencia);
    int *arreglo = malloc(3 * sizeof *arreglo);
    if (arreglo == NULL) {
        fputs("No se reservó memoria\n", stderr);
        return EXIT_FAILURE;
    }
    for (size_t i = 0; i < 3; ++i) arreglo[i] = (int)i * 2;
    for (size_t i = 0; i < 3; ++i) printf("%d ", arreglo[i]);
    putchar('\n');
    free(arreglo);
    arreglo = NULL;
    return EXIT_SUCCESS;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\punteros-y-memoria\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   Original=7, por puntero=7
 *   0 2 4 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
