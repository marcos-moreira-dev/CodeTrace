# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('03-algoritmos/euclides/euclides.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\03-algoritmos\euclides\euclides.py'

def mcd(a: int, b: int) -> int:
    while b != 0:
        a, b = b, a % b
    return a


if __name__ == "__main__":
    print("MCD:", mcd(48, 18))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\euclides\euclides.py
# Salida esperada:
#   MCD: 6
# CodeTrace | FIN EJEMPLO DE CONSOLA
