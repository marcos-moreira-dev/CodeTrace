# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py observer
# Dominio: Counter-Strike — el marcador notifica a las pantallas de espectadores.
# Patrón: Observer. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from collections.abc import Callable

class MarcadorObservable:
    def __init__(self):
        self._observadores: list[Callable[[str, int], None]] = []

    def suscribir(self, receptor):
        self._observadores.append(receptor)

    def anotar(self, equipo, puntos):
        # Cada HUD recibe el mismo dato; no compite por atenderlo.
        for receptor in tuple(self._observadores):
            receptor(equipo, puntos)

def demo():
    marcador = MarcadorObservable()
    marcador.suscribir(lambda e, p: print(f"HUD: {e}={p}"))
    marcador.anotar("CT", 2)
    marcador.anotar("T", 1)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py observer
# Salida esperada:
#   HUD: CT=2
#   HUD: T=1
# CodeTrace | FIN EJEMPLO DE CONSOLA
