// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\01-fundamentos\realloc-vector\realloc_vector.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de realloc y capacidad dinámica.
 *
 * Un vector mantiene tamaño lógico y capacidad. Cuando se agota,
 * duplica su almacenamiento: el costo de agregar es O(1) amortizado,
 * aunque la operación que redimensiona copia O(n) elementos.
 * Se usa un puntero temporal al llamar realloc; si falla, el bloque
 * original sigue siendo válido y puede liberarse.
 */
typedef struct {
    int *datos;
    size_t longitud, capacidad;
} Vector;

static int agregar(Vector *v, int valor) {
    if (v->longitud == v->capacidad) {
        size_t proxima = v->capacidad ? v->capacidad * 2 : 2;
        if (proxima < v->capacidad || proxima > SIZE_MAX / sizeof *v->datos)
            return 0;
        int *nuevo = realloc(v->datos, proxima * sizeof *v->datos);
        if (!nuevo) return 0;
        v->datos = nuevo;
        v->capacidad = proxima;
    }
    v->datos[v->longitud++] = valor;
    return 1;
}
int main(void) {
    Vector v = {0};
    for (int i = 1; i <= 10; i++) {
        if (!agregar(&v, i * 3)) {
            free(v.datos);
            return EXIT_FAILURE;
        }
    }
    printf("Cantidad=%zu, capacidad=%zu, último=%d\n",
        v.longitud, v.capacidad, v.datos[v.longitud - 1]);
    free(v.datos);
    return EXIT_SUCCESS;
}
