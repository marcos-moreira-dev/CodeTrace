#!/bin/sh
# CodeTrace | Referencia: POSIX sh (WSL/Linux/Git Bash).
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Comprobar sintaxis: sh -n '.\03-algoritmos\euclides\euclides.sh'
# Ejecutar en WSL/Git Bash: sh '.\03-algoritmos\euclides\euclides.sh'

a=48
b=18
while [ "$b" -ne 0 ]; do
    resto=$((a % b))
    a=$b
    b=$resto
done
printf 'MCD: %s\n' "$a"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 03-algoritmos/euclides/euclides.sh
# Salida esperada:
#   MCD: 6
# CodeTrace | FIN EJEMPLO DE CONSOLA
