#!/bin/sh
# CodeTrace | Referencia: POSIX sh (WSL/Linux/Git Bash).
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Comprobar sintaxis: sh -n '.\01-fundamentos/flujos\tuberias.sh'
# Ejecutar en WSL/Git Bash: sh '.\01-fundamentos/flujos\tuberias.sh'

printf '%s\n' "Java" "Kotlin" "Python" "Go" |
    grep -i 'o' |
    sort
