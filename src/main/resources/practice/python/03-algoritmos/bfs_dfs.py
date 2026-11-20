# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\03-algoritmos\bfs_dfs.py"
# Ejecutar: python -B ".\03-algoritmos\bfs_dfs.py"

"""Cliente de BFS y DFS con listas de adyacencia.

BFS utiliza una cola para visitar vértices por número de aristas.
DFS utiliza una pila para profundizar antes de explorar otras rutas.
En un grafo almacenado por listas, ambos tienen tiempo O(V+E) y
memoria adicional O(V).
"""
from collections import deque


def bfs(grafo: dict[str, list[str]], origen: str) -> list[str]:
    if origen not in grafo:
        raise ValueError("Origen ausente")
    cola = deque([origen])
    visitados = {origen}
    orden = []
    while cola:
        actual = cola.popleft()
        orden.append(actual)
        for vecino in grafo.get(actual, []):
            if vecino not in visitados:
                visitados.add(vecino)
                cola.append(vecino)
    return orden


def dfs(grafo: dict[str, list[str]], origen: str) -> list[str]:
    if origen not in grafo:
        raise ValueError("Origen ausente")
    pila = [origen]
    visitados: set[str] = set()
    orden = []
    while pila:
        actual = pila.pop()
        if actual in visitados:
            continue
        visitados.add(actual)
        orden.append(actual)
        pila.extend(reversed(grafo.get(actual, [])))
    return orden


def main() -> None:
    grafo = {"A": ["B", "C"], "B": ["D"], "C": ["E"], "D": [], "E": []}
    print("BFS:", bfs(grafo, "A"))
    print("DFS:", dfs(grafo, "A"))


if __name__ == "__main__":
    main()
