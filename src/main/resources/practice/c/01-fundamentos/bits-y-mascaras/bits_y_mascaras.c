// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\01-fundamentos\bits-y-mascaras\bits_y_mascaras.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdint.h>
#include <stdio.h>

/**
 * Cliente de operaciones bit a bit y máscaras de permisos.
 *
 * OR activa banderas, AND prueba presencia, AND con complemento
 * limpia banderas. Este patrón se encuentra en registros de hardware,
 * protocolos y campos de bits. Usar uint8_t evita depender del signo
 * de char; el ejercicio no necesita manipular memoria real de hardware.
 */
enum { LECTURA = 1u << 0, ESCRITURA = 1u << 1, EJECUCION = 1u << 2 };
int main(void) {
    uint8_t permisos = LECTURA;
    permisos |= ESCRITURA;
    printf("Leer: %s\n", (permisos & LECTURA) ? "sí" : "no");
    permisos &= (uint8_t)~ESCRITURA;
    printf("Escribir: %s\n", (permisos & ESCRITURA) ? "sí" : "no");
    return 0;
}
