# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Descuentos de inscripción a un torneo de partidas tácticas.
# Patrón strategy: Cambiar la política de cálculo sin alterar el cliente.
# Glosario: Inscripción: registro de un equipo en el torneo.
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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\strategy\main.py
# Nota: estrategias.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Normal: 100
#   Promoción: 90.0
# CodeTrace | FIN EJEMPLO DE CONSOLA
