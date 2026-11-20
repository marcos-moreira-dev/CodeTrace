# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('cola.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Queue FIFO por nodos enlazados, sin arreglo ni capacidad fija.

Se mantienen referencias a cabeza y cola para encolar y retirar
en O(1). Vaciar por completo exige reiniciar ambos extremos.
"""
from __future__ import annotations
from dataclasses import dataclass

@dataclass
class Nodo:
    dato: str
    siguiente: Nodo | None = None

class ColaEnlazada:
    def __init__(self) -> None:
        self.primero: Nodo | None = None
        self.ultimo: Nodo | None = None
        self.cantidad = 0

    def encolar(self, dato: str) -> None:
        n = Nodo(dato)
        if self.ultimo is None:
            self.primero = n
        else:
            self.ultimo.siguiente = n
        self.ultimo = n
        self.cantidad += 1

    def desencolar(self) -> str:
        if self.primero is None:
            raise IndexError("Cola vacía")
        dato = self.primero.dato
        self.primero = self.primero.siguiente
        if self.primero is None:
            self.ultimo = None
        self.cantidad -= 1
        return dato

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\cola-enlazada\main.py
# Nota: cola.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   FIFO enlazada correcta
# CodeTrace | FIN EJEMPLO DE CONSOLA
