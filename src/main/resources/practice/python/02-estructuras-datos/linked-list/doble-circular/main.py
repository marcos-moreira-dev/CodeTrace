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

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
