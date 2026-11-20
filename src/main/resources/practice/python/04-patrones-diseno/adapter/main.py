# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\04-patrones-diseno\adapter\main.py"
# Ejecutar: python -B ".\04-patrones-diseno\adapter\main.py"

"""Cliente Adapter: desacoplar el dominio de una API incompatible.

Nuestro dominio espera grados Celsius. El proveedor hipotético conserva
su método Fahrenheit porque no tenemos autoridad para modificarlo.
El cliente conoce el protocolo SensorCelsius, no cómo convierte unidades
la implementación adaptadora.
"""
from conversor import Adaptador, SensorCelsius, TermometroLegado


def mostrar(sensor: SensorCelsius) -> None:
    print(f"Lectura: {sensor.leer_celsius():.1f} °C")


def main() -> None:
    mostrar(Adaptador(TermometroLegado(77.0)))


if __name__ == "__main__":
    main()
