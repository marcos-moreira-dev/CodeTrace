# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('tabla.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Hash con direccionamiento abierto, sondeo lineal y capacidad fija.

No soporta borrar: sería necesario un tombstone para conservar
las cadenas de sondeo. Peor O(n), esperado O(1) con baja ocupación.
"""
class TablaHashAbierta:
    def __init__(self, capacidad: int) -> None:
        if capacidad <= 0: raise ValueError("capacidad")
        self.datos: list[tuple[str,int] | None] = [None]*capacidad
        self.cantidad = 0

    def poner(self, clave: str, valor: int) -> None:
        n=len(self.datos); inicio=hash(clave)%n
        for paso in range(n):
            i=(inicio+paso)%n
            par=self.datos[i]
            if par is None:
                self.datos[i]=(clave,valor)
                self.cantidad+=1
                return
            if par[0]==clave:
                self.datos[i]=(clave,valor)
                return
        raise OverflowError("Tabla llena")

    def buscar(self, clave: str) -> int | None:
        n=len(self.datos);inicio=hash(clave)%n
        for paso in range(n):
            par=self.datos[(inicio+paso)%n]
            if par is None: return None
            if par[0]==clave: return par[1]
        return None
