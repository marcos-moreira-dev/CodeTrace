# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py strategy
# Dominio: Counter-Strike — eco = fase de ahorro de créditos virtuales.
# Patrón: Strategy. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from collections.abc import Callable

def eco(creditos):
    return f"Eco: guardar {creditos} créditos"

def equipo(creditos):
    return f"Equipo: asignar {creditos} créditos virtuales"

class Economia:
    def __init__(self, politica: Callable[[int], str]):
        self.politica = politica

    def plan(self, creditos):
        # La política describe la decisión; el saldo real vive en el jugador.
        return self.politica(creditos)

def demo():
    gestor = Economia(eco)
    print(gestor.plan(1500))
    gestor.politica = equipo
    print(gestor.plan(1500))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py strategy
# Salida esperada:
#   Eco: guardar 1500 créditos
#   Equipo: asignar 1500 créditos virtuales
# CodeTrace | FIN EJEMPLO DE CONSOLA
