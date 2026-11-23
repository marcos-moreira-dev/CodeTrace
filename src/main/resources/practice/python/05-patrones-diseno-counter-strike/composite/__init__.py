# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py composite
# Dominio: Counter-Strike; paquete de práctica composite.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .mapas import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py composite
# Salida esperada:
#   Mapas registrados: 3
# CodeTrace | FIN EJEMPLO DE CONSOLA
