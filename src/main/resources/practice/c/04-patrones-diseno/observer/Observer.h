// CodeTrace | Referencia: C11, Observer con cabecera y archivo fuente
// Compilar: gcc -std=c11 main.c Observer.c -o observer
#ifndef CODETRACE_OBSERVER_H
#define CODETRACE_OBSERVER_H
typedef void (*notificar_fn)(const char *mensaje);
typedef struct {
    notificar_fn suscriptores[8];
    int cantidad;
} CentroEventos;
void registrar(CentroEventos *centro, notificar_fn funcion);
void publicar(const CentroEventos *centro, const char *mensaje);
#endif

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\04-patrones-diseno\observer\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: Observer.h es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   Evento: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
