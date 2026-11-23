# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Exportación de estadísticas de un torneo de partidas tácticas.
# Patrón factory-method: Delegar al creador concreto el formato del reporte.
# Glosario: Reporte: resumen de resultados de una partida, no instrucciones tácticas.
# Compilar: python 04-patrones-diseno/factory-method/main.py
from creadores import CreadorTexto

def main() -> None:
    print(CreadorTexto().preparar("Factura 001"))

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\factory-method\main.py
# Salida esperada:
#   TEXTO: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
