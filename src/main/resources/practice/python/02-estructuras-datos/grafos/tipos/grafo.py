# CodeTrace | Referencia: Python 3.12
# Comprobar desde copia del paquete (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('02-estructuras-datos/grafos/tipos/grafo.py').read_text(encoding='utf-8'))"
# Ejecutar: ejecutar desde el cliente main.py de esta carpeta

"""Grafo dirigido/no dirigido, ponderado/no ponderado, con adyacencias.

La representación con dict de dict emplea O(V+E) memoria. Un grafo
no dirigido almacena la relación en ambos sentidos, mientras uno
dirigido puede tener A->B sin B->A. La estructura permite pesos
negativos como datos, sin afirmar que Dijkstra pueda utilizarlos.
"""
from collections import deque
from math import isfinite


class Grafo:
    def __init__(self, dirigido: bool, ponderado: bool) -> None:
        self.dirigido, self.ponderado = dirigido, ponderado
        self.ady: dict[str, dict[str, float]] = {}

    def vertice(self, identificador: str) -> None:
        if not identificador:
            raise ValueError("Vértice vacío")
        self.ady.setdefault(identificador, {})

    def conectar(self, desde: str, hasta: str, peso: float = 1.0) -> None:
        if desde == hasta or not isfinite(peso):
            raise ValueError("Sin lazos y pesos finitos")
        if not self.ponderado and peso != 1.0:
            raise ValueError("Grafo sin pesos")
        self.vertice(desde)
        self.vertice(hasta)
        self.ady[desde][hasta] = peso
        if not self.dirigido:
            self.ady[hasta][desde] = peso

    def vecinos(self, identificador: str) -> dict[str, float]:
        return dict(self.ady[identificador])

    def componentes(self) -> int:
        """Solo en no dirigidos: O(V+E), contando vértices aislados."""
        if self.dirigido:
            raise ValueError("Conectividad dirigida es otro concepto")
        visitados: set[str] = set()
        cantidad = 0
        for inicio in self.ady:
            if inicio in visitados:
                continue
            cantidad += 1
            cola = deque([inicio])
            visitados.add(inicio)
            while cola:
                for vecino in self.ady[cola.popleft()]:
                    if vecino not in visitados:
                        visitados.add(vecino)
                        cola.append(vecino)
        return cantidad

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\grafos\tipos\main.py
# Nota: grafo.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Aristas dirigidas: {'A': {'B': 3.5}, 'B': {}}
#   Componentes no dirigidas: 2
# CodeTrace | FIN EJEMPLO DE CONSOLA
