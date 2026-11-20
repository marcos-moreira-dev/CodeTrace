// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\02-estructuras-datos\lista-enlazada\lista_enlazada.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de lista simplemente enlazada y asignación dinámica.
 *
 * Cada nodo almacena un valor y dirección del siguiente. Insertar
 * en cabeza cuesta O(1) y buscar O(n); no existe acceso aleatorio
 * O(1) como en un arreglo. El cliente posee y libera todos los nodos.
 */
typedef struct Nodo {
    int valor;
    struct Nodo *siguiente;
} Nodo;

static int insertar(Nodo **cabeza, int valor) {
    Nodo *nuevo = malloc(sizeof *nuevo);
    if (!nuevo) return 0;
    nuevo->valor = valor;
    nuevo->siguiente = *cabeza;
    *cabeza = nuevo;
    return 1;
}
static void liberar(Nodo *cabeza) {
    while (cabeza) {
        Nodo *siguiente = cabeza->siguiente;
        free(cabeza);
        cabeza = siguiente;
    }
}
int main(void) {
    Nodo *lista = NULL;
    for (int n = 1; n <= 4; n++) {
        if (!insertar(&lista, n)) { liberar(lista); return EXIT_FAILURE; }
    }
    for (Nodo *p = lista; p; p = p->siguiente) printf("%d ", p->valor);
    putchar('\n');
    liberar(lista);
    return 0;
}
