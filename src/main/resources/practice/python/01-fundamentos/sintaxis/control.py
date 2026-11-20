# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA de este paquete:
# Comprobar sintaxis sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('01-sintaxis/control.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\01-sintaxis\control.py'

def sumar_pares(limite: int) -> int:
    total = 0
    for numero in range(limite + 1):
        if numero % 2 == 0:
            total += numero
    return total

print(sumar_pares(10))
