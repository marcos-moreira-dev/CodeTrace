# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py chain-of-responsibility
# Dominio: Counter-Strike; paquete de práctica chain-of-responsibility.
# El archivo delimita un paquete local: evita confundirlo con módulos externos
# homónimos instalados en el entorno. Exponemos solo su demostración.
from .cadena import demo

__all__ = ["demo"]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py chain-of-responsibility
# Salida esperada:
#   Rescate: rehén virtual liberado
#   Reconocimiento: rival marcado en el minimapa
#   Inventario: MP9 registrado como objeto virtual
#   Sin responsable: evento del servidor no reconocido
# CodeTrace | FIN EJEMPLO DE CONSOLA
