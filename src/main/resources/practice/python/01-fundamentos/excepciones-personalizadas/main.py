# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente de excepción propia, @property e invariante de dominio.

El modelo inventario valida el negocio y este cliente decide
cómo presentar un rechazo. Se compara con las excepciones checked
de Java y las excepciones unchecked de C#/Kotlin. Una operación
fallida debe conservar el estado anterior.
"""
from inventario import Inventario, StockInsuficienteError


def main() -> None:
    inventario = Inventario(5)
    inventario.despachar(2)
    try:
        inventario.despachar(10)
    except StockInsuficienteError as error:
        assert error.disponibles == 3
        print("Rechazo:", error)
    else:
        raise AssertionError("Debía fallar")
    assert inventario.disponibles == 3


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Rechazo:
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
