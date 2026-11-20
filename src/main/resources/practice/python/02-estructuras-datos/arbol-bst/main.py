# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\02-estructuras-datos\arbol-bst\main.py"
# Ejecutar: python -B ".\02-estructuras-datos\arbol-bst\main.py"

"""Cliente de un árbol binario de búsqueda sin balanceo.

La elección de claves determina su altura. Un recorrido inorden
produce los valores ascendentes porque el árbol conserva la
propiedad izquierda-menor/derecha-mayor. No confundas BST con AVL:
este ejercicio no implementa rotaciones.
"""
from arbol import ArbolBusqueda


def main() -> None:
    arbol = ArbolBusqueda()
    for numero in (8, 3, 10, 1, 6, 14, 4, 7):
        arbol.insertar(numero)
    print(arbol.inorden())
    assert arbol.contiene(6) and not arbol.contiene(9)


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\arbol-bst\main.py
# Salida esperada:
#   [1, 3, 4, 6, 7, 8, 10, 14]
# CodeTrace | FIN EJEMPLO DE CONSOLA
