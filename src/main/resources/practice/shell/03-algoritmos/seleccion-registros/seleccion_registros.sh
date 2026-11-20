# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n ".\03-algoritmos/seleccion-registros/seleccion_registros.sh"
# Ejecutar: sh ".\03-algoritmos/seleccion-registros/seleccion_registros.sh"

#!/bin/sh
# Cliente de procesamiento secuencial de registros.
#
# awk filtra filas y sort ordena la salida por valor numérico.
# POSIX sh actúa como coordinador de procesos en vez de implementar
# tablas hash u ordenamientos en un lenguaje poco adecuado para ello.
set -eu
printf '%s\n' 'Ana 20' 'Luis 10' 'Eva 30' 'Ana 25' |
    awk '$2 >= 20 { print $2, $1 }' |
    sort -n

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 03-algoritmos/seleccion-registros/seleccion_registros.sh
# Salida esperada:
#   20 Ana
#   25 Ana
#   30 Eva
# CodeTrace | FIN EJEMPLO DE CONSOLA
