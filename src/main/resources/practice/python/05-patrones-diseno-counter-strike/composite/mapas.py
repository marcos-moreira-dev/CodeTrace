# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py composite
# Dominio: Counter-Strike — mapas agrupados en carpetas de recursos.
# Patrón: Composite. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from dataclasses import dataclass, field
from typing import Protocol

class Recurso(Protocol):
    def contar(self) -> int: ...

@dataclass
class Mapa:
    nombre: str
    def contar(self):
        return 1

@dataclass
class Carpeta:
    nombre: str
    hijos: list[Recurso] = field(default_factory=list)

    def contar(self):
        # Una subcarpeta suma sus mapas recursivamente; una hoja vale uno.
        return sum(hijo.contar() for hijo in self.hijos)

def demo():
    raiz = Carpeta("maps", [Carpeta("oficiales", [Mapa("de_dust2"),
                   Mapa("de_inferno")]), Mapa("cs_office")])
    print(f"Mapas registrados: {raiz.contar()}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py composite
# Salida esperada:
#   Mapas registrados: 3
# CodeTrace | FIN EJEMPLO DE CONSOLA
