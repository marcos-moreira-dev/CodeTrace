#!/bin/sh
# CodeTrace | Referencia: POSIX sh (WSL/Linux/Git Bash).
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Comprobar sintaxis: sh -n '.\01-fundamentos/flujos\tuberias.sh'
# Ejecutar en WSL/Git Bash: sh '.\01-fundamentos/flujos\tuberias.sh'

printf '%s\n' "Java" "Kotlin" "Python" "Go" |
    grep -i 'o' |
    sort

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 01-fundamentos/flujos/tuberias.sh
# Salida esperada:
#   Go
#   Kotlin
#   Python
# CodeTrace | FIN EJEMPLO DE CONSOLA
