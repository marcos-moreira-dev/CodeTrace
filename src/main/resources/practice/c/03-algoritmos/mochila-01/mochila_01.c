// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\03-algoritmos\mochila-01\mochila_01.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>
#include <stdlib.h>

/**
 * Cliente de mochila 0/1 por programación dinámica.
 *
 * Cada artículo se utiliza a lo sumo una vez. Se recorre capacidad
 * hacia atrás para evitar reutilizarlo dentro de una misma vuelta.
 * Tiempo O(n*C) y memoria O(C); seudopolinómico respecto a C.
 */
static int mochila(const int *peso, const int *valor, size_t n, int capacidad) {
    if (capacidad < 0) return -1;
    int *dp = calloc((size_t)capacidad + 1, sizeof *dp);
    if (!dp) return -1;
    for (size_t i = 0; i < n; i++) {
        if (peso[i] <= 0 || valor[i] < 0) { free(dp); return -1; }
        for (int c = capacidad; c >= peso[i]; c--) {
            int candidato = dp[c - peso[i]] + valor[i];
            if (candidato > dp[c]) dp[c] = candidato;
        }
    }
    int mejor = dp[capacidad];
    free(dp);
    return mejor;
}
int main(void) {
    const int pesos[] = {2, 3, 4, 5}, valores[] = {3, 4, 5, 8};
    printf("Máximo=%d\n", mochila(pesos, valores, 4, 8));
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\03-algoritmos\mochila-01\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   Máximo=12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
