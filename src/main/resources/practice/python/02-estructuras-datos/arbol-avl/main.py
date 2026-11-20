# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente AVL: contrasta la altura de un BST sin balanceo.

Una secuencia ascendente degeneraría un BST ordinario; un AVL debe
mantener factor de balance en [-1,1] y altura O(log n). La validación
didáctica recorre todos los nodos y no forma parte de la operación.
"""
from avl import AVL

def main() -> None:
    arbol = AVL()
    for i in range(1, 16): arbol.insertar(i)
    assert arbol.inorden() == list(range(1, 16))
    altura = arbol.validar()
    assert altura <= 5
    print("Altura AVL:", altura)

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\arbol-avl\main.py
# Salida esperada:
#   Altura AVL: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
