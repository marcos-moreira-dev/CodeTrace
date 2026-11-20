# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\02-estructuras-datos\heaps-y-prioridades\heaps_y_prioridades.py"
# Ejecutar: python -B ".\02-estructuras-datos\heaps-y-prioridades\heaps_y_prioridades.py"

"""Cliente de una cola de prioridad con heapq.

heapq implementa un montículo mínimo sobre una lista; push y pop
cuestan O(log n), mientras que la raíz se consulta en O(1).
Se usan tuplas (prioridad, secuencia, trabajo) para garantizar
un criterio secundario determinista en empates.
"""
from heapq import heappop, heappush
from itertools import count


def main() -> None:
    consecutivo = count()
    pendientes: list[tuple[int, int, str]] = []
    for prioridad, nombre in [(2, "informe"), (1, "factura"), (1, "correo")]:
        heappush(pendientes, (prioridad, next(consecutivo), nombre))
    while pendientes:
        print(heappop(pendientes)[2])


if __name__ == "__main__":
    main()
