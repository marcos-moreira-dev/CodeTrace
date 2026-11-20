# CodeTrace | Referencia: POSIX sh (Linux/WSL)
# Comprobar: sh -n ARCHIVO.sh
# Ejecutar: sh ARCHIVO.sh

#!/bin/sh
# Cliente de procesamiento de texto con tuberías POSIX.
#
# sort agrupa términos iguales y uniq -c cuenta ocurrencias. El shell
# coordina procesos externos: no se reinventan colecciones ni estructuras
# de datos OOP. La complejidad depende de sort, no solo del bucle shell.
set -eu
printf '%s\n' manzana pera manzana naranja pera manzana |
    sort |
    uniq -c
