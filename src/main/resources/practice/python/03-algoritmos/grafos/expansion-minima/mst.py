# CodeTrace | Referencia: Python 3.12
# Comprobar en copia (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/grafos/expansion-minima/mst.py').read_text(encoding='utf-8'))"
# Ejecutar: desde main.py del mismo ejercicio

"""Prim, Kruskal y Union-Find: bosque de expansión mínima.

Un árbol de expansión mínima (MST) conecta todos los vértices de una
componente no dirigida minimizando la SUMA de sus aristas. No equivale
a encontrar el camino más corto entre dos vértices. En un grafo con
varias componentes se obtiene un bosque de expansión mínima.

Kruskal: ordenar aristas y unir componentes con DSU, O(E log E).
Prim: extender una frontera con heapq, O(E log E) con cola perezosa.
"""
from dataclasses import dataclass
from heapq import heappop, heappush


@dataclass(frozen=True)
class Arista:
    a: int
    b: int
    costo: int


class UnionFind:
    def __init__(self, cantidad: int) -> None:
        self.padre = list(range(cantidad))
        self.tamano = [1] * cantidad
        self.componentes = cantidad

    def buscar(self, v: int) -> int:
        if self.padre[v] != v:
            self.padre[v] = self.buscar(self.padre[v])
        return self.padre[v]

    def unir(self, a: int, b: int) -> bool:
        raiz_a, raiz_b = self.buscar(a), self.buscar(b)
        if raiz_a == raiz_b:
            return False
        if self.tamano[raiz_a] < self.tamano[raiz_b]:
            raiz_a, raiz_b = raiz_b, raiz_a
        self.padre[raiz_b] = raiz_a
        self.tamano[raiz_a] += self.tamano[raiz_b]
        self.componentes -= 1
        return True


def kruskal(vertices: int, aristas: list[Arista]) -> tuple[int, list[Arista], int]:
    validar(vertices, aristas)
    conjuntos = UnionFind(vertices)
    elegido = []
    total = 0
    for arista in sorted(aristas, key=lambda a: a.costo):
        if conjuntos.unir(arista.a, arista.b):
            elegido.append(arista)
            total += arista.costo
    return total, elegido, conjuntos.componentes


def prim(vertices: int, aristas: list[Arista]) -> tuple[int, list[Arista], int]:
    validar(vertices, aristas)
    vecinos: list[list[tuple[int, int, int]]] = [[] for _ in range(vertices)]
    for e in aristas:
        vecinos[e.a].append((e.costo, e.a, e.b))
        vecinos[e.b].append((e.costo, e.b, e.a))
    visitados: set[int] = set()
    elegido: list[Arista] = []
    total = componentes = 0
    for origen in range(vertices):
        if origen in visitados:
            continue
        componentes += 1
        visitados.add(origen)
        cola = list(vecinos[origen])
        from heapq import heapify
        heapify(cola)
        while cola:
            costo, a, b = heappop(cola)
            if b in visitados:
                continue
            visitados.add(b)
            elegido.append(Arista(a, b, costo))
            total += costo
            for arista in vecinos[b]:
                if arista[2] not in visitados:
                    heappush(cola, arista)
    return total, elegido, componentes


def validar(vertices: int, aristas: list[Arista]) -> None:
    if vertices <= 0:
        raise ValueError("Vértices")
    for e in aristas:
        if not (0 <= e.a < vertices and 0 <= e.b < vertices and e.a != e.b):
            raise ValueError("Arista no válida")
