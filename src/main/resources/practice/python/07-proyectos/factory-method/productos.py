# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Compilar: python 07-proyectos/factory-method/main.py
from typing import Protocol

class Exportador(Protocol):
    def exportar(self, texto: str) -> str: ...

class ExportadorTexto:
    def exportar(self, texto: str) -> str:
        return f"TEXTO: {texto}"
