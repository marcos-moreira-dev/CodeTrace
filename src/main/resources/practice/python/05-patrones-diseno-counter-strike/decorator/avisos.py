# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py decorator
# Dominio: Counter-Strike — etiquetas de ronda y urgencia de un aviso del juego.
# Patrón: Decorator. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from typing import Callable

Aviso = Callable[[], str]

def urgente(aviso: Aviso) -> Aviso:
    # La función devuelta captura el aviso anterior, sin cambiar su texto.
    return lambda: "[URGENTE] " + aviso()

def de_ronda(aviso: Aviso, ronda: int) -> Aviso:
    return lambda: f"[RONDA {ronda}] " + aviso()

def demo():
    mensaje = de_ronda(urgente(lambda: "Rehén virtual rescatado"), 12)
    print(mensaje())

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py decorator
# Salida esperada:
#   [RONDA 12] [URGENTE] Rehén virtual rescatado
# CodeTrace | FIN EJEMPLO DE CONSOLA
