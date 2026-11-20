# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: Consulte main.py

"""Cola circular de capacidad fija.

head apunta al primer elemento; size representa los elementos presentes.
Se reutilizan posiciones del arreglo mediante aritmética modular.
Encolar y desencolar cuestan O(1) y no requieren desplazar elementos.
"""


class ColaCircular:
    def __init__(self, capacidad: int) -> None:
        if capacidad <= 0:
            raise ValueError("capacidad positiva requerida")
        self._datos: list[str | None] = [None] * capacidad
        self._cabeza = 0
        self._cantidad = 0

    def encolar(self, valor: str) -> None:
        if self._cantidad == len(self._datos):
            raise OverflowError("cola llena")
        indice = (self._cabeza + self._cantidad) % len(self._datos)
        self._datos[indice] = valor
        self._cantidad += 1

    def desencolar(self) -> str:
        if not self._cantidad:
            raise IndexError("cola vacía")
        valor = self._datos[self._cabeza]
        self._datos[self._cabeza] = None
        self._cabeza = (self._cabeza + 1) % len(self._datos)
        self._cantidad -= 1
        assert valor is not None
        return valor

    def __len__(self) -> int:
        return self._cantidad
