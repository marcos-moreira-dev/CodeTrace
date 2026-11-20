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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\linked-list\doble\main.py
# Salida esperada:
#   ['A', 'B']
# CodeTrace | FIN EJEMPLO DE CONSOLA
