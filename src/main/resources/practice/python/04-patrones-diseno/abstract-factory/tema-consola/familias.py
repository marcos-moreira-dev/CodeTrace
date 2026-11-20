# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('familias.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Abstract Factory produce familias coherentes de productos.

Botón y diálogo son contratos separados. Cada fábrica concreta entrega
ambos productos del mismo tema; un cliente no conoce tipos concretos.
"""
from typing import Protocol

class Boton(Protocol):
    def pintar(self) -> str: ...

class Dialogo(Protocol):
    def pintar(self) -> str: ...

class Fabrica(Protocol):
    def boton(self) -> Boton: ...
    def dialogo(self) -> Dialogo: ...

class Texto:
    def __init__(self, valor: str) -> None:
        self.valor=valor
    def pintar(self) -> str: return self.valor

class Claro:
    def boton(self) -> Boton: return Texto("[ OK ]")
    def dialogo(self) -> Dialogo: return Texto("Ventana clara")

class Oscuro:
    def boton(self) -> Boton: return Texto("< OK >")
    def dialogo(self) -> Dialogo: return Texto("Ventana oscura")
