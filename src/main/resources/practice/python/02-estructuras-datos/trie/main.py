# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Trie: prefijo presente, pero no necesariamente palabra.

"cas" es prefijo de "casa" y "casco" aunque "cas" no sea una
palabra insertada. La representación usa dict, no un alfabeto fijo.
"""
from trie import Trie

def main() -> None:
    t = Trie()
    t.insertar("casa"); t.insertar("casco")
    assert t.prefijo("cas") and not t.contiene("cas")
    assert t.contiene("casa") and not t.contiene("cama")
    print("Trie de prefijos OK")

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\trie\main.py
# Salida esperada:
#   Trie de prefijos OK
# CodeTrace | FIN EJEMPLO DE CONSOLA
