# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Compilar: python 04-patrones-diseno/factory-method/main.py
from abc import ABC, abstractmethod
from productos import Exportador, ExportadorTexto

class CreadorExportadores(ABC):
    @abstractmethod
    def crear(self) -> Exportador: ...

    def preparar(self, texto: str) -> str:
        return self.crear().exportar(texto)

class CreadorTexto(CreadorExportadores):
    def crear(self) -> Exportador:
        return ExportadorTexto()
