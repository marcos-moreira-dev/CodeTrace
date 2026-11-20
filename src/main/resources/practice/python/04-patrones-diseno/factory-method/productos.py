# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Compilar: python 04-patrones-diseno/factory-method/main.py
from typing import Protocol

class Exportador(Protocol):
    def exportar(self, texto: str) -> str: ...

class ExportadorTexto:
    def exportar(self, texto: str) -> str:
        return f"TEXTO: {texto}"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\factory-method\main.py
# Nota: productos.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   TEXTO: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
