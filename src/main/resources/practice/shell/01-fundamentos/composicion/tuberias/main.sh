# CodeTrace | Referencia: Bash 5+, modularizacion y pipelines
# Compilar: bash 07-proyectos/tuberias/main.sh
#!/usr/bin/env bash
set -euo pipefail
directorio="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
source "$directorio/funciones.sh"
printf '%s\n' "FACTURA 001" "NOTA 002" "FACTURA 003" |
    normalizar | filtrar_facturas
