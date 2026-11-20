/* CodeTrace | Referencia: C17 / GCC 13+ / Linux o WSL
 * Compilar y ejecutar en una COPIA: sh compilar.sh
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
