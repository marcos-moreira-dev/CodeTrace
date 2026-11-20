# CodeTrace | Referencia: Python 3.12
# Comprobar desde copia del paquete (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/grafos/dijkstra/dijkstra.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B ".\03-algoritmos\grafos\dijkstra\dijkstra.py"

"""Cliente de Dijkstra con heap y reconstrucción de ruta.

Se parte de una lista de adyacencia dirigida y ponderada; TODAS sus
aristas deben ser no negativas. La PriorityQueue se modela con heapq
y se dejan entradas viejas que se ignoran al extraer. No se usa una
eliminación lineal del heap como en cierta versión original del
repositorio. Tiempo O((V+E) log(V+E)) con cola perezosa, espacio O(V+E).
"""
from heapq import heappop, heappush
from math import inf


def dijkstra(grafo: dict[str, list[tuple[str, float]]], origen: str):
    if origen not in grafo:
        raise ValueError("Origen ausente")
    vertices = set(grafo)
    for aristas in grafo.values():
        for destino, peso in aristas:
            if peso < 0 or peso == inf:
                raise ValueError("Dijkstra requiere pesos finitos no negativos")
            vertices.add(destino)
    distancia = {vertice: inf for vertice in vertices}
    previo: dict[str, str] = {}
    distancia[origen] = 0
    pendientes = [(0.0, origen)]
    while pendientes:
        costo, actual = heappop(pendientes)
        if costo != distancia[actual]:
            continue
        for destino, peso in grafo.get(actual, []):
            propuesta = costo + peso
            if propuesta < distancia[destino]:
                distancia[destino] = propuesta
                previo[destino] = actual
                heappush(pendientes, (propuesta, destino))
    return distancia, previo


def reconstruir(previo: dict[str, str], origen: str, destino: str) -> list[str]:
    if destino == origen:
        return [origen]
    if destino not in previo:
        return []
    invertido = [destino]
    while invertido[-1] != origen:
        invertido.append(previo[invertido[-1]])
    return invertido[::-1]


def main() -> None:
    red = {"A": [("B", 4), ("C", 1)],
           "B": [("D", 1)], "C": [("B", 2), ("D", 5)],
           "D": [("E", 3)], "E": []}
    dist, anterior = dijkstra(red, "A")
    ruta = reconstruir(anterior, "A", "E")
    print("Ruta mínima:", ruta, "costo:", dist["E"])
    assert ruta == ["A", "C", "B", "D", "E"] and dist["E"] == 7


if __name__ == "__main__":
    main()
