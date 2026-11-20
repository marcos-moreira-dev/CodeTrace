#!/bin/sh
# CodeTrace | Referencia: POSIX sh + GCC 13+ para C17 (Linux/WSL)
# Comprobar: sh -n compilar.sh
# Ejecutar en una COPIA desde cualquier carpeta: sh compilar.sh
# No modificar archivos originales: todos los artefactos van a mktemp.
set -eu
cd "$(dirname "$0")"
tmp=$(mktemp -d)
trap 'rm -rf "$tmp"' EXIT HUP INT TERM
mkdir -p "$tmp/estatica" "$tmp/dinamica"

# Variante 1: .o -> liboperaciones.a -> ejecutable.
gcc -std=c17 -Wall -Wextra -Werror -c operaciones.c -o "$tmp/operaciones.o"
ar rcs "$tmp/estatica/liboperaciones.a" "$tmp/operaciones.o"
gcc -std=c17 -Wall -Wextra -Werror main.c \
  "$tmp/estatica/liboperaciones.a" -o "$tmp/estatica/app"

# Variante 2: .so + ejecutable con referencia al símbolo importado.
gcc -std=c17 -Wall -Wextra -Werror -fPIC -shared operaciones.c \
  -o "$tmp/dinamica/liboperaciones.so"
gcc -std=c17 -Wall -Wextra -Werror main.c \
  -L"$tmp/dinamica" -loperaciones -Wl,-rpath,'$ORIGIN' \
  -o "$tmp/dinamica/app"

salida_estatica=$("$tmp/estatica/app")
salida_dinamica=$("$tmp/dinamica/app")
printf 'Estática: %s\nDinámica: %s\n' "$salida_estatica" "$salida_dinamica"
test "$salida_estatica" = "$salida_dinamica"
echo 'OK: enlazado estático y compartido equivalentes'

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   gcc -std=c17 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.c -o "$env:TEMP\ct-ejemplo-c.exe"
#   & "$env:TEMP\ct-ejemplo-c.exe"
# Salida esperada:
#   Suma=7, producto=12
# CodeTrace | FIN EJEMPLO DE CONSOLA
