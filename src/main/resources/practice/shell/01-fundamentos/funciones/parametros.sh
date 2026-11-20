#!/bin/sh
# CodeTrace | Referencia: POSIX sh (WSL/Linux/Git Bash).
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Comprobar sintaxis: sh -n '.\01-fundamentos/funciones\parametros.sh'
# Ejecutar en WSL/Git Bash: sh '.\01-fundamentos/funciones\parametros.sh'

saludar() {
    nombre=$1
    printf 'Hola, %s\n' "$nombre"
}
saludar "CodeTrace"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 01-fundamentos/funciones/parametros.sh
# Salida esperada:
#   Hola, CodeTrace
# CodeTrace | FIN EJEMPLO DE CONSOLA
