# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n 01-fundamentos/variables-y-quoting/variables_y_quoting.sh
# Ejecutar: sh 01-fundamentos/variables-y-quoting/variables_y_quoting.sh

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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   wsl sh 01-fundamentos/variables-y-quoting/variables_y_quoting.sh
# Salida esperada:
#   Argumento: <dos palabras>
#   Número de argumentos recibidos: 0
# CodeTrace | FIN EJEMPLO DE CONSOLA
