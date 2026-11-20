# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/busqueda-binaria/busqueda_binaria.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\03-algoritmos\busqueda-binaria\busqueda_binaria.py'

def buscar(datos: list[int], objetivo: int) -> int:
    izquierda, derecha = 0, len(datos) - 1
    while izquierda <= derecha:
        medio = izquierda + (derecha - izquierda) // 2
        if datos[medio] == objetivo:
            return medio
        if datos[medio] < objetivo:
            izquierda = medio + 1
        else:
            derecha = medio - 1
    return -1


if __name__ == "__main__":
    print(buscar([2, 4, 8, 12, 16], 12))
