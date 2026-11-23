# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py adapter
# Dominio: Counter-Strike — ping = latencia del servidor en milisegundos.
# Patrón: Adapter. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

class TelemetriaLegada:
    def microsegundos(self) -> int:
        return 42000

class AdaptadorPing:
    def __init__(self, origen: TelemetriaLegada):
        self.origen = origen

    def mostrar(self) -> str:
        # El HUD consume ms; conservamos intacta la API antigua.
        return f"Ping: {self.origen.microsegundos() // 1000} ms"

def demo():
    print(AdaptadorPing(TelemetriaLegada()).mostrar())

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py adapter
# Salida esperada:
#   Ping: 42 ms
# CodeTrace | FIN EJEMPLO DE CONSOLA
