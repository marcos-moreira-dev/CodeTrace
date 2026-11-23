# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py template-method
# Dominio: Counter-Strike — resumen de una partida en texto y CSV.
# Patrón: Template Method. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from abc import ABC, abstractmethod

class Reporte(ABC):
    def generar(self, mapa, puntos):
        # Las dos fases siempre se ejecutan en este orden.
        return self.encabezado(mapa) + self.detalle(puntos)

    @abstractmethod
    def encabezado(self, mapa): ...

    @abstractmethod
    def detalle(self, puntos): ...

class Texto(Reporte):
    def encabezado(self, mapa): return f"Partida {mapa}: "
    def detalle(self, puntos): return f"CT={puntos}"

class Csv(Reporte):
    def encabezado(self, mapa): return f"mapa,rondasCt;{mapa},"
    def detalle(self, puntos): return str(puntos)

def demo():
    for nombre, formato in [("Texto", Texto()), ("CSV", Csv())]:
        print(f"{nombre}: {formato.generar('de_dust2', 7)}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py template-method
# Salida esperada:
#   Texto: Partida de_dust2: CT=7
#   CSV: mapa,rondasCt;de_dust2,7
# CodeTrace | FIN EJEMPLO DE CONSOLA
