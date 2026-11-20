// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\02-estructuras-datos\tabla-hash\tabla_hash.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <string.h>

/**
 * Cliente de una tabla hash mínima con sondeo lineal.
 *
 * Las claves son cadenas cortas. Dos claves con el mismo índice inicial
 * se almacenan en posiciones consecutivas libres. El factor de carga
 * se mantiene bajo para ofrecer O(1) promedio, pero el peor caso
 * de búsqueda e inserción es O(capacidad). No soporta borrado.
 */
enum { CAP = 11, MAX_CLAVE = 20 };
typedef struct { char clave[MAX_CLAVE]; int valor; int ocupado; } Celda;
typedef struct { Celda celdas[CAP]; } Tabla;

static unsigned hash(const char *s) {
    unsigned h = 2166136261u;
    while (*s) { h ^= (unsigned char)*s++; h *= 16777619u; }
    return h % CAP;
}
static int poner(Tabla *t, const char *s, int valor) {
    if (strlen(s) >= MAX_CLAVE) return 0;
    unsigned indice = hash(s);
    for (unsigned n = 0; n < CAP; n++) {
        Celda *c = &t->celdas[(indice + n) % CAP];
        if (!c->ocupado || strcmp(c->clave, s) == 0) {
            if (!c->ocupado) { strcpy(c->clave, s); c->ocupado = 1; }
            c->valor = valor;
            return 1;
        }
    }
    return 0;
}
static int obtener(const Tabla *t, const char *s, int *salida) {
    unsigned indice = hash(s);
    for (unsigned n = 0; n < CAP; n++) {
        const Celda *c = &t->celdas[(indice + n) % CAP];
        if (!c->ocupado) return 0;
        if (strcmp(c->clave, s) == 0) { *salida = c->valor; return 1; }
    }
    return 0;
}
int main(void) {
    Tabla tabla = {0};
    int resultado;
    poner(&tabla, "sensor", 42);
    poner(&tabla, "router", 7);
    if (obtener(&tabla, "sensor", &resultado))
        printf("sensor=%d\n", resultado);
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\02-estructuras-datos\tabla-hash\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   sensor=42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
