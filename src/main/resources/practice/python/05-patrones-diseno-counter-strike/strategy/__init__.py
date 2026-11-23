# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py strategy
# Dominio: Counter-Strike; paquete de práctica strategy.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .economia import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py strategy
# Salida esperada:
#   Eco: guardar 1500 créditos
#   Equipo: asignar 1500 créditos virtuales
# CodeTrace | FIN EJEMPLO DE CONSOLA
