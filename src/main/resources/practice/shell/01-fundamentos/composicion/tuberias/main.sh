# CodeTrace | Referencia: Bash 5+, modularizacion y pipelines
# Compilar: bash 01-fundamentos/composicion/tuberias/main.sh
#!/usr/bin/env bash
set -euo pipefail
directorio="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
source "$directorio/funciones.sh"
printf '%s\n' "FACTURA 001" "NOTA 002" "FACTURA 003" |
    normalizar | filtrar_facturas

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl bash 01-fundamentos/composicion/tuberias/main.sh
# Salida esperada:
#   factura 001
#   factura 003
# CodeTrace | FIN EJEMPLO DE CONSOLA
