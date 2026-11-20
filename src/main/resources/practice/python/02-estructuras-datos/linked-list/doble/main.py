# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Linked List doble: anterior/siguiente y dos extremos.

Su ventaja sobre una lista simple es navegar atrás y quitar el
último nodo en O(1). Su costo es una referencia extra por nodo.
En producción Python suele bastar collections.deque; aquí se
estudia explícitamente la representación de memoria.
"""
from lista import ListaDoble

def main() -> None:
    l = ListaDoble[str]()
    for s in ("A", "B", "C"):
        l.agregar_final(s)
    assert l.adelante() == ["A", "B", "C"]
    assert l.atras() == ["C", "B", "A"]
    assert l.quitar_final() == "C" and l.cantidad == 2
    print(l.adelante())

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
