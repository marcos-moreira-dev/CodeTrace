# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA de este paquete:
# Comprobar sintaxis sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('02-objetos/clases.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\02-objetos\clases.py'

class Contador:
    def __init__(self) -> None:
        self.valor = 0

    def incrementar(self) -> int:
        self.valor += 1
        return self.valor

contador = Contador()
print(contador.incrementar())
