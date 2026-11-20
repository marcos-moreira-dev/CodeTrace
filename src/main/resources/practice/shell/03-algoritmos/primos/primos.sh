#!/bin/sh
# CodeTrace | Referencia: POSIX sh (WSL/Linux/Git Bash).
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Comprobar sintaxis: sh -n '.\03-algoritmos\primos\primos.sh'
# Ejecutar en WSL/Git Bash: sh '.\03-algoritmos\primos\primos.sh'

numero=17
divisor=2
primo=1
while [ "$divisor" -lt "$numero" ]; do
    if [ $((numero % divisor)) -eq 0 ]; then
        primo=0
        break
    fi
    divisor=$((divisor + 1))
done
printf 'Primo: %s\n' "$primo"
