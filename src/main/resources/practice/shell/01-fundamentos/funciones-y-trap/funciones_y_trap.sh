# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n 01-fundamentos/funciones-y-trap/funciones_y_trap.sh
# Ejecutar: sh 01-fundamentos/funciones-y-trap/funciones_y_trap.sh

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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 01-fundamentos/funciones-y-trap/funciones_y_trap.sh
# Salida esperada:
#   registro
# CodeTrace | FIN EJEMPLO DE CONSOLA
