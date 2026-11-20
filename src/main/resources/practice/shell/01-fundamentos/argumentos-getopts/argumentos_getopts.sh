# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n ".\01-fundamentos/argumentos-getopts/argumentos_getopts.sh"
# Ejecutar: sh ".\01-fundamentos/argumentos-getopts/argumentos_getopts.sh"

#!/bin/sh
# Cliente de argumentos, funciones y getopts POSIX.
#
# getopts permite procesar opciones sin depender de extensiones Bash.
# Se valida un entero simple antes de usar la aritmética del shell.
# El resultado va a stdout; el error a stderr con estado distinto de 0.
set -eu
cantidad=3
while getopts "n:" opcion; do
    case "$opcion" in
        n) cantidad=$OPTARG ;;
        *) printf 'Opción inválida\n' >&2; exit 2 ;;
    esac
done
case "$cantidad" in
    ''|*[!0-9]*) printf 'n debe ser entero no negativo\n' >&2; exit 2 ;;
esac
i=0
while [ "$i" -lt "$cantidad" ]; do
    printf 'Iteración %s\n' "$i"
    i=$((i + 1))
done
