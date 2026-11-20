# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('deque.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Deque de Python: doble extremo como estructura estándar.

collections.deque permite append/appendleft/pop/popleft O(1).
Se envuelve para fijar una API didáctica, sin reinventar nodos que
ya están demostrados en linked-list/doble. No es multiprocessing.
"""
from collections import deque as ColeccionDeque

class Deque:
    def __init__(self) -> None:
        self._datos: ColeccionDeque[str] = ColeccionDeque()

    def insertar_primero(self, valor: str) -> None:
        self._datos.appendleft(valor)

    def insertar_ultimo(self, valor: str) -> None:
        self._datos.append(valor)

    def quitar_primero(self) -> str:
        return self._datos.popleft()

    def quitar_ultimo(self) -> str:
        return self._datos.pop()
