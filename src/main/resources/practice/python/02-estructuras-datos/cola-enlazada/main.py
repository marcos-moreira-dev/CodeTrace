# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente cola enlazada vs cola circular de arreglo.

Una cola enlazada inserta nodos dinámicos en vez de reservar una
capacidad inicial. Con cabeza y cola, ambas operaciones son O(1).
Se verifica el caso de vaciarla y reutilizarla, que suele producir
errores si no se reinicia tail.
"""
from cola import ColaEnlazada

def main() -> None:
    q = ColaEnlazada()
    q.encolar("A"); q.encolar("B")
    assert q.desencolar() == "A"
    assert q.desencolar() == "B"
    q.encolar("C")
    assert q.desencolar() == "C" and q.cantidad == 0
    print("FIFO enlazada correcta")

if __name__ == "__main__":
    main()
