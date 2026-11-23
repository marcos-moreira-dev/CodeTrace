# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py command
# Dominio: Counter-Strike — registrar o revertir una victoria CT.
# Patrón: Command. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from dataclasses import dataclass

@dataclass
class Marcador:
    ct: int = 0
    t: int = 0

class SumarCt:
    def __init__(self, receptor: Marcador):
        self.receptor = receptor
        self.ejecutado = False

    def ejecutar(self):
        if self.ejecutado:
            raise ValueError("Ya ejecutado")
        # El comando guarda el receptor; el marcador no conoce al cliente.
        self.receptor.ct += 1
        self.ejecutado = True

    def deshacer(self):
        if not self.ejecutado:
            raise ValueError("No ejecutado")
        self.receptor.ct -= 1
        self.ejecutado = False

def demo():
    marcador = Marcador()
    comando = SumarCt(marcador)
    comando.ejecutar()
    print(f"Tras ejecutar: CT={marcador.ct}")
    comando.deshacer()
    print(f"Tras deshacer: CT={marcador.ct}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py command
# Salida esperada:
#   Tras ejecutar: CT=1
#   Tras deshacer: CT=0
# CodeTrace | FIN EJEMPLO DE CONSOLA
