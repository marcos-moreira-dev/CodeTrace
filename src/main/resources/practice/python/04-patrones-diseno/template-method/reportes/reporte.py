# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('reporte.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Template Method: orden fijo de pasos, subclases varían el formato.

ABC y abstractmethod expresan contratos nominales. Un @final de typing
es una sugerencia para herramientas estáticas, no seguridad dinámica:
Python no prohíbe una sobrescritura en tiempo de ejecución.
"""
from abc import ABC, abstractmethod
from typing import final

class ProcesoReporte(ABC):
    @final
    def ejecutar(self, titulo: str, total: int) -> str:
        if not titulo or total<0: raise ValueError("Reporte")
        return self.encabezado(titulo)+self.detalle(total)+self.pie()

    @abstractmethod
    def encabezado(self, titulo: str) -> str: ...

    @abstractmethod
    def detalle(self, total: int) -> str: ...

    def pie(self) -> str: return "\n"

class ReporteTexto(ProcesoReporte):
    def encabezado(self, titulo: str) -> str: return f"== {titulo} ==\n"
    def detalle(self, total: int) -> str: return f"Total: {total}"

class ReporteCsv(ProcesoReporte):
    def encabezado(self, titulo: str) -> str: return "titulo,total\n"
    def detalle(self, total: int) -> str: return f"ventas,{total}"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\template-method\reportes\main.py
# Nota: reporte.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   == ventas ==
#   Total: 15
#   titulo,total
#   ventas,15
# CodeTrace | FIN EJEMPLO DE CONSOLA
