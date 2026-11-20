# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile main.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Cliente de comparación de 5 ordenamientos, referencia Java/libro.

Todos reciben COPIAS de una misma entrada. No medir milisegundos
como prueba de Big-O; la complejidad requiere razonamiento o una
serie experimental controlada. Insertion y Bubble son adaptativos;
QuickSort con pivote final degenera con datos ordenados.
"""
from ordenamientos import insercion,burbuja,mezcla,heap,rapido

def main() -> None:
    metodos = [("Insercion",insercion),("Burbuja",burbuja),
               ("Mezcla",mezcla),("HeapSort",heap),("QuickSort",rapido)]
    esperado=[1,2,3,5,7,8,9]
    for nombre,fn in metodos:
        datos=[9,3,8,1,7,2,5]
        fn(datos)
        assert datos==esperado, nombre
        print(f"{nombre}: {datos}")
        vacio: list[int]=[]
        repetidos=[3,1,3,1]
        fn(vacio);fn(repetidos)
        assert vacio==[] and repetidos==[1,1,3,3]

if __name__=="__main__": main()

# Salida esperada (cinco líneas, la misma lista para los cinco):
# Insercion: [1, 2, 3, 5, 7, 8, 9]
# Burbuja: [1, 2, 3, 5, 7, 8, 9]
# Mezcla: [1, 2, 3, 5, 7, 8, 9]
# HeapSort: [1, 2, 3, 5, 7, 8, 9]
# QuickSort: [1, 2, 3, 5, 7, 8, 9]
