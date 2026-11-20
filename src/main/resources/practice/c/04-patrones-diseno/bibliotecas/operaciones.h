/* CodeTrace | Referencia: C17 / GCC 13+
 * Compilar: gcc -std=c17 -Wall -Wextra ARCHIVO.c -o "$env:TEMP\ct-ejemplo.exe"
 * Ejecutar: Consulte main.c
 */

#ifndef CODETRACE_OPERACIONES_H
#define CODETRACE_OPERACIONES_H
/* Interfaz pública compartida por la biblioteca estática y la dinámica. */
int sumar(int a, int b);
int multiplicar(int a, int b);
#endif
