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
