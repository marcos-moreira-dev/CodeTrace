# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('lista.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Linked List doble: referencias anterior y siguiente.

Insertar al principio o final O(1). Recorrer O(n), memoria O(n).
Un elemento desconectado debe liberar sus enlaces y actualizar extremos.
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

class ListaDoble(Generic[T]):
    def __init__(self) -> None:
        self.primero: Nodo[T] | None = None
        self.ultimo: Nodo[T] | None = None
        self.cantidad = 0

    def agregar_final(self, dato: T) -> None:
        n = Nodo(dato, self.ultimo)
        if self.ultimo is None:
            self.primero = n
        else:
            self.ultimo.siguiente = n
        self.ultimo = n
        self.cantidad += 1

    def quitar_final(self) -> T:
        if self.ultimo is None:
            raise IndexError("Lista vacía")
        valor = self.ultimo.dato
        self.ultimo = self.ultimo.anterior
        if self.ultimo is None:
            self.primero = None
        else:
            self.ultimo.siguiente = None
        self.cantidad -= 1
        return valor

    def adelante(self) -> list[T]:
        r: list[T] = []
        n = self.primero
        while n is not None:
            r.append(n.dato)
            n = n.siguiente
        return r

    def atras(self) -> list[T]:
        r: list[T] = []
        n = self.ultimo
        while n is not None:
            r.append(n.dato)
            n = n.anterior
        return r

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\doble\main.py
# Nota: lista.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   ['A', 'B']
# CodeTrace | FIN EJEMPLO DE CONSOLA
