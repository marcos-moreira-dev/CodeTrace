# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('02-estructuras-datos/pila/pila.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\02-estructuras-datos\pila\pila.py'

class Pila:
    def __init__(self) -> None:
        self.datos: list[int] = []

    def apilar(self, valor: int) -> None:
        self.datos.append(valor)

    def desapilar(self) -> int:
        if not self.datos:
            raise IndexError("Pila vacía")
        return self.datos.pop()


if __name__ == "__main__":
    pila = Pila()
    pila.apilar(12)
    pila.apilar(8)
    print(pila.desapilar())
