# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('lista.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Lista enlazada simple: nodos independientes unidos por referencias.

Insertar en la cabeza O(1), buscar un valor O(n). En código de
producción Python se prefiere list o collections.deque según el
acceso; aquí importa observar el modelo de memoria y los enlaces.
"""
from dataclasses import dataclass
from typing import Generic, TypeVar

T = TypeVar("T")


@dataclass
class Nodo(Generic[T]):
    dato: T
    siguiente: "Nodo[T] | None" = None


class ListaSimple(Generic[T]):
    def __init__(self) -> None:
        self.primero: Nodo[T] | None = None
        self.cantidad = 0

    def insertar_inicio(self, dato: T) -> None:
        self.primero = Nodo(dato, self.primero)
        self.cantidad += 1

    def valores(self) -> list[T]:
        salida: list[T] = []
        actual = self.primero
        while actual is not None:
            salida.append(actual.dato)
            actual = actual.siguiente
        return salida

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\simple\main.py
# Nota: lista.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   ['Redes', 'Estructuras', 'Matemática']
# CodeTrace | FIN EJEMPLO DE CONSOLA
