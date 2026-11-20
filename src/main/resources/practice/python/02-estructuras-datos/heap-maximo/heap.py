# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('heap.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Max-heap manual para contrastar con heapq (min-heap estándar).

Almacena valores en un arreglo y conserva padre >= hijos.
Insertar y extraer cuestan O(log n); consultar raíz O(1).
"""
class HeapMaximo:
    def __init__(self) -> None:
        self.datos: list[int] = []

    def insertar(self, valor: int) -> None:
        a = self.datos
        a.append(valor)
        i = len(a)-1
        while i > 0:
            padre = (i-1)//2
            if a[padre] >= a[i]: break
            a[padre],a[i] = a[i],a[padre]
            i = padre

    def extraer(self) -> int:
        a = self.datos
        if not a: raise IndexError("Heap vacío")
        mayor = a[0]
        ultimo = a.pop()
        if a:
            a[0] = ultimo
            i = 0
            while True:
                m=i
                izq=2*i+1
                der=izq+1
                if izq < len(a) and a[izq]>a[m]: m=izq
                if der < len(a) and a[der]>a[m]: m=der
                if m==i: break
                a[i],a[m]=a[m],a[i]
                i=m
        return mayor
