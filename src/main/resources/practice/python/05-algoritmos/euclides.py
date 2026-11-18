# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('05-algoritmos/euclides.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\05-algoritmos\euclides.py'

def mcd(a: int, b: int) -> int:
    while b != 0:
        a, b = b, a % b
    return a


if __name__ == "__main__":
    print("MCD:", mcd(48, 18))
