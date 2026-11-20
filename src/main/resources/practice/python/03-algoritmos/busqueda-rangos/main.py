# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -m py_compile main.py
# Ejecutar: python -B main.py (desde esta carpeta)

"""Cliente limites de un rango en lista ordenada.

[1,3,3,3,5,9]: los tres valores 3 ocupan [1,4) y el 4
se insertaria en indice 4. Lower/upper NO ordenan la lista ni
son equivalentes al algoritmo Insertion Sort.
"""
from limites import lower_bound,upper_bound

def main() -> None:
    a=[1,3,3,3,5,9]
    l,u=lower_bound(a,3),upper_bound(a,3)
    pos=lower_bound(a,4)
    assert (l,u,pos,upper_bound(a,4))==(1,4,4,4)
    print(f"Valor 3: [{l}, {u})")
    print(f"Insertar 4 en indice: {pos}")

if __name__=="__main__": main()

# Salida esperada:
# Valor 3: [1, 4)
# Insertar 4 en indice: 4

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\busqueda-rangos\main.py
# Salida esperada:
#   Valor 3: [1, 4)
#   Insertar 4 en indice: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
