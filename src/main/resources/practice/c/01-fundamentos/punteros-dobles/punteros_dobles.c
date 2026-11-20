// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\01-fundamentos\punteros-dobles\punteros_dobles.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de punteros y punteros a punteros en C17.
 *
 * Un int* indica dónde está el entero. Un int** permite modificar
 * desde otra función el puntero del llamador, como sucede en APIs
 * que crean recursos. El contrato debe establecer quién libera la
 * memoria: en este caso, el main después de usarla.
 */
static int crear_entero(int **destino, int valor) {
    if (!destino) return 0;
    int *creado = malloc(sizeof *creado);
    if (!creado) return 0;
    *creado = valor;
    *destino = creado;
    return 1;
}
int main(void) {
    int *dato = NULL;
    if (!crear_entero(&dato, 42)) return EXIT_FAILURE;
    printf("Valor: %d\n", *dato);
    free(dato);
    dato = NULL;
    return EXIT_SUCCESS;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\punteros-dobles\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   Valor: 42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
