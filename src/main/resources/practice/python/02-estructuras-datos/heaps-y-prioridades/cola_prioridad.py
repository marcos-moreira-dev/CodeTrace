# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('cola_prioridad.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cola de prioridad que usa heapq con desempate determinista.

Las tuplas (prioridad, secuencia, tarea) ordenan primero por número;
cuando hay empate, se utiliza el contador de llegada. Encolar y
extraer cuestan O(log n), la memoria crece con las tareas pendientes.
"""
from heapq import heappush, heappop
from itertools import count


class ColaPrioridad:
    def __init__(self) -> None:
        self._consecutivo = count()
        self._pendientes: list[tuple[int, int, str]] = []

    def encolar(self, nombre: str, prioridad: int) -> None:
        heappush(self._pendientes, (prioridad, next(self._consecutivo), nombre))

    def extraer(self) -> str:
        if not self._pendientes:
            raise IndexError("Cola vacía")
        return heappop(self._pendientes)[2]

    def vacia(self) -> bool:
        return not self._pendientes

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\heaps-y-prioridades\main.py
# Nota: cola_prioridad.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   ['factura', 'correo', 'informe']
# CodeTrace | FIN EJEMPLO DE CONSOLA
