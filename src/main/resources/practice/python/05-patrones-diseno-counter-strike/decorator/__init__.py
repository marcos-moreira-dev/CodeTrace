# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py decorator
# Dominio: Counter-Strike; paquete de práctica decorator.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .avisos import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py decorator
# Salida esperada:
#   [RONDA 12] [URGENTE] Rehén virtual rescatado
# CodeTrace | FIN EJEMPLO DE CONSOLA
