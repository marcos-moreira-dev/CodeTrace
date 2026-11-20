# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente heap máximo y comparación con heapq.

Python ofrece heapq como heap mínimo, pero una implementación manual
aclara las fórmulas de índices y la diferencia entre heap y BST.
El arreglo interno NO queda ordenado; extraer sucesivamente sí.
"""
from heap import HeapMaximo

def main() -> None:
    heap = HeapMaximo()
    for x in (4, 1, 9, 7, 2): heap.insertar(x)
    assert [heap.extraer() for _ in range(5)] == [9, 7, 4, 2, 1]
    print("Max heap correcto")

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Max heap correcto
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
