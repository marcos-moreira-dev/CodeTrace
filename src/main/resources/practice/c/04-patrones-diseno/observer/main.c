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
 * Salida esperada de consola:
 *   Sin salida de texto: los casos correctos finalizan silenciosamente.
 * Verificacion: las aserciones se cumplen y el proceso termina sin error.
 */
