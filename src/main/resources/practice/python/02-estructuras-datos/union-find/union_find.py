# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('union_find.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""DSU/Union-Find: compresión de caminos y unión por tamaño.

Consulta/unión costo amortizado O(alfa(n)), casi constante.
Mantiene componentes, pero no guarda el recorrido para volver
a construir un camino entre nodos de un grafo.
"""
class UnionFind:
    def __init__(self, n: int) -> None:
        if n <= 0: raise ValueError("n")
        self.padre = list(range(n))
        self.tamanio = [1]*n
        self.componentes = n

    def raiz(self, x: int) -> int:
        if self.padre[x] != x:
            self.padre[x] = self.raiz(self.padre[x])
        return self.padre[x]

    def unir(self, a: int, b: int) -> bool:
        x,y = self.raiz(a), self.raiz(b)
        if x == y: return False
        if self.tamanio[x] < self.tamanio[y]: x,y = y,x
        self.padre[y] = x
        self.tamanio[x] += self.tamanio[y]
        self.componentes -= 1
        return True

    def conectados(self, a: int, b: int) -> bool:
        return self.raiz(a) == self.raiz(b)
