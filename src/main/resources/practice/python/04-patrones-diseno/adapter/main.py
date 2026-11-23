# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Sensor legado del servidor que aloja partidas ficticias.
# Patrón adapter: Convertir lecturas para una interfaz que espera otra unidad.
# Glosario: Servidor de partida: equipo informático que coordina el juego.
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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\adapter\main.py
# Salida esperada:
#   Lectura: 25.0 °C
# CodeTrace | FIN EJEMPLO DE CONSOLA
