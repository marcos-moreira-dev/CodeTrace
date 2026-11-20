# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile main.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Cliente Binary Insertion Sort.

Buscar una posicion no equivale a evitar desplazamientos; por eso
una insercion binaria sobre un arreglo sigue siendo cuadratica en
movimientos. Contrastar con Insertion Sort lineal y Merge Sort.
"""
from insercion_binaria import ordenar

def main() -> None:
    a=[5,1,3,3,2]
    ordenar(a)
    assert a==[1,2,3,3,5]
    vacio: list[int]=[]
    ordenar(vacio)
    assert vacio==[]
    print("Insercion binaria:",a)

if __name__=="__main__": main()

# Salida esperada:
# Insercion binaria: [1, 2, 3, 3, 5]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\ordenamientos\insercion-binaria\main.py
# Salida esperada:
#   Insercion binaria: [1, 2, 3, 3, 5]
# CodeTrace | FIN EJEMPLO DE CONSOLA
