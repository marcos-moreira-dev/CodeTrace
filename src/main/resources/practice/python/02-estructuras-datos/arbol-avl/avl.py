# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('avl.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""BST autoequilibrado AVL.

Balance = altura(izq) - altura(der) en [-1,1]. Las rotaciones corrigen
desbalance tras insertar. Insertar/buscar O(log n) garantizado.
"""
from __future__ import annotations
from dataclasses import dataclass

@dataclass
class Nodo:
    dato: int
    izq: Nodo | None = None
    der: Nodo | None = None
    altura: int = 1

def altura(n: Nodo | None) -> int:
    return n.altura if n else 0

def actualizar(n: Nodo) -> None:
    n.altura = 1 + max(altura(n.izq), altura(n.der))

def girar_derecha(y: Nodo) -> Nodo:
    x = y.izq
    assert x is not None
    y.izq = x.der
    x.der = y
    actualizar(y); actualizar(x)
    return x

def girar_izquierda(x: Nodo) -> Nodo:
    y = x.der
    assert y is not None
    x.der = y.izq
    y.izq = x
    actualizar(x); actualizar(y)
    return y

class AVL:
    def __init__(self) -> None:
        self.raiz: Nodo | None = None

    def insertar(self, dato: int) -> None:
        def agregar(n: Nodo | None) -> Nodo:
            if n is None:
                return Nodo(dato)
            if dato < n.dato:
                n.izq = agregar(n.izq)
            elif dato > n.dato:
                n.der = agregar(n.der)
            else:
                return n
            actualizar(n)
            bal = altura(n.izq) - altura(n.der)
            if bal > 1:
                assert n.izq is not None
                if dato > n.izq.dato:
                    n.izq = girar_izquierda(n.izq)
                return girar_derecha(n)
            if bal < -1:
                assert n.der is not None
                if dato < n.der.dato:
                    n.der = girar_derecha(n.der)
                return girar_izquierda(n)
            return n
        self.raiz = agregar(self.raiz)

    def inorden(self) -> list[int]:
        salida: list[int] = []
        def recorrer(n: Nodo | None) -> None:
            if n:
                recorrer(n.izq); salida.append(n.dato); recorrer(n.der)
        recorrer(self.raiz)
        return salida

    def validar(self) -> int:
        def validar(n: Nodo | None) -> int:
            if n is None: return 0
            a, b = validar(n.izq), validar(n.der)
            assert abs(a-b) <= 1 and n.altura == 1 + max(a,b)
            return n.altura
        return validar(self.raiz)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\arbol-avl\main.py
# Nota: avl.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Altura AVL: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
