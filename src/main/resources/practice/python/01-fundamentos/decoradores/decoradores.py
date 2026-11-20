# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: python -B .\01-fundamentos\decoradores.py

"""Cliente de decoradores: envolver comportamiento sin editar la función.

Un decorador recibe una función y devuelve otra. Es un uso práctico de
funciones de orden superior y closures, comparable a ciertos patrones
de envoltura de otros lenguajes. functools.wraps preserva metadatos.
"""
from functools import wraps
from collections.abc import Callable
from typing import TypeVar, ParamSpec

P = ParamSpec("P")
R = TypeVar("R")


def anunciar(funcion: Callable[P, R]) -> Callable[P, R]:
    @wraps(funcion)
    def envoltura(*args: P.args, **kwargs: P.kwargs) -> R:
        print(f"Ejecutando {funcion.__name__}")
        return funcion(*args, **kwargs)
    return envoltura


@anunciar
def sumar(a: int, b: int) -> int:
    return a + b


def main() -> None:
    print("Resultado:", sumar(4, 7))


if __name__ == "__main__":
    main()
