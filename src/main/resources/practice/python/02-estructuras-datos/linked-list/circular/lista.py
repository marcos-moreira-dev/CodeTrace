# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('lista.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Lista circular simple: la cola apunta de vuelta a la cabeza.

La terminación del recorrido no puede depender de encontrar None,
sino de la cantidad de nodos. Insertar/rotar O(1), recorrer O(n).
"""
from __future__ import annotations
from dataclasses import dataclass
from typing import Generic, TypeVar
T = TypeVar("T")

@dataclass
class Nodo(Generic[T]):
    dato: T
    siguiente: Nodo[T] | None = None

class ListaCircular(Generic[T]):
    def __init__(self) -> None:
        self.ultimo: Nodo[T] | None = None
        self.cantidad = 0

    def agregar(self, dato: T) -> None:
        n = Nodo(dato)
        if self.ultimo is None:
            n.siguiente = n
        else:
            n.siguiente = self.ultimo.siguiente
            self.ultimo.siguiente = n
        self.ultimo = n
        self.cantidad += 1

    def rotar(self) -> None:
        if self.ultimo is not None:
            self.ultimo = self.ultimo.siguiente

    def valores(self) -> list[T]:
        r: list[T] = []
        if self.ultimo is None:
            return r
        actual = self.ultimo.siguiente
        for _ in range(self.cantidad):
            assert actual is not None
            r.append(actual.dato)
            actual = actual.siguiente
        return r

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\circular\main.py
# Nota: lista.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   ['Luis', 'Mia', 'Ana']
# CodeTrace | FIN EJEMPLO DE CONSOLA
