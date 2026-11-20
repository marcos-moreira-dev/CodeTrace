// CodeTrace | Referencia: C11, Observer con cabecera y archivo fuente
// Compilar: gcc -std=c11 main.c Observer.c -o observer
#include <stdio.h>
#include "Observer.h"
static void mostrar(const char *evento) { printf("Evento: %s\n", evento); }
int main(void) {
    CentroEventos centro = {0};
    registrar(&centro, mostrar);
    publicar(&centro, "Factura 001");
    return 0;
}

/*
 * Salida esperada de consola (exacta para los datos del cliente):
 * Evento: Factura 001
 * Verificacion: exit code 0 y ninguna excepcion inesperada.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\04-patrones-diseno\observer\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   Evento: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
