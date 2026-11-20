# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile limites.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Lower/Upper Bound para una lista ascendente, incluso con repetidos.

lower_bound: primer indice a[i] >= objetivo.
upper_bound: primer indice a[i] > objetivo.
Regresan una posicion en [0,n], incluso si falta el objetivo.
O(log n) comparaciones, O(1) espacio; precondicion: lista ordenada.
"""
def lower_bound(a: list[int],objetivo:int)->int:
    lo,hi=0,len(a)
    while lo<hi:
        m=lo+(hi-lo)//2
        if a[m]<objetivo:lo=m+1
        else:hi=m
    return lo

def upper_bound(a: list[int],objetivo:int)->int:
    lo,hi=0,len(a)
    while lo<hi:
        m=lo+(hi-lo)//2
        if a[m]<=objetivo:lo=m+1
        else:hi=m
    return lo

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\busqueda-rangos\main.py
# Nota: limites.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Valor 3: [1, 4)
#   Insertar 4 en indice: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
