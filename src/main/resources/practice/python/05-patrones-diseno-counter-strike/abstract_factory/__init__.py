# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py abstract-factory
# Dominio: Counter-Strike; paquete de práctica abstract-factory.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .familias import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py abstract-factory
# Salida esperada:
#   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
#   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
# CodeTrace | FIN EJEMPLO DE CONSOLA
