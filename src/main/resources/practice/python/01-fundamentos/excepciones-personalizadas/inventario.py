# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('inventario.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Excepción de dominio y control de invariantes de inventario.

Python no tiene checked exceptions del compilador. Una excepción
derivada puede llevar atributos de negocio y preservar la causa;
no todo fallo merece un tipo propio. Aquí el stock nunca es negativo.
"""
class StockInsuficienteError(Exception):
    def __init__(self, solicitadas: int, disponibles: int) -> None:
        self.solicitadas = solicitadas
        self.disponibles = disponibles
        super().__init__(f"Solicitadas={solicitadas}, disponibles={disponibles}")


class Inventario:
    def __init__(self, cantidad: int) -> None:
        if cantidad < 0:
            raise ValueError("Stock negativo")
        self._cantidad = cantidad

    @property
    def disponibles(self) -> int:
        return self._cantidad

    def despachar(self, cantidad: int) -> None:
        if cantidad <= 0:
            raise ValueError("Cantidad")
        if cantidad > self._cantidad:
            raise StockInsuficienteError(cantidad, self._cantidad)
        self._cantidad -= cantidad
