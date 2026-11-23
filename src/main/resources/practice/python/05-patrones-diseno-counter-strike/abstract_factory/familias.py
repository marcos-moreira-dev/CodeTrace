# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py abstract-factory
# Dominio: Counter-Strike — HUD claro y oscuro, marcador e inventario.
# Patrón: Abstract Factory. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from typing import Protocol

class FamiliaHud(Protocol):
    def marcador(self, puntos: int) -> str: ...
    def inventario(self, objeto: str) -> str: ...

class Clara:
    def marcador(self, puntos: int) -> str:
        return f"[CLARO] CT={puntos}"
    def inventario(self, objeto: str) -> str:
        return f"[CLARO] equipo={objeto}"

class Oscura:
    def marcador(self, puntos: int) -> str:
        return f"[OSCURO] CT={puntos}"
    def inventario(self, objeto: str) -> str:
        return f"[OSCURO] equipo={objeto}"

def demo() -> None:
    # La lista contiene familias completas del HUD, no jugadores.
    for nombre, familia in [("Claro", Clara()), ("Oscuro", Oscura())]:
        print(f"{nombre}: {familia.marcador(5)} | {familia.inventario('MP9')}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py abstract-factory
# Salida esperada:
#   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
#   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
# CodeTrace | FIN EJEMPLO DE CONSOLA
