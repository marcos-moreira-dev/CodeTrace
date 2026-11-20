# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile ordenamientos.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Cinco ordenamientos clásicos del libro, sobre listas de enteros.

Insertion: mejor O(n), peor O(n²), estable, O(1) aux.
Bubble opt.: mejor O(n), peor O(n²), estable, O(1) aux.
Merge: O(n log n), estable si en empate toma izquierda, O(n) aux.
Heap: O(n log n), no estable, O(1) aux.
Quick: esperado O(n log n), peor O(n²) con pivote final, no estable.
Las pruebas miden orden correcto, NO tiempos de ejecución.
"""

def insercion(a: list[int]) -> None:
    for i in range(1, len(a)):
        clave = a[i]
        j = i - 1
        while j >= 0 and a[j] > clave:
            a[j + 1] = a[j]
            j -= 1
        a[j + 1] = clave

def burbuja(a: list[int]) -> None:
    limite = len(a)
    while limite > 1:
        ultimo = 0
        for j in range(1, limite):
            if a[j - 1] > a[j]:
                a[j - 1], a[j] = a[j], a[j - 1]
                ultimo = j
        limite = ultimo

def mezcla(a: list[int]) -> None:
    temporal = [0] * len(a)
    def ordenar(lo: int, hi: int) -> None:
        if hi - lo <= 1: return
        m = lo + (hi - lo) // 2
        ordenar(lo, m)
        ordenar(m, hi)
        i, j = lo, m
        for k in range(lo, hi):
            if j == hi or (i < m and a[i] <= a[j]):
                temporal[k] = a[i]
                i += 1
            else:
                temporal[k] = a[j]
                j += 1
        a[lo:hi] = temporal[lo:hi]
    ordenar(0, len(a))

def heap(a: list[int]) -> None:
    def hundir(n: int, i: int) -> None:
        while True:
            mayor = i
            izq, der = 2*i+1, 2*i+2
            if izq < n and a[izq] > a[mayor]: mayor = izq
            if der < n and a[der] > a[mayor]: mayor = der
            if mayor == i: return
            a[i],a[mayor] = a[mayor],a[i]
            i = mayor
    for i in range(len(a)//2-1,-1,-1): hundir(len(a),i)
    for fin in range(len(a)-1,0,-1):
        a[0],a[fin] = a[fin],a[0]
        hundir(fin,0)

def rapido(a: list[int]) -> None:
    def ordenar(lo: int, hi: int) -> None:
        if lo >= hi: return
        pivote = a[hi]
        i = lo
        for j in range(lo,hi):
            if a[j] <= pivote:
                a[i],a[j] = a[j],a[i]
                i += 1
        a[i],a[hi] = a[hi],a[i]
        ordenar(lo,i-1)
        ordenar(i+1,hi)
    ordenar(0,len(a)-1)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\ordenamientos\comparativa-cinco\main.py
# Nota: ordenamientos.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Insercion: [1, 2, 3, 5, 7, 8, 9]
#   Burbuja: [1, 2, 3, 5, 7, 8, 9]
#   Mezcla: [1, 2, 3, 5, 7, 8, 9]
#   HeapSort: [1, 2, 3, 5, 7, 8, 9]
#   QuickSort: [1, 2, 3, 5, 7, 8, 9]
# CodeTrace | FIN EJEMPLO DE CONSOLA
