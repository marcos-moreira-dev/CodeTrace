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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\adapter\main.py
# Nota: conversor.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Lectura: 25.0 °C
# CodeTrace | FIN EJEMPLO DE CONSOLA
