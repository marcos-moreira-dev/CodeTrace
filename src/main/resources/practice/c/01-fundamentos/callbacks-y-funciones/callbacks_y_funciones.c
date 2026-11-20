/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra 01-fundamentos/callbacks-y-funciones/callbacks_y_funciones.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: & "$env:TEMP\ct-ejemplo.exe"
 */

#include <stdio.h>

/**
 * Cliente de punteros a función: una alternativa de C a las lambdas Java.
 *
 * Una rutina genérica recibe la dirección de una función y la invoca.
 * Su firma forma parte del contrato. Esto permite seleccionar estrategias
 * sin crear objetos o interfaces ficticias en C.
 */
typedef int (*transformacion)(int);
static int duplicar(int x) { return x * 2; }
static int aumentar(int x) { return x + 10; }
static int aplicar(int x, transformacion operacion) {
    return operacion(x);
}
int main(void) {
    printf("Doble=%d\n", aplicar(7, duplicar));
    printf("Incremento=%d\n", aplicar(7, aumentar));
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\callbacks-y-funciones\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   Doble=14
 *   Incremento=17
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
