// CodeTrace | Referencia: C11, Observer con cabecera y archivo fuente
// Compilar: gcc -std=c11 main.c Observer.c -o observer
#include "Observer.h"
void registrar(CentroEventos *centro, notificar_fn funcion) {
    if (centro->cantidad < 8) centro->suscriptores[centro->cantidad++] = funcion;
}
void publicar(const CentroEventos *centro, const char *mensaje) {
    for (int i = 0; i < centro->cantidad; ++i)
        centro->suscriptores[i](mensaje);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\04-patrones-diseno\observer\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: Observer.c es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   Evento: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
