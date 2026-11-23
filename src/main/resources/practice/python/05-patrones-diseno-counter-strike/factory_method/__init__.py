# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py factory-method
# Dominio: Counter-Strike; paquete de práctica factory-method.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .reportes import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py factory-method
# Salida esperada:
#   Texto: CT=7
#   CSV: equipo,rondas;CT,7
# CodeTrace | FIN EJEMPLO DE CONSOLA
