# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py factory-method
# Dominio: Counter-Strike — exportación de puntuación virtual.
# Patrón: Factory Method. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from abc import ABC, abstractmethod

class Creador(ABC):
    @abstractmethod
    def crear_exportador(self): ...

    def publicar(self, puntos):
        # El flujo es común; la subclase elige el formato.
        return self.crear_exportador()(puntos)

class Texto(Creador):
    def crear_exportador(self):
        return lambda p: f"CT={p}"

class Csv(Creador):
    def crear_exportador(self):
        return lambda p: f"equipo,rondas;CT,{p}"

def demo():
    for nombre, creador in [("Texto", Texto()), ("CSV", Csv())]:
        print(f"{nombre}: {creador.publicar(7)}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py factory-method
# Salida esperada:
#   Texto: CT=7
#   CSV: equipo,rondas;CT,7
# CodeTrace | FIN EJEMPLO DE CONSOLA
