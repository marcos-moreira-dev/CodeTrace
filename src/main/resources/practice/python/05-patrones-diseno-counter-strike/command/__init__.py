# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py command
# Dominio: Counter-Strike; paquete de práctica command.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .marcador import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py command
# Salida esperada:
#   Tras ejecutar: CT=1
#   Tras deshacer: CT=0
# CodeTrace | FIN EJEMPLO DE CONSOLA
