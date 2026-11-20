/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra ARCHIVO.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: & "$env:TEMP\ct-pila.exe"
 */

#include "pila.h"
#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de una pila LIFO en C: la última entrada sale primero.
 *
 * Los archivos .h publican el contrato; pila.c es dueño de la memoria
 * y oculta el contenido de struct Pila. main.c solo utiliza la API.
 * El diseño hace visible la propiedad de recursos y la compilación
 * de varias unidades de traducción, sin MVC artificial.
 *
 * Compilar este caso desde la raíz del paquete:
 * gcc -std=c17 -Wall -Wextra -include stdint.h 02-estructuras-datos/pila/main.c 02-estructuras-datos/pila/pila.c -o pila.exe
 * Ejecutar: ./pila.exe
 */
int main(void) {
    Pila *pila = pila_crear(3);
    if (!pila) return EXIT_FAILURE;
    pila_insertar(pila, 10);
    pila_insertar(pila, 20);
    int valor = 0;
    while (pila_extraer(pila, &valor)) printf("%d\n", valor);
    pila_destruir(pila);
    return EXIT_SUCCESS;
}

/*
 * Salida esperada de consola (exacta para los datos del cliente):
 * 20
 * 10
 * Verificacion: exit code 0 y ninguna excepcion inesperada.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\pila\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   20
 *   10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
