// CodeTrace | Referencia: C17 / GCC 13+
// Compilar en copia del paquete, PowerShell: gcc -std=c17 -Wall -Wextra ".\01-fundamentos\structs_y_enums.c" -o "$env:TEMP\ct-c-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-c-ejemplo.exe"

#include <stdio.h>

/**
 * Cliente de struct, enum, typedef y contratos de datos.
 *
 * Una struct agrupa campos contiguos; su diseño afecta tamaño,
 * alineación y vida útil. Un enum ofrece nombres simbólicos a
 * constantes enteras. A diferencia de un record Java, struct
 * no genera automáticamente igualdad ni representación textual.
 */
typedef enum { PENDIENTE, PROCESADO, ENTREGADO } Estado;
typedef struct {
    unsigned codigo;
    double total;
    Estado estado;
} Pedido;

static void imprimir(const Pedido *pedido) {
    printf("Pedido %u, total %.2f, estado=%d\n",
        pedido->codigo, pedido->total, (int)pedido->estado);
}
int main(void) {
    const Pedido pedido = {1001u, 19.75, PROCESADO};
    imprimir(&pedido);
    printf("Tamaño: %zu bytes\n", sizeof pedido);
    return 0;
}
