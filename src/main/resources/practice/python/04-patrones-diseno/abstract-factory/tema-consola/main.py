# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Abstract Factory, contrastado con Factory Method.

Una fábrica crea DOS productos relacionados. No basta con una función
que elija un botón: toda la familia comparte una identidad de tema.
Python emplea Protocol estructural y duck typing en vez de interfaces
nominales obligatorias.
"""
from familias import Fabrica, Claro, Oscuro

def pantalla(fabrica: Fabrica) -> str:
    return fabrica.boton().pintar()+" / "+fabrica.dialogo().pintar()

def main() -> None:
    a,b=pantalla(Claro()),pantalla(Oscuro())
    assert a!=b and "clara" in a and "oscura" in b
    print(a);print(b)

if __name__ == "__main__":
    main()

# Salida esperada de consola (dos líneas):
#   [ OK ] / Ventana clara
#   < OK > / Ventana oscura
# Los Protocol definen los contratos, no la presentación del resultado.
