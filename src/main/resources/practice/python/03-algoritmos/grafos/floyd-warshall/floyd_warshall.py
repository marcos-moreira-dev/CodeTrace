# CodeTrace | Referencia: Python 3.12
# Comprobar desde copia del paquete (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/grafos/floyd-warshall/floyd_warshall.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B ".\03-algoritmos\grafos\floyd-warshall\floyd_warshall.py"

"""Cliente Floyd-Warshall: distancias mínimas entre TODOS los pares.

Distintos de Dijkstra (una fuente), estos tres bucles calculan una
matriz completa de distancias, O(V³) tiempo y O(V²) memoria.
Permiten aristas negativas siempre que no haya ciclos negativos.
Si la diagonal llega a ser negativa, el camino mínimo no existe
para ciertos pares: en este ejemplo se rechaza la matriz completa.
"""
from math import inf


def floyd_warshall(matriz: list[list[float]]) -> list[list[float]]:
    n = len(matriz)
    if any(len(fila) != n for fila in matriz):
        raise ValueError("La matriz debe ser cuadrada")
    dist = [fila.copy() for fila in matriz]
    for k in range(n):
        for i in range(n):
            if dist[i][k] == inf:
                continue
            for j in range(n):
                propuesta = dist[i][k] + dist[k][j]
                if propuesta < dist[i][j]:
                    dist[i][j] = propuesta
    if any(dist[i][i] < 0 for i in range(n)):
        raise ValueError("Ciclo negativo")
    return dist


def main() -> None:
    red = [
        [0, 4, 10, inf],
        [inf, 0, -2, inf],
        [inf, inf, 0, 3],
        [inf, inf, inf, 0],
    ]
    dist = floyd_warshall(red)
    print("Distancia 0 -> 3:", dist[0][3])
    assert dist[0][3] == 5


if __name__ == "__main__":
    main()
