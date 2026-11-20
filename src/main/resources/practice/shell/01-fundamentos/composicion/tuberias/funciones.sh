# CodeTrace | Referencia: Bash 5+, modularizacion y pipelines
# Compilar: bash 07-proyectos/tuberias/main.sh
#!/usr/bin/env bash
normalizar() { tr '[:upper:]' '[:lower:]'; }
filtrar_facturas() { grep '^factura'; }
