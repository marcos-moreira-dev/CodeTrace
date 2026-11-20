# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile insercion_binaria.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Binary Insertion Sort: posicion por upper_bound en prefijo ordenado.

Usar upper_bound mantiene la estabilidad de elementos iguales.
Comparaciones O(n log n), pero movimientos O(n²): no es tiempo total
O(n log n). Memoria auxiliar O(1) en arreglo/lista modificada in-place.
"""
def ordenar(a: list[int]) -> None:
    for i in range(1,len(a)):
        clave=a[i]
        lo,hi=0,i
        while lo<hi:
            m=lo+(hi-lo)//2
            if a[m]<=clave: lo=m+1
            else: hi=m
        for j in range(i,lo,-1):
            a[j]=a[j-1]
        a[lo]=clave
