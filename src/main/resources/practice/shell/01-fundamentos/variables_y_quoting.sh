# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n ARCHIVO.sh
# Ejecutar: sh ARCHIVO.sh

#!/bin/sh
# Cliente de expansión de variables y quoting POSIX.
#
# La expansión sin comillas puede producir división en palabras y
# expansión de patrones. Con comillas dobles se conserva el argumento
# completo. El script no depende de Bash, arrays ni utilidades externas.
set -eu
nombre='dos palabras'
mostrar() { printf 'Argumento: <%s>\n' "$1"; }
mostrar "$nombre"
printf 'Número de argumentos recibidos: %s\n' "$#"
