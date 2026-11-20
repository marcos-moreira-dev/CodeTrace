# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('grafo.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Grafo dirigido con pesos por listas de adyacencia: O(V+E) memoria.

Representación independiente de algoritmos: Dijkstra requiere pesos
no negativos; Bellman-Ford tolera negativos sin ciclos negativos
alcanzables. La inversa de un arco no se inserta automáticamente.
"""
from dataclasses import dataclass
from math import isfinite

@dataclass(frozen=True)
class Arista:
    destino: str
    peso: float

class GrafoPonderado:
    def __init__(self) -> None:
        self.ady: dict[str, list[Arista]] = {}

    def conectar(self, origen: str, destino: str, peso: float) -> None:
        if not isfinite(peso): raise ValueError("Peso no finito")
        self.ady.setdefault(origen, []).append(Arista(destino, peso))
        self.ady.setdefault(destino, [])

    def vecinos(self, origen: str) -> tuple[Arista,...]:
        return tuple(self.ady[origen])

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\grafos\ponderados\main.py
# Nota: grafo.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Adyacencias: (Arista(destino='UIO', peso=12), Arista(destino='CUE', peso=18))
# CodeTrace | FIN EJEMPLO DE CONSOLA
