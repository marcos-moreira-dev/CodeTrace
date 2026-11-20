# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\04-patrones-diseno\adapter\conversor.py"
# Ejecutar: ejecutar desde el módulo cliente del mismo ejercicio

"""Contrato legado y adaptador de unidades.

La API externa entrega Fahrenheit; el resto del sistema quiere Celsius.
El adaptador traduce sin cambiar al proveedor. En Python, Protocol
permite expresar el contrato mediante métodos, sin herencia explícita.
"""
from typing import Protocol


class SensorCelsius(Protocol):
    def leer_celsius(self) -> float: ...


class TermometroLegado:
    def __init__(self, fahrenheit: float) -> None:
        self._temperatura = fahrenheit

    def lectura_fahrenheit(self) -> float:
        return self._temperatura


class Adaptador:
    def __init__(self, proveedor: TermometroLegado) -> None:
        self._proveedor = proveedor

    def leer_celsius(self) -> float:
        return (self._proveedor.lectura_fahrenheit() - 32) * 5 / 9
