# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py template-method
# Dominio: Counter-Strike; paquete de práctica template-method.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .plantilla import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py template-method
# Salida esperada:
#   Texto: Partida de_dust2: CT=7
#   CSV: mapa,rondasCt;de_dust2,7
# CodeTrace | FIN EJEMPLO DE CONSOLA
