# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: Consulte main.py

"""Strategies como funciones: Python no requiere interfaz nominal.

El contrato es un Callable que recibe un subtotal y devuelve el total.
La separación permite agregar reglas sin editar el cliente.
"""
from collections.abc import Callable

Estrategia = Callable[[float], float]


def normal(subtotal: float) -> float:
    return subtotal


def promocion(subtotal: float) -> float:
    return subtotal * 0.9
