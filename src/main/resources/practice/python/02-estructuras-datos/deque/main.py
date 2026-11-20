# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Deque: combina comportamiento LIFO y FIFO.

A diferencia de una pila y una cola, Deque permite operar en ambos
extremos. Python dispone de collections.deque y no necesita una
jerarquía Java. El costo es O(1) en cada extremo (sin acceso indexado
aleatorio eficiente en todo el contenedor).
"""
from deque import Deque

def main() -> None:
    d = Deque()
    d.insertar_primero("B")
    d.insertar_primero("A")
    d.insertar_ultimo("C")
    assert [d.quitar_primero(), d.quitar_ultimo(), d.quitar_ultimo()] == ["A", "C", "B"]
    print("Deque verificada")

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\deque\main.py
# Salida esperada:
#   Deque verificada
# CodeTrace | FIN EJEMPLO DE CONSOLA
