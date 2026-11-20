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
