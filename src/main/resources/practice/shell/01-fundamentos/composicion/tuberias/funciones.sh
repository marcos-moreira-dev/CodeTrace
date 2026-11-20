# CodeTrace | Referencia: Bash 5+, modularizacion y pipelines
# Compilar: bash 01-fundamentos/composicion/tuberias/main.sh
#!/usr/bin/env bash
normalizar() { tr '[:upper:]' '[:lower:]'; }
filtrar_facturas() { grep '^factura'; }

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl bash 01-fundamentos/composicion/tuberias/main.sh
# Nota: funciones.sh es un componente; la salida corresponde al cliente main.sh.
# Salida esperada:
#   factura 001
#   factura 003
# CodeTrace | FIN EJEMPLO DE CONSOLA
