# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('lista.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Lista circular DOBLE: enlaces anterior/siguiente forman un anillo.

head.prev = tail y tail.next = head; ambas direcciones siguen el
anillo. Se detiene la iteración por contador en lugar de None.
"""
from __future__ import annotations
from dataclasses import dataclass
from typing import Generic, TypeVar
T = TypeVar("T")

@dataclass
class Nodo(Generic[T]):
    dato: T
    anterior: Nodo[T] | None = None
    siguiente: Nodo[T] | None = None

class ListaDobleCircular(Generic[T]):
    def __init__(self) -> None:
        self.ultimo: Nodo[T] | None = None
        self.cantidad = 0

    def agregar(self, dato: T) -> None:
        n = Nodo(dato)
        if self.ultimo is None:
            n.anterior = n
            n.siguiente = n
        else:
            primero = self.ultimo.siguiente
            assert primero is not None
            n.anterior = self.ultimo
            n.siguiente = primero
            primero.anterior = n
            self.ultimo.siguiente = n
        self.ultimo = n
        self.cantidad += 1

    def valores(self, inverso: bool = False) -> list[T]:
        r: list[T] = []
        if self.ultimo is None:
            return r
        actual = self.ultimo if inverso else self.ultimo.siguiente
        for _ in range(self.cantidad):
            assert actual is not None
            r.append(actual.dato)
            actual = actual.anterior if inverso else actual.siguiente
        return r

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\doble-circular\main.py
# Nota: lista.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   [2, 3, 5] [5, 3, 2]
# CodeTrace | FIN EJEMPLO DE CONSOLA
