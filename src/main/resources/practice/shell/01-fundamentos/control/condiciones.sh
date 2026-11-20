#!/bin/sh
# CodeTrace | Referencia: POSIX sh (dash, ash o Bash en modo sh).
# Comandos desde la carpeta del paquete, en una COPIA:
# Comprobar sintaxis: sh -n '.\01-fundamentos/control\condiciones.sh'
# Ejecutar en Bash/WSL: sh '.\01-fundamentos/control\condiciones.sh'

numero=8
if [ "$numero" -gt 5 ]; then
    printf 'Mayor que cinco\n'
else
    printf 'Cinco o menos\n'
fi
