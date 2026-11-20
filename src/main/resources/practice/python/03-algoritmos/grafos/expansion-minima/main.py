# CodeTrace | Referencia: Python 3.12
# Comprobar en copia (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/grafos/expansion-minima/main.py').read_text(encoding='utf-8'))"
# Ejecutar: desde main.py del mismo ejercicio

"""Cliente comparativo de árboles de expansión mínima.

Se representa una red NO DIRIGIDA de cinco edificios. Prim utiliza
una cola de prioridad para expandir la red; Kruskal decide por
orden global y evita ciclos mediante Union-Find. Ambos obtienen
el costo 8 y conectan V-1 aristas cuando el grafo es conexo.
En seis vértices, con el último aislado, se obtiene un bosque.
"""
from mst import Arista, prim, kruskal


def main() -> None:
    red = [Arista(0, 1, 3), Arista(0, 2, 1),
           Arista(1, 2, 2), Arista(1, 3, 4),
           Arista(2, 3, 5), Arista(3, 4, 1),
           Arista(2, 4, 8)]
    p = prim(5, red)
    k = kruskal(5, red)
    print("Prim:", p)
    print("Kruskal:", k)
    assert p[0] == k[0] == 8
    assert len(p[1]) == len(k[1]) == 4
    assert kruskal(6, red)[2] == 2


if __name__ == "__main__":
    main()
