# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\01-fundamentos\protocolos-y-genericos\protocolos_y_genericos.py"
# Ejecutar: python -B ".\01-fundamentos\protocolos-y-genericos\protocolos_y_genericos.py"

"""Cliente de typing.Protocol, contratos estructurales y duck typing.

Una función que recibe objetos con un método describir no necesita
conocer su clase concreta. Protocol comunica ese contrato a los
comprobadores estáticos; Python lo aplica de forma estructural sin
exigir herencia explícita.
"""
from typing import Protocol, TypeVar


class Descriptible(Protocol):
    def describir(self) -> str: ...


T = TypeVar("T", bound=Descriptible)


class Sensor:
    def __init__(self, codigo: str) -> None:
        self.codigo = codigo

    def describir(self) -> str:
        return f"Sensor {self.codigo}"


def presentar(articulo: T) -> T:
    print(articulo.describir())
    return articulo


def main() -> None:
    presentar(Sensor("LM35"))


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\01-fundamentos\protocolos-y-genericos\protocolos_y_genericos.py
# Salida esperada:
#   Sensor LM35
# CodeTrace | FIN EJEMPLO DE CONSOLA
