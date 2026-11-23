# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py strategy-puntuacion
# Dominio: Counter-Strike — reglas ficticias de puntos del torneo casual y competitivo.
# Patrón: Strategy. Ejercicio de consola independiente del motor real.
# Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores en pantalla.

def casual(victorias):
    return 2 * victorias

def competitiva(victorias):
    return 3 * victorias

def puntos(victorias, regla):
    # No son reglas oficiales: comparamos algoritmos sobre dos victorias.
    return regla(victorias)

def demo():
    print(f"Casual: {puntos(2, casual)} puntos")
    print(f"Competitivo: {puntos(2, competitiva)} puntos")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py strategy-puntuacion
# Salida esperada:
#   Casual: 4 puntos
#   Competitivo: 6 puntos
# CodeTrace | FIN EJEMPLO DE CONSOLA
