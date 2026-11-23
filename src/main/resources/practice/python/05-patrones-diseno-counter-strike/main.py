# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py
# Dominio: Counter-Strike — partida automática de rescate de NPC.
# Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

import importlib
import sys
from facade.fachada import FachadaCounterStrike

PATRONES = {
    "abstract-factory": "abstract_factory.familias", "adapter": "adapter.ping",
    "builder": "builder.sala", "chain-of-responsibility": "chain_of_responsibility.cadena",
    "command": "command.marcador", "composite": "composite.mapas",
    "decorator": "decorator.avisos", "factory": "factory.chat",
    "factory-method": "factory_method.reportes", "observer": "observer.eventos",
    "strategy": "strategy.economia", "strategy-puntuacion": "strategy_puntuacion.reglas",
    "template-method": "template_method.plantilla",
}

if __name__ == "__main__":
    # Sin argumentos se relata la partida completa; no hay entrada interactiva.
    # Un argumento opcional permite ejecutar solo un patrón didáctico.
    if len(sys.argv) == 1:
        FachadaCounterStrike().jugar_partida()
    else:
        importlib.import_module(PATRONES[sys.argv[1]]).demo()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py
# Salida esperada:
#   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
#   Mapa: cs_office | Rondas: 3 | Ping: 42 ms
#   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
#   Mapas registrados: 2
#   --- RONDA 1 ---
#   Eco: guardar 1600 créditos
#   Rescate: Elena llegó a la zona de extracción
#   Rehén Elena: RESCATADO | escolta=Astra | calma=50 | integridad=92
#   Marcador: CT 1 - 0 T
#   --- RONDA 2 ---
#   Equipo: asignar 1900 créditos virtuales
#   Inventario: MP9 registrado como objeto virtual
#   Tiempo agotado sin rescate
#   Marcador: CT 1 - 1 T
#   --- RONDA 3 ---
#   Eco: guardar 1250 créditos
#   Rescate: Omar llegó a la zona de extracción
#   Rehén Omar: RESCATADO | escolta=Astra | calma=36 | integridad=86
#   Marcador: CT 2 - 1 T
#   === FIN DE LA PARTIDA ===
#   Resultado: Gana CT
#   Créditos Astra: 1550
#   Reporte: CT=2
# CodeTrace | FIN EJEMPLO DE CONSOLA
