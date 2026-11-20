# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente hash abierto: colisiones, actualización y tabla llena.

Una colisión no permite sobrescribir otro identificador. Este caso
contrasta sondeo lineal contra buckets encadenados, y explica por qué
el factor de carga y la eliminación afectan las garantías.
"""
from tabla import TablaHashAbierta

def main() -> None:
    tabla=TablaHashAbierta(2)
    tabla.poner("A",1);tabla.poner("B",2);tabla.poner("A",3)
    assert (tabla.buscar("A"),tabla.buscar("B"),tabla.buscar("Z"))==(3,2,None)
    assert tabla.cantidad==2
    try:
        tabla.poner("C",4)
    except OverflowError:
        print("Tabla llena controlada")
    else:
        raise AssertionError("Se permitió una inserción extra")

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\main.py
# Salida esperada:
#   Tabla llena controlada
# CodeTrace | FIN EJEMPLO DE CONSOLA
