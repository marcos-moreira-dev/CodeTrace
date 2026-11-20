# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('observador.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Observer con funciones como suscriptores.

El emisor almacena callbacks, no clases concretas de vista o registro.
Se proporciona desuscripción explícita para estudiar ciclo de vida:
una lista de observadores puede retener objetos sin querer.
"""
from collections.abc import Callable


class Emisor:
    def __init__(self) -> None:
        self.oyentes: list[Callable[[int], None]] = []
        self.valor = 0

    def suscribir(self, escucha: Callable[[int], None]) -> None:
        if escucha not in self.oyentes:
            self.oyentes.append(escucha)

    def desuscribir(self, escucha: Callable[[int], None]) -> None:
        self.oyentes.remove(escucha)

    def actualizar(self, valor: int) -> None:
        self.valor = valor
        for escucha in tuple(self.oyentes):
            escucha(valor)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\observer\basico\main.py
# Nota: observador.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Observer y desuscripción verificados
# CodeTrace | FIN EJEMPLO DE CONSOLA
