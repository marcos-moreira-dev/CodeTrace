#!/bin/sh
# CodeTrace | Referencia: POSIX sh + C++20 / g++ 13+ (Linux/WSL)
# Comprobar: sh -n compilar.sh
# Ejecutar desde una COPIA: sh compilar.sh
set -eu
cd "$(dirname "$0")"
tmp=$(mktemp -d)
trap 'rm -rf "$tmp"' EXIT HUP INT TERM
mkdir -p "$tmp/estatica" "$tmp/dinamica"

g++ -std=c++20 -Wall -Wextra -Werror -c operaciones.cpp -o "$tmp/operaciones.o"
ar rcs "$tmp/estatica/liboperaciones.a" "$tmp/operaciones.o"
g++ -std=c++20 -Wall -Wextra -Werror main.cpp \
  "$tmp/estatica/liboperaciones.a" -o "$tmp/estatica/app"

g++ -std=c++20 -Wall -Wextra -Werror -fPIC -shared operaciones.cpp \
  -o "$tmp/dinamica/liboperaciones.so"
g++ -std=c++20 -Wall -Wextra -Werror main.cpp \
  -L"$tmp/dinamica" -loperaciones -Wl,-rpath,'$ORIGIN' \
  -o "$tmp/dinamica/app"

salida_estatica=$("$tmp/estatica/app")
salida_dinamica=$("$tmp/dinamica/app")
printf 'Estática: %s\nDinámica: %s\n' "$salida_estatica" "$salida_dinamica"
test "$salida_estatica" = "$salida_dinamica"
echo 'OK: binarios equivalentes con enlazado diferente'

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
#   & "$env:TEMP\ct-ejemplo-cpp.exe"
# Salida esperada:
#   Suma=7, producto=12
# CodeTrace | FIN EJEMPLO DE CONSOLA
