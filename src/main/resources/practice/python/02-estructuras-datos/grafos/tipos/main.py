# CodeTrace | Referencia: Python 3.12
# Comprobar desde copia del paquete (PowerShell): python -c "import ast,pathlib; ast.parse(pathlib.Path('02-estructuras-datos/grafos/tipos/main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B ".\02-estructuras-datos\grafos\tipos\main.py"

"""Cliente de tipos de grafo y su representación.

Un grafo es dirigido o no dirigido según las flechas. La ponderación
es independiente de la orientación. Un grafo desconectado tiene más
de una componente. El programa muestra también la distinción entre
lista de adyacencia O(V+E) y matriz O(V²), sin forzar clases de Java.
"""
from grafo import Grafo


def main() -> None:
    calle = Grafo(dirigido=True, ponderado=True)
    calle.conectar("A", "B", 3.5)
    assert "B" in calle.vecinos("A") and "A" not in calle.vecinos("B")
    red = Grafo(dirigido=False, ponderado=False)
    red.conectar("A", "B")
    red.conectar("B", "C")
    red.vertice("AISLADO")
    assert red.componentes() == 2
    print("Aristas dirigidas:", calle.ady)
    print("Componentes no dirigidas:", red.componentes())


if __name__ == "__main__":
    main()
