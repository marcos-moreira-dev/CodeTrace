# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('pila.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Pila LIFO didáctica con list de Python.

append/pop al final cuestan O(1) amortizado. En producción
una list es suficiente para una pila: recrear nodos manuales
sería oportuno solo para estudiar las referencias internas.
"""
class Pila:
    def __init__(self) -> None:
        self.datos: list[int] = []

    def apilar(self, valor: int) -> None:
        self.datos.append(valor)

    def desapilar(self) -> int:
        if not self.datos:
            raise IndexError("Pila vacía")
        return self.datos.pop()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\02-estructuras-datos\pila\main.py
# Nota: pila.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   LIFO y pila vacía verificados
# CodeTrace | FIN EJEMPLO DE CONSOLA
