# CodeTrace | Referencia: Python 3.12+, Dijkstra con heap
# Compilar: python dijkstra.py
import heapq

def dijkstra(grafo: list[list[tuple[int, int]]], inicio: int) -> list[float]:
    distancia = [float("inf")] * len(grafo)
    distancia[inicio] = 0
    cola = [(0, inicio)]
    while cola:
        costo, nodo = heapq.heappop(cola)
        if costo != distancia[nodo]:
            continue
        for vecino, peso in grafo[nodo]:
            if peso < 0:
                raise ValueError("Dijkstra requiere pesos no negativos")
            nuevo = costo + peso
            if nuevo < distancia[vecino]:
                distancia[vecino] = nuevo
                heapq.heappush(cola, (nuevo, vecino))
    return distancia

if __name__ == "__main__":
    grafo = [[(1, 2), (2, 8)], [(2, 3)], []]
    print(dijkstra(grafo, 0))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\dijkstra\dijkstra.py
# Salida esperada:
#   [0, 2, 5]
# CodeTrace | FIN EJEMPLO DE CONSOLA
