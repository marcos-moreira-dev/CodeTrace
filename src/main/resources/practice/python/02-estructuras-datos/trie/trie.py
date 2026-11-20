# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('trie.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Trie: índice de prefijos por caracteres, sobre diccionarios.

Inserción/consulta O(L) promedio para palabra de longitud L;
espacio proporcional a nodos creados. A diferencia del BST, no
compara palabras completas en cada nivel.
"""
from dataclasses import dataclass, field

@dataclass
class Nodo:
    hijos: dict[str, "Nodo"] = field(default_factory=dict)
    fin: bool = False

class Trie:
    def __init__(self) -> None:
        self.raiz = Nodo()

    def insertar(self, texto: str) -> None:
        actual = self.raiz
        for c in texto:
            actual = actual.hijos.setdefault(c, Nodo())
        actual.fin = True

    def _nodo(self, texto: str) -> Nodo | None:
        actual = self.raiz
        for c in texto:
            actual = actual.hijos.get(c)
            if actual is None: return None
        return actual

    def contiene(self, palabra: str) -> bool:
        n = self._nodo(palabra)
        return n is not None and n.fin

    def prefijo(self, texto: str) -> bool:
        return self._nodo(texto) is not None

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\trie\main.py
# Nota: trie.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Trie de prefijos OK
# CodeTrace | FIN EJEMPLO DE CONSOLA
