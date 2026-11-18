#!/bin/sh
# CodeTrace | Referencia: POSIX sh (dash, ash o Bash en modo sh).
# Comandos desde la carpeta del paquete, en una COPIA:
# Comprobar sintaxis: sh -n '.\01-sintaxis\variables.sh'
# Ejecutar en Bash/WSL: sh '.\01-sintaxis\variables.sh'

nombre="CodeTrace"
cantidad=3
printf '%s: %s\n' "$nombre" "$cantidad"
