// CodeTrace | Referencia: C17 (ISO C17); GCC 13+.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: gcc -std=c17 -Wall -Wextra '.\01-fundamentos/funciones\arreglos.c' -o "$env:TEMP\codetrace-c.exe"
// Ejecutar: & "$env:TEMP\codetrace-c.exe"

#include <stdio.h>

int sumar(const int datos[], int cantidad) {
    int resultado = 0;
    for (int i = 0; i < cantidad; i++) {
        resultado += datos[i];
    }
    return resultado;
}

int main(void) {
    int datos[] = {2, 3, 5};
    printf("%d\n", sumar(datos, 3));
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\funciones\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Salida esperada:
 *   10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
