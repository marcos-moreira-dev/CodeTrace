# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n ARCHIVO.sh
# Ejecutar: sh ARCHIVO.sh

#!/bin/sh
# Cliente de funciones, estado de salida y limpieza de recursos.
#
# trap registra la operación de limpieza si el script termina. La ruta
# temporal es propia y se usa mktemp; el nombre se cita al expandirlo.
# No editar archivos originales del proyecto para practicar el ejemplo.
set -eu
temporal=$(mktemp)
limpiar() { rm -f "$temporal"; }
trap limpiar EXIT HUP INT TERM
printf 'registro\n' > "$temporal"
cat "$temporal"
