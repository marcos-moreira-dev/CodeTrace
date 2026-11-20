# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente de grafos ponderados, separados del algoritmo aplicado.

Diferenciar dirección del arco y su peso: A->B puede costar distinto
de B->A. No se presupone una arista inversa ni que Dijkstra funcione
con pesos negativos. El grafo modela el dominio, no selecciona rutas.
"""
from grafo import GrafoPonderado

def main() -> None:
    g=GrafoPonderado()
    g.conectar("GYE","UIO",12)
    g.conectar("GYE","CUE",18)
    g.conectar("UIO","CUE",8)
    assert len(g.vecinos("GYE"))==2
    assert not g.vecinos("CUE")
    print("Adyacencias:",g.vecinos("GYE"))

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\grafos\ponderados\main.py
# Salida esperada:
#   Adyacencias: (Arista(destino='UIO', peso=12), Arista(destino='CUE', peso=18))
# CodeTrace | FIN EJEMPLO DE CONSOLA
