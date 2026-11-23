# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py factory
# Dominio: Counter-Strike — mensajes del chat de la sala.
# Patrón: Simple Factory. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

from dataclasses import dataclass

@dataclass(frozen=True)
class Mensaje:
    contenido: str
    def mostrar(self):
        return "Chat de equipo: " + self.contenido

def crear_aviso(texto):
    # El anfitrión pide un aviso; no necesita conocer su construcción.
    return Mensaje(texto)

def demo():
    print(crear_aviso("Ronda iniciada").mostrar())

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py factory
# Salida esperada:
#   Chat de equipo: Ronda iniciada
# CodeTrace | FIN EJEMPLO DE CONSOLA
