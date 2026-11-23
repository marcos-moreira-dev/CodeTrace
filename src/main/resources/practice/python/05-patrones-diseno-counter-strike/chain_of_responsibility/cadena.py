# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py chain-of-responsibility
# Dominio: Counter-Strike — avisos de NPC rescatado, minimapa e inventario virtual.
# Patrón: Chain of Responsibility. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from dataclasses import dataclass
from typing import Callable

@dataclass(frozen=True)
class Aviso:
    tipo: str
    detalle: str

class Manejador:
    def __init__(self, tipo: str, prefijo: str):
        self.tipo, self.prefijo = tipo, prefijo
        self.siguiente = None

    def enlazar(self, siguiente):
        self.siguiente = siguiente
        return siguiente

    def atender(self, aviso: Aviso) -> str:
        # Un aviso de rescate solo lo atiende el responsable de NPC.
        if aviso.tipo == self.tipo:
            return f"{self.prefijo}: {aviso.detalle}"
        if self.siguiente is not None:
            return self.siguiente.atender(aviso)
        return f"Sin responsable: {aviso.detalle}"

def crear_cadena():
    primero = Manejador("rehen", "Rescate")
    primero.enlazar(Manejador("rival", "Reconocimiento")).enlazar(
        Manejador("equipo", "Inventario"))
    return primero

def demo():
    cadena = crear_cadena()
    # Son cuatro solicitudes, no cuatro responsables: todas entran por el mismo objeto.
    avisos = [Aviso("rehen", "rehén virtual liberado"),
              Aviso("rival", "rival marcado en el minimapa"),
              Aviso("equipo", "MP9 registrado como objeto virtual"),
              Aviso("otro", "evento del servidor no reconocido")]
    for aviso in avisos:
        print(cadena.atender(aviso))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py chain-of-responsibility
# Salida esperada:
#   Rescate: rehén virtual liberado
#   Reconocimiento: rival marcado en el minimapa
#   Inventario: MP9 registrado como objeto virtual
#   Sin responsable: evento del servidor no reconocido
# CodeTrace | FIN EJEMPLO DE CONSOLA
