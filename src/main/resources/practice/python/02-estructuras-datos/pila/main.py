# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente de pila LIFO y comprobación de contratos.

La última inserción se recupera primero. Se prueba una extracción
inexistente: una excepción describe el error de uso en vez
de devolver un valor inventado que pudiera ser dato legítimo.
"""
from pila import Pila


def main() -> None:
    pila = Pila()
    for valor in (10, 20, 30):
        pila.apilar(valor)
    assert pila.desapilar() == 30
    assert pila.desapilar() == 20
    assert pila.desapilar() == 10
    try:
        pila.desapilar()
    except IndexError:
        print("LIFO y pila vacía verificados")
    else:
        raise AssertionError("Se aceptó una extracción imposible")


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   LIFO y pila vacía verificados
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
