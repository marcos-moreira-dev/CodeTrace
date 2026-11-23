# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py
# Dominio: Counter-Strike; organización del simulador de partidas.
# La fachada es la API pública para iniciar una nueva sesión de rescate.
from .fachada import FachadaCounterStrike

__all__ = ["FachadaCounterStrike"]

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
