# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py builder
# Dominio: Counter-Strike — sala privada; cs_office es un mapa de rescate.
# Patrón: Builder. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from dataclasses import dataclass

@dataclass(frozen=True)
class Sala:
    mapa: str
    rondas: int
    rehenes: bool

class Configurador:
    def __init__(self):
        self._mapa = "de_dust2"
        self._rondas = 6
        self._rehenes = False

    def mapa(self, nombre):
        self._mapa = nombre
        return self

    def rondas(self, cantidad):
        self._rondas = cantidad
        return self

    def rescate(self, habilitado):
        self._rehenes = habilitado
        return self

    def construir(self):
        # La sala solo nace cuando el anfitrión termina de elegir opciones.
        if not self._mapa or self._rondas <= 0:
            raise ValueError("Sala inválida")
        return Sala(self._mapa, self._rondas, self._rehenes)

def demo():
    s = Configurador().mapa("cs_office").rondas(12).rescate(True).construir()
    print(f"Sala: {s.mapa}, rondas={s.rondas}, rehenes={str(s.rehenes).lower()}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py builder
# Salida esperada:
#   Sala: cs_office, rondas=12, rehenes=true
# CodeTrace | FIN EJEMPLO DE CONSOLA
