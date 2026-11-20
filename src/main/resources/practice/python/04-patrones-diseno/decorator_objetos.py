# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\04-patrones-diseno\decorator_objetos.py"
# Ejecutar: python -B ".\04-patrones-diseno\decorator_objetos.py"

"""Cliente Decorator mediante composición de objetos en Python.

No confundir el patrón Decorator de GoF con la sintaxis @decorador
para funciones: pueden relacionarse, pero no son la misma construcción.
Una notificación puede envolverse para añadir etiquetas y auditoría.
Las capas poseen el mismo método enviar y se llaman por composición.
"""
from typing import Protocol


class Notificador(Protocol):
    def enviar(self, mensaje: str) -> None: ...


class Consola:
    def enviar(self, mensaje: str) -> None:
        print("Mensaje:", mensaje)


class ConPrefijo:
    def __init__(self, interno: Notificador, prefijo: str) -> None:
        self._interno = interno
        self._prefijo = prefijo

    def enviar(self, mensaje: str) -> None:
        self._interno.enviar(self._prefijo + mensaje)


def main() -> None:
    notificador: Notificador = ConPrefijo(Consola(), "[INFO] ")
    notificador.enviar("Proceso terminado")


if __name__ == "__main__":
    main()
