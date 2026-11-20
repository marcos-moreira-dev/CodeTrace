# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Linked List doble circular; dos enlaces por nodo y anillo.

Esta variante combina navegación bidireccional y rotación circular.
El cliente comprueba vistas en ambos sentidos, pero el modelo es
deliberadamente pequeño: no es una colección concurrente ni general.
"""
from lista import ListaDobleCircular

def main() -> None:
    l = ListaDobleCircular[int]()
    for x in (2, 3, 5):
        l.agregar(x)
    assert l.valores() == [2, 3, 5]
    assert l.valores(inverso=True) == [5, 3, 2]
    print(l.valores(), l.valores(True))

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\doble-circular\main.py
# Salida esperada:
#   [2, 3, 5] [5, 3, 2]
# CodeTrace | FIN EJEMPLO DE CONSOLA
