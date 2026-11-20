# CodeTrace | Referencia: Python 3.12.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sin escribir archivos: python -c "import ast,pathlib; ast.parse(pathlib.Path('04-patrones-diseno/observador.py').read_text(encoding='utf-8'))"
# Ejecutar sin bytecode: python -B '.\04-patrones-diseno\observador.py'

from collections.abc import Callable


class Emisor:
    def __init__(self) -> None:
        self.oyentes: list[Callable[[int], None]] = []

    def suscribir(self, escucha: Callable[[int], None]) -> None:
        self.oyentes.append(escucha)

    def actualizar(self, valor: int) -> None:
        for escucha in self.oyentes:
            escucha(valor)


if __name__ == "__main__":
    emisor = Emisor()
    emisor.suscribir(lambda valor: print("Cambio:", valor))
    emisor.actualizar(42)
