# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\02-estructuras-datos\arbol-bst\arbol.py"
# Ejecutar: ejecutar desde el módulo cliente del mismo ejercicio

"""Árbol BST con nodos enlazados.

Todo nodo de la izquierda contiene un valor menor que su padre y los
de la derecha uno mayor. Búsqueda e inserción requieren O(h), donde
h es la altura; sin balanceo pueden degradarse a O(n).
"""
from dataclasses import dataclass


@dataclass
class Nodo:
    valor: int
    izquierdo: "Nodo | None" = None
    derecho: "Nodo | None" = None


class ArbolBusqueda:
    def __init__(self) -> None:
        self.raiz: Nodo | None = None

    def insertar(self, valor: int) -> None:
        if self.raiz is None:
            self.raiz = Nodo(valor)
            return
        actual = self.raiz
        while True:
            if valor == actual.valor:
                return
            if valor < actual.valor:
                if actual.izquierdo is None:
                    actual.izquierdo = Nodo(valor)
                    return
                actual = actual.izquierdo
            else:
                if actual.derecho is None:
                    actual.derecho = Nodo(valor)
                    return
                actual = actual.derecho

    def contiene(self, valor: int) -> bool:
        actual = self.raiz
        while actual:
            if actual.valor == valor:
                return True
            actual = actual.izquierdo if valor < actual.valor else actual.derecho
        return False

    def inorden(self) -> list[int]:
        resultado: list[int] = []

        def recorrer(nodo: Nodo | None) -> None:
            if nodo:
                recorrer(nodo.izquierdo)
                resultado.append(nodo.valor)
                recorrer(nodo.derecho)

        recorrer(self.raiz)
        return resultado
