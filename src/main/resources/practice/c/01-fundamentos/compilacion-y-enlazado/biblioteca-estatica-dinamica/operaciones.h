/* CodeTrace | Referencia: C17 / GCC 13+ / Linux o WSL
 * Compilar: sh compilar.sh
 * Esta cabecera se incluye desde main.c y operaciones.c; no se enlaza sola.
 */
#ifndef CT_C_OPERACIONES_H
#define CT_C_OPERACIONES_H

/* Contrato público; oculta la implementación y permite compilar por separado.
 * #ifndef impide incluir dos veces el mismo contrato en una unidad de traducción.
 */
int sumar(int a, int b);
int multiplicar(int a, int b);
#endif

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   gcc -std=c17 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
 *   & "$env:TEMP\ct-ejemplo-c.exe"
 * Nota: operaciones.h es un componente; la salida corresponde al cliente main.c.
 * Salida esperada:
 *   Suma=7, producto=12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
